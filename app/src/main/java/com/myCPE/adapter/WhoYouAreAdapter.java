package com.myCPE.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myCPE.R;

import java.util.ArrayList;

public class WhoYouAreAdapter extends RecyclerView.Adapter<WhoYouAreAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> whoshouldattend;
    LayoutInflater mInflater;

    public WhoYouAreAdapter(Context mContext, ArrayList<String> whoshouldattend) {
        this.mContext = mContext;
        this.whoshouldattend = whoshouldattend;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_who_shold_attend, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        viewHolder.tv_who_attend.setText(whoshouldattend.get(position));


    }


    @Override
    public int getItemCount() {
        return whoshouldattend.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_who_attend;


        private ViewHolder(View itemView) {
            super(itemView);
            tv_who_attend = (TextView) itemView.findViewById(R.id.tv_who_attend);


        }
    }
}
