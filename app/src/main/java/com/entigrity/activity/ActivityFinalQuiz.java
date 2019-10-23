package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.adapter.FinalQuizAdapter;
import com.entigrity.databinding.ActivityFinalQuizBinding;
import com.entigrity.model.final_Quiz.FinalQuizQuestionsItem;
import com.entigrity.model.final_Quiz.Final_Quiz;
import com.entigrity.model.final_quiz_answer.FinalQuizAnswer;
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

import static com.entigrity.utility.Constant.arraylistselectedanswerfinal;
import static com.entigrity.utility.Constant.arraylistselectedquestionfinal;

public class ActivityFinalQuiz extends AppCompatActivity {

    ActivityFinalQuizBinding binding;

    public List<FinalQuizQuestionsItem> finalquestion = new ArrayList<>();
    public List<Boolean> arrayboolean = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    private static final String TAG = ActivityReviewQuestion.class.getName();
    public Context context;
    public int webinar_id = 0;
    public FinalQuizAdapter adapter;
    private static ActivityFinalQuiz instance;
    public String webinar_type = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_final_quiz);
        context = ActivityFinalQuiz.this;
        mAPIService = ApiUtilsNew.getAPIService();
        instance = ActivityFinalQuiz.this;

        Intent intent = getIntent();
        if (intent != null) {
            webinar_id = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
        }

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewFinalQuiz.setLayoutManager(linearLayoutManager);
        binding.recyclerviewFinalQuiz.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.recyclerviewFinalQuiz.setItemAnimator(new DefaultItemAnimator());

        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebinarDetailsActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();
            }
        });

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetFinalQuiz();
        } else {
            Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }


        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalquizquestion = android.text.TextUtils.join(",", arraylistselectedquestionfinal);
                System.out.println(finalquizquestion);

                String finalquizwanswer = android.text.TextUtils.join(",", arraylistselectedanswerfinal);
                System.out.println(finalquizwanswer);


                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetSubmitAnswer(finalquizquestion, finalquizwanswer);
                } else {
                    Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(context, WebinarDetailsActivity.class);
        i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
        i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
        startActivity(i);
        finish();
    }

    private void GetSubmitAnswer(String finalquizquestion, String finalanswer) {

        mAPIService.FinalQuizAnswer(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id
                , finalquizquestion, finalanswer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FinalQuizAnswer>() {
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
                    public void onNext(FinalQuizAnswer finalQuizAnswer) {

                        if (finalQuizAnswer.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            arraylistselectedquestionfinal.clear();
                            arraylistselectedanswerfinal.clear();

                            finish();


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivback, finalQuizAnswer.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                });


    }

    private void GetFinalQuiz() {
        arraylistselectedanswerfinal.clear();
        arraylistselectedquestionfinal.clear();

        mAPIService.FinalQuiz(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Final_Quiz>() {
                    @Override
                    public void onCompleted() {
                        if (finalquestion.size() > 0) {
                            adapter = new FinalQuizAdapter(context, finalquestion, arrayboolean);
                            binding.recyclerviewFinalQuiz.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);
                        if (Constant.status_code == 401) {
                            finish();
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.ivback, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Final_Quiz Final_Quiz) {

                        if (Final_Quiz.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            finalquestion = Final_Quiz.getPayload().getFinalQuizQuestions();


                            for (int i = 0; i < finalquestion.size(); i++) {
                                if (finalquestion.get(i).getA().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedanswerfinal.add("a");
                                    arraylistselectedquestionfinal.add(finalquestion.get(i).getId());
                                } else if (finalquestion.get(i).getB().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedanswerfinal.add("b");
                                    arraylistselectedquestionfinal.add(finalquestion.get(i).getId());
                                } else if (finalquestion.get(i).getC().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedanswerfinal.add("c");
                                    arraylistselectedquestionfinal.add(finalquestion.get(i).getId());
                                } else if (finalquestion.get(i).getD().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedanswerfinal.add("d");
                                    arraylistselectedquestionfinal.add(finalquestion.get(i).getId());
                                }

                            }


                            for (int i = 0; i < finalquestion.size(); i++) {
                                arrayboolean.add(false);
                            }


                            if (finalquestion.size() > 0) {
                                binding.recyclerviewFinalQuiz.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                                binding.recyclerviewFinalQuiz.setVisibility(View.GONE);
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivback, Final_Quiz.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });

    }

    public void SubmitButtonVisible() {
        binding.relbottom.setVisibility(View.VISIBLE);
    }

    public void SubmitButtonInVisible() {
        binding.relbottom.setVisibility(View.GONE);
    }

    public static ActivityFinalQuiz getInstance() {
        return instance;

    }


}
