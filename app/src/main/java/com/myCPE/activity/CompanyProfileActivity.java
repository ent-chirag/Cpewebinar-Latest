package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityCompanyProfileBinding;
import com.myCPE.model.company_details_new.CompanyDetailsModel;
import com.myCPE.model.getprivacypolicy.GetPrivacyPolicy;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CompanyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCompanyProfileBinding binding;
    public Context context;
    private APIService mAPIService;
    ProgressDialog progressDialog;

    private int company_id = 0;
    private int speaker_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_profile);
//        setContentView(R.layout.activity_company_profile);

        mAPIService = ApiUtilsNew.getAPIService();
        context = CompanyProfileActivity.this;

        Intent intent = getIntent();
        company_id = intent.getIntExtra("company_id", 0);
        speaker_id = intent.getIntExtra("speaker_id", 0);

        Log.e("*+*+*", "Company ID : " + company_id);
        Log.e("*+*+*", "Speaker ID : " + speaker_id);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetCompanyDetails();
        } else {
            Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void GetCompanyDetails() {
        mAPIService.GetCompanyDetailsNew(getResources().getString(R.string.accept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyDetailsModel>() {
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
                            Snackbar.make(binding.relImgBack, message, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(CompanyDetailsModel companyDetailsModel) {
                        if (companyDetailsModel.isSuccess()) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            String sampleResponse = companyDetailsModel.getPayload().getCompany().getCompanyName();
                            Log.e("*+*+*","Sample response : "+sampleResponse);
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.relImgBack, companyDetailsModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    /*@Override
                    public void onNext(GetPrivacyPolicy getPrivacyPolicy) {

                        if (getPrivacyPolicy.isSuccess() == true) {

                            binding.webview.setWebViewClient(new PrivacyPolicyActivity.CustomWebViewClient());
                            WebSettings webSetting = binding.webview.getSettings();
                            webSetting.setJavaScriptEnabled(true);
                            webSetting.setDisplayZoomControls(true);
                            binding.webview.loadUrl(getPrivacyPolicy.getPayload().getLink());

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivback, getPrivacyPolicy.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }*/
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relImgBack:

                finish();

                break;
        }
    }
}
