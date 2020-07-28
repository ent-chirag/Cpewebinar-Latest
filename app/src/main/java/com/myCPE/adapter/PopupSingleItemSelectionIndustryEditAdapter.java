package com.myCPE.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myCPE.R;
import com.myCPE.activity.EditProfileActivity;
import com.myCPE.model.Job_title.JobTitleItem;
import com.myCPE.model.industry.IndustriesListItem;
import com.myCPE.utility.Constant;

import java.util.List;

//import com.myCPE.activity.SignUpActivity;

public class PopupSingleItemSelectionIndustryEditAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isLoadingAdded = false;
    private Context mContext;
    LayoutInflater mInflater;
//    public List<com.myCPE.model.homewebinarnew.WebinarItem> mList;
//    public List<SignUpActivity.getcountryarraylistNew> mListNew;
//    public List<CountryModel> mListNew;
//    public List<CountryItem> mListNew;
//    public List<StateItem> mListNew;
//    public List<CityItem> mListNew;
//    public List<JobTitleItem> mListNew;
    public List<IndustriesListItem> mListNew;
    public Dialog dialogCertificate;

//    public PopupSingleItemSelectionAdapter(Context mContext, List<com.myCPE.model.homewebinarnew.WebinarItem> mList) {
//    public PopupSingleItemSelectionJobTitleEditAdapter(Context mContext, List<CityItem> mListNew) {
//    public PopupSingleItemSelectionIndustryEditAdapter(Context mContext, List<JobTitleItem> mListNew) {
    public PopupSingleItemSelectionIndustryEditAdapter(Context mContext, List<IndustriesListItem> mListNew) {
        this.mContext = mContext;
        this.mListNew = mListNew;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        dialogCertificate = new Dialog(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        RecyclerView.ViewHolder vh;

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.row_popup_single_item, parent, false);

            vh = new HomeViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof HomeViewHolder) {
            ((HomeViewHolder) viewHolder).txtCountryName.setText(mListNew.get(position).getName());

//            if(!Constant.selectedCityIdSU.equalsIgnoreCase("0")) {
            Log.e("*+*+*","Job Title SU");
//            if(!Constant.selectedJobTitleIdSU.equalsIgnoreCase("0")) {
            if(!Constant.selectedIndustryIdSU.equalsIgnoreCase("0")) {
//                if(Integer.parseInt(Constant.selectedCityIdSU) == mListNew.get(position).getId()) {
//                if(Integer.parseInt(Constant.selectedJobTitleIdSU) == mListNew.get(position).getId()) {
                if(Integer.parseInt(Constant.selectedIndustryIdSU) == mListNew.get(position).getId()) {
                    ((HomeViewHolder) viewHolder).txtCountryName.setBackgroundResource(R.drawable.rounded_background_yellow_selected);
                } else {
                    ((HomeViewHolder) viewHolder).txtCountryName.setBackgroundResource(R.drawable.rounded_background_blue_white);
                }
            }

            ((HomeViewHolder) viewHolder).txtCountryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("*+*+*","Selected Company name is : "+mListNew.get(position).getName());
                    Log.e("*+*+*","Selected Company id is : "+mListNew.get(position).getId());

//                    Constant.selectedCityIdSU = "" + mListNew.get(position).getId();
//                    Constant.selectedJobTitleIdSU = "" + mListNew.get(position).getId();
                    Constant.selectedIndustryIdSU = "" + mListNew.get(position).getId();
//                    Constant.selectedCityNameSU = "" + mListNew.get(position).getName();
                    Constant.selectedIndustryNameSU = "" + mListNew.get(position).getName();
//                    Constant.selectedCityPositionSU = "" + position;
                    Constant.selectedIndustryPositionSU = "" + position;

//                    SignUpActivity.getInstance().selectCity();
//                    EditProfileActivity.getInstance().selectCityEdit();
                    EditProfileActivity.getInstance().selectJobTitleEdit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListNew == null ? 0 : mListNew.size();
    }


    @Override
    public int getItemViewType(int position) {
        return (position == mListNew.size() - 1 && isLoadingAdded) ? VIEW_ITEM : VIEW_PROG;
    }


    public static class HomeViewHolder extends RecyclerView.ViewHolder {

//        RelativeLayout rel_item;
        TextView txtCountryName;

        private HomeViewHolder(View itemView) {
            super(itemView);

//            ivfavorite = (ImageView) itemView.findViewById(R.id.ivfavorite);
            txtCountryName = (TextView) itemView.findViewById(R.id.txtCountryName);

        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.loadmore_progress);
        }
    }
}
