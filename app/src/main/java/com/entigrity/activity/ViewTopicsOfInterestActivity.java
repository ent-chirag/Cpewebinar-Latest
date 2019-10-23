package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.adapter.RecycleviewSectionFavoriteTestAdapter;
import com.entigrity.adapter.RecycleviewSectionViewProfileTestAdapter;
import com.entigrity.databinding.ActivityViewTopicsofinterestBinding;
import com.entigrity.model.view_interest_favorite.ViewTopicsFavorite;
import com.entigrity.model.view_topics_of_interest.TopicOfInterestsItem;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.view.SimpleDividerItemDecoration;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ViewTopicsOfInterestActivity extends AppCompatActivity {
    public Context context;
    private APIService mAPIService;
    public ActivityViewTopicsofinterestBinding binding;
    private static final String TAG = ViewTopicsOfInterestActivity.class.getName();
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    private ArrayList<TopicOfInterestsItem> topicsofinterestitem = new ArrayList<TopicOfInterestsItem>();
    private ArrayList<com.entigrity.model.view_interest_favorite.TopicOfInterestsItem> topicsofinterestitemfavorite = new ArrayList<com.entigrity.model.view_interest_favorite.TopicOfInterestsItem>();
    RecycleviewSectionViewProfileTestAdapter adapter;
    RecycleviewSectionFavoriteTestAdapter favoriteviewtopicsofinterestadapter;
    private String fromscreen = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topicsofinterest);
        mAPIService = ApiUtilsNew.getAPIService();
        context = ViewTopicsOfInterestActivity.this;


        Intent intent = getIntent();
        if (intent != null) {

            fromscreen = intent.getStringExtra(getResources().getString(R.string.str_get_key_screen));

            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.rvtopicsOfInterest.setLayoutManager(linearLayoutManager);
            binding.rvtopicsOfInterest.addItemDecoration(new SimpleDividerItemDecoration(context));


            if (fromscreen.equalsIgnoreCase(getResources().getString(R.string.from_favorite))) {
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetTopicsofInterest();
                } else {
                    Snackbar.make(binding.rvtopicsOfInterest, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            } else {
                topicsofinterestitem = intent.getParcelableArrayListExtra(getResources().getString(R.string.pass_view_topics_of_interest));
                if (topicsofinterestitem.size() > 0) {
                    adapter = new RecycleviewSectionViewProfileTestAdapter(context, topicsofinterestitem);
                    binding.rvtopicsOfInterest.setAdapter(adapter);
                }
            }


        }


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void GetTopicsofInterest() {

        mAPIService.GetViewTopicsOfInterestFavorite(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ViewTopicsFavorite>() {
                    @Override
                    public void onCompleted() {
                        if (topicsofinterestitemfavorite.size() > 0) {
                            favoriteviewtopicsofinterestadapter = new RecycleviewSectionFavoriteTestAdapter(context, topicsofinterestitemfavorite);
                            binding.rvtopicsOfInterest.setAdapter(favoriteviewtopicsofinterestadapter);
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
                            Snackbar.make(binding.rvtopicsOfInterest, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(ViewTopicsFavorite viewTopicsFavorite) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (viewTopicsFavorite.isSuccess()) {
                            if (viewTopicsFavorite.getPayload().getTopicOfInterests().size() > 0) {

                                for (int i = 0; i < viewTopicsFavorite.getPayload().getTopicOfInterests().size(); i++) {

                                    com.entigrity.model.view_interest_favorite.TopicOfInterestsItem topicOfInterestsItem = new
                                            com.entigrity.model.view_interest_favorite.TopicOfInterestsItem();
                                    topicOfInterestsItem.setName(viewTopicsFavorite.getPayload().getTopicOfInterests().get(i).getName());
                                    topicOfInterestsItem.setId(viewTopicsFavorite.getPayload().getTopicOfInterests().get(i).getId());
                                    if (viewTopicsFavorite.getPayload().getTopicOfInterests().get(i).getTags() != null) {
                                        topicOfInterestsItem.setTags(viewTopicsFavorite.getPayload().getTopicOfInterests().get(i).getTags());
                                    }

                                    topicsofinterestitemfavorite.add(topicOfInterestsItem);
                                }


                            }


                        } else {
                            Snackbar.make(binding.rvtopicsOfInterest, viewTopicsFavorite.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }


}
