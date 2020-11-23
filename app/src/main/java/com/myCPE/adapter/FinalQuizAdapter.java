package com.myCPE.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myCPE.R;
import com.myCPE.activity.ActivityFinalQuiz;
import com.myCPE.model.final_Quiz.FinalQuizQuestionsItem;
import com.myCPE.utility.Constant;

import java.util.List;

public class FinalQuizAdapter extends RecyclerView.Adapter<FinalQuizAdapter.ViewHolder> {

    private Context mContext;
    public List<FinalQuizQuestionsItem> finalquizquestion;
    public List<Boolean> arrayboolean;
    LayoutInflater mInflater;


    public FinalQuizAdapter(Context mContext, List<FinalQuizQuestionsItem> reviewquestion, List<Boolean> arrayboolean) {
        this.mContext = mContext;
        this.finalquizquestion = reviewquestion;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        if (!finalquizquestion.get(position).getQuestionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_question.setText(finalquizquestion.get(position).getQuestionTitle());
        }

        int total_count = position + 1;

        viewHolder.tv_number.setText("" + total_count);

        if (!finalquizquestion.get(position).getA().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_a.setText(finalquizquestion.get(position).getA().getOptionTitle());
        }
        if (!finalquizquestion.get(position).getB().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_b.setText(finalquizquestion.get(position).getB().getOptionTitle());
        }
        if (!finalquizquestion.get(position).getC().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_c.setText(finalquizquestion.get(position).getC().getOptionTitle());
        }
        if (!finalquizquestion.get(position).getD().getOptionTitle().equalsIgnoreCase("")) {
            viewHolder.tv_ans_d.setText(finalquizquestion.get(position).getD().getOptionTitle());
        }

        viewHolder.lv_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "a");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("a")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getA().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }

        });

        viewHolder.tv_ans_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "a");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("a")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getA().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }
        });

        viewHolder.lv_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "b");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("b")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getB().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }
        });

        viewHolder.tv_ans_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "b");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("b")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getB().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }
        });

        viewHolder.lv_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "c");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("c")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getC().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }
        });

        viewHolder.tv_ans_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "c");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("c")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getC().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }
        });

        viewHolder.lv_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "d");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("d")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getD().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
                }
            }
        });

        viewHolder.tv_ans_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.checkbox_select_a.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_unchecked));
                viewHolder.checkbox_select_d.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rev_checked));

                Constant.hashmap_answer_string_final_question.put("" + finalquizquestion.get(position).getId(), "d");
                ActivityFinalQuiz.getInstance().ShowHideSubmitButton();

                if (finalquizquestion.get(position).getAnswer().equalsIgnoreCase("d")) {
                    finalquizquestion.get(position).setCorrect(true);
                } else {
                    finalquizquestion.get(position).setCorrect(false);
                }

                if (finalquizquestion.get(position).getD().getIsAnswer().equalsIgnoreCase("true")) {
                    arrayboolean.set(position, true);
                } else {
                    arrayboolean.set(position, false);
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
        return finalquizquestion.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_number, tv_question, tv_ans_a, tv_ans_b, tv_ans_c, tv_ans_d;
        public RelativeLayout checkbox_select_a, checkbox_select_b, checkbox_select_c, checkbox_select_d;
        public LinearLayout lv_A, lv_B, lv_C, lv_D;

        private ViewHolder(View itemView) {
            super(itemView);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_question = (TextView) itemView.findViewById(R.id.tv_question);

            checkbox_select_a = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_a);
            checkbox_select_b = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_b);
            checkbox_select_c = (RelativeLayout) itemView.findViewById(R.id.checkbox_select_c);
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
