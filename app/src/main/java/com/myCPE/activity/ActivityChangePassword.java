package com.myCPE.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityChangePasswordNewBinding;
import com.myCPE.model.changepassword.ChangePasswordModel;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityChangePassword extends AppCompatActivity {

    // Sample test commit..
    ActivityChangePasswordNewBinding binding;
    public Context context;
    private APIService mAPIService_new;
    private static final String TAG = ActivityChangePassword.class.getName();
    ProgressDialog progressDialog;
    View view;

    private boolean checkpasswordvisiblestatus = false;
    private boolean checknewpasswordvisiblestatus = false;
    private boolean checkconformpasswordvisiblestatus = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password_new);
        mAPIService_new = ApiUtilsNew.getAPIService();
        context = ActivityChangePassword.this;


        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.hideKeyboard((Activity) context);

                if (Validation()) {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        ChangePassword(AppSettings.get_login_token(context), Constant.Trim(binding.edtOldpassword.getText().toString()),
                                Constant.Trim(binding.edtNewpassword.getText().toString()),
                                Constant.Trim(binding.edtConfirmpassword.getText().toString()));
                    } else {
                        Snackbar.make(binding.btnSubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.edtOldpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.edtOldpassword.getRight() - binding.edtOldpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here

                        if (binding.edtOldpassword.getText().length() > 0) {
                            if (checkpasswordvisiblestatus == false) {
                                checkpasswordvisiblestatus = true;
                                binding.edtOldpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                binding.edtOldpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eye, 0);
                                binding.edtOldpassword.setSelection(binding.edtOldpassword.length());

                            } else {
                                checkpasswordvisiblestatus = false;
                                binding.edtOldpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                binding.edtOldpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eys, 0);
                                binding.edtOldpassword.setSelection(binding.edtOldpassword.length());
                            }

                        } else {
                            Snackbar.make(binding.edtOldpassword, getResources().getString(R.string.validate_password), Snackbar.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        binding.edtNewpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.edtNewpassword.getRight() - binding.edtNewpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here

                        if (binding.edtNewpassword.getText().length() > 0) {
                            if (checknewpasswordvisiblestatus == false) {
                                checknewpasswordvisiblestatus = true;
                                binding.edtNewpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                binding.edtNewpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eye, 0);
                                binding.edtNewpassword.setSelection(binding.edtNewpassword.length());

                            } else {
                                checknewpasswordvisiblestatus = false;
                                binding.edtNewpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                binding.edtNewpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eys, 0);
                                binding.edtNewpassword.setSelection(binding.edtNewpassword.length());
                            }

                        } else {
                            Snackbar.make(binding.edtNewpassword, getResources().getString(R.string.validate_password), Snackbar.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        binding.edtConfirmpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (binding.edtConfirmpassword.getRight() - binding.edtConfirmpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here

                        if (binding.edtConfirmpassword.getText().length() > 0) {
                            if (checkconformpasswordvisiblestatus == false) {
                                checkconformpasswordvisiblestatus = true;
                                binding.edtConfirmpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                binding.edtConfirmpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eye, 0);
                                binding.edtConfirmpassword.setSelection(binding.edtConfirmpassword.length());

                            } else {
                                checkconformpasswordvisiblestatus = false;
                                binding.edtConfirmpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                binding.edtConfirmpassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.password_eys, 0);
                                binding.edtConfirmpassword.setSelection(binding.edtConfirmpassword.length());
                            }

                        } else {
                            Snackbar.make(binding.edtConfirmpassword, getResources().getString(R.string.validate_password), Snackbar.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });


    }

    public void ChangePassword(String Authorization, String current_password, String new_password, String confirm_password) {

        // RxJava
        mAPIService_new.changepassword(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + Authorization, current_password
                , new_password, confirm_password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangePasswordModel>() {
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

                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.btnSubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }


                    @Override
                    public void onNext(ChangePasswordModel changePasswordModel) {
                        if (changePasswordModel.isSuccess()) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.btnSubmit, changePasswordModel.getMessage(), Snackbar.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();

                                }
                            }, 2000);


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Snackbar.make(binding.btnSubmit, changePasswordModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });

    }


    public Boolean Validation() {
        if (Constant.Trim(binding.edtOldpassword.getText().toString()).isEmpty()) {

            binding.edtOldpassword.requestFocus();
            binding.edtConfirmpassword.clearFocus();
            binding.edtConfirmpassword.clearFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.validate_oldpassword), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtOldpassword.getText().toString()).length() < 6) {

            binding.edtOldpassword.requestFocus();
            binding.edtNewpassword.clearFocus();
            binding.edtConfirmpassword.clearFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.password_length), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtNewpassword.getText().toString()).isEmpty()) {

            binding.edtOldpassword.clearFocus();
            binding.edtNewpassword.requestFocus();
            binding.edtConfirmpassword.clearFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.validate_newpassword), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtNewpassword.getText().toString()).length() < 6) {

            binding.edtOldpassword.clearFocus();
            binding.edtNewpassword.requestFocus();
            binding.edtConfirmpassword.clearFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.password_length), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtConfirmpassword.getText().toString()).isEmpty()) {

            binding.edtOldpassword.clearFocus();
            binding.edtNewpassword.clearFocus();
            binding.edtConfirmpassword.requestFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.validate_confirmpassword), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtConfirmpassword.getText().toString()).length() < 6) {
            binding.edtOldpassword.clearFocus();
            binding.edtNewpassword.clearFocus();
            binding.edtConfirmpassword.requestFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.password_length), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (!Constant.Trim(binding.edtNewpassword.getText().toString()).equals(Constant.Trim(binding.edtConfirmpassword.getText().toString()))) {

            binding.edtOldpassword.clearFocus();
            binding.edtNewpassword.clearFocus();
            binding.edtConfirmpassword.clearFocus();

            Snackbar.make(binding.btnSubmit, getResources().getString(R.string.val_new_confirm_password_not_match), Snackbar.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }


    }
}
