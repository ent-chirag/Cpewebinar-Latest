package com.entigrity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.activity.WebinarDetailsActivity;
import com.entigrity.model.notification.NotificationListItem;
import com.entigrity.utility.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class NotificationAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<NotificationListItem> mList;
    LayoutInflater mInflater;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isLoadingAdded = false;

    public NotificationAdapter(Context mContext, List<NotificationListItem> mList) {
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
                    R.layout.row_notificationlist, parent, false);

            vh = new ViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {


            if (!mList.get(position).getNotificationMessage().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_notification_title.setText(mList.get(position).getNotificationMessage());
            }


            ((ViewHolder) viewHolder).tv_notification_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, WebinarDetailsActivity.class);
                    i.putExtra(mContext.getResources().getString(R.string.pass_webinar_id), mList
                            .get(position).getWebinarId());
                    i.putExtra(mContext.getResources().getString(R.string.screen_detail), 3);
                    i.putExtra(mContext.getResources().getString(R.string.pass_webinar_type), mList
                            .get(position).getWebinartype());
                    mContext.startActivity(i);
                    ((Activity) mContext).finish();
                }
            });


            if (mList.get(position).getTimestamp() != 0) {

                String notificationdate = getDateCurrentTimeZone(mList.get(position).getTimestamp());

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                try {
                    Date pastdate = sdf.parse(notificationdate);
                    Date currentdate = new Date();
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(currentdate.getTime() - pastdate.getTime());
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(currentdate.getTime() - pastdate.getTime());
                    long hours = TimeUnit.MILLISECONDS.toHours(currentdate.getTime() - pastdate.getTime());
                    long days = TimeUnit.MILLISECONDS.toDays(currentdate.getTime() - pastdate.getTime());


                    if (seconds < 60) {
                        ((ViewHolder) viewHolder).tv_notification_time.setText("" + seconds + " seconds ago");
                    } else if (minutes < 60) {
                        ((ViewHolder) viewHolder).tv_notification_time.setText("" + minutes + " minutes ago");
                    } else if (hours < 24) {
                        ((ViewHolder) viewHolder).tv_notification_time.setText("" + hours + " hours ago");
                    } else {
                        ((ViewHolder) viewHolder).tv_notification_time.setText("" + days + " days ago");
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
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


    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            //sdf.setTimeZone(TimeZone.getDefault());
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }


    @Override
    public int getItemViewType(int position) {
        return (position == mList.size() - 1 && isLoadingAdded) ? VIEW_ITEM : VIEW_PROG;

    }


    public void add(NotificationListItem notificationListItem) {
        mList.add(notificationListItem);
        notifyItemInserted(mList.size());
    }

    public void addAll(List<NotificationListItem> mcList) {
        for (NotificationListItem mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new NotificationListItem());
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_notification_image;
        TextView tv_notification_title, tv_notification_time;
        RelativeLayout rel_notification_body;


        private ViewHolder(View itemView) {
            super(itemView);

            iv_notification_image = (ImageView) itemView.findViewById(R.id.iv_notification_image);
            tv_notification_title = (TextView) itemView.findViewById(R.id.tv_notification_title);
            tv_notification_time = (TextView) itemView.findViewById(R.id.tv_notification_time);
            rel_notification_body = (RelativeLayout) itemView.findViewById(R.id.rel_notification_body);


        }
    }
}
