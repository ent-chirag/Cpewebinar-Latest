package com.myCPE.webinarDetail;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.ActivityReviewQuestion;
import com.myCPE.adapter.ReviewQuestionAdapter;
import com.myCPE.databinding.FragmentReviewQuestionsBinding;
import com.myCPE.model.review_question.ReviewQuestionsItem;
import com.myCPE.model.review_question.Review_Question;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.myCPE.utility.Constant.arraylistselectedquestionreview;
import static com.myCPE.utility.Constant.arraylistselectedreviewanswerreview;
import static com.myCPE.utility.Constant.failure_message;

public class ReviewQuestionsFragment extends Fragment implements View.OnClickListener {

    //    FragmentDescriptionBinding binding;
    FragmentReviewQuestionsBinding binding;
    View view;

    public Context context;
    ProgressDialog progressDialog;
    private APIService mAPIService;

    public List<ReviewQuestionsItem> reviewquestion = new ArrayList<>();
    public List<Boolean> arrayboolean = new ArrayList<>();

    private int webinar_id = 0;
    private int question_showing = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_questions, null, false);

        context = getActivity();
        mAPIService = ApiUtilsNew.getAPIService();

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

        return view = binding.getRoot();
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

        int aa = question_showing - 1;
        if (reviewquestion.get(aa).isAnswerable()) {
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

            binding.tvAnsResponse.setText(reviewquestion.get(aa).getA().getDescription());

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(aa).getId(), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(aa).getId(), "a");

            if(reviewquestion.get(aa).getAnswer().equalsIgnoreCase("a")) {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(aa).getId(), true);
            } else {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(aa).getId(), false);
            }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

            if (reviewquestion.get(aa).getA().getIsAnswer().equalsIgnoreCase("true")) {
                arrayboolean.set(aa, true);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            } else {
                arrayboolean.set(aa, false);
                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            }
        }
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
        int ab = question_showing - 1;
        if (reviewquestion.get(ab).isAnswerable()) {
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

            binding.tvAnsResponse.setText(reviewquestion.get(ab).getB().getDescription());

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(ab), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(ab).getId(), "b");

            if(reviewquestion.get(ab).getAnswer().equalsIgnoreCase("b")) {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(ab).getId(), true);
            } else {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(ab).getId(), false);
            }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

            if (reviewquestion.get(ab).getB().getIsAnswer().equalsIgnoreCase("true")) {
                arrayboolean.set(ab, true);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            } else {
                arrayboolean.set(ab, false);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            }
        }
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

        int ac = question_showing - 1;
        if (reviewquestion.get(ac).isAnswerable()) {
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

            binding.tvAnsResponse.setText(reviewquestion.get(ac).getC().getDescription());

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(ac), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(ac).getId(), "c");

            if(reviewquestion.get(ac).getAnswer().equalsIgnoreCase("c")) {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(ac).getId(), true);
            } else {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(ac).getId(), false);
            }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

            if (reviewquestion.get(ac).getC().getIsAnswer().equalsIgnoreCase("true")) {
                arrayboolean.set(ac, true);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            } else {
                arrayboolean.set(ac, false);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            }
        }

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

        int ad = question_showing - 1;
        if (reviewquestion.get(ad).isAnswerable()) {
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

            binding.tvAnsResponse.setText(reviewquestion.get(ad).getD().getDescription());

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(ad), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(ad).getId(), "d");

            if(reviewquestion.get(ad).getAnswer().equalsIgnoreCase("d")) {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(ad).getId(), true);
            } else {
                Constant.hashmap_answer_state.put(""+reviewquestion.get(ad).getId(), false);
            }

//            ActivityReviewQuestion.getInstance().ShowHideSubmitButton();

            if (reviewquestion.get(ad).getD().getIsAnswer().equalsIgnoreCase("true")) {
                arrayboolean.set(ad, true);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            } else {
                arrayboolean.set(ad, false);

                if (areAllTrue(arrayboolean)) {
                    Constant.isAllAnswerTrue = true;
                } else {
                    Constant.isAllAnswerTrue = false;
                }
            }
        }

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
            if(Constant.hashmap_asnwer_string_review_question.size() == reviewquestion.size()) {
                checkAnswers();
            } else {
                for (String key : Constant.hashmap_asnwer_string_review_question.keySet()) {
                    int intKey = Integer.parseInt("" + key);
                    if (intKey == reviewquestion.get(lp - 1).getId()) {
                        String selectedOption = Constant.hashmap_asnwer_string_review_question.get(key);
                        if (selectedOption.equalsIgnoreCase("a")) {
                            selectionA();
                            break;
                        } else if (selectedOption.equalsIgnoreCase("b")) {
                            selectionB();
                            break;
                        } else if (selectedOption.equalsIgnoreCase("c")) {
                            selectionC();
                            break;
                        } else if (selectedOption.equalsIgnoreCase("d")) {
                            selectionD();
                            break;
                        }
                    } else {
                        resetSelection();
                    }
                }
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

            for (String key : Constant.hashmap_asnwer_string_review_question.keySet()) {
                int intKey = Integer.parseInt("" + key);
                if (intKey == reviewquestion.get(lp).getId()) {
                    String selectedOption = Constant.hashmap_asnwer_string_review_question.get(key);
                    if (selectedOption.equalsIgnoreCase("a")) {
                        selectionA();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        selectionB();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        selectionC();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        selectionD();
                        break;
                    }
                } else {
                    resetSelection();
                }
            }
        }
    }

    private void checkAnswers() {

        boolean check = false;
        for (int i = 0; i < reviewquestion.size(); i++) {
            if(Constant.hashmap_answer_state.get(""+reviewquestion.get(i).getId())) {
                check = true;
            } else {
                check = false;
                break;
            }
        }

        if(check) {
            Log.e("*+*+*","All true");
            // Take api call to submit the review questions and set the quiz to the first question..
            question_showing = 1;

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

        } else {
            Log.e("*+*+*","Oops");

            question_showing = 1;

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

            for (String key : Constant.hashmap_asnwer_string_review_question.keySet()) {
                int intKey = Integer.parseInt("" + key);
                if (intKey == reviewquestion.get(0).getId()) {
                    String selectedOption = Constant.hashmap_asnwer_string_review_question.get(key);
                    if (selectedOption.equalsIgnoreCase("a")) {
                        selectionA();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        selectionB();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        selectionC();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        selectionD();
                        break;
                    }
                } else {
                    resetSelection();
                }
            }

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

            for (String key : Constant.hashmap_asnwer_string_review_question.keySet()) {
                int intKey = Integer.parseInt("" + key);
                if (intKey == reviewquestion.get(dv).getId()) {
                    String selectedOption = Constant.hashmap_asnwer_string_review_question.get(key);
                    if (selectedOption.equalsIgnoreCase("a")) {
                        Log.e("*+*+*", "Selected Option is a");
                        selectionA();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("b")) {
                        Log.e("*+*+*", "Selected Option is b");
                        selectionB();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("c")) {
                        Log.e("*+*+*", "Selected Option is c");
                        selectionC();
                        break;
                    } else if (selectedOption.equalsIgnoreCase("d")) {
                        Log.e("*+*+*", "Selected Option is d");
                        selectionD();
                        break;
                    }
                } else {
                    Log.e("*+*+*", "Reset Selection");
                    resetSelection();
                }
            }
        }

        Log.e("*+*+*", "Hashmamp on prev : " + Constant.hashmap_asnwer_string_review_question);

    }
}
