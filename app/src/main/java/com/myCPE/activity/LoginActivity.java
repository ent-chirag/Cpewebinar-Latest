package com.myCPE.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityLoginBinding;
import com.myCPE.model.login.LoginModel;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    public Context context;
    private APIService mAPIService;
    private boolean checkpasswordvisiblestatus = false;
    private static final String TAG = LoginActivity.class.getName();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAPIService = ApiUtilsNew.getAPIService();
        context = LoginActivity.this;


        AppSettings.set_device_id(context, Constant.GetDeviceid(context));


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.hideKeyboard((Activity) context);
                if (Validation()) {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        LoginPost(Constant.Trim(binding.edtusername.getText().toString()), Constant.Trim(binding.edtpassword.getText()
                                .toString()), AppSettings.get_device_id(context), AppSettings.get_device_token(context), Constant.device_type);
                    } else {
                        Snackbar.make(binding.btnSubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }


                }
            }
        });


        binding.tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, SignUpActivity.class);
                startActivity(i);
                finish();


            }
        });


        binding.strForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);

            }
        });


        binding.edtpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.edtpassword.getRight() - binding.edtpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here

                        if (binding.edtpassword.getText().length() > 0) {
                            if (checkpasswordvisiblestatus == false) {
                                checkpasswordvisiblestatus = true;
                                binding.edtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                binding.edtpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eye, 0);
                                binding.edtpassword.setSelection(binding.edtpassword.length());

                            } else {
                                checkpasswordvisiblestatus = false;
                                binding.edtpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                binding.edtpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eys, 0);
                                binding.edtpassword.setSelection(binding.edtpassword.length());
                            }

                        } else {
                            Snackbar.make(binding.edtpassword, getResources().getString(R.string.validate_password), Snackbar.LENGTH_SHORT).show();
                        }


                        return true;
                    }
                }
                return false;
            }
        });


    }


    public void LoginPost(String username, String password, String device_id, String device_token, String device_type) {

        // RxJava
        mAPIService.login(getResources().getString(R.string.accept), username, password
                , device_id, device_token, device_type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //handle failure response

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }


                        String message = Constant.GetReturnResponse(context, e);
                        Snackbar.make(binding.btnSubmit, message, Snackbar.LENGTH_SHORT).show();


                    }


                    @Override
                    public void onNext(LoginModel login) {
                        if (login.isSuccess()) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            AppSettings.set_login_token(context, login.getPayload().getToken());

                            AppSettings.set_email_id(context, login.getPayload().getEmail());


                            Constant.Log(TAG, "login token" + AppSettings.get_login_token(context));


                            Intent i = new Intent(context, MainActivity.class);
                            startActivity(i);
                            finish();


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            Snackbar.make(binding.btnSubmit, login.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    public Boolean Validation() {
        if (Constant.Trim(binding.edtusername.getText().toString()).isEmpty()) {
            binding.edtusername.requestFocus();
            binding.edtpassword.clearFocus();
            Snackbar.make(binding.edtusername, getResources().getString(R.string.validate_email_id), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtpassword.getText().toString()).isEmpty()) {
            binding.edtusername.clearFocus();
            binding.edtpassword.requestFocus();
            Snackbar.make(binding.edtpassword, getResources().getString(R.string.validate_password), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtpassword.getText().toString()).length() < 6) {
            binding.edtusername.clearFocus();
            binding.edtpassword.requestFocus();
            Snackbar.make(binding.edtpassword, getResources().getString(R.string.password_length), Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }


}
