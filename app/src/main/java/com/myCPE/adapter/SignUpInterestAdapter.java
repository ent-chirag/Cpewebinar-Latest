package com.myCPE.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myCPE.R;
import com.myCPE.model.topicsofinterestn.TagsItem;
import com.myCPE.utility.Constant;

import java.util.ArrayList;
import java.util.List;

public class SignUpInterestAdapter extends RecyclerView.Adapter<SignUpInterestAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    List<TagsItem> mlist;
    public List<TagsItem> topicsofinterestitemfilter = new ArrayList<TagsItem>();
    LayoutInflater mInflater;
    private CustomFilter mFilter;

    public SignUpInterestAdapter(Context mContext, List<TagsItem> mList) {
        this.mContext = mContext;
        this.mlist = mList;
        this.topicsofinterestitemfilter = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mFilter = new CustomFilter();


    }

    @NonNull
    @Override
    public SignUpInterestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new SignUpInterestAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        final TagsItem tagsItem = topicsofinterestitemfilter.get(position);


        if (!tagsItem.getName().equalsIgnoreCase("")) {
            viewHolder.itemTitle.setText(tagsItem.getName());
        }

        viewHolder.cbselection.setEnabled(false);

        if (tagsItem.isIsChecked()) {
            viewHolder.cbselection.setChecked(true);
        } else {
            viewHolder.cbselection.setChecked(false);
        }


        viewHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tagsItem.isIsChecked()) {
                    tagsItem.setIsChecked(false);

                    for (int i = 0; i < mlist.size(); i++) {
                        if (tagsItem.getId() == mlist.get(i).getId()) {
                            mlist.set(i, tagsItem);
                        }
                    }

                    for (int k = 0; k < Constant.arraylistselectedvalue.size(); k++) {
                        if (tagsItem.getId() == Constant.arraylistselectedvalue.get(k)) {
                            Constant.arraylistselectedvalue.remove(k);
                        }
                    }


                    viewHolder.cbselection.setChecked(false);


                } else {
                    tagsItem.setIsChecked(true);

                    for (int i = 0; i < mlist.size(); i++) {
                        if (tagsItem.getId() == mlist.get(i).getId()) {
                            mlist.set(i, tagsItem);
                            Constant.arraylistselectedvalue.add(tagsItem.getId());
                        }
                    }


                    viewHolder.cbselection.setChecked(true);
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return topicsofinterestitemfilter.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private CheckBox cbselection;
        private RelativeLayout lv_topics_item;


        private ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

            itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            cbselection = (CheckBox) itemView.findViewById(R.id.cbselection);
            lv_topics_item = (RelativeLayout) itemView.findViewById(R.id.lv_topics_item);


        }
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence != null && charSequence.length() > 0) {
                ArrayList<TagsItem> filters = new ArrayList<>();//charSequence = charSequence.toString().toUpperCase();

                String charcter = charSequence.toString();


                if (!charcter.equalsIgnoreCase("")) {
                    for (int i = 0; i < mlist.size(); i++) {
                        if (mlist.get(i).getName().toLowerCase().contains(charcter.toLowerCase())) {
                            TagsItem tagsItem = new TagsItem();
                            tagsItem.setIsChecked(mlist.get(i).isIsChecked());
                            tagsItem.setName(mlist.get(i).getName());
                            tagsItem.setId(mlist.get(i).getId());
                            filters.add(tagsItem);
                        } else if (mlist.get(i).getName().toUpperCase().contains(charcter.toUpperCase())) {
                            TagsItem tagsItem = new TagsItem();
                            tagsItem.setIsChecked(mlist.get(i).isIsChecked());
                            tagsItem.setName(mlist.get(i).getName());
                            tagsItem.setId(mlist.get(i).getId());
                            filters.add(tagsItem);
                        }
                    }
                    results.count = filters.size();
                    results.values = filters;
                }


            } else {


                results.count = mlist.size();
                results.values = mlist;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            topicsofinterestitemfilter = (ArrayList<TagsItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
