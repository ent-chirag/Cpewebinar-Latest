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
    private int question_showing = 0;

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

                        Log.e("*+*+*","Review Questions size : "+reviewquestion.size());

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
                            question_showing++;

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
                Log.e("*+*+*","nextPrev click on next button question showing : "+question_showing);
                if(question_showing == 0) {
                    Log.e("*+*+*","nextPrev click on next button question showing == 0 : "+question_showing);

                    binding.relPrev.setVisibility(View.INVISIBLE);
                    binding.tvNumber.setText("1");
                    binding.tvQuestion.setText(reviewquestion.get(0).getQuestionTitle());
                    binding.tvAnsA.setText(reviewquestion.get(0).getA().getOptionTitle());
                    binding.tvAnsB.setText(reviewquestion.get(0).getB().getOptionTitle());
                    binding.tvAnsC.setText(reviewquestion.get(0).getC().getOptionTitle());
                    binding.tvAnsD.setText(reviewquestion.get(0).getD().getOptionTitle());

                    question_showing++;
                } else {

                    if(question_showing != reviewquestion.size()) {
//                        int j = question_showing+1;
                        int j = question_showing;
                        binding.relPrev.setVisibility(View.VISIBLE);
                        binding.tvNumber.setText(""+j);
                        binding.tvQuestion.setText(reviewquestion.get(j).getQuestionTitle());
                        binding.tvAnsA.setText(reviewquestion.get(j).getA().getOptionTitle());
                        binding.tvAnsB.setText(reviewquestion.get(j).getB().getOptionTitle());
                        binding.tvAnsC.setText(reviewquestion.get(j).getC().getOptionTitle());
                        binding.tvAnsD.setText(reviewquestion.get(j).getD().getOptionTitle());

                        question_showing++;
                    }
                }

                if(question_showing == reviewquestion.size()) {
                    binding.tvNextSubmit.setText("Submit");
                } else {
                    binding.tvNextSubmit.setText("Next");
                }
                break;

            case R.id.relPrev:

                if(question_showing != 0) {
                    question_showing--;

                    int j = question_showing;
                    binding.relPrev.setVisibility(View.VISIBLE);
                    binding.tvNumber.setText(""+j);
                    binding.tvQuestion.setText(reviewquestion.get(j).getQuestionTitle());
                    binding.tvAnsA.setText(reviewquestion.get(j).getA().getOptionTitle());
                    binding.tvAnsB.setText(reviewquestion.get(j).getB().getOptionTitle());
                    binding.tvAnsC.setText(reviewquestion.get(j).getC().getOptionTitle());
                    binding.tvAnsD.setText(reviewquestion.get(j).getD().getOptionTitle());
                }

                break;

            case R.id.tv_next_submit:
                break;

            case R.id.tv_prev:
                break;
        }
    }
}
