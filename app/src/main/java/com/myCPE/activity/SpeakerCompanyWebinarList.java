package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
//    private String is_from = "";
//    public static String is_from = "";

    private int upcoming_count = 0;
    private int past_count = 0;

    private boolean loading = true;

    private int apiCallCount = 0;

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

        apiCallCount = 0;

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
        Constant.is_from = intent.getStringExtra("is_from");
        upcoming_count = intent.getIntExtra("upcoming_count", 0);
        past_count = intent.getIntExtra("past_count", 0);

        if (Constant.is_from.equalsIgnoreCase("company")) {
            speaker_id = "";
        } else {
            company_id = "";
        }

        if (webinar_type.equalsIgnoreCase("live")) {
            binding.linFilter.setVisibility(View.VISIBLE);
            if (upcoming_count == 0) {
                binding.btnUpcoming.setVisibility(View.GONE);
                binding.btnPast.setBackgroundResource(R.drawable.chipsetview_filter_home);
                binding.btnPast.setTextColor(getResources().getColor(R.color.White));
                is_upcoming = 0;
                Constant.isUpcomingListing = false;
            }

            if (past_count == 0) {
                binding.btnPast.setVisibility(View.GONE);
                binding.btnUpcoming.setBackgroundResource(R.drawable.chipsetview_filter_home);
                binding.btnUpcoming.setTextColor(getResources().getColor(R.color.White));
                is_upcoming = 1;
                Constant.isUpcomingListing = true;
            }
        } else {
            binding.linFilter.setVisibility(View.GONE);
        }

        Log.e("*+*+*", "Company_id : " + company_id);
        Log.e("*+*+*", "Speaker_id : " + speaker_id);
        Log.e("*+*+*", "Webinar_type : " + webinar_type);

        callApiContent();

        binding.swipeRefreshLayouthome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApiContent();
            }
        });

        binding.rvhome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (loading) {
                    if (!islast) {
                        if (isLastVisible()) {
                            loading = false;
                            start = start + 10;
                            limit = 10;
                            loadNextPage();
                        }
                    }
                }
            }
        });

        /*binding.rvhome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });*/

    }

    private void callApiContent() {

        if (Constant.isNetworkAvailable(context)) {

            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.rvhome.setLayoutManager(linearLayoutManager);
            binding.rvhome.addItemDecoration(new SimpleDividerItemDecoration(context));
            binding.rvhome.setItemAnimator(new DefaultItemAnimator());
            binding.rvhome.setHasFixedSize(true);

            if(apiCallCount == 0) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            }
            getListingData();
        } else {
            Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void getListingData() {

        apiCallCount++;

//        mAPIService.GetHomeWebinarListNew(getResources().getString(R.string.accept),
        mAPIService.GetCompanySpeakerWebinarListNew(getResources().getString(R.string.accept),
                getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), start, limit, company_id, speaker_id,
                webinar_type, is_upcoming).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Webinar_Home_New>() {
                    @Override
                    public void onCompleted() {

                        Log.e("*+*+*", "list_onCompleted is called");

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

                        if (binding.swipeRefreshLayouthome.isRefreshing()) {
                            binding.swipeRefreshLayouthome.setRefreshing(false);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {

                        Log.e("*+*+*", "list_onError is called");

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

                        if (binding.swipeRefreshLayouthome.isRefreshing()) {
                            binding.swipeRefreshLayouthome.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onNext(Webinar_Home_New webinar_home_new) {

                        Log.e("*+*+*", "list_onNext is called");

                        if (binding.swipeRefreshLayouthome.isRefreshing()) {
                            binding.swipeRefreshLayouthome.setRefreshing(false);
                        }

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

                if (past_count != 0) {
                    binding.btnPast.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnPast.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                    binding.btnUpcoming.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnUpcoming.setTextColor(getResources().getColor(R.color.White));

                    is_upcoming = 1;
                    Constant.isUpcomingListing = true;
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        getListingData();
                    } else {
                        Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.btn_past:

                if (upcoming_count != 0) {
                    binding.btnUpcoming.setBackgroundResource(R.drawable.chipsetview_filter_home_unselected);
                    binding.btnUpcoming.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                    binding.btnPast.setBackgroundResource(R.drawable.chipsetview_filter_home);
                    binding.btnPast.setTextColor(getResources().getColor(R.color.White));

                    is_upcoming = 0;
                    Constant.isUpcomingListing = false;
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        Constant.isFromCSPast = true;
                        getListingData();
                    } else {
                        Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    private void loadNextPage() {
        if (Constant.isNetworkAvailable(context)) {
            binding.progressBar.setVisibility(View.VISIBLE);
//            GetHomeListNew(Constant.webinartype, SubjectAreaFilter, Constant.search, Constant.price_filter, Constant.date_filter, Constant.is_cpd, "", start, limit);
            getListingData();
        } else {
            Snackbar.make(binding.rvhome, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvhome.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.rvhome.getAdapter().getItemCount() - 1;

        return (pos >= numItems);
    }
}
