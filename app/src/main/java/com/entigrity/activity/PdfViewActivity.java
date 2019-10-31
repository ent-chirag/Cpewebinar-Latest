package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.entigrity.R;
import com.entigrity.databinding.ActivityPdfviewBinding;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;

public class PdfViewActivity extends AppCompatActivity {


    ActivityPdfviewBinding binding;
    ProgressDialog progressDialog;
    public Context context;
    private static final String TAG = PdfViewActivity.class.getName();
    public String myCertificate = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pdfview);
        context = PdfViewActivity.this;

        binding.screentitle.setText(context.getResources().getString(R.string.str_view_web_view_certificate));

        Intent intent = getIntent();
        if (intent != null) {
            myCertificate = intent.getStringExtra(getResources().getString(R.string.str_document_link));
        }


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            display();
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


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.webview.canGoBack()) {
            binding.webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void display() {
        String url = "http://docs.google.com/gview?embedded=true&url=" + myCertificate;
        Log.i(TAG, "Opening PDF: " + url);
        WebSettings webSetting = binding.webview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        binding.webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webSetting.setDisplayZoomControls(true);
        binding.webview.loadUrl(url);
        binding.webview.setWebViewClient(new CustomWebViewClient());

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

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }


    }


}
