package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityContactUsBinding;
import com.myCPE.model.getcontactusinfo.GetContactUsInfo;
import com.myCPE.model.postcontactus.PostContactQuery;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ActivityContactUs extends AppCompatActivity {

    ActivityContactUsBinding binding;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    public Context context;
    private static final String TAG = ActivityContactUs.class.getName();
    private String contact_number = "", email_id = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        context = ActivityContactUs.this;
        mAPIService = ApiUtilsNew.getAPIService();

        binding.edtSubject.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        binding.edtSubject.setRawInputType(InputType.TYPE_CLASS_TEXT);


        binding.edtReview.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.edtReview.setRawInputType(InputType.TYPE_CLASS_TEXT);


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetContactUsInfo();
        } else {
            Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }


        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Validation()) {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        PostContactUsQuery(getResources().getString(R.string.accept),
                                binding.edtReview.getText().toString(), binding.edtSubject.getText().toString());
                    } else {
                        Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }


                }


            }
        });


        binding.relImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.lvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!contact_number.equalsIgnoreCase("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact_number, null));
                    startActivity(intent);

                }


            }
        });

        binding.lvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email_id.equalsIgnoreCase("")) {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", email_id, null));
                    startActivity(Intent.createChooser(emailIntent, "mail"));

                }


            }
        });


    }

    private void PostContactUsQuery(String accept, String message, String subject) {

        mAPIService.PostContactUsQuery(accept, message,
                subject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostContactQuery>() {
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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }


                    @Override
                    public void onNext(PostContactQuery postContactQuery) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (postContactQuery.isSuccess()) {
                            Snackbar.make(binding.btnsubmit, postContactQuery.getMessage(), Snackbar.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    finish();

                                }
                            }, 2000);
                        } else {
                            Snackbar.make(binding.btnsubmit, postContactQuery.getMessage(), Snackbar.LENGTH_SHORT).show();

                        }


                    }
                });


    }

    public Boolean Validation() {
        if (binding.edtSubject.getText().toString().isEmpty()) {
            binding.edtSubject.requestFocus();
            binding.edtReview.clearFocus();
            Snackbar.make(binding.edtSubject, getResources().getString(R.string.val_subject), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (binding.edtReview.getText().toString().isEmpty()) {
            binding.edtSubject.clearFocus();
            binding.edtReview.requestFocus();
            Snackbar.make(binding.edtReview, getResources().getString(R.string.val_review), Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public void GetContactUsInfo() {

        mAPIService.GetContactUsInfo(getResources().getString(R.string.accept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetContactUsInfo>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);

                        if (Constant.status_code == 401) {
                            MainActivity.getInstance().AutoLogout();
                        } else {
                            Snackbar.make(binding.relImgBack, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(GetContactUsInfo getContactUsInfo) {

                        if (getContactUsInfo.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            contact_number = getContactUsInfo.getPayload().getContactNumber();
                            email_id = getContactUsInfo.getPayload().getEmailId();

                            if (!getContactUsInfo.getPayload().getContactNumber().equalsIgnoreCase("")) {
                                binding.tvMobileNumber.setText(getContactUsInfo.getPayload().getContactNumber());
                            }

                            if (!getContactUsInfo.getPayload().getEmailId().equalsIgnoreCase("")) {
                                binding.tvEmailid.setText(getContactUsInfo.getPayload().getEmailId());
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.relImgBack, getContactUsInfo.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }

                });


    }

}
