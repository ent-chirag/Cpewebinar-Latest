package com.myCPE.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myCPE.R;
import com.myCPE.model.testimonial.WebinarTestimonialItem;

import java.util.List;

public class TestimonialAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<WebinarTestimonialItem> mList;
    LayoutInflater mInflater;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isLoadingAdded = false;

    public TestimonialAdapter(Context mContext, List<WebinarTestimonialItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        RecyclerView.ViewHolder vh;
        if (viewtype == VIEW_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);


        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_tetimonial, parent, false);

            vh = new ViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {

            if (!mList.get(position).getFirstName().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_username_name.setText(mList.get(position).getFirstName() + " " + mList.get(position).getLastName() +
                        " " + mList.get(position).getDesignation());
            }

            if (!mList.get(position).getReview().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_review_decription.setText(mList.get(position).getReview());
            }

            if (!mList.get(position).getDate().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_date.setText(mList.get(position).getDate());
            }

            if (!mList.get(position).getRate().equalsIgnoreCase("")) {

                if (mList.get(position).getRate().equalsIgnoreCase("0")) {

                    ((ViewHolder) viewHolder).iv_testimonial_star.setImageResource(R.mipmap.rev_star_zero);

                } else if (mList.get(position).getRate().equalsIgnoreCase("1")) {

                    ((ViewHolder) viewHolder).iv_testimonial_star.setImageResource(R.mipmap.rev_star_one);

                } else if (mList.get(position).getRate().equalsIgnoreCase("2")) {

                    ((ViewHolder) viewHolder).iv_testimonial_star.setImageResource(R.mipmap.rev_star_two);

                } else if (mList.get(position).getRate().equalsIgnoreCase("3")) {

                    ((ViewHolder) viewHolder).iv_testimonial_star.setImageResource(R.mipmap.rev_star_three);

                } else if (mList.get(position).getRate().equalsIgnoreCase("4")) {

                    ((ViewHolder) viewHolder).iv_testimonial_star.setImageResource(R.mipmap.rev_star_four);

                } else if (mList.get(position).getRate().equalsIgnoreCase("5")) {

                    ((ViewHolder) viewHolder).iv_testimonial_star.setImageResource(R.mipmap.rev_star_five);
                }


            }


        }/*else {
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }*/

    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.loadmore_progress);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return (position == mList.size() - 1 && isLoadingAdded) ? VIEW_ITEM : VIEW_PROG;
    }


    public void add(WebinarTestimonialItem webinarTestimonialItem) {
        mList.add(webinarTestimonialItem);
        notifyItemInserted(mList.size());
    }

    public void addAll(List<WebinarTestimonialItem> mcList) {
        for (WebinarTestimonialItem mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new WebinarTestimonialItem());
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_username_name, tv_review_decription, tv_date;
        ImageView iv_testimonial_star;

        private ViewHolder(View itemView) {
            super(itemView);
            tv_username_name = (TextView) itemView.findViewById(R.id.tv_username_name);
            tv_review_decription = (TextView) itemView.findViewById(R.id.tv_review_decription);
            iv_testimonial_star = (ImageView) itemView.findViewById(R.id.iv_testimonial_star);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);


        }
    }
}
