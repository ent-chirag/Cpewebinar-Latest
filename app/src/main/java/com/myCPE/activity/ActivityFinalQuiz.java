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

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.adapter.FinalQuizAdapter;
import com.myCPE.databinding.ActivityFinalQuizBinding;
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

    public Dialog myDialog;
    public Dialog myDialogaddreview;

    private int rating = 0;
    private boolean isLikeToKnowMore = false;
    private int is_like = 0;
    private String strReview = "";

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
        Constant.hashmap_answer_string_final_question.clear();

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
        tv_title.setText(getResources().getString(R.string.str_title_question_rating_popup)+" "+WebinarDetailsActivity.getInstance().aboutpresenterCompanyName+"?");

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

                rating =4;

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

                if(!isLikeToKnowMore){
                    Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.str_validation_like_know_more), Snackbar.LENGTH_SHORT).show();
                } else if(rating == 0){
                    Snackbar.make(binding.recyclerviewFinalQuiz, getResources().getString(R.string.str_validation_rating), Snackbar.LENGTH_SHORT).show();
                } else {
                    // Take the API calls here..
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
//                        GetSubmitAnswer(questionsParams, ansParams, "" + percentage);
                        GetSubmitReviewAnswer(is_like, rating, strReview,sucessmessage);
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

    private void GetSubmitReviewAnswer(int is_like, int rating, String strReview,final String sucessmessage) {

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
                            if(myDialogaddreview.isShowing()){
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


}
