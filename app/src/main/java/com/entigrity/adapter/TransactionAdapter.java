package com.entigrity.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.entigrity.R;
import com.entigrity.activity.PdfViewActivity;
import com.entigrity.model.payment_transcation.TransactionItem;
import com.entigrity.utility.Constant;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter implements ActivityCompat.OnRequestPermissionsResultCallback {

    private Context mContext;
    LayoutInflater mInflater;
    private List<TransactionItem> mList;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    //public String payment_receipt = "";
    private boolean isLoadingAdded = false;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private long refid;
    ArrayList<Long> list = new ArrayList<>();
    private DownloadManager downloadManager;

    public TransactionAdapter(Context mContext, List<TransactionItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        mContext.registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

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
                    R.layout.row_mytranscation, parent, false);

            vh = new ViewHolder(v);
        }
        return vh;
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
                        /*if (!payment_receipt.equalsIgnoreCase("")) {
                            DownloadCertificate(payment_receipt);
                        } else {
                            Constant.toast(mContext, mContext.getResources().getString(R.string.payment_link_not_available));
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

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.loadmore_progress);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        if (viewHolder instanceof ViewHolder) {

            String htmlString = "<u>Download Receipt</u>";
            ((ViewHolder) viewHolder).tv_download_receipt.setText(Html.fromHtml(htmlString));


            if (!mList.get(position).getTitle().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_webinar_title.setText(mList.get(position).getTitle());
            }

            if (!mList.get(position).getWebinarType().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).btn_webinarstatus_status.setText(mList.get(position).getWebinarType());
            }


          /*  if (!mList.get(position).getReceipt().equalsIgnoreCase("")) {
                payment_receipt = mList.get(position).getReceipt();

                Constant.Log("payment_receipt", "+++" + payment_receipt);
                //Constant.Log("receipt", "+++" + mList.get(position).getReceipt());
            }
*/

            if (!mList.get(position).getAmount().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_webinar_cost.setText("$ " + mList.get(position).getAmount());
            }

            if (!mList.get(position).getPaymentDate().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_payment_date.setText(mList.get(position).getPaymentDate());
            }


            if (!mList.get(position).getTransactionId().equalsIgnoreCase("")) {
                ((ViewHolder) viewHolder).tv_trx_value.setText(mList.get(position).getTransactionId());
            }

            ((ViewHolder) viewHolder).tv_download_receipt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    checkAndroidVersion(mList.get(position).getReceipt());
                    if (!mList.get(position).getReceipt().equalsIgnoreCase("")) {
                        Intent i = new Intent(mContext, PdfViewActivity.class);
                        i.putExtra(mContext.getResources().getString(R.string.str_document_link),
                                mList.get(position).getReceipt());
                        i.putExtra(mContext.getResources().getString(R.string.str_pdf_view_titile), mContext.getString(R.string.str_receipt));
                        mContext.startActivity(i);
                    }



                }
            });


        }/*else {
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }*/

    }

    private void checkAndroidVersion(String payment_receipt) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(payment_receipt);
        } else {
            // write your logic here


            if (!payment_receipt.equalsIgnoreCase("")) {
                DownloadCertificate(payment_receipt);
            } else {
                Constant.toast(mContext, mContext.getResources().getString(R.string.payment_receipt_not_available));
            }


        }


    }

    private void checkPermission(String payment_receipt) {

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

            if (!payment_receipt.equalsIgnoreCase("")) {
                DownloadCertificate(payment_receipt);
            } else {
                Constant.toast(mContext, mContext.getResources().getString(R.string.str_payment_link_not_found));
            }
        }
    }

    public void DownloadCertificate(String Receipt) {

        list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Receipt));
        String extension = Receipt.substring(Receipt.lastIndexOf('.') + 1).trim();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading Payment Receipt");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Payment Receipt" + "." + extension);

        refid = downloadManager.enqueue(request);


        list.add(refid);
    }


    @Override
    public int getItemViewType(int position) {
        return (position == mList.size() - 1 && isLoadingAdded) ? VIEW_ITEM : VIEW_PROG;

    }

    public void add(TransactionItem transactionItem) {
        mList.add(transactionItem);
        notifyItemInserted(mList.size());
    }

    public void addAll(List<TransactionItem> mcList) {
        for (TransactionItem mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new TransactionItem());
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_download_receipt, tv_webinar_title, tv_payment_date, tv_webinar_cost, tv_trx_value;
        public Button btn_webinarstatus_status;


        private ViewHolder(View itemView) {
            super(itemView);
            tv_download_receipt = (TextView) itemView.findViewById(R.id.tv_download_receipt);
            tv_webinar_title = (TextView) itemView.findViewById(R.id.tv_webinar_title);
            tv_payment_date = (TextView) itemView.findViewById(R.id.tv_payment_date);
            tv_webinar_cost = (TextView) itemView.findViewById(R.id.tv_webinar_cost);
            tv_trx_value = (TextView) itemView.findViewById(R.id.tv_trx_value);


            btn_webinarstatus_status = (Button) itemView.findViewById(R.id.btn_webinarstatus_status);


        }
    }
}
