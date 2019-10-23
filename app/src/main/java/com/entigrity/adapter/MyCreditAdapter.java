package com.entigrity.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.entigrity.R;
import com.entigrity.activity.ActivityEvolutionForm;
import com.entigrity.activity.PdfViewActivity;
import com.entigrity.activity.WebinarDetailsActivity;
import com.entigrity.model.My_Credit.MyCreditsItem;
import com.entigrity.utility.Constant;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class MyCreditAdapter extends RecyclerView.Adapter implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Context mContext;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean isLoadingAdded = false;
    LayoutInflater mInflater;
    private List<MyCreditsItem> mList = new ArrayList<MyCreditsItem>();
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    ProgressDialog mProgressDialog;
    DownloadTask downloadTask;
    //  public String certificate_link = "";
    private DownloadManager downloadManager;
    private long refid;
    ArrayList<Long> list = new ArrayList<>();


    public MyCreditAdapter(Context mContext, List<MyCreditsItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

       /* mContext.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));*/

    }


    public void add(MyCreditsItem myCreditsItem) {
        mList.add(myCreditsItem);
        notifyItemInserted(mList.size());
    }

    public void addAll(List<MyCreditsItem> mcList) {
        for (MyCreditsItem mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new MyCreditsItem());
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
                    R.layout.row_mycredit, parent, false);

            vh = new ViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {

            Constant.Log("size", "" + mList.size());


            if (!mList.get(position).getCertificateLink().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).btn_certification_download.setVisibility(View.VISIBLE);
                ((ViewHolder) viewHolder).tv_webinar_status.setVisibility(View.GONE);
                ((ViewHolder) viewHolder).btn_certification_download.setText(mList.get(position).getWebinarStatus());
            } else {
                ((ViewHolder) viewHolder).tv_webinar_status.setVisibility(View.VISIBLE);
                ((ViewHolder) viewHolder).btn_certification_download.setVisibility(View.GONE);
            }

          /*  if (!mList.get(position).getCertificateLink().equalsIgnoreCase("")) {
                certificate_link = mList.get(position).getCertificateLink();
            }*/


            ((ViewHolder) viewHolder).btn_certification_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndroidVersion(mList.get(position).getCertificateLink());
                }
            });

            ((ViewHolder) viewHolder).tv_webinar_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mList.get(position).getWebinarStatus().equalsIgnoreCase(mContext.getResources().getString(R.string.str_webinar_status_enroll))) {
                        String url = mList.get(position).getJoinUrl();
                        if (!url.equalsIgnoreCase("")) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            mContext.startActivity(i);
                        } else {
                            Constant.toast(mContext, mContext.getResources().getString(R.string.str_joinlink_not_avilable));
                        }
                    } else if (mList.get(position).getWebinarStatus().equalsIgnoreCase(mContext.getResources().getString(R.string.str_webinar_status_in_progress))) {
                        String url = mList.get(position).getJoinUrl();
                        if (!url.equalsIgnoreCase("")) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            mContext.startActivity(i);
                        }
                    }


                }
            });


            if (!mList.get(position).getWebinarTitle().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_webinar_title.setText(mList.get(position).getWebinarTitle());
            }
            if (!mList.get(position).getSpeakerName().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_speaker_name.setText(mList.get(position).getSpeakerName());
            }

            if (mList.get(position).getWebinarCreditType().equalsIgnoreCase("CPE/CE")) {
                ((ViewHolder) viewHolder).tv_credit.setText(mList.get(position).getCredit() + " CPE , "
                        + mList.get(position).getCeCredit() + " CE");
            } else if (mList.get(position).getWebinarCreditType().equalsIgnoreCase("CPE")) {
                ((ViewHolder) viewHolder).tv_credit.setText(mList.get(position).getCredit() + " CPE");

            } else if (mList.get(position).getWebinarCreditType().equalsIgnoreCase("CE")) {
                ((ViewHolder) viewHolder).tv_credit.setText(mList.get(position).getCeCredit() + " CE");
            }


            if (!mList.get(position).getSubject().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_job_title.setText(mList.get(position).getSubject());
            }
            if (!mList.get(position).getWebinarType().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).btn_webinar_type.setText(mList.get(position).getWebinarType());
            }

            if (!mList.get(position).getWebinarStatus().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_webinar_status.setText(mList.get(position).getWebinarStatus());
            }


            ((ViewHolder) viewHolder).rel_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, WebinarDetailsActivity.class);
                    i.putExtra(mContext.getResources().getString(R.string.pass_webinar_id), Integer.parseInt(mList
                            .get(position).getWebinarId()));
                    i.putExtra(mContext.getResources().getString(R.string.screen_detail), 2);
                    i.putExtra(mContext.getResources().getString(R.string.pass_webinar_type), mList
                            .get(position).getWebinarType());

                    mContext.startActivity(i);


                }
            });

            if (!mList.get(position).getHostDate().equalsIgnoreCase("")) {
                StringTokenizer tokens = new StringTokenizer(mList.get(position).getHostDate(), " ");
                String day = tokens.nextToken();// this will contain year
                String month = tokens.nextToken();//this will contain month
                String year = tokens.nextToken();//this will contain day


                ((ViewHolder) viewHolder).webinar_date.setText(month + " " + day + ", " + year);

            }


        }/*else {
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }*/

    }


    @Override
    public int getItemViewType(int position) {
        return (position == mList.size() - 1 && isLoadingAdded) ? VIEW_ITEM : VIEW_PROG;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.loadmore_progress);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {
                        // write your logic here
                       /* if (!certificate_link.equalsIgnoreCase("")) {
                            DownloadCertificate(certificate_link);
                        } else {
                            Constant.toast(mContext, mContext.getResources().getString(R.string.str_certificate_link_not_found));
                        }*/


                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !((Activity) mContext).shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                            intent.setData(uri);
                            mContext.startActivity(intent);
                        } else {
                            ((Activity) mContext).requestPermissions(
                                    new String[]{Manifest.permission
                                            .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_MULTIPLE_REQUEST);
                        }
                    }
                }
                break;


        }

    }

    public void unregister(final Context context) {
        if (onComplete != null) {
            try {
                context.unregisterReceiver(onComplete);
            } catch (Exception e) {
                // ignore
            }
            onComplete = null;
        }

    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {


            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            list.remove(referenceId);


            if (list.isEmpty()) {


                Toast.makeText(mContext, "Download complete", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(mContext)
                                .setSmallIcon(R.mipmap.app_icon)
                                .setContentTitle("Document")
                                .setContentText("MYCpe");


                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_webinar_title, tv_speaker_name, tv_credit, tv_job_title, webinar_date;
        public Button btn_webinar_type, tv_webinar_status, btn_certification_download;
        public RelativeLayout rel_item;


        private ViewHolder(View itemView) {
            super(itemView);
            tv_webinar_title = (TextView) itemView.findViewById(R.id.tv_webinar_title);
            tv_speaker_name = (TextView) itemView.findViewById(R.id.tv_speaker_name);
            tv_credit = (TextView) itemView.findViewById(R.id.tv_credit);
            tv_job_title = (TextView) itemView.findViewById(R.id.tv_job_title);
            webinar_date = (TextView) itemView.findViewById(R.id.webinar_date);
            btn_webinar_type = (Button) itemView.findViewById(R.id.btn_webinar_type);
            tv_webinar_status = (Button) itemView.findViewById(R.id.tv_webinar_status);
            rel_item = (RelativeLayout) itemView.findViewById(R.id.rel_item);
            btn_certification_download = (Button) itemView.findViewById(R.id.btn_certification_download);


        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        //private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            /*PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();*/
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.show();
            mProgressDialog.setMessage("downloading");
            mProgressDialog.setMax(100);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
        }


        @Override
        protected String doInBackground(String... sUrl) {
            int count;
            try {

                for (int i = 0; i < sUrl.length; i++) {
                    URL url = new URL(sUrl[i]);
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();


                    String extension = sUrl[i].substring(sUrl[i].lastIndexOf('.') + 1).trim();


                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(
                            url.openStream(), 8192);
                    // System.out.println("Data::" + sUrl[i]);
                    // Output stream to write file
                    OutputStream output = new FileOutputStream(
                            "/sdcard/certificate" + new Date().getTime() + "." + extension);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress((int) ((total * 100) / lenghtOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                }
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
           /* mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);*/
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAndroidVersion(String certificate_link) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(certificate_link);
        } else {
            // write your logic here

            Intent i = new Intent(mContext, PdfViewActivity.class);
            i.putExtra(mContext.getResources().getString(R.string.str_document_link), certificate_link);
            mContext.startActivity(i);


          /*  if (!certificate_link.equalsIgnoreCase("")) {

                DownloadCertificate(certificate_link);


            } else {
                Constant.toast(mContext, mContext.getResources().getString(R.string.str_certificate_link_not_found));
            }*/


        }

    }

    public void DownloadCertificate(String Certificate) {

        list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Certificate));
        String extension = Certificate.substring(Certificate.lastIndexOf('.') + 1).trim();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading Certificate");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Certificate" + "." + extension);

        refid = downloadManager.enqueue(request);


        list.add(refid);
    }


    private void checkPermission(String certificate_link) {

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(mContext,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    ((Activity) mContext, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            ((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((Activity) mContext).requestPermissions(new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }


            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((Activity) mContext).requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else {
            // write your logic code if permission already granted

            if (!certificate_link.equalsIgnoreCase("")) {
                //DownloadCertificate(certificate_link);

                Intent i = new Intent(mContext, PdfViewActivity.class);
                i.putExtra(mContext.getResources().getString(R.string.str_document_link), certificate_link);
                mContext.startActivity(i);

            } else {
                Constant.toast(mContext, mContext.getResources().getString(R.string.str_certificate_link_not_found));
            }
        }
    }
}
