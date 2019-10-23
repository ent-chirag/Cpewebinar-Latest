package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.databinding.ActivityEvolutionFormBinding;
import com.entigrity.model.evaluation_form.Evalutionformmodel;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityEvolutionForm extends AppCompatActivity {

    ActivityEvolutionFormBinding binding;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    public Context context;
    public int webinar_id = 0;
    public String webinar_type = "";
    public String Screen = "";
    private String validate = "";
    private static final String TAG = ActivityEvolutionForm.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_evolution_form);

        context = ActivityEvolutionForm.this;
        mAPIService = ApiUtilsNew.getAPIService();

        Intent intent = getIntent();
        if (intent != null) {
            webinar_id = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
            Screen = intent.getStringExtra(getResources().getString(R.string.screen));
        }


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Screen.equalsIgnoreCase(getResources().getString(R.string.mywebinar))) {
                    Intent i = new Intent(ActivityEvolutionForm.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else if (Screen.equalsIgnoreCase(getResources().getString(R.string.favroitescreen))) {
                    Intent i = new Intent(ActivityEvolutionForm.this, ActivityFavorite.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(ActivityEvolutionForm.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }


            }
        });

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetEvaluationForm();
        } else {
            Snackbar.make(binding.ivback, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Screen.equalsIgnoreCase(getResources().getString(R.string.mywebinar))) {
            Intent i = new Intent(ActivityEvolutionForm.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        } else if (Screen.equalsIgnoreCase(getResources().getString(R.string.favroitescreen))) {
            Intent i = new Intent(ActivityEvolutionForm.this, ActivityFavorite.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(ActivityEvolutionForm.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
            i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }

    private void GetEvaluationForm() {

        mAPIService.EvaluationForm(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Evalutionformmodel>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);
                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.ivback, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Evalutionformmodel evalutionformmodel) {

                        if (evalutionformmodel.isSuccess() == true) {

                            binding.webview.setWebViewClient(new CustomWebViewClient());
                            WebSettings webSetting = binding.webview.getSettings();
                            webSetting.setJavaScriptEnabled(true);
                            webSetting.setDisplayZoomControls(true);
                            binding.webview.loadUrl(evalutionformmodel.getPayload().getLink());


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivback, evalutionformmodel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }

                });
    }

    public class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            System.out.println("url :-" + url);
            validate = getLastBitFromUrl(url);
            System.out.println("url :-" + validate);

            if (validate.equalsIgnoreCase("success")) {

                if (Screen.equalsIgnoreCase(getResources().getString(R.string.mywebinar))) {
                    Intent i = new Intent(ActivityEvolutionForm.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else if (Screen.equalsIgnoreCase(getResources().getString(R.string.favroitescreen))) {
                    Intent i = new Intent(ActivityEvolutionForm.this, ActivityFavorite.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(ActivityEvolutionForm.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }

}
