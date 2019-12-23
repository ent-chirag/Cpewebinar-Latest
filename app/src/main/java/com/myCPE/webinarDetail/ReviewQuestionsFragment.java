package com.myCPE.webinarDetail;

import android.app.ProgressDialog;
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

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.myCPE.utility.Constant.arraylistselectedquestionreview;
import static com.myCPE.utility.Constant.arraylistselectedreviewanswerreview;

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

                            /*adapter = new ReviewQuestionAdapter(context, reviewquestion, arrayboolean);
                            binding.recyclerviewReviewQuestion.setAdapter(adapter);*/
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



                            /*if (reviewquestion.size() > 0) {
                                binding.recyclerviewReviewQuestion.setVisibility(View.VISIBLE);
                                binding.tvNodatafound.setVisibility(View.GONE);
                            } else {
                                binding.tvNodatafound.setVisibility(View.VISIBLE);
                                binding.recyclerviewReviewQuestion.setVisibility(View.GONE);
                            }*/


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

    private void resetAnswer() {
        // When ever user is clicking on the next or the previous button..
        // On this condition we have to check for the question is changed or the same..
        // If the question is changed then have to go for the checking the below conditions..
        // We have to check the condition for the is user answered this question or not..
        // But if the user didn't answered the question then in that case we have to reset all selections..
        // If user has answered the question then we have to set that answer as selected..
        // Now for the answered or not in that case we have to add the answers on the hashmap with the ids for the question id..

        // New Logic..
        // Whenever user will click on the next and previous button then we have already placed the conditions there..
        // In those conditions we are already checking for question state is changed or not..
        // now if we have the question changed state..
        // Then we have to just check the condition for if the question is answered or not..
        // If not answered then have to reset all selection there..
        // If answered there then have to check for the which option is given from the help of hashmap with the id as param..

        binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
        binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

        binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
        binding.tvResponseTag.setVisibility(View.GONE);
        binding.tvAnsResponse.setVisibility(View.GONE);

    }

    private void answerA() {

        int aa = question_showing-1;
        if (reviewquestion.get(aa).isAnswerable()) {
            binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
            binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

            binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvResponseTag.setVisibility(View.GONE);
            binding.tvAnsResponse.setVisibility(View.GONE);

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(aa).getId(), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(aa).getId(), "a");

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

    private void answerB() {
        int ab = question_showing-1;
        if (reviewquestion.get(ab).isAnswerable()) {
            binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
            binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

            binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvResponseTag.setVisibility(View.GONE);
            binding.tvAnsResponse.setVisibility(View.GONE);

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(ab), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(ab).getId(), "b");

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

    private void answerC() {

        int ac = question_showing-1;
        if (reviewquestion.get(ac).isAnswerable()) {
            binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));
            binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));

            binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvResponseTag.setVisibility(View.GONE);
            binding.tvAnsResponse.setVisibility(View.GONE);

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(ac), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(ac).getId(), "c");

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

    private void answerD() {

        int ad = question_showing-1;
        if (reviewquestion.get(ad).isAnswerable()) {
            binding.checkboxSelectA.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectB.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectC.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_unchecked));
            binding.checkboxSelectD.setBackgroundDrawable(getResources().getDrawable(R.drawable.rev_checked));

            binding.tvAnsA.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsB.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsC.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvAnsD.setTextColor(getResources().getColor(R.color.subcategory_topics));
            binding.tvResponseTag.setVisibility(View.GONE);
            binding.tvAnsResponse.setVisibility(View.GONE);

            Constant.hashmap_asnwer_review_question.put("" + reviewquestion.get(ad), true);
            Constant.hashmap_asnwer_string_review_question.put("" + reviewquestion.get(ad).getId(), "d");

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

    }

    private void btnPrev() {

        binding.tvNextSubmit.setText("Next");
        Log.e("*+*+*", "Question showing default is : " + question_showing);
        if (question_showing == 1) {
            Log.e("*+*+*", "Question showing is if : " + question_showing);
            binding.tvNumber.setText("1");
            binding.tvQuestion.setText(reviewquestion.get(0).getQuestionTitle());
            binding.tvAnsA.setText(reviewquestion.get(0).getA().getOptionTitle());
            binding.tvAnsB.setText(reviewquestion.get(0).getB().getOptionTitle());
            binding.tvAnsC.setText(reviewquestion.get(0).getC().getOptionTitle());
            binding.tvAnsD.setText(reviewquestion.get(0).getD().getOptionTitle());

        } else {
            question_showing--;
            binding.tvNumber.setText("" + question_showing);
            int dv = question_showing - 1;
            binding.tvQuestion.setText(reviewquestion.get(dv).getQuestionTitle());
            binding.tvAnsA.setText(reviewquestion.get(dv).getA().getOptionTitle());
            binding.tvAnsB.setText(reviewquestion.get(dv).getB().getOptionTitle());
            binding.tvAnsC.setText(reviewquestion.get(dv).getC().getOptionTitle());
            binding.tvAnsD.setText(reviewquestion.get(dv).getD().getOptionTitle());

            Log.e("*+*+*", "Question showing is else : " + question_showing);
            if (question_showing == 1) {
                binding.relPrev.setVisibility(View.INVISIBLE);
            }
        }

    }
}
