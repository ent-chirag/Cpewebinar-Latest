package com.myCPE.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myCPE.R;
import com.myCPE.model.view_topics_of_interest.TopicOfInterestsItem;
import com.myCPE.view.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class RecycleviewSectionViewProfileTestAdapter extends RecyclerView.Adapter<RecycleviewSectionViewProfileTestAdapter.SectionViewHolder> {


    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_category;
        private ImageView iv_edit;
        private RecyclerView item_recycler_view;


        public SectionViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            tv_category = (TextView) itemView.findViewById(R.id.tv_category);
            item_recycler_view = (RecyclerView) itemView.findViewById(R.id.item_recycler_view);
            iv_edit = (ImageView) itemView.findViewById(R.id.iv_edit);

        }
    }

    private ArrayList<TopicOfInterestsItem> topicsofinterestitem;
    public static Context mContext;


    public RecycleviewSectionViewProfileTestAdapter(Context context, ArrayList<TopicOfInterestsItem> topicsofinterestitem) {
        this.mContext = context;
        this.topicsofinterestitem = topicsofinterestitem;


    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SectionViewHolder holder, final int position) {


        holder.item_recycler_view.setNestedScrollingEnabled(false);

        holder.iv_edit.setVisibility(View.GONE);

        holder.tv_category.setText(topicsofinterestitem.get(position).getName());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        int spacing = (int) mContext.getResources().getDimension(R.dimen._3sdp); // 50px
        boolean includeEdge = false;
        holder.item_recycler_view.addItemDecoration(new GridSpacingItemDecoration(3, spacing, includeEdge));
        holder.item_recycler_view.setLayoutManager(gridLayoutManager);

        List<com.myCPE.model.view_topics_of_interest.TagsItem> itemsInSection = topicsofinterestitem.get(position).getTags();

        if (itemsInSection.size() > 0) {
            NeastedViewProfileAdapter adapter = new NeastedViewProfileAdapter(mContext, itemsInSection);
            holder.item_recycler_view.setAdapter(adapter);
        }


    }

    @Override
    public int getItemCount() {
        return topicsofinterestitem.size();
    }


}

