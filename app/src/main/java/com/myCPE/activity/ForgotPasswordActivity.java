package com.myCPE.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.myCPE.R;
import com.myCPE.databinding.ActivityForgotpasswordNewLayoutBinding;
import com.myCPE.model.forgotpassword.Forgotpaawordmodel;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ForgotPasswordActivity extends AppCompatActivity {
//    ActivityForgotpasswordBinding binding;
    ActivityForgotpasswordNewLayoutBinding binding;
    public Context context;
    private APIService mAPIService_new;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword_new_layout);
        context = ForgotPasswordActivity.this;
        mAPIService_new = ApiUtilsNew.getAPIService();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.hideKeyboard((Activity) context);

                if (Validation()) {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        ForgotPassword(Constant.Trim(binding.edtEmailId.getText().toString()));
                    } else {
                        Snackbar.make(binding.btnSubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                    }

                }
            }
        });


//        binding.ivBack.setOnClickListener(new View.OnClickListener() {
        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public Boolean Validation() {

        if (Constant.Trim(binding.edtEmailId.getText().toString()).isEmpty()) {
            Snackbar.make(binding.edtEmailId, getResources().getString(R.string.forgot_passoword_email), Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }


    public void ForgotPassword(String emailid) {

        // RxJava
        mAPIService_new.forgotpassword(getResources().getString(R.string.accept), emailid
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Forgotpaawordmodel>() {
                    @Override
                    public void onCompleted() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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
                    public void onNext(Forgotpaawordmodel forgotpaawordmodel) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (forgotpaawordmodel.isSuccess()) {

                            binding.edtEmailId.setText("");
                            Snackbar.make(binding.btnSubmit, forgotpaawordmodel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(binding.btnSubmit, forgotpaawordmodel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}
