package com.myCPE.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.CompanyProfileActivity;
import com.myCPE.activity.LoginActivity;
import com.myCPE.activity.PdfViewActivity;
import com.myCPE.activity.SignUpActivity;
import com.myCPE.activity.SpeakerProfileActivity;
import com.myCPE.activity.WebinarDetailsActivity;
import com.myCPE.model.homewebinarnew.MyCertificateLinksItem;
import com.myCPE.model.registerwebinar.ModelRegisterWebinar;
import com.myCPE.model.webinar_like_dislike.Webinar_Like_Dislike_Model;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.view.SimpleDividerItemDecoration;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContinueWatchAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isLoadingAdded = false;
    private Context mContext;
    LayoutInflater mInflater;
    //    public List<com.myCPE.model.homewebinarnew.WebinarItem> mList;
    public List<com.myCPE.model.homewebinarnew.RecentWebinarItem> recentList;
    private APIService mAPIService;
    public Dialog dialogCertificate;
    public CertificatesListHomeMyWebinarPopUpAdapter certificatesListPopUpAdapter;
    ProgressDialog progressDialog;
    public Dialog myDialog;
    private TextView tv_cancel, tv_login, tv_create_account;
    //    public String certificate_link = "";
    LinearLayoutManager linearLayoutManager;
    String join_url = "";


    public ContinueWatchAdapter(Context mContext, List<com.myCPE.model.homewebinarnew.RecentWebinarItem> recentList) {
        this.mContext = mContext;
//        this.mList = mList;
        this.recentList = recentList;
        mAPIService = ApiUtilsNew.getAPIService();
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
                R.layout.row_continue_watch, parent, false);

        vh = new HomeViewHolder(v);
//        }
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof HomeViewHolder) {
            Constant.Log("size", "" + recentList.size());

//            tvWebinarState, tvWebinarCredit, tvWebinarPrice, txtWebinarTitle, txtWebinarAuthor, txtWebinarDate
            ((HomeViewHolder) viewHolder).txtWebinarTitle.setText(recentList.get(position).getWebinarTitle());

            if (!recentList.get(position).getWebinarImage().equalsIgnoreCase("")) {
                ((HomeViewHolder) viewHolder).imgBanner.setVisibility(View.VISIBLE);
                /*Picasso.with(mContext).load(recentList.get(position).getWebinarImage())
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into(((HomeViewHolder) viewHolder).imgBanner);*/
            } else {
                ((HomeViewHolder) viewHolder).imgBanner.setVisibility(View.GONE);
            }

            ((HomeViewHolder) viewHolder).imgContinueWatchPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("*+*+*","Clicked on Play icon position is : "+position);
                    Intent i = new Intent(mContext, WebinarDetailsActivity.class);
                    i.putExtra(mContext.getResources().getString(R.string.pass_webinar_id), 2086);
                    i.putExtra(mContext.getResources().getString(R.string.screen_detail), 1);
                    i.putExtra(mContext.getResources().getString(R.string.pass_webinar_type), "ON-DEMAND");
                    Constant.isFromSpeakerCompanyWebinarList = false;
                    mContext.startActivity(i);
                    ((Activity) mContext).finish();
                }
            });

        }
    }

    public void add(com.myCPE.model.homewebinarnew.RecentWebinarItem recentWebinarItem) {
        recentList.add(recentWebinarItem);
        notifyItemInserted(recentList.size());
    }

    public void addAll(List<com.myCPE.model.homewebinarnew.RecentWebinarItem> recentWebinarItems) {
        for (com.myCPE.model.homewebinarnew.RecentWebinarItem mc : recentWebinarItems) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new com.myCPE.model.homewebinarnew.RecentWebinarItem());
    }

    @Override
    public int getItemCount() {
        return recentList == null ? 0 : recentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == recentList.size() - 1 && isLoadingAdded) ? VIEW_ITEM : VIEW_PROG;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        TextView txtWebinarTitle;
        RelativeLayout relBGShapeCard;
        ImageView imgBanner, imgContinueWatchPlay;

        private HomeViewHolder(View itemView) {
            super(itemView);

//            txtWebinarRegistrationState = (TextView) itemView.findViewById(R.id.txtWebinarRegistrationState);
            txtWebinarTitle = (TextView) itemView.findViewById(R.id.txtWebinarTitle);
            relBGShapeCard = (RelativeLayout) itemView.findViewById(R.id.relBGShapeCard);
            imgBanner = (ImageView) itemView.findViewById(R.id.imgBanner);
            imgContinueWatchPlay = (ImageView) itemView.findViewById(R.id.imgContinueWatchPlay);

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
