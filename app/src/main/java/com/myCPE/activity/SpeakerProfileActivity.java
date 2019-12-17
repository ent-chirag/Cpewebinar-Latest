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

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivitySpeakerProfileBinding;
import com.myCPE.model.SpeakerDetails.SpeakerDetailsNew;
import com.myCPE.model.company_details_new.CompanyDetailsModel;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;
import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SpeakerProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySpeakerProfileBinding binding;
    public Context context;
    private APIService mAPIService;
    ProgressDialog progressDialog;

    private int company_id = 0;
    private int speaker_id = 0;

    private String profile_pic_url = "";
    private String speaker_name = "";
    private String speaker_location = "";
    private String webinar_count = "";
    private String professional_trained_count = "";
    private String followers_count = "";
    private String description = "";
    private String website = "";
    private String no_of_speakers = "";
    private String rating = "";
    private String review_count = "";
    private String speaker_designation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_speaker_profile);
//        setContentView(R.layout.activity_speaker_profile);

        mAPIService = ApiUtilsNew.getAPIService();
        context = SpeakerProfileActivity.this;

        binding.relImgBack.setOnClickListener(this);

        Intent intent = getIntent();
        company_id = intent.getIntExtra("company_id", 0);
        speaker_id = intent.getIntExtra("speaker_id", 0);

        Log.e("*+*+*", "Company ID : " + company_id);
        Log.e("*+*+*", "Speaker ID : " + speaker_id);

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetSpeakerDetails();
        } else {
            Snackbar.make(binding.relImgBack, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }



    }

    private void GetSpeakerDetails() {
        mAPIService.GetSpeakerDetailsNew(getResources().getString(R.string.accept), ""+speaker_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SpeakerDetailsNew>() {
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
                    public void onNext(SpeakerDetailsNew speakerDetails) {
                        if (speakerDetails.isSuccess()) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            String sampleResponse = speakerDetails.getPayload().getSpeaker().getSpeakerName();
                            Log.e("*+*+*","Sample response : "+sampleResponse);

                            // Now we have all the data here to show in each tags..
                            if(!speakerDetails.getPayload().getSpeaker().getSpeakerProfilePic().equalsIgnoreCase("")) {
                                profile_pic_url = speakerDetails.getPayload().getSpeaker().getSpeakerProfilePic();

                                Picasso.with(context).load(profile_pic_url)
                                        .placeholder(R.drawable.profile_place_holder)
                                        .into(binding.imgProfilePic);
                            }

                            if(!speakerDetails.getPayload().getSpeaker().getSpeakerName().equalsIgnoreCase("")) {
                                speaker_name = speakerDetails.getPayload().getSpeaker().getSpeakerName();
                            }

                            if(!speakerDetails.getPayload().getSpeaker().getState().equalsIgnoreCase("") ||
                                    !speakerDetails.getPayload().getSpeaker().getCity().equalsIgnoreCase("")) {
                                speaker_location = speakerDetails.getPayload().getSpeaker().getCity() + ", " +speakerDetails.getPayload().getSpeaker().getState();
                            }

                            if(!speakerDetails.getPayload().getSpeaker().getRating().equalsIgnoreCase("")) {
                                rating = speakerDetails.getPayload().getSpeaker().getRating();
                            }

                            if(!speakerDetails.getPayload().getSpeaker().getSpeakerWebsite().equalsIgnoreCase("")) {
                                website = speakerDetails.getPayload().getSpeaker().getSpeakerWebsite();
                            }

                            if(!speakerDetails.getPayload().getSpeaker().getSpeakerDesignation().equalsIgnoreCase("")) {
                                speaker_designation = speakerDetails.getPayload().getSpeaker().getSpeakerDesignation();
                            }

                            if(!speakerDetails.getPayload().getSpeaker().getSpeakerDescription().equalsIgnoreCase("")) {
                                description = speakerDetails.getPayload().getSpeaker().getSpeakerDescription();
                            }

                            if(speakerDetails.getPayload().getSpeaker().getReviewCount() != 0) {
                                review_count = ""+speakerDetails.getPayload().getSpeaker().getReviewCount();
                            }

                            if(speakerDetails.getPayload().getSpeaker().getNoOfWebinar() != 0) {
                                webinar_count = ""+speakerDetails.getPayload().getSpeaker().getNoOfWebinar();
                            }

                            if(speakerDetails.getPayload().getSpeaker().getNoOfFollowers() != 0) {
                                followers_count = ""+speakerDetails.getPayload().getSpeaker().getNoOfFollowers();
                            }

                            if(speakerDetails.getPayload().getSpeaker().getNoOfProfessionalsTrained() != 0) {
                                professional_trained_count = ""+speakerDetails.getPayload().getSpeaker().getNoOfProfessionalsTrained();
                            }

                            loadScreenData();

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.relImgBack, speakerDetails.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadScreenData() {

        binding.tvSpeakerName.setText(speaker_name);
        binding.tvSpeakerLocation.setText(speaker_location);
        binding.tvSpeakerDesignation.setText(speaker_designation);
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
