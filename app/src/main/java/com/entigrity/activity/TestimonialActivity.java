package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.adapter.TestimonialAdapter;
import com.entigrity.databinding.ActivityTestimonialBinding;
import com.entigrity.model.testimonial.Model_Testimonial;
import com.entigrity.model.testimonial.WebinarTestimonialItem;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.view.SimpleDividerItemDecoration;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestimonialActivity extends AppCompatActivity {

    ActivityTestimonialBinding binding;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    public Context context;
    private static final String TAG = TestimonialActivity.class.getName();
    LinearLayoutManager linearLayoutManager;
    private TestimonialAdapter adapter;
    private List<WebinarTestimonialItem> mListtestimonial = new ArrayList<WebinarTestimonialItem>();
    private boolean loading = true;
    public boolean islast = false;
    public int webinarid = 0;
    public String webinar_type = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_testimonial);
        context = TestimonialActivity.this;
        mAPIService = ApiUtilsNew.getAPIService();

        Intent intent = getIntent();
        if (intent != null) {
            webinarid = intent.getIntExtra(getResources().getString(R.string.pass_webinar_id), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
        }

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvTestimonialList.setLayoutManager(linearLayoutManager);
        binding.rvTestimonialList.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.rvTestimonialList.setItemAnimator(new DefaultItemAnimator());


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebinarDetailsActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinarid);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();
            }
        });


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetTestimonialList();
        } else {
            Snackbar.make(binding.rvTestimonialList, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });


       /* binding.rvTestimonialList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (loading) {
                    if (!islast) {
                        if (isLastVisible()) {
                            loading = false;
                            loadNextPage();
                        }
                    }
                }


            }
        });*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(context, WebinarDetailsActivity.class);
        i.putExtra(getResources().getString(R.string.pass_webinar_id), webinarid);
        i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
        startActivity(i);
        finish();


    }

    private void refreshItems() {

        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {

        loading = true;

        if (Constant.isNetworkAvailable(context)) {
            GetTestimonialList();
        } else {
            Snackbar.make(binding.rvTestimonialList, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }


    private void GetTestimonialList() {

        mAPIService.GetTestimonial(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                webinarid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Model_Testimonial>() {
                    @Override
                    public void onCompleted() {


                        if (binding.progressBar.getVisibility() == View.VISIBLE) {
                            binding.progressBar.setVisibility(View.GONE);
                        }


                        loading = true;
                        if (mListtestimonial.size() > 0) {
                            adapter = new TestimonialAdapter(context, mListtestimonial);
                            binding.rvTestimonialList.setAdapter(adapter);
                        }


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
                    public void onNext(Model_Testimonial model_testimonial) {

                        if (model_testimonial.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            } else {
                                if (binding.swipeRefreshLayout.isRefreshing()) {
                                    binding.swipeRefreshLayout.setRefreshing(false);
                                }
                            }


                            islast = model_testimonial.getPayload().isIsLast();

                            mListtestimonial = model_testimonial.getPayload().getWebinarTestimonial();


                           /* if (start == 0 && limit == 10) {


                            } else {
                                if (mListtestimonial.size() > 20) {
                                    mListtestimonial.remove(mListtestimonial.size() - 1);
                                }


                                List<WebinarTestimonialItem> webinaritems = model_testimonial.getPayload().getWebinarTestimonial();
                                adapter.addAll(webinaritems);
                            }
*/

                            if (mListtestimonial.size() > 0) {
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
                            Snackbar.make(binding.ivback, model_testimonial.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });


    }

    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.rvTestimonialList.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = binding.rvTestimonialList.getAdapter().getItemCount() - 1;
        return (pos >= numItems);
    }

}
