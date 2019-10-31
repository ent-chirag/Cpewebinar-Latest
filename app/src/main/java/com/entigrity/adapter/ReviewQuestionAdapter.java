package com.entigrity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.activity.ActivityReviewQuestion;
import com.entigrity.model.review_question.ReviewQuestionsItem;

import java.util.List;

public class ReviewQuestionAdapter extends RecyclerView.Adapter<ReviewQuestionAdapter.ViewHolder> {

    private Context mContext;
    public List<ReviewQuestionsItem> reviewquestion;
    public List<Boolean> arrayboolean;
    LayoutInflater mInflater;


    public ReviewQuestionAdapter(Context mContext, List<ReviewQuestionsItem> reviewquestion, List<Boolean> arrayboolean) {
        this.mContext = mContext;
        this.reviewquestion = reviewquestion;
        this.arrayboolean = arrayboolean;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_question, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        if (!reviewquestion.get(position).getQuestionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_question.setText(reviewquestion.get(position).getQuestionTitle());
        }

        int total_count = position + 1;

        viewHolder.tv_number.setText("" + total_count);

        if (!reviewquestion.get(position).getA().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_a.setText(reviewquestion.get(position).getA().getOptionTitle());
        }
        if (!reviewquestion.get(position).getB().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_b.setText(reviewquestion.get(position).getB().getOptionTitle());
        }
        if (!reviewquestion.get(position).getC().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_c.setText(reviewquestion.get(position).getC().getOptionTitle());
        }
        if (!reviewquestion.get(position).getD().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_d.setText(reviewquestion.get(position).getD().getOptionTitle());
        }


        viewHolder.tv_ans_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewHolder.checkbox_select_a.setChecked(true);
//                viewHolder.checkbox_select_b.setChecked(false);
//                viewHolder.checkbox_select_c.setChecked(false);
//                viewHolder.checkbox_select_d.setChecked(false);
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                if (reviewquestion.get(position).getA().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);

                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.correct_ans));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                } else {
                    arrayboolean.set(position, false);


                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.wrong_ans));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));

                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                }


            }
        });


        viewHolder.tv_ans_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                viewHolder.checkbox_select_a.setChecked(false);
//                viewHolder.checkbox_select_b.setChecked(true);
//                viewHolder.checkbox_select_c.setChecked(false);
//                viewHolder.checkbox_select_d.setChecked(false);
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                if (reviewquestion.get(position).getB().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);

                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.correct_ans));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                } else {
                    arrayboolean.set(position, false);

                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.wrong_ans));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                }

            }
        });

        viewHolder.tv_ans_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewHolder.checkbox_select_a.setChecked(false);
//                viewHolder.checkbox_select_b.setChecked(false);
//                viewHolder.checkbox_select_c.setChecked(true);
//                viewHolder.checkbox_select_d.setChecked(false);
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                if (reviewquestion.get(position).getC().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);

                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.correct_ans));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                } else {
                    arrayboolean.set(position, false);


                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.wrong_ans));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                }
            }
        });


        viewHolder.tv_ans_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                viewHolder.checkbox_select_a.setChecked(false);
//                viewHolder.checkbox_select_b.setChecked(false);
//                viewHolder.checkbox_select_c.setChecked(false);
//                viewHolder.checkbox_select_d.setChecked(true);
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));

                if (reviewquestion.get(position).getD().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);


                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.correct_ans));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                } else {
                    arrayboolean.set(position, false);

                    viewHolder.tv_ans_a.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_b.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_c.setTextColor(mContext.getResources().getColor(R.color.subcategory_topics));
                    viewHolder.tv_ans_d.setTextColor(mContext.getResources().getColor(R.color.wrong_ans));
                    if (areAllTrue(arrayboolean)) {
                        ActivityReviewQuestion.getInstance().SubmitButtonVisible();
                    } else {
                        ActivityReviewQuestion.getInstance().SubmitButtonInVisible();
                    }
                }
            }
        });


    }

    public static boolean areAllTrue(List<Boolean> array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }


    @Override
    public int getItemCount() {
        return reviewquestion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_number, tv_question, tv_ans_a, tv_ans_b, tv_ans_c, tv_ans_d;
//        public CheckBox checkbox_select_a, checkbox_select_b, checkbox_select_c, checkbox_select_d;
        public RelativeLayout checkbox_select_a, checkbox_select_b, checkbox_select_c, checkbox_select_d;
        public LinearLayout lv_A, lv_B, lv_C, lv_D;

        private ViewHolder(View itemView) {
            super(itemView);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);

//            checkbox_select_a = (CheckBox) itemView.findViewById(R.id.checkbox_select_a);
            checkbox_select_a = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_a);
//            checkbox_select_b = (CheckBox) itemView.findViewById(R.id.checkbox_select_b);
            checkbox_select_b = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_b);
//            checkbox_select_c = (CheckBox) itemView.findViewById(R.id.checkbox_select_c);
            checkbox_select_c = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_c);
//            checkbox_select_d = (CheckBox) itemView.findViewById(R.id.checkbox_select_d);
            checkbox_select_d = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_d);


            tv_ans_a = (TextView) itemView.findViewById(R.id.tv_ans_a);
            tv_ans_b = (TextView) itemView.findViewById(R.id.tv_ans_b);
            tv_ans_c = (TextView) itemView.findViewById(R.id.tv_ans_c);
            tv_ans_d = (TextView) itemView.findViewById(R.id.tv_ans_d);


            lv_A = (LinearLayout) itemView.findViewById(R.id.lv_A);
            lv_B = (LinearLayout) itemView.findViewById(R.id.lv_B);
            lv_C = (LinearLayout) itemView.findViewById(R.id.lv_C);
            lv_D = (LinearLayout) itemView.findViewById(R.id.lv_D);


        }
    }
}
