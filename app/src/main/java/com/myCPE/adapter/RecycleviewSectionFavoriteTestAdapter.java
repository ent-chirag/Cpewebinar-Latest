package com.myCPE.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myCPE.R;
import com.myCPE.view.GridSpacingItemDecoration;

import java.util.List;


public class RecycleviewSectionFavoriteTestAdapter extends RecyclerView.Adapter<RecycleviewSectionFavoriteTestAdapter.SectionViewHolder> {


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

    private List<com.myCPE.model.view_interest_favorite.TopicOfInterestsItem> mlist;
    public static Context mContext;


    public RecycleviewSectionFavoriteTestAdapter(Context context, List<com.myCPE.model.view_interest_favorite.TopicOfInterestsItem> mlist) {
        this.mContext = context;
        this.mlist = mlist;


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

        holder.tv_category.setText(mlist.get(position).getName());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        int spacing = (int) mContext.getResources().getDimension(R.dimen._3sdp); // 50px
        boolean includeEdge = false;
        holder.item_recycler_view.addItemDecoration(new GridSpacingItemDecoration(3, spacing, includeEdge));
        holder.item_recycler_view.setLayoutManager(gridLayoutManager);

        List<com.myCPE.model.view_interest_favorite.TagsItem> itemsInSection = mlist.get(position).getTags();

        if (itemsInSection.size() > 0) {
            NeastedFavoriteAdapter adapter = new NeastedFavoriteAdapter(mContext, itemsInSection);
            holder.item_recycler_view.setAdapter(adapter);
        }


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


}

