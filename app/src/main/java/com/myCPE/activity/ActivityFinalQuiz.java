package com.myCPE.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.FinalQuizAdapter;
import com.myCPE.databinding.ActivityFinalQuizBinding;
import com.myCPE.databinding.ActivityFinalQuizNewBinding;
import com.myCPE.model.final_Quiz.FinalQuizQuestionsItem;
import com.myCPE.model.final_Quiz.Final_Quiz;
import com.myCPE.model.final_quiz_answer.FinalQuizAnswer;
import com.myCPE.model.review_answer.AddReview;
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

import static com.myCPE.utility.Constant.arraylistselectedanswerfinal;
import static com.myCPE.utility.Constant.arraylistselectedquestionfinal;

public class ActivityFinalQuiz extends AppCompatActivity implements View.OnClickListener {

    //    ActivityFinalQuizBinding binding;
    ActivityFinalQuizNewBinding binding;

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

    public Dialog myDialog;
    public Dialog myDialogaddreview;

    private int rating = 0;
    private boolean isLikeToKnowMore = false;
    private int is_like = 0;
    private String strReview = "";

    private int question_showing = 1;
    private boolean isSubmit = false;

    private int qPos = 0;
    private boolean isPrevClickable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_final_quiz);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_final_quiz_new);
        context = ActivityFinalQuiz.this;
        mAPIService = ApiUtilsNew.getAPIService();
        instance = ActivityFinalQuiz.this;

        Intent intent = getIntent();
        if (intent != null) {
            webinar_id = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
        }

        Constant.hashmap_submit_answers.clear();
        Constant.hashmap_answer_state.clear();
        Constant.hashmap_asnwer_review_question.clear();
        Constant.hashmap_asnwer_string_review_question.clear();
        Constant.hashmap_answer_string_final_question.clear();

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewFinalQuiz.setLayoutManager(linearLayoutManager);
        binding.recyclerviewFinalQuiz.addItemDecoration(new SimpleDividerItemDecoration(context));
        binding.recyclerviewFinalQuiz.setItemAnimator(new DefaultItemAnimator());
        Constant.hashmap_answer_string_final_question.clear();

        binding.relNextSubmit.setOnClickListener(this);
        binding.relPrev.setOnClickListener(this);
        binding.tvPrev.setOnClickListener(this);
        binding.tvNextSubmit.setOnClickListener(this);

        binding.lvA.setOnClickListener(this);
        binding.lvB.setOnClickListener(this);
        binding.lvC.setOnClickListener(this);
        binding.lvD.setOnClickListener(this);
        binding.tvAnsA.setOnClickListener(this);
        binding.tvAnsB.setOnClickListener(this);
        binding.tvAnsC.setOnClickListener(this);
        binding.tvAnsD.setOnClickListener(this);

        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebinarDetailsActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();
            }
        });

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


//        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
        binding.relbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalquizquestion = android.text.TextUtils.join(",", arraylistselectedquestionfinal);
                System.out.println(finalquizquestion);

                String finalquizwanswer = android.text.TextUtils.join(",", arraylistselectedanswerfinal);
                System.out.println(finalquizwanswer);

                String questionsParams = "";
                String ansParams = "";

                Iterator myVeryOwnIterator = Constant.hashmap_answer_string_final_question.keySet().iterator();
                while (myVeryOwnIterator.hasNext()) {
                    String key = (String) myVeryOwnIterator.next();
                    String value = (String) Constant.hashmap_answer_string_final_question.get(key);

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

                int count = 0;
                for (int i = 0; i < finalquestion.size(); i++) {
                    if (finalquestion.get(i).isCorrect()) {
                        count++;
                    }
                }

                Log.e("*+*+*", "Correct Answer count is : " + count);

                Log.e("*+*+*", "Final Quiz Questions : " + questionsParams);
                Log.e("*+*+*", "Final Quiz Answers : " + ansParams);

                float percentage = (count * 100) / finalquestion.size();
                Log.e("*+*+*", "Percentage is : " + percentage);

                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                    GetSubmitAnswer(finalquizquestion, finalquizwanswer);
                    GetSubmitAnswer(questionsParams, ansParams, "" + percentage);
                } else {
                    Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showPopUp(String msg, final boolean flag) {

        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.final_quiz_popup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);

        TextView popup_description, tv_ok;

        popup_description = (TextView) myDialog.findViewById(R.id.popup_description);
        tv_ok = (TextView) myDialog.findViewById(R.id.tv_ok);

        popup_description.setText("" + msg);
        if (flag) {
            popup_description.setTextColor(context.getResources().getColor(R.color.correct_ans));
        } else {
            popup_description.setTextColor(context.getResources().getColor(R.color.wrong_ans));
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag) {
                    if (myDialog.isShowing()) {
                        myDialog.dismiss();
                    }
                    Intent i = new Intent(context, WebinarDetailsActivity.class);
                    i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    startActivity(i);
                    finish();


                } else {
                    if (myDialog.isShowing()) {
                        myDialog.dismiss();
                    }
                    // Also we have to load the final quiz to first question..
                    isPrevClickable = false;
                    binding.tvNextSubmit.setText("Next");
                    question_showing = 1;

                    binding.tvNumber.setText("Questions 1/" + finalquestion.size());
                    binding.tvQuestion.setText(finalquestion.get(0).getQuestionTitle());
                    binding.tvAnsA.setText(finalquestion.get(0).getA().getOptionTitle());
                    binding.tvAnsB.setText(finalquestion.get(0).getB().getOptionTitle());
                    binding.tvAnsC.setText(finalquestion.get(0).getC().getOptionTitle());
                    binding.tvAnsD.setText(finalquestion.get(0).getD().getOptionTitle());

                    makeAnswerSelection(0);
                }

            }
        });

        myDialog.show();

    }


    private void showAddReviewPopUp(final String sucessmessage) {
        myDialogaddreview = new Dialog(context);
        myDialogaddreview.setContentView(R.layout.rating_popup);
        myDialogaddreview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialogaddreview.setCanceledOnTouchOutside(false);
        myDialogaddreview.setCancelable(false);

        ImageView iv_close = (ImageView) myDialogaddreview.findViewById(R.id.ivclose);
        Button btn_submit = (Button) myDialogaddreview.findViewById(R.id.btn_submit);
        final ImageView iv_one = (ImageView) myDialogaddreview.findViewById(R.id.iv_one);
        final ImageView iv_two = (ImageView) myDialogaddreview.findViewById(R.id.iv_two);
        final ImageView iv_three = (ImageView) myDialogaddreview.findViewById(R.id.iv_three);
        final ImageView iv_four = (ImageView) myDialogaddreview.findViewById(R.id.iv_four);
        final ImageView iv_five = (ImageView) myDialogaddreview.findViewById(R.id.iv_five);

        final EditText edt_review = (EditText) myDialogaddreview.findViewById(R.id.edt_review);

        final TextView tv_title = (TextView) myDialogaddreview.findViewById(R.id.tv_title);

        final RelativeLayout relChecked_yes = (RelativeLayout) myDialogaddreview.findViewById(R.id.relChecked_yes);
        final RelativeLayout relChecked_no = (RelativeLayout) myDialogaddreview.findViewById(R.id.relChecked_no);
        final TextView tv_yes = (TextView) myDialogaddreview.findViewById(R.id.tv_yes);
        final TextView tv_no = (TextView) myDialogaddreview.findViewById(R.id.tv_no);

        iv_close.setVisibility(View.GONE);
        tv_title.setText(getResources().getString(R.string.str_title_question_rating_popup) + " " + WebinarDetailsActivity.getInstance().aboutpresenterCompanyName + "?");

        rating = 0;
        isLikeToKnowMore = false;
        is_like = 0;
        strReview = "";

        relChecked_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 1;
            }
        });

        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 1;
            }
        });

        relChecked_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 0;
            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_select));
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_not_select));
                isLikeToKnowMore = true;
                is_like = 0;
            }
        });

        iv_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star);
                iv_three.setImageResource(R.mipmap.add_review_star);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 1;

            }
        });


        iv_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 2;

            }
        });


        iv_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 3;

            }
        });

        iv_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star_hover);
                iv_five.setImageResource(R.mipmap.add_review_star);

                rating = 4;

            }
        });

        iv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star_hover);
                iv_five.setImageResource(R.mipmap.add_review_star_hover);

                rating = 5;

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Take API CALL here then on the success call add the following code there..
                /*Intent i = new Intent(context, WebinarDetailsActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                startActivity(i);
                finish();*/

                // Check for validation..
                strReview = edt_review.getText().toString();

                if (!isLikeToKnowMore) {
                    Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.str_validation_like_know_more), Snackbar.LENGTH_SHORT).show();
                } else if (rating == 0) {
                    Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.str_validation_rating), Snackbar.LENGTH_SHORT).show();
                } else {
                    // Take the API calls here..
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                        GetSubmitAnswer(questionsParams, ansParams, "" + percentage);
                        GetSubmitReviewAnswer(is_like, rating, strReview, sucessmessage);
                    } else {
                        Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogaddreview.dismiss();
            }
        });


        myDialogaddreview.show();
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

    private void GetSubmitReviewAnswer(int is_like, int rating, String strReview, final String sucessmessage) {

        mAPIService.AddReview(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id
                , rating, is_like, strReview).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddReview>() {
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
                    public void onNext(AddReview addReview) {

                        if (addReview.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            if (myDialogaddreview.isShowing()) {
                                myDialogaddreview.dismiss();
                            }

                            showPopUp(sucessmessage, true);


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    private void GetSubmitAnswer(String finalquizquestion, String finalanswer, String percentage) {

        mAPIService.FinalQuizAnswer(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context), webinar_id
                , finalquizquestion, finalanswer, percentage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

                            showAddReviewPopUp(finalQuizAnswer.getMessage());

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            showPopUp(finalQuizAnswer.getMessage(), false);

//                            Snackbar.make(binding.ivback, finalQuizAnswer.getMessage(), Snackbar.LENGTH_SHORT).show();
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

                            if (finalquestion.size() > 0) {
                                binding.tvNumber.setText("Questions 1/" + finalquestion.size());
                                binding.tvQuestion.setText(finalquestion.get(0).getQuestionTitle());
                                binding.tvAnsA.setText(finalquestion.get(0).getA().getOptionTitle());
                                binding.tvAnsB.setText(finalquestion.get(0).getB().getOptionTitle());
                                binding.tvAnsC.setText(finalquestion.get(0).getC().getOptionTitle());
                                binding.tvAnsD.setText(finalquestion.get(0).getD().getOptionTitle());

//                            binding.relPrev.setVisibility(View.INVISIBLE);
                                isPrevClickable = false;
                            }

                            for (int i = 0; i < finalquestion.size(); i++) {
                                finalquestion.get(i).setCorrect(false);
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


                            /*if (finalquestion.size() > 0) {
                                binding.recyclerviewFinalQuiz.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                                binding.recyclerviewFinalQuiz.setVisibility(View.GONE);
                            }*/


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivback, Final_Quiz.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });

    }

    // This method is not used for now..
    public void SubmitButtonVisible() {
        binding.relbottom.setVisibility(View.VISIBLE);
    }

    public void SubmitButtonInVisible() {
        binding.relbottom.setVisibility(View.GONE);
    }

    public void ShowHideSubmitButton() {
        if (finalquestion.size() == Constant.hashmap_answer_string_final_question.size()) {
            binding.relbottom.setVisibility(View.VISIBLE);
        } else {
            binding.relbottom.setVisibility(View.GONE);
        }
    }

    public static ActivityFinalQuiz getInstance() {
        return instance;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relPrev:
                if (isPrevClickable) {
                    btnPrev();
                } else {
                    Toast.makeText(context, "Oops, you are already on first question.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_prev:
                if (isPrevClickable) {
                    btnPrev();
                } else {
                    Toast.makeText(context, "Oops, you are already on first question.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.relNextSubmit:
                btnNext();
                break;

            case R.id.tv_next_submit:
                btnNext();
                break;

            case R.id.tv_ans_a:
                answerA();
                break;

            case R.id.lv_a:
                answerA();
                break;

            case R.id.tv_ans_b:
                answerB();
                break;

            case R.id.lv_b:
                answerB();
                break;

            case R.id.tv_ans_c:
                answerC();
                break;

            case R.id.lv_c:
                answerC();
                break;

            case R.id.tv_ans_d:
                answerD();
                break;

            case R.id.lv_d:
                answerD();
                break;

        }
    }

    private void btnNext() {

        int lp = question_showing;
//        binding.relPrev.setVisibility(View.VISIBLE);
        isPrevClickable = true;

        // Submit Button Click
        if (question_showing == finalquestion.size()) {
            // All questions are answered..
            if (Constant.hashmap_asnwer_string_review_question.size() == finalquestion.size()) {
                isSubmit = true;
                Constant.hashmap_submit_answers.putAll(Constant.hashmap_asnwer_string_review_question);
//                checkAnswers();
                submitAnswer();
            } else {
                Snackbar.make(binding.tvNumber, getResources().getString(R.string.ans_all_question), Snackbar.LENGTH_SHORT).show();
            }

        } else {
            // Next Button Click
//            binding.tvNumber.setText("" + (lp + 1));
            int lp1 = lp + 1;
            binding.tvNumber.setText("Questions " + lp1 + "/" + finalquestion.size());
            binding.tvQuestion.setText(finalquestion.get(lp).getQuestionTitle());
            binding.tvAnsA.setText(finalquestion.get(lp).getA().getOptionTitle());
            binding.tvAnsB.setText(finalquestion.get(lp).getB().getOptionTitle());
            binding.tvAnsC.setText(finalquestion.get(lp).getC().getOptionTitle());
            binding.tvAnsD.setText(finalquestion.get(lp).getD().getOptionTitle());

            question_showing++;

            if (question_showing == finalquestion.size()) {
                binding.tvNextSubmit.setText("Submit");
            } else {
                binding.tvNextSubmit.setText("Next");
            }

            makeAnswerSelection(lp);
        }
    }

    private void btnPrev() {

        binding.tvNextSubmit.setText("Next");
        if (question_showing == 1) {
//            binding.tvNumber.setText("1");
            binding.tvNumber.setText("Questions 1/" + finalquestion.size());
            binding.tvQuestion.setText(finalquestion.get(0).getQuestionTitle());
            binding.tvAnsA.setText(finalquestion.get(0).getA().getOptionTitle());
            binding.tvAnsB.setText(finalquestion.get(0).getB().getOptionTitle());
            binding.tvAnsC.setText(finalquestion.get(0).getC().getOptionTitle());
            binding.tvAnsD.setText(finalquestion.get(0).getD().getOptionTitle());

            makeAnswerSelection(0);

        } else {
            question_showing--;
//            binding.tvNumber.setText("" + question_showing);
            binding.tvNumber.setText("Questions " + question_showing + "/" + finalquestion.size());
            int dv = question_showing - 1;
            binding.tvQuestion.setText(finalquestion.get(dv).getQuestionTitle());
            binding.tvAnsA.setText(finalquestion.get(dv).getA().getOptionTitle());
            binding.tvAnsB.setText(finalquestion.get(dv).getB().getOptionTitle());
            binding.tvAnsC.setText(finalquestion.get(dv).getC().getOptionTitle());
            binding.tvAnsD.setText(finalquestion.get(dv).getD().getOptionTitle());

            if (question_showing == 1) {
//                binding.relPrev.setVisibility(View.INVISIBLE);
                isPrevClickable = false;
            }

            makeAnswerSelection(dv);
        }
    }

    private void selectionA() {
        binding.lvA.setBackgroundResource(R.drawable.rounded_answer_blue);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.White));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
    }

    private void answerA() {

        qPos = question_showing - 1;

        binding.lvA.setBackgroundResource(R.drawable.rounded_answer_blue);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.White));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));

//        binding.tvAnsResponse.setText(finalquestion.get(qPos).getA().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + finalquestion.get(qPos).getId(), true);
        Constant.hashmap_asnwer_string_review_question.put("" + finalquestion.get(qPos).getId(), "a");

        Constant.hashmap_answer_string_final_question.put("" + finalquestion.get(qPos).getId(), "a");

        if (finalquestion.get(qPos).getAnswer().equalsIgnoreCase("a")) {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), true);
        } else {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), false);
        }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

        if (finalquestion.get(qPos).getA().getIsAnswer().equalsIgnoreCase("true")) {
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
    }

    private void selectionB() {

        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_answer_blue);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.White));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
    }

    private void answerB() {
        qPos = question_showing - 1;

        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_answer_blue);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.White));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));

//        binding.tvAnsResponse.setText(finalquestion.get(qPos).getB().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + finalquestion.get(qPos), true);
        Constant.hashmap_asnwer_string_review_question.put("" + finalquestion.get(qPos).getId(), "b");

        Constant.hashmap_answer_string_final_question.put("" + finalquestion.get(qPos).getId(), "b");

        if (finalquestion.get(qPos).getAnswer().equalsIgnoreCase("b")) {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), true);
        } else {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), false);
        }

        if (finalquestion.get(qPos).getB().getIsAnswer().equalsIgnoreCase("true")) {
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
        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_answer_blue);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.White));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
    }

    private void answerC() {

        qPos = question_showing - 1;

        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_answer_blue);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.White));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));

//        binding.tvAnsResponse.setText(finalquestion.get(qPos).getC().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + finalquestion.get(qPos), true);
        Constant.hashmap_asnwer_string_review_question.put("" + finalquestion.get(qPos).getId(), "c");

        Constant.hashmap_answer_string_final_question.put("" + finalquestion.get(qPos).getId(), "c");

        if (finalquestion.get(qPos).getAnswer().equalsIgnoreCase("c")) {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), true);
        } else {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), false);
        }

        if (finalquestion.get(qPos).getC().getIsAnswer().equalsIgnoreCase("true")) {
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
        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_answer_blue);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.White));
    }

    private void answerD() {

        qPos = question_showing - 1;

        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_answer_blue);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.White));

//        binding.tvAnsResponse.setText(finalquestion.get(qPos).getD().getDescription());

        Constant.hashmap_asnwer_review_question.put("" + finalquestion.get(qPos), true);
        Constant.hashmap_asnwer_string_review_question.put("" + finalquestion.get(qPos).getId(), "d");

        Constant.hashmap_answer_string_final_question.put("" + finalquestion.get(qPos).getId(), "d");

        if (finalquestion.get(qPos).getAnswer().equalsIgnoreCase("d")) {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), true);
        } else {
            Constant.hashmap_answer_state.put("" + finalquestion.get(qPos).getId(), false);
        }

        if (finalquestion.get(qPos).getD().getIsAnswer().equalsIgnoreCase("true")) {
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
    }

    public static boolean areAllTrue(List<Boolean> array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }

    private void makeAnswerSelection(int lp) {

        for (String key : Constant.hashmap_asnwer_string_review_question.keySet()) {
            int intKey = Integer.parseInt("" + key);
            if (intKey == finalquestion.get(lp).getId()) {
                String selectedOption = Constant.hashmap_asnwer_string_review_question.get(key);
                boolean ansState = Constant.hashmap_answer_state.get(key);

                if (ansState) {
//                    binding.tvResponseTag.setText(getResources().getString(R.string.why_correct));
                    if (selectedOption.equalsIgnoreCase("a")) {
                        selectionA();
                        /*if (isSubmit) {
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("a")) {
                                binding.lvA.setBackgroundResource(R.drawable.rounded_answer_green);
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.White));
                                disableClick();
                            } else {
//                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        selectionB();
                        /*if (isSubmit) {
//                            binding.tvAnsB.setTextColor(getResources().getColor(R.color.correct_ans));
//                            disableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("b")) {
                                binding.lvB.setBackgroundResource(R.drawable.rounded_answer_green);
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.White));
                                disableClick();
                            } else {
//                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        selectionC();
                        /*if (isSubmit) {
//                            binding.tvAnsC.setTextColor(getResources().getColor(R.color.correct_ans));
//                            disableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("c")) {
                                binding.lvC.setBackgroundResource(R.drawable.rounded_answer_green);
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.White));
                                disableClick();
                            } else {
//                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        selectionD();
                        /*if (isSubmit) {
//                            binding.tvAnsD.setTextColor(getResources().getColor(R.color.correct_ans));
//                            disableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("d")) {
                                binding.lvD.setBackgroundResource(R.drawable.rounded_answer_green);
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.White));
                                disableClick();
                            } else {
//                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    }
                } else {
//                    binding.tvResponseTag.setText(getResources().getString(R.string.why_incorrect));
                    if (selectedOption.equalsIgnoreCase("a")) {
                        selectionA();
                        /*if (isSubmit) {
//                            binding.tvAnsA.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("a")) {
                                binding.lvA.setBackgroundResource(R.drawable.rounded_answer_red);
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.White));
                                enableClick();
                            } else {
//                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsA.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        selectionB();
                        /*if (isSubmit) {
//                            binding.tvAnsB.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("b")) {
                                binding.lvB.setBackgroundResource(R.drawable.rounded_answer_red);
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.White));
                                enableClick();
                            } else {
//                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsB.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        selectionC();
                        /*if (isSubmit) {
//                            binding.tvAnsC.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("c")) {
                                binding.lvC.setBackgroundResource(R.drawable.rounded_answer_red);
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.White));
                                enableClick();
                            } else {
//                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsC.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        selectionD();
                        /*if (isSubmit) {
//                            binding.tvAnsD.setTextColor(getResources().getColor(R.color.wrong_ans));
//                            enableClick();
                            if (Constant.hashmap_submit_answers.get(""+finalquestion.get(lp).getId()).equalsIgnoreCase("d")) {
                                binding.lvD.setBackgroundResource(R.drawable.rounded_answer_red);
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.White));
                                enableClick();
                            } else {
//                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
                                binding.tvAnsD.setTextColor(getResources().getColor(R.color.White));
                                Constant.hashmap_submit_answers.put(""+finalquestion.get(lp).getId(), "");
                                enableClick();
                            }
                        }*/
                        break;
                    }
                }
            } else {
                resetSelection();
            }
        }
    }

    private void resetSelection() {
        Log.e("*+*+*","resetSelection is called");
        binding.lvA.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvB.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvC.setBackgroundResource(R.drawable.rounded_white_boundry);
        binding.lvD.setBackgroundResource(R.drawable.rounded_white_boundry);

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.color_text_black_oppacity));
    }

    private void submitAnswer() {

        for (String key: Constant.hashmap_answer_string_final_question.keySet()) {
            Log.e("**+*+*","Key : "+key);
            Log.e("**+*+*","Value : "+Constant.hashmap_answer_string_final_question.get(key));
        }

        String finalquizquestion = android.text.TextUtils.join(",", arraylistselectedquestionfinal);
        System.out.println(finalquizquestion);

        String finalquizwanswer = android.text.TextUtils.join(",", arraylistselectedanswerfinal);
        System.out.println(finalquizwanswer);

        String questionsParams = "";
        String ansParams = "";

//        Iterator myVeryOwnIterator = Constant.hashmap_asnwer_review_question.keySet().iterator();
        Iterator myVeryOwnIterator = Constant.hashmap_answer_string_final_question.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            String key = (String) myVeryOwnIterator.next();
//            String value = (String) Constant.hashmap_asnwer_string_review_question.get(key);
            String value = (String) Constant.hashmap_answer_string_final_question.get(key);

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

        int count = 0;
        for (String key: Constant.hashmap_answer_state.keySet()) {
            Log.e("**+*+*","Key : "+key);
            Log.e("**+*+*","Value : "+Constant.hashmap_answer_state.get(key));
            if(Constant.hashmap_answer_state.get(key)) {
                count++;
            }
        }

        Log.e("*+*+*", "Correct Answer count is : " + count);

        Log.e("*+*+*", "Final Quiz Questions : " + questionsParams);
        Log.e("*+*+*", "Final Quiz Answers : " + ansParams);

        float percentage = (count * 100) / finalquestion.size();
        Log.e("*+*+*", "Percentage is : " + percentage);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                    GetSubmitAnswer(finalquizquestion, finalquizwanswer);
            GetSubmitAnswer(questionsParams, ansParams, "" + percentage);
        } else {
            Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void checkAnswers() {

        boolean check = false;
        for (int i = 0; i < finalquestion.size(); i++) {
            if (Constant.hashmap_answer_state.get("" + finalquestion.get(i).getId())) {
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

            /*if (Constant.isAllAnswerTrue) {
                if (Constant.isNetworkAvailable(context)) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                    GetSubmitAnswer(questionsParams, ansParams);
                } else {
                    Snackbar.make(binding.relNextSubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                }
            }*/

        } else {
            Log.e("*+*+*", "Oops");

            question_showing = 1;
//            isSubmit = false;

//            binding.tvNumber.setText("1");
            binding.tvNumber.setText("Questions 1/"+finalquestion.size());
            binding.tvQuestion.setText(finalquestion.get(0).getQuestionTitle());
            binding.tvAnsA.setText(finalquestion.get(0).getA().getOptionTitle());
            binding.tvAnsB.setText(finalquestion.get(0).getB().getOptionTitle());
            binding.tvAnsC.setText(finalquestion.get(0).getC().getOptionTitle());
            binding.tvAnsD.setText(finalquestion.get(0).getD().getOptionTitle());

//            binding.relPrev.setVisibility(View.INVISIBLE);
            isPrevClickable = false;
            binding.tvNextSubmit.setText("Next");

//            binding.tvResponseTag.setVisibility(View.VISIBLE);
//            binding.tvAnsResponse.setVisibility(View.VISIBLE);

            makeAnswerSelection(0);
        }
    }
}
