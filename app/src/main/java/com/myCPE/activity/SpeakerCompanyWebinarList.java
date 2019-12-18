package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.CompanySpeakerWebinarListAdapter;
import com.myCPE.adapter.HomeALLAdapter;
import com.myCPE.databinding.ActivitySpeakerCompanyWebinarListBinding;
import com.myCPE.databinding.ActivitySpeakerProfileBinding;
import com.myCPE.model.homewebinarnew.WebinarItem;
import com.myCPE.model.homewebinarnew.Webinar_Home_New;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.myCPE.utility.Constant.arraylistselectedvalue;

public class SpeakerCompanyWebinarList extends AppCompatActivity implements View.OnClickListener {

    ActivitySpeakerCompanyWebinarListBinding binding;
//    ActivitySpeakerProfileBinding binding;
    public Context context;
    private APIService mAPIService;
    ProgressDialog progressDialog;

    public int start = 0, limit = 10;

    private String company_id = "";
    private String speaker_id = "";
    private String webinar_type = "";
    private int is_upcoming = 1;
    private String is_from = "";

    private boolean loading = true;

    LinearLayoutManager linearLayoutManager;

    private List<com.myCPE.model.homewebinarnew.WebinarItem> arrHomelistnew = new ArrayList<WebinarItem>();
//    HomeALLAdapter adapter;
    CompanySpeakerWebinarListAdapter adapter;
    public boolean islast = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speaker_company_webinar_list);
//        setContentView(R.layout.activity_speaker_company_webinar_list);

        mAPIService = ApiUtilsNew.getAPIService();
        context = SpeakerCompanyWebinarList.this;

        binding.btnPast.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
        binding.btnPast.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
        binding.btnUpcoming.setBackgroundResource(R.drawable.chipsetview_filter_home);
        binding.btnUpcoming.setTextColor(getResources().getColor(R.color.White));

        binding.relImgBack.setOnClickListener(this);
        binding.btnUpcoming.setOnClickListener(this);
        binding.btnPast.setOnClickListener(this);

        Intent intent = getIntent();
        company_id = intent.getStringExtra("company_id");
        speaker_id = intent.getStringExtra("speaker_id");
        webinar_type = intent.getStringExtra("webinar_type");
        is_from = intent.getStringExtra("is_from");

        if(is_from.equalsIgnoreCase("company")) {
            speaker_id = "";
        } else {
            company_id = "";
        }

        if(webinar_type.equalsIgnoreCase("live")) {
            binding.linFilter.setVisibility(View.VISIBLE);
        } else {
            binding.linFilter.setVisibility(View.GONE);
        }

        Log.e("*+*+*","Company_id : "+company_id);
        Log.e("*+*+*","Speaker_id : "+speaker_id);
        Log.e("*+*+*","Webinar_type : "+webinar_type);

        if (Constant.isNetworkAvailable(context)) {

            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.rvhome.setLayoutManager(linearLayoutManager);
            binding.rvhome.addItemDecoration(new SimpleDividerItemDecoration(context));
            binding.rvhome.setItemAnimator(new DefaultItemAnimator());
            binding.rvhome.setHasFixedSize(true);

            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            getListingData();
        } else {
            Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void getListingData() {

//        mAPIService.GetHomeWebinarListNew(getResources().getString(R.string.accept),
        mAPIService.GetCompanySpeakerWebinarListNew(getResources().getString(R.string.accept),
                getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), start, limit, company_id, speaker_id,
                webinar_type, is_upcoming).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Webinar_Home_New>() {
                    @Override
                    public void onCompleted() {

                        Log.e("*+*+*","list_onCompleted is called");

                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        } else if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        loading = true;

                        if (start == 0 && limit == 10) {
                            if (arrHomelistnew.size() > 0) {
//                                adapter = new HomeALLAdapter(context, arrHomelistnew);
                                adapter = new CompanySpeakerWebinarListAdapter(context, arrHomelistnew);
                                binding.rvhome.setAdapter(adapter);

                            }
                        } else {
                            adapter.addLoadingFooter();
                        }
                    }


                    @Override
                    public void onError(Throwable e) {

                        Log.e("*+*+*","list_onError is called");

                        if (start == 0 && limit == 10) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        } else {
                            if (binding.progressBar.getVisibility() == View.VISIBLE) {
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        }

                        String message = Constant.GetReturnResponse(context, e);

                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.relImgBack, message, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(Webinar_Home_New webinar_home_new) {

                        Log.e("*+*+*","list_onNext is called");

                        if (webinar_home_new.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthome.isRefreshing()) {
                                    binding.swipeRefreshLayouthome.setRefreshing(false);
                                }
                            }

                            arraylistselectedvalue.clear();

                            islast = webinar_home_new.getPayload().isIsLast();

                            if (start == 0 && limit == 10) {
                                if (arrHomelistnew.size() > 0) {
                                    arrHomelistnew.clear();
                                }
                            }

                            if (start == 0 && limit == 10) {
                                arrHomelistnew = webinar_home_new.getPayload().getWebinar();

                            } else {

                                if (arrHomelistnew.size() > 20) {
                                    arrHomelistnew.remove(arrHomelistnew.size() - 1);
                                }

                                List<WebinarItem> webinaritems = webinar_home_new.getPayload().getWebinar();
                                adapter.addAll(webinaritems);
                            }

                            if (arrHomelistnew.size() > 0) {
                                binding.swipeRefreshLayouthome.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.swipeRefreshLayouthome.setVisibility(View.GONE);
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthome.isRefreshing()) {
                                    binding.swipeRefreshLayouthome.setRefreshing(false);
                                }
                            }
                            Snackbar.make(binding.rvhome, webinar_home_new.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relImgBack:
                finish();
                break;

            case R.id.btn_upcoming:

                binding.btnPast.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                binding.btnPast.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                binding.btnUpcoming.setBackgroundResource(R.drawable.chipsetview_filter_home);
                binding.btnUpcoming.setTextColor(getResources().getColor(R.color.White));

                is_upcoming = 1;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    getListingData();
                } else {
                    Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }

                break;

            case R.id.btn_past:

                binding.btnUpcoming.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                binding.btnUpcoming.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                binding.btnPast.setBackgroundResource(R.drawable.chipsetview_filter_home);
                binding.btnPast.setTextColor(getResources().getColor(R.color.White));

                is_upcoming = 0;
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    getListingData();
                } else {
                    Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
