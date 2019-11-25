package com.entigrity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.entigrity.R;
import com.entigrity.databinding.ActivityPdfviewBinding;
import com.entigrity.utility.Constant;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PdfViewActivity extends AppCompatActivity {
    ActivityPdfviewBinding binding;
    ProgressDialog mProgressDialog;
    public Context context;
    public String myCertificate = "";
    public static final int PERMISSIONS_MULTIPLE_REQUEST_CERTIFICATE = 12345;
    public ArrayList<Long> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdfview);
        context = PdfViewActivity.this;

        mProgressDialog = new ProgressDialog(context);

        binding.screentitle.setText(context.getResources().getString(R.string.str_view_web_view_certificate));

        Constant.setLightStatusBar(PdfViewActivity.this);

        Intent intent = getIntent();
        if (intent != null) {
            myCertificate = intent.getStringExtra(getResources().getString(R.string.str_document_link));
        }

        if (Constant.isNetworkAvailable(context)) {
            checkAndroidVersionCertificate();
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

    private void downloadFile() {
        mProgressDialog.show();
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setMax(100);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        DownloadFileTask task = new DownloadFileTask(
                PdfViewActivity.this,
                myCertificate,
                "/download/Certificate.pdf");
        task.startTask();
    }


    public class DownloadFileTask {
        public static final String TAG = "DownloadFileTask";

        private PdfViewActivity context;
        private GetTask contentTask;
        private String url;
        private String fileName;

        public DownloadFileTask(PdfViewActivity context, String url, String fileName) {
            this.context = context;
            this.url = url;
            this.fileName = fileName;
        }

        public void startTask() {
            doRequest();
        }

        private void doRequest() {
            contentTask = new GetTask();
            contentTask.execute();
        }

        private class GetTask extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... arg0) {
                int count;
                try {
                    Log.d(TAG, "url = " + url);
                    URL _url = new URL(url);
                    URLConnection conection = _url.openConnection();
                    conection.connect();
                    InputStream input = new BufferedInputStream(_url.openStream(),
                            8192);
                    OutputStream output = new FileOutputStream(
                            Environment.getExternalStorageDirectory() + fileName);
                    byte data[] = new byte[1024];
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
                return null;
            }

            protected void onPostExecute(String data) {
                context.onFileDownloaded();
            }
        }
    }


    public void onFileDownloaded() {

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + "/download/Certificate.pdf");
        if (file.exists()) {
            binding.pdfView.fromFile(file)
                    //.pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true)
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .password(null)
                    .scrollHandle(null)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            binding.pdfView.setMinZoom(2f);
                            binding.pdfView.setMidZoom(1f);
                            binding.pdfView.setMaxZoom(10f);
                            binding.pdfView.zoomTo(1.0f);
                            binding.pdfView.scrollTo(0, 100);
                            binding.pdfView.moveTo(0f, 0f);
                        }
                    })
                    .load();

        }
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
                downloadFile();
            }
        }


    }


    private void checkAndroidVersionCertificate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission_Certificate();
        } else {

            if (!myCertificate.equalsIgnoreCase("")) {
                downloadFile();
            }
        }
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
                            downloadFile();

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


}
