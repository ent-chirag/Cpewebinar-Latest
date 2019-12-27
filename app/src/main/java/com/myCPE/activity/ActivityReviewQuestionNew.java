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
import android.widget.Toast;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.ReviewQuestionAdapter;
import com.myCPE.databinding.ActivityReviewQuestionNewBinding;
import com.myCPE.model.SubmitReviewAnswer.SubmitAnswerModel;
import com.myCPE.model.review_question.ReviewQuestionsItem;
import com.myCPE.model.review_question.Review_Question;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.myCPE.utility.Constant.arraylistselectedquestionreview;
import static com.myCPE.utility.Constant.arraylistselectedreviewanswerreview;

public class ActivityReviewQuestionNew extends AppCompatActivity implements View.OnClickListener {

    ActivityReviewQuestionNewBinding binding;
    public List<ReviewQuestionsItem> reviewquestion = new ArrayList<>();
    public List<Boolean> arrayboolean = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    private static final String TAG = ActivityReviewQuestionNew.class.getName();
    public Context context;

    public ReviewQuestionAdapter adapter;
    private static ActivityReviewQuestionNew instance;
    public int webinar_id = 0;
    public String webinar_type = "";
    private String watchedDuration = "";

    private int question_showing = 1;
    private boolean isSubmit = false;

    private int qPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_review_question_new);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_question_new);

        context = ActivityReviewQuestionNew.this;
        mAPIService = ApiUtilsNew.getAPIService();
        instance = ActivityReviewQuestionNew.this;

        Constant.hashmap_submit_answers.clear();
        Constant.hashmap_answer_state.clear();
        Constant.hashmap_asnwer_review_question.clear();
        Constant.hashmap_asnwer_string_review_question.clear();
        Constant.hashmap_answer_string_final_question.clear();

        Intent intent = getIntent();
        if (intent != null) {
            webinar_id = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
        }

        watchedDuration = Constant.watchedDuration;

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

        webinar_id = Integer.parseInt(Constant.webinar_id);

        binding.relNextSubmit.setOnClickListener(this);
        binding.relPrev.setOnClickListener(this);
        binding.tvPrev.setOnClickListener(this);
        binding.tvNextSubmit.setOnClickListener(this);
        binding.lvA.setOnClickListener(this);
        binding.lvB.setOnClickListener(this);
        binding.lvC.setOnClickListener(this);
        binding.lvD.setOnClickListener(this);
        binding.checkboxSelectA.setOnClickListener(this);
        binding.checkboxSelectB.setOnClickListener(this);
        binding.checkboxSelectC.setOnClickListener(this);
        binding.checkboxSelectD.setOnClickListener(this);
        binding.tvAnsA.setOnClickListener(this);
        binding.tvAnsB.setOnClickListener(this);
        binding.tvAnsC.setOnClickListener(this);
        binding.tvAnsD.setOnClickListener(this);

        binding.tvResponseTag.setVisibility(View.GONE);
        binding.tvAnsResponse.setVisibility(View.GONE);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetReviewQuestion();
        } else {
            Snackbar.make(binding.tvNumber, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void GetReviewQuestion() {

        arraylistselectedreviewanswerreview.clear();
        arraylistselectedquestionreview.clear();

        mAPIService.ReviewQuestion(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Review_Question>() {
                    @Override
                    public void onCompleted() {

                        Log.e("*+*+*", "Review Questions size : " + reviewquestion.size());

                        if (reviewquestion.size() > 0) {
                            // Previously we are loading data here in adapter from here..
                            // Now we have to show the first question here..

                            binding.tvNumber.setText("1");
                            binding.tvQuestion.setText(reviewquestion.get(0).getQuestionTitle());
                            binding.tvAnsA.setText(reviewquestion.get(0).getA().getOptionTitle());
                            binding.tvAnsB.setText(reviewquestion.get(0).getB().getOptionTitle());
                            binding.tvAnsC.setText(reviewquestion.get(0).getC().getOptionTitle());
                            binding.tvAnsD.setText(reviewquestion.get(0).getD().getOptionTitle());

                            binding.relPrev.setVisibility(View.INVISIBLE);

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
                            Snackbar.make(binding.tvNumber, message, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(Review_Question review_question) {

                        if (review_question.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            reviewquestion = review_question.getPayload().getReviewQuestions();

                            for (int i = 0; i < reviewquestion.size(); i++) {
                                Constant.hashmap_asnwer_review_question.put(reviewquestion.get(i).getQuestionTitle(), false);
                                reviewquestion.get(i).setAnswerable(true);
                                if (reviewquestion.get(i).getA().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedreviewanswerreview.add("a");
                                    arraylistselectedquestionreview.add(reviewquestion.get(i).getId());
                                } else if (reviewquestion.get(i).getB().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedreviewanswerreview.add("b");
                                    arraylistselectedquestionreview.add(reviewquestion.get(i).getId());
                                } else if (reviewquestion.get(i).getC().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedreviewanswerreview.add("c");
                                    arraylistselectedquestionreview.add(reviewquestion.get(i).getId());
                                } else if (reviewquestion.get(i).getD().getIsAnswer().equalsIgnoreCase("true")) {
                                    arraylistselectedreviewanswerreview.add("d");
                                    arraylistselectedquestionreview.add(reviewquestion.get(i).getId());
                                }
                            }

                            for (int i = 0; i < reviewquestion.size(); i++) {
                                arrayboolean.add(false);
                            }

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.tvNumber, review_question.getMessage(), Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relNextSubmit:
                btnNext();
                break;

            case R.id.relPrev:
                btnPrev();
                break;

            case R.id.tv_next_submit:
                btnNext();
                break;

            case R.id.tv_prev:
                btnPrev();
                break;

            case R.id.tv_ans_a:
                answerA();
                break;

            case R.id.checkbox_select_a:
                answerA();
                break;

            case R.id.lv_a:
                answerA();
                break;

            case R.id.tv_ans_b:
                answerB();
                break;

            case R.id.checkbox_select_b:
                answerB();
                break;

            case R.id.lv_b:
                answerB();
                break;

            case R.id.tv_ans_c:
                answerC();
                break;

            case R.id.checkbox_select_c:
                answerC();
                break;

            case R.id.lv_c:
                answerC();
                break;

            case R.id.tv_ans_d:
                answerD();
                break;

            case R.id.checkbox_select_d:
                answerD();
                break;

            case R.id.lv_d:
                answerD();
                break;
        }
    }

    private void resetSelection() {

        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//        binding.tvResponseTag.setVisibility(View.GONE);
//        binding.tvAnsResponse.setVisibility(View.GONE);

    }

    private void selectionA() {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//        binding.tvResponseTag.setVisibility(View.GONE);
//        binding.tvAnsResponse.setVisibility(View.GONE);
    }

    private void answerA() {

//        int aa = question_showing - 1;
        qPos = question_showing - 1;
//        if (reviewquestion.get(qPos).isAnswerable()) {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//            binding.tvResponseTag.setVisibility(View.GONE);
//            binding.tvAnsResponse.setVisibility(View.GONE);

        binding.tvAnsResponse.setText(reviewquestion.get(qPos).getA().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(qPos).getId(), true);
        Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(qPos).getId(), "a");

        if (reviewquestion.get(qPos).getAnswer().equalsIgnoreCase("a")) {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), true);
//                reviewquestion.get(qPos).setAnswerable(false);
        } else {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), false);
//                reviewquestion.get(qPos).setAnswerable(true);
        }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

        if (reviewquestion.get(qPos).getA().getIsAnswer().equalsIgnoreCase("true")) {
            arrayboolean.set(qPos, true);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        } else {
            arrayboolean.set(qPos, false);
            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        }
//        }
    }

    private void selectionB() {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//        binding.tvResponseTag.setVisibility(View.GONE);
//        binding.tvAnsResponse.setVisibility(View.GONE);
    }

    private void answerB() {
//        int ab = question_showing - 1;
        qPos = question_showing - 1;
//        if (reviewquestion.get(qPos).isAnswerable()) {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//            binding.tvResponseTag.setVisibility(View.GONE);
//            binding.tvAnsResponse.setVisibility(View.GONE);

        binding.tvAnsResponse.setText(reviewquestion.get(qPos).getB().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(qPos), true);
        Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(qPos).getId(), "b");

        if (reviewquestion.get(qPos).getAnswer().equalsIgnoreCase("b")) {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), true);
//                reviewquestion.get(qPos).setAnswerable(false);
        } else {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), false);
//                reviewquestion.get(qPos).setAnswerable(true);
        }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

        if (reviewquestion.get(qPos).getB().getIsAnswer().equalsIgnoreCase("true")) {
            arrayboolean.set(qPos, true);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        } else {
            arrayboolean.set(qPos, false);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        }
//        }
    }

    private void selectionC() {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//        binding.tvResponseTag.setVisibility(View.GONE);
//        binding.tvAnsResponse.setVisibility(View.GONE);
    }

    private void answerC() {

//        int ac = question_showing - 1;
        qPos = question_showing - 1;
//        if (reviewquestion.get(qPos).isAnswerable()) {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//            binding.tvResponseTag.setVisibility(View.GONE);
//            binding.tvAnsResponse.setVisibility(View.GONE);

        binding.tvAnsResponse.setText(reviewquestion.get(qPos).getC().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(qPos), true);
        Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(qPos).getId(), "c");

        if (reviewquestion.get(qPos).getAnswer().equalsIgnoreCase("c")) {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), true);
//                reviewquestion.get(qPos).setAnswerable(false);
        } else {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), false);
//                reviewquestion.get(qPos).setAnswerable(true);
        }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

        if (reviewquestion.get(qPos).getC().getIsAnswer().equalsIgnoreCase("true")) {
            arrayboolean.set(qPos, true);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        } else {
            arrayboolean.set(qPos, false);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        }
//        }
    }

    private void selectionD() {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//        binding.tvResponseTag.setVisibility(View.GONE);
//        binding.tvAnsResponse.setVisibility(View.GONE);
    }

    private void answerD() {

//        int ad = question_showing - 1;
        qPos = question_showing - 1;
//        if (reviewquestion.get(qPos).isAnswerable()) {
        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
//            binding.tvResponseTag.setVisibility(View.GONE);
//            binding.tvAnsResponse.setVisibility(View.GONE);

        binding.tvAnsResponse.setText(reviewquestion.get(qPos).getD().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(qPos), true);
        Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(qPos).getId(), "d");

        if (reviewquestion.get(qPos).getAnswer().equalsIgnoreCase("d")) {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), true);
//                reviewquestion.get(qPos).setAnswerable(false);
        } else {
            Constant.hashmap_answer_state.put("" + reviewquestion.get(qPos).getId(), false);
//                reviewquestion.get(qPos).setAnswerable(true);
        }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

        if (reviewquestion.get(qPos).getD().getIsAnswer().equalsIgnoreCase("true")) {
            arrayboolean.set(qPos, true);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        } else {
            arrayboolean.set(qPos, false);

            if (areAllTrue(arrayboolean)) {
                Constant.isAllAnswerTrue = true;
            } else {
                Constant.isAllAnswerTrue = false;
            }
        }
//        }
    }

    public static boolean areAllTrue(List<Boolean> array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }

    private void btnNext() {

        int lp = question_showing;
        binding.relPrev.setVisibility(View.VISIBLE);

        // Submit Button Click
        if (question_showing == reviewquestion.size()) {
            // All questions are answered..
            if (Constant.hashmap_asnwer_string_review_question.size() == reviewquestion.size()) {
                isSubmit = true;
                Constant.hashmap_submit_answers.putAll(Constant.hashmap_asnwer_string_review_question);
                checkAnswers();
            } else {
                Snackbar.make(binding.tvNumber, getResources().getString(R.string.ans_all_question), Snackbar.LENGTH_SHORT).show();
            }

        } else {
            // Next Button Click
            binding.tvNumber.setText("" + (lp + 1));
            binding.tvQuestion.setText(reviewquestion.get(lp).getQuestionTitle());
            binding.tvAnsA.setText(reviewquestion.get(lp).getA().getOptionTitle());
            binding.tvAnsB.setText(reviewquestion.get(lp).getB().getOptionTitle());
            binding.tvAnsC.setText(reviewquestion.get(lp).getC().getOptionTitle());
            binding.tvAnsD.setText(reviewquestion.get(lp).getD().getOptionTitle());

            question_showing++;

            if (question_showing == reviewquestion.size()) {
                binding.tvNextSubmit.setText("Submit");
            } else {
                binding.tvNextSubmit.setText("Next");
            }

            makeAnswerSelection(lp);
        }
    }

    private void checkAnswers() {

        boolean check = false;
        for (int i = 0; i < reviewquestion.size(); i++) {
            if (Constant.hashmap_answer_state.get("" + reviewquestion.get(i).getId())) {
                check = true;
            } else {
                check = false;
                break;
            }
        }

        if (check) {
            Log.e("*+*+*", "All true");
            // Take api call to submit the review questions and set the quiz to the first question..

            Log.e("*+*+*", "All answers are true");

            String questionsParams = "";
            String ansParams = "";

            Iterator myVeryOwnIterator = Constant.hashmap_asnwer_string_review_question.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                String value = (String) Constant.hashmap_asnwer_string_review_question.get(key);

                if (questionsParams.equalsIgnoreCase("")) {
                    questionsParams = "" + key;
                } else {
                    questionsParams = questionsParams + "," + key;
                }

                if (ansParams.equalsIgnoreCase("")) {
                    ansParams = "" + value;
                } else {
                    ansParams = ansParams + "," + value;
                }
            }

            Log.e("*+*+*", "Question Params : " + questionsParams);
            Log.e("*+*+*", "Answer Params : " + ansParams);

            if (Constant.isAllAnswerTrue) {
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetSubmitAnswer(questionsParams, ansParams);
                } else {
                    Snackbar.make(binding.relNextSubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }

        } else {
            Log.e("*+*+*", "Oops");

            question_showing = 1;
//            isSubmit = false;

            binding.tvNumber.setText("1");
            binding.tvQuestion.setText(reviewquestion.get(0).getQuestionTitle());
            binding.tvAnsA.setText(reviewquestion.get(0).getA().getOptionTitle());
            binding.tvAnsB.setText(reviewquestion.get(0).getB().getOptionTitle());
            binding.tvAnsC.setText(reviewquestion.get(0).getC().getOptionTitle());
            binding.tvAnsD.setText(reviewquestion.get(0).getD().getOptionTitle());

            binding.relPrev.setVisibility(View.INVISIBLE);
            binding.tvNextSubmit.setText("Next");

            binding.tvResponseTag.setVisibility(View.VISIBLE);
            binding.tvAnsResponse.setVisibility(View.VISIBLE);

            makeAnswerSelection(0);
        }
    }

    private void btnPrev() {

        binding.tvNextSubmit.setText("Next");
        if (question_showing == 1) {
            binding.tvNumber.setText("1");
            binding.tvQuestion.setText(reviewquestion.get(0).getQuestionTitle());
            binding.tvAnsA.setText(reviewquestion.get(0).getA().getOptionTitle());
            binding.tvAnsB.setText(reviewquestion.get(0).getB().getOptionTitle());
            binding.tvAnsC.setText(reviewquestion.get(0).getC().getOptionTitle());
            binding.tvAnsD.setText(reviewquestion.get(0).getD().getOptionTitle());

            makeAnswerSelection(0);

        } else {
            question_showing--;
            binding.tvNumber.setText("" + question_showing);
            int dv = question_showing - 1;
            binding.tvQuestion.setText(reviewquestion.get(dv).getQuestionTitle());
            binding.tvAnsA.setText(reviewquestion.get(dv).getA().getOptionTitle());
            binding.tvAnsB.setText(reviewquestion.get(dv).getB().getOptionTitle());
            binding.tvAnsC.setText(reviewquestion.get(dv).getC().getOptionTitle());
            binding.tvAnsD.setText(reviewquestion.get(dv).getD().getOptionTitle());

            if (question_showing == 1) {
                binding.relPrev.setVisibility(View.INVISIBLE);
            }

            makeAnswerSelection(dv);
        }
    }

    private void makeAnswerSelection(int lp) {

        for (String key : Constant.hashmap_asnwer_string_review_question.keySet()) {
            int intKey = Integer.parseInt("" + key);
            if (intKey == reviewquestion.get(lp).getId()) {
                String selectedOption = Constant.hashmap_asnwer_string_review_question.get(key);
                boolean ansState = Constant.hashmap_answer_state.get(key);

                if (ansState) {
                    binding.tvResponseTag.setText(getResources().getString(R.string.why_correct));
                    if (selectedOption.equalsIgnoreCase("a")) {
                        selectionA();
                        if (isSubmit) {
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("a")) {
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.correct_ans));
                                disableClick();
                            } else {
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        selectionB();
                        if (isSubmit) {
//                            binding.tvAnsB.setTextColor(getResources().getColor(R.color.correct_ans));
//                            disableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("b")) {
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.correct_ans));
                                disableClick();
                            } else {
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        selectionC();
                        if (isSubmit) {
//                            binding.tvAnsC.setTextColor(getResources().getColor(R.color.correct_ans));
//                            disableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("c")) {
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.correct_ans));
                                disableClick();
                            } else {
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        selectionD();
                        if (isSubmit) {
//                            binding.tvAnsD.setTextColor(getResources().getColor(R.color.correct_ans));
//                            disableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("d")) {
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.correct_ans));
                                disableClick();
                            } else {
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    }
                } else {
                    binding.tvResponseTag.setText(getResources().getString(R.string.why_incorrect));
                    if (selectedOption.equalsIgnoreCase("a")) {
                        selectionA();
                        if (isSubmit) {
//                            binding.tvAnsA.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("a")) {
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.wrong_ans));
                                enableClick();
                            } else {
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        selectionB();
                        if (isSubmit) {
//                            binding.tvAnsB.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("b")) {
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.wrong_ans));
                                enableClick();
                            } else {
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        selectionC();
                        if (isSubmit) {
//                            binding.tvAnsC.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("c")) {
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.wrong_ans));
                                enableClick();
                            } else {
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        selectionD();
                        if (isSubmit) {
//                            binding.tvAnsD.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+reviewquestion.get(lp).getId()).equalsIgnoreCase("d")) {
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.wrong_ans));
                                enableClick();
                            } else {
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
                                Constant.hashmap_submit_answers.put(""+reviewquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }
                        break;
                    }
                }
            } else {
                resetSelection();
            }
        }
    }

    private void disableClick() {
        binding.lvA.setEnabled(false);
        binding.lvB.setEnabled(false);
        binding.lvC.setEnabled(false);
        binding.lvD.setEnabled(false);

        binding.tvAnsA.setEnabled(false);
        binding.tvAnsB.setEnabled(false);
        binding.tvAnsC.setEnabled(false);
        binding.tvAnsD.setEnabled(false);

        binding.checkboxSelectA.setEnabled(false);
        binding.checkboxSelectB.setEnabled(false);
        binding.checkboxSelectC.setEnabled(false);
        binding.checkboxSelectD.setEnabled(false);
    }

    private void enableClick() {
        binding.lvA.setEnabled(true);
        binding.lvB.setEnabled(true);
        binding.lvC.setEnabled(true);
        binding.lvD.setEnabled(true);

        binding.tvAnsA.setEnabled(true);
        binding.tvAnsB.setEnabled(true);
        binding.tvAnsC.setEnabled(true);
        binding.tvAnsD.setEnabled(true);

        binding.checkboxSelectA.setEnabled(true);
        binding.checkboxSelectB.setEnabled(true);
        binding.checkboxSelectC.setEnabled(true);
        binding.checkboxSelectD.setEnabled(true);
    }

    private void GetSubmitAnswer(String reviewquestion, String reviewanswer) {

        mAPIService.SubmitReviewAnswer(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id
                , reviewquestion, reviewanswer, watchedDuration).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubmitAnswerModel>() {
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
                    public void onNext(SubmitAnswerModel submitAnswerModel) {

                        if (submitAnswerModel.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            arraylistselectedquestionreview.clear();
                            arraylistselectedreviewanswerreview.clear();
//                            finish();
                            Intent i = new Intent(context, WebinarDetailsActivity.class);
                            i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                            i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                            startActivity(i);
                            finish();

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivback, submitAnswerModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                });

    }
}
