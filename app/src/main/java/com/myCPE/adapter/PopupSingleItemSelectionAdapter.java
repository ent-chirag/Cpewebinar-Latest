package com.myCPE.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myCPE.R;
import com.myCPE.activity.SignUpActivity;
import com.myCPE.model.country.CountryItem;
import com.myCPE.model.country.CountryModel;
import com.myCPE.utility.Constant;
import com.myCPE.webservice.ApiUtilsNew;
import java.util.List;

public class PopupSingleItemSelectionAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isLoadingAdded = false;
    private Context mContext;
    LayoutInflater mInflater;
//    public List<com.myCPE.model.homewebinarnew.WebinarItem> mList;
//    public List<SignUpActivity.getcountryarraylistNew> mListNew;
//    public List<CountryModel> mListNew;
    public List<CountryItem> mListNew;
    public Dialog dialogCertificate;

//    public PopupSingleItemSelectionAdapter(Context mContext, List<com.myCPE.model.homewebinarnew.WebinarItem> mList) {
    public PopupSingleItemSelectionAdapter(Context mContext, List<CountryItem> mListNew) {
        this.mContext = mContext;
        this.mListNew = mListNew;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        dialogCertificate = new Dialog(mContext);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        RecyclerView.ViewHolder vh;
        /*if (viewtype == VIEW_ITEM) {

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);

        } else {*/
            View v = LayoutInflater.from(parent.getContext()).inflate(
//                    R.layout.row_webinar, parent, false);
                    R.layout.row_popup_single_item, parent, false);

            vh = new HomeViewHolder(v);
//        }
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof HomeViewHolder) {
//            Constant.Log("size", "" + mList.size());
            ((HomeViewHolder) viewHolder).txtCountryName.setText(mListNew.get(position).getName());
            /*if (mList.get(position).getWebinarType().equalsIgnoreCase(mContext.getResources()
                    .getString(R.string.str_live))) {
                ((HomeViewHolder) viewHolder).rel_date_and_time.setVisibility(View.VISIBLE);
            }*/

//            if(!Constant.selectedCountryNameSU.equalsIgnoreCase("Country")) {
            if(!Constant.selectedCountryIdSU.equalsIgnoreCase("0")) {
                if(Integer.parseInt(Constant.selectedCountryIdSU) == mListNew.get(position).getId()) {
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

                    Constant.selectedCountryIdSU = "" + mListNew.get(position).getId();
                    Constant.selectedCountryNameSU = "" + mListNew.get(position).getName();
                    Constant.selectedCountryPositionSU = "" + position;

                    SignUpActivity.getInstance().selectCountry();
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
