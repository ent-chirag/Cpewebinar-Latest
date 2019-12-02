package com.myCPE.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myCPE.R;
import com.myCPE.utility.Constant;

import java.util.ArrayList;

public class TopicsofinterestPopUpAdapter extends RecyclerView.Adapter<TopicsofinterestPopUpAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<com.myCPE.model.topics_subcategory.TopicOfInterestsItem> mListrtopicsofinterestsubcategory = new ArrayList<>();
    LayoutInflater mInflater;


    public TopicsofinterestPopUpAdapter(Context mContext, ArrayList<com.myCPE.model.topics_subcategory.TopicOfInterestsItem> mList) {
        this.mContext = mContext;
        this.mListrtopicsofinterestsubcategory = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_row_topics_of_interset, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final com.myCPE.model.topics_subcategory.TopicOfInterestsItem tagsItem = mListrtopicsofinterestsubcategory.get(position);


        if (!tagsItem.getName().equalsIgnoreCase("")) {
            holder.tv_topics.setText(tagsItem.getName());
        }


        holder.cbselection.setEnabled(false);

        if (tagsItem.isIsChecked()) {
            holder.cbselection.setChecked(true);
        } else {
            holder.cbselection.setChecked(false);
        }


        holder.tv_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (tagsItem.isIsChecked()) {
                        tagsItem.setIsChecked(false);

                        for (int i = 0; i < mListrtopicsofinterestsubcategory.size(); i++) {
                            if (tagsItem.getId() == mListrtopicsofinterestsubcategory.get(i).getId()) {
                                mListrtopicsofinterestsubcategory.set(i, tagsItem);
                            }
                        }

                        int removed_position = Constant.arraylistselectedtopicsofinterest.indexOf(mListrtopicsofinterestsubcategory.get(position).getId());
                        Constant.arraylistselectedtopicsofinterest.remove(removed_position);


                        holder.cbselection.setChecked(false);


                    } else {
                        tagsItem.setIsChecked(true);

                        for (int i = 0; i < mListrtopicsofinterestsubcategory.size(); i++) {
                            if (tagsItem.getId() == mListrtopicsofinterestsubcategory.get(i).getId()) {
                                mListrtopicsofinterestsubcategory.set(i, tagsItem);

                            }
                        }


                        Constant.arraylistselectedtopicsofinterest.add(mListrtopicsofinterestsubcategory.get(position).getId());
                        holder.cbselection.setChecked(true);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return mListrtopicsofinterestsubcategory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbselection;
        private RelativeLayout rel_topics;
        private TextView tv_topics;


        private ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

            cbselection = (CheckBox) itemView.findViewById(R.id.cbselection);
            tv_topics = (TextView) itemView.findViewById(R.id.tv_topics);
            rel_topics = (RelativeLayout) itemView.findViewById(R.id.rel_topics);


        }
    }
}
