package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.NotificationAdapter;
import com.myCPE.databinding.ActivityNotificationBinding;
import com.myCPE.model.notification.NotificationListItem;
import com.myCPE.model.notification.NotificationModel;
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

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    public Context context;
    private static final String TAG = NotificationActivity.class.getName();
    LinearLayoutManager linearLayoutManager;
    private NotificationAdapter adapter;
    private List<NotificationListItem> mListnotificationlist = new ArrayList<NotificationListItem>();
    private boolean loading = true;
    public boolean islast = false;
    public int start = 0, limit = 10;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        context = NotificationActivity.this;
        mAPIService = ApiUtilsNew.getAPIService();
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvNotificationlist.setLayoutManager(linearLayoutManager);
        binding.rvNotificationlist.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.rvNotificationlist.setItemAnimator(new DefaultItemAnimator());


//        binding.ivback.setOnClickListener(new View.OnClickListener() {
        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetNotificationList(start, limit);
        } else {
            Snackbar.make(binding.rvNotificationlist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


        binding.rvNotificationlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void refreshItems() {

        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {

        start = 0;
        limit = 10;
        loading = true;

        if (Constant.isNetworkAvailable(context)) {
            GetNotificationList(start, limit);
        } else {
            Snackbar.make(binding.rvNotificationlist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }


    private void loadNextPage() {
        if (Constant.isNetworkAvailable(context)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            GetNotificationList(start, limit);
        } else {
            Snackbar.make(binding.rvNotificationlist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }


    private void GetNotificationList(final int start, final int limit) {

        mAPIService.GetNotificationModel(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                start, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NotificationModel>() {
                    @Override
                    public void onCompleted() {
                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        } else if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                        loading = true;

                        if (start == 0 && limit == 10) {
                            if (mListnotificationlist.size() > 0) {
                                adapter = new NotificationAdapter(context, mListnotificationlist);
                                binding.rvNotificationlist.setAdapter(adapter);
                            }
                        } else {
                            adapter.addLoadingFooter();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

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
                    public void onNext(NotificationModel notificationModel) {

                        if (notificationModel.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }


                            islast = notificationModel.getPayload().isIsLast();


                            if (start == 0 && limit == 10) {
                                if (mListnotificationlist.size() > 0) {
                                    mListnotificationlist.clear();
                                }
                            }


                            if (start == 0 && limit == 10) {
                                mListnotificationlist = notificationModel.getPayload().getNotificationList();

                            } else {

                                if (mListnotificationlist.size() > 20) {
                                    mListnotificationlist.remove(mListnotificationlist.size() - 1);
                                }


                                List<NotificationListItem> webinaritems = notificationModel.getPayload().getNotificationList();
                                adapter.addAll(webinaritems);


                            }


                            if (mListnotificationlist.size() > 0) {
                                binding.swipeRefreshLayout.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                                binding.swipeRefreshLayout.setVisibility(View.GONE);
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                            Snackbar.make(binding.relImgBack, notificationModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });

    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvNotificationlist.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.rvNotificationlist.getAdapter().getItemCount() - 1;

        return (pos >= numItems);
    }
}
