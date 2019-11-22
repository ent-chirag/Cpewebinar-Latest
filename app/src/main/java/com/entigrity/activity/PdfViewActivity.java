package com.entigrity.activity;

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
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.entigrity.R;
import com.entigrity.databinding.ActivityPdfviewBinding;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;

import java.util.ArrayList;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

public class PdfViewActivity extends AppCompatActivity {
    ActivityPdfviewBinding binding;
    ProgressDialog progressDialog;
    public Context context;
    private static final String TAG = PdfViewActivity.class.getName();
    public String myCertificate = "";
    public static final int PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE = 12345;
    public long refid;
    private DownloadManager downloadManager;
    public ArrayList<Long> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdfview);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        context = PdfViewActivity.this;

        binding.screentitle.setText(context.getResources().getString(R.string.str_view_web_view_certificate));

        Constant.setLightStatusBar(PdfViewActivity.this);

        Intent intent = getIntent();
        if (intent != null) {
            myCertificate = intent.getStringExtra(getResources().getString(R.string.str_document_link));
        }

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    display();
                }
            },5000);
//            display();
        } else {
            Snackbar.make(binding.ivback, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        binding.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!myCertificate.equalsIgnoreCase("")) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, myCertificate);
                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                } else {
                    Constant.toast(context, getResources().getString(R.string.str_sharing_not_avilable));
                }

            }
        });

        binding.ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndroidVersionCertificate();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission_Certificate() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    ((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            ((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE);
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE);
            }
        } else {
            // write your logic code if permission already granted

            if (!myCertificate.equalsIgnoreCase("")) {
                DownloadCertificate(myCertificate);
            } else {
                Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
            }
        }


    }


    private void checkAndroidVersionCertificate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission_Certificate();
        } else {
            // write your logic here
            if (!myCertificate.equalsIgnoreCase("")) {
                DownloadCertificate(myCertificate);
            } else {
                Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
            }
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {


            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            list.remove(referenceId);


            if (list.isEmpty()) {


                Toast.makeText(context, "Download complete", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.app_icon)
                                .setContentTitle("Document")
                                .setContentText("MYCpe");


                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, mBuilder.build());


            }

        }
    };


    private void DownloadCertificate(String myCertificate) {

        list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myCertificate));
        String extension = myCertificate.substring(myCertificate.lastIndexOf('.') + 1).trim();
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading Certificate");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/MyCpe/" + "/" + "Certificate" + "." + extension);
        refid = downloadManager.enqueue(request);

        list.add(refid);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE:

                if (grantResults.length > 0) {
                    boolean writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (writePermission && readExternalFile) {

                        if (!myCertificate.equalsIgnoreCase("")) {
                            DownloadCertificate(myCertificate);
                        } else {
                            Constant.toast(context, context.getResources().getString(R.string.str_certificate_link_not_found));
                        }

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                            requestPermissions(
                                    new String[]{Manifest.permission
                                            .READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE);
                        }
                    }
                }
                break;

        }


    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.webview.canGoBack()) {
            binding.webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private void display() {
//        String url = "https://docs.google.com/gview?embedded=true&url=" + myCertificate;
        String url = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + myCertificate;
        Log.i(TAG, "Opening PDF: " + url);

//        uri = Uri.parse( url.toURI().toString() );
//        uri = Uri.parse( myCertificate.toString() );

//        binding.pdfView.fromUri(uri);

        WebSettings webSetting = binding.webview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        binding.webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webSetting.setDisplayZoomControls(true);
        binding.webview.clearCache(true);
        binding.webview.clearFormData();
        binding.webview.clearHistory();
        binding.webview.loadUrl(url);
//        binding.webview.loadUrl(myCertificate);
        binding.webview.setWebViewClient(new CustomWebViewClient());

        binding.webview.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("*+*+*","onProgressChanged : "+newProgress);
                /*if(newProgress<100) {
                    progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                }*/
                if(newProgress == 100)
                {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });

    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            /*if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }*/
        }


    }


}
