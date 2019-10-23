package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.databinding.ActivityPaymentBinding;
import com.entigrity.view.DialogsUtils;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    public Context context;
    ProgressDialog progressDialog;
    private static final String TAG = PaymentActivity.class.getName();
    public String payment_link = "";
    public int webinarid = 0;
    public String webinar_type = "";
    private String validate = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        context = PaymentActivity.this;


        Intent intent = getIntent();
        if (intent != null) {
            payment_link = intent.getStringExtra(getResources().getString(R.string.str_payment_link));
            webinarid = intent.getIntExtra(getResources().getString(R.string.pass_webinar_id), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));


            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            binding.webview.setWebViewClient(new CustomWebViewClient());
            WebSettings webSetting = binding.webview.getSettings();
            webSetting.setJavaScriptEnabled(true);
            webSetting.setDisplayZoomControls(true);
            binding.webview.loadUrl(payment_link);
        }


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate.equalsIgnoreCase("success")) {
                    Intent i = new Intent(PaymentActivity.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra(context.getResources().getString(R.string.pass_webinar_id), webinarid);
                    i.putExtra(context.getResources().getString(R.string.pass_webinar_type), webinar_type);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    PaymentActivity.this.finish();

                } else {
                    Intent i = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
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


    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // view.loadUrl(url);
            System.out.println("url :-" + url);
            validate = getLastBitFromUrl(url);
            System.out.println("url :-" + validate);

            if (validate.equalsIgnoreCase("success")) {
                Intent i = new Intent(PaymentActivity.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(context.getResources().getString(R.string.pass_webinar_id), webinarid);
                i.putExtra(context.getResources().getString(R.string.pass_webinar_type), webinar_type);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                PaymentActivity.this.finish();

            }

            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
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

    public  String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (validate.equalsIgnoreCase("success")) {
            Intent i = new Intent(PaymentActivity.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra(context.getResources().getString(R.string.pass_webinar_id), webinarid);
            i.putExtra(context.getResources().getString(R.string.pass_webinar_type), webinar_type);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            PaymentActivity.this.finish();

        } else {
            Intent i = new Intent(PaymentActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }
}
