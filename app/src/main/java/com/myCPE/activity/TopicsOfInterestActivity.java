package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.RecycleviewSectionTestAdapter;
import com.myCPE.databinding.ActivityTopicsofinterestBinding;
import com.myCPE.model.view_interest_favorite.ViewTopicsFavorite;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopicsOfInterestActivity extends AppCompatActivity {
    public Context context;
    ActivityTopicsofinterestBinding binding;
    private APIService mAPIService;
    private static final String TAG = TopicsOfInterestActivity.class.getName();
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;
    RecycleviewSectionTestAdapter adapter;
    private String fromscreen = "";
    private static TopicsOfInterestActivity instance;
    private ArrayList<com.myCPE.model.view_interest_favorite.TopicOfInterestsItem> topicsofinterestitemfavorite = new ArrayList<com.myCPE.model.view_interest_favorite.TopicOfInterestsItem>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_topicsofinterest);
        mAPIService = ApiUtilsNew.getAPIService();
        context = TopicsOfInterestActivity.this;
        instance = TopicsOfInterestActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            fromscreen = intent.getStringExtra(getResources().getString(R.string.str_get_key_screen_key));
        }

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvtopicsOfInterest.setLayoutManager(linearLayoutManager);
        binding.rvtopicsOfInterest.addItemDecoration(new SimpleDividerItemDecoration(context));

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetTopicsofInterest();
        } else {
            Snackbar.make(binding.tvSubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.isdataupdate = true;
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Constant.isdataupdate = true;
        finish();
    }

    public void GetTopicsofInterest() {
        mAPIService.GetTopicsofInterests(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ViewTopicsFavorite>() {
                    @Override
                    public void onCompleted() {
                        if (topicsofinterestitemfavorite.size() > 0) {
                            adapter = new RecycleviewSectionTestAdapter(context, topicsofinterestitemfavorite);
                            binding.rvtopicsOfInterest.setAdapter(adapter);
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
                            Snackbar.make(binding.tvSubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(ViewTopicsFavorite viewTopicsFavorite) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                        if (topicsofinterestitemfavorite.size() > 0) {
                            topicsofinterestitemfavorite.clear();
                        }


                        if (viewTopicsFavorite.isSuccess()) {
                            if (viewTopicsFavorite.getPayload().getTopicOfInterests().size() > 0) {

                                for (int i = 0; i < viewTopicsFavorite.getPayload().getTopicOfInterests().size(); i++) {

                                    com.myCPE.model.view_interest_favorite.TopicOfInterestsItem topicOfInterestsItem = new
                                            com.myCPE.model.view_interest_favorite.TopicOfInterestsItem();
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

    public static TopicsOfInterestActivity getInstance() {
        return instance;

    }


    public void ReCreate() {
        recreate();
    }


}
