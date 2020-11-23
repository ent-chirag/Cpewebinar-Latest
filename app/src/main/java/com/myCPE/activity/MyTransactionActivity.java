package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.TransactionAdapter;
import com.myCPE.databinding.ActivityMytransactionNewBinding;
import com.myCPE.model.payment_transcation.Model_Transcation;
import com.myCPE.model.payment_transcation.TransactionItem;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyTransactionActivity extends AppCompatActivity {

    ActivityMytransactionNewBinding binding;
    TransactionAdapter adapter;
    ProgressDialog progressDialog;
    private APIService mAPIService;
    private static final String TAG = MyTransactionActivity.class.getName();
    private List<TransactionItem> mlist_paymentt_ranscation = new ArrayList<TransactionItem>();
    public Context context;
    private boolean loading = true;
    public boolean islast = false;
    public int start = 0, limit = 10;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mytransaction_new);
        context = MyTransactionActivity.this;
        mAPIService = ApiUtilsNew.getAPIService();

//        Constant.setLightStatusBar(MyTransactionActivity.this);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.myTranscationlist.setLayoutManager(linearLayoutManager);
        //binding.myTranscationlist.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.myTranscationlist.setItemAnimator(new DefaultItemAnimator());


//        binding.ivback.setOnClickListener(new View.OnClickListener() {
        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetMyTranscation(start, limit);
        } else {
            Snackbar.make(binding.myTranscationlist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

        binding.myTranscationlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.unregister(context);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void loadNextPage() {

        if (Constant.isNetworkAvailable(context)) {
            GetMyTranscation(start, limit);
        } else {
            Snackbar.make(binding.myTranscationlist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void refreshItems() {

        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        start = 0;
        limit = 10;
        loading = true;

        if (Constant.isNetworkAvailable(context)) {
            GetMyTranscation(start, limit);
        } else {
            Snackbar.make(binding.myTranscationlist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void GetMyTranscation(final int start, final int limit) {

        mAPIService.GetMyTranscation(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                start, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Model_Transcation>() {
                    @Override
                    public void onCompleted() {
                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        } else if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                        loading = true;

                        if (start == 0 && limit == 10) {
                            if (mlist_paymentt_ranscation.size() > 0) {
                                adapter = new TransactionAdapter(MyTransactionActivity.this, mlist_paymentt_ranscation);
                                binding.myTranscationlist.setAdapter(adapter);

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
//                            Snackbar.make(binding.ivback, message, Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(binding.relImgBack, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Model_Transcation model_transcation) {

                        if (model_transcation.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }


                            islast = model_transcation.getPayload().isIsLast();


                            if (start == 0 && limit == 10) {
                                if (mlist_paymentt_ranscation.size() > 0) {
                                    mlist_paymentt_ranscation.clear();
                                }
                            }


                            if (start == 0 && limit == 10) {
                                mlist_paymentt_ranscation = model_transcation.getPayload().getTransaction();

                            } else {

                                if (mlist_paymentt_ranscation.size() > 20) {
                                    mlist_paymentt_ranscation.remove(mlist_paymentt_ranscation.size() - 1);
                                }


                                List<TransactionItem> webinaritems = model_transcation.getPayload().getTransaction();
                                adapter.addAll(webinaritems);


                            }


                            if (mlist_paymentt_ranscation.size() > 0) {
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
//                            Snackbar.make(binding.ivback, model_transcation.getMessage(), Snackbar.LENGTH_SHORT).show();
                            Snackbar.make(binding.relImgBack, model_transcation.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });


    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.myTranscationlist.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.myTranscationlist.getAdapter().getItemCount() - 1;

        return (pos >= numItems);
    }
}
