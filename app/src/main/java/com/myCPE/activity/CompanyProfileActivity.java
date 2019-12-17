package com.myCPE.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityCompanyProfileBinding;
import com.myCPE.model.company_details_new.CompanyDetailsModel;
import com.myCPE.model.getprivacypolicy.GetPrivacyPolicy;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;
import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CompanyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCompanyProfileBinding binding;
    public Context context;
    private APIService mAPIService;
    ProgressDialog progressDialog;

    private int company_id = 0;
    private int speaker_id = 0;

    private String profile_pic_url = "";
    private String company_name = "";
    private String company_location = "";
    private String webinar_count = "";
    private String professional_trained_count = "";
    private String followers_count = "";
    private String description = "";
    private String website = "";
    private String no_of_speakers = "";
    private String rating = "";
    private String review_count = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_profile);
//        setContentView(R.layout.activity_company_profile);

        mAPIService = ApiUtilsNew.getAPIService();
        context = CompanyProfileActivity.this;

        Intent intent = getIntent();
        company_id = intent.getIntExtra("company_id", 0);
        speaker_id = intent.getIntExtra("speaker_id", 0);

        Log.e("*+*+*", "Company ID : " + company_id);
        Log.e("*+*+*", "Speaker ID : " + speaker_id);

        binding.relImgBack.setOnClickListener(this);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetCompanyDetails();
        } else {
            Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void GetCompanyDetails() {
        mAPIService.GetCompanyDetailsNew(getResources().getString(R.string.accept), ""+company_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyDetailsModel>() {
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
                    public void onNext(CompanyDetailsModel companyDetailsModel) {
                        if (companyDetailsModel.isSuccess()) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            String sampleResponse = companyDetailsModel.getPayload().getCompany().getCompanyName();
                            Log.e("*+*+*","Sample response : "+sampleResponse);

                            // Now we have all the data here to show in each tags..
                            if(!companyDetailsModel.getPayload().getCompany().getCompanyProfilePic().equalsIgnoreCase("")) {
                                profile_pic_url = companyDetailsModel.getPayload().getCompany().getCompanyProfilePic();

                                Picasso.with(context).load(profile_pic_url)
                                        .placeholder(R.drawable.profile_place_holder)
                                        .into(binding.imgProfilePic);
                            }

                            if(!companyDetailsModel.getPayload().getCompany().getCompanyName().equalsIgnoreCase("")) {
                              company_name = companyDetailsModel.getPayload().getCompany().getCompanyName();
                            }

                            if(!companyDetailsModel.getPayload().getCompany().getState().equalsIgnoreCase("") ||
                                    !companyDetailsModel.getPayload().getCompany().getCity().equalsIgnoreCase("")) {
                                company_location = companyDetailsModel.getPayload().getCompany().getCity() + ", " +companyDetailsModel.getPayload().getCompany().getState();
                            }

                            if(!companyDetailsModel.getPayload().getCompany().getRating().equalsIgnoreCase("")) {
                                rating = companyDetailsModel.getPayload().getCompany().getRating();
                            }

                            if(!companyDetailsModel.getPayload().getCompany().getCompanyWebsite().equalsIgnoreCase("")) {
                                website = companyDetailsModel.getPayload().getCompany().getCompanyWebsite();
                            }

                            if(!companyDetailsModel.getPayload().getCompany().getCompanyDescription().equalsIgnoreCase("")) {
                                description = companyDetailsModel.getPayload().getCompany().getCompanyDescription();
                            }

                            if(companyDetailsModel.getPayload().getCompany().getReviewCount() != 0) {
                                review_count = ""+companyDetailsModel.getPayload().getCompany().getReviewCount();
                            }

                            if(companyDetailsModel.getPayload().getCompany().getNoOfWebinar() != 0) {
                                webinar_count = ""+companyDetailsModel.getPayload().getCompany().getNoOfWebinar();
                            }

                            if(companyDetailsModel.getPayload().getCompany().getNoOfFollowers() != 0) {
                                followers_count = ""+companyDetailsModel.getPayload().getCompany().getNoOfFollowers();
                            }

                            if(companyDetailsModel.getPayload().getCompany().getNoOfProfessionalsTrained() != 0) {
                                professional_trained_count = ""+companyDetailsModel.getPayload().getCompany().getNoOfProfessionalsTrained();
                            }

                            loadScreenData();

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.relImgBack, companyDetailsModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadScreenData() {

        binding.tvCompanyName.setText(company_name);
        binding.tvCompanyLocation.setText(company_location);
        binding.tvWebinarCount.setText(webinar_count);
        binding.tvProfTrained.setText(professional_trained_count);
        binding.tvFollowersCount.setText(followers_count);
        binding.tvDescription.setText(description);
        binding.tvWebsite.setText(website);
        binding.tvRatingReview.setText(rating+" ("+review_count+" reviews)");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relImgBack:

                finish();

                break;
        }
    }
}
