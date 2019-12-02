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
import com.myCPE.adapter.MyFavoriteAdapter;
import com.myCPE.databinding.ActivityFavoritteBinding;
import com.myCPE.model.myfavorites.ModelFavorites;
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

public class ActivityFavorite extends AppCompatActivity {

    ActivityFavoritteBinding binding;
    public Context context;
    LinearLayoutManager linearLayoutManager;
    private APIService mAPIService_new;
    private static final String TAG = ActivityFavorite.class.getName();
    ProgressDialog progressDialog;
    private List<com.myCPE.model.myfavorites.WebinarItem> arrmyfavorites = new ArrayList<>();
    private String webinartype = "live";
    private String topicsofinterest = "";
    private boolean loading = true;
    public int start = 0, limit = 10;
    public boolean islast = false;
    MyFavoriteAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favoritte);
        context = ActivityFavorite.this;
        mAPIService_new = ApiUtilsNew.getAPIService();

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvFavoritelist.setLayoutManager(linearLayoutManager);
        binding.rvFavoritelist.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.rvFavoritelist.setItemAnimator(new DefaultItemAnimator());
        binding.rvFavoritelist.setHasFixedSize(true);


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetMyFavorites(webinartype, topicsofinterest, start, limit);
        } else {
            Snackbar.make(binding.rvFavoritelist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

        }

        binding.rvFavoritelist.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isdataupdate = true;
                finish();
            }
        });
        binding.swipeRefreshLayouthome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constant.isdataupdate = true;
        finish();
    }

    public void refreshItems() {
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        start = 0;
        limit = 10;
        loading = true;

        if (Constant.isNetworkAvailable(context)) {
            GetMyFavorites(webinartype, topicsofinterest, start, limit);
        } else {
            Snackbar.make(binding.rvFavoritelist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvFavoritelist.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.rvFavoritelist.getAdapter().getItemCount() - 1;
        return (pos >= numItems);
    }


    private void loadNextPage() {
        if (Constant.isNetworkAvailable(context)) {
            binding.progressBar.setVisibility(View.VISIBLE);
            GetMyFavorites(webinartype, topicsofinterest, start, limit);
        } else {
            Snackbar.make(binding.rvFavoritelist, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void GetMyFavorites(final String webinartype, final String topicsofinterest, final int start, final int limit) {

        mAPIService_new.GetMyFavorites(getResources().getString(R.string.accept),
                getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), start, limit, webinartype, topicsofinterest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelFavorites>() {
                    @Override
                    public void onCompleted() {

                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        }


                        loading = true;

                        if (start == 0 && limit == 10) {
                            if (arrmyfavorites.size() > 0) {
                                adapter = new MyFavoriteAdapter(context, arrmyfavorites);
                                binding.rvFavoritelist.setAdapter(adapter);

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
                            Snackbar.make(binding.rvFavoritelist, message, Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onNext(ModelFavorites modelFavorites) {

                        if (modelFavorites.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayouthome.isRefreshing()) {
                                    binding.swipeRefreshLayouthome.setRefreshing(false);
                                }
                            }


                            islast = modelFavorites.getPayload().isIsLast();


                            if (start == 0 && limit == 10) {
                                if (arrmyfavorites.size() > 0) {
                                    arrmyfavorites.clear();
                                }

                            }


                            if (start == 0 && limit == 10) {
                                arrmyfavorites = modelFavorites.getPayload().getWebinar();


                            } else {


                                if (arrmyfavorites.size() > 20) {
                                    arrmyfavorites.remove(arrmyfavorites.size() - 1);
                                }


                                List<com.myCPE.model.myfavorites.WebinarItem> webinaritems = modelFavorites.getPayload().getWebinar();
                                adapter.addAll(webinaritems);


                            }


                            if (arrmyfavorites.size() > 0) {
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
                            Snackbar.make(binding.rvFavoritelist, modelFavorites.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }


                });


    }
}
