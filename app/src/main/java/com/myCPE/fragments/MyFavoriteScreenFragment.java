package com.myCPE.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.ActivityFavorite;
import com.myCPE.databinding.FragmentMyfavoritescreenBinding;
import com.myCPE.model.favorites_count.Favorite_Count_Model;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;
import com.squareup.picasso.Picasso;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyFavoriteScreenFragment extends Fragment {
    View view;
    public Context context;
    private APIService mAPIService;
    ProgressDialog progressDialog;
    FragmentMyfavoritescreenBinding binding;
    private static final String TAG = MyFavoriteScreenFragment.class.getName();
    private String BannerImage = "https://my-cpe.com/images/about-us-bg.jpg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_myfavoritescreen, null, false);
        context = getActivity();
        mAPIService = ApiUtilsNew.getAPIService();

        binding.getRoot().setFocusableInTouchMode(true);
        binding.getRoot().requestFocus();
        binding.getRoot().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ConfirmationPopup();
                    return true;
                }
                return false;
            }
        });


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetFavoriteDetails();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

        }


        binding.lvTopicsofinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent i = new Intent(getActivity(), ViewTopicsOfInterestActivity.class);
                i.putExtra(getResources().getString(R.string.str_get_key_screen), getResources().getString(R.string.from_favorite));
                startActivity(i);*/

            }
        });

        binding.lvWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ActivityFavorite.class);
                startActivity(i);


            }
        });


        binding.lvSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(binding.lvSpeaker, getResources().getString(R.string.str_comming_soon), Snackbar.LENGTH_SHORT).show();
            }
        });


        binding.lvCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(binding.lvCompany, getResources().getString(R.string.str_comming_soon), Snackbar.LENGTH_SHORT).show();
            }
        });


        return view = binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isdataupdate) {
            Constant.isdataupdate = false;
            if (Constant.isNetworkAvailable(context)) {
                progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                GetFavoriteDetails();
            } else {
                Snackbar.make(getActivity().findViewById(android.R.id.content), getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

            }
        }
    }

    private void GetFavoriteDetails() {

        mAPIService.GetFavoriteCountModel(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Favorite_Count_Model>() {
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
                            Snackbar.make(binding.ivbanner, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(Favorite_Count_Model favorite_count_model) {

                        if (favorite_count_model.isSuccess() == true) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            if (!favorite_count_model.getPayload().getProfilePicture().equalsIgnoreCase("")
                                    && favorite_count_model.getPayload().getProfilePicture() != null) {
                                Picasso.with(context).load(favorite_count_model.getPayload().getProfilePicture())
                                        .placeholder(R.drawable.profile_place_holder)
                                        .into(binding.ivProfilePicture);
                            } else {

                                binding.ivProfilePicture.setImageResource(R.drawable.profile_place_holder);
                            }


                            Picasso.with(context).load(BannerImage)
                                    .placeholder(R.mipmap.webinar_placeholder)
                                    .into(binding.ivbanner);


                            if (!favorite_count_model.getPayload().getFirstName().equalsIgnoreCase("")) {
                                binding.profilename.setText("" + favorite_count_model.getPayload().getFirstName());
                            }


                            if (favorite_count_model.getPayload().getWebinarCount() == 0) {
                                binding.webinarcount.setText("" + "(" + 0 + ")");
                            } else {
                                binding.webinarcount.setText("" + "( " + favorite_count_model.getPayload().getWebinarCount() + " )");
                            }


                            if (favorite_count_model.getPayload().getTopicsOfInterestCount() == 0) {
                                binding.webinartopicsofinterest.setText("" + "(" + 0 + ")");
                            } else {
                                binding.webinartopicsofinterest.setText("" + "( " + favorite_count_model.getPayload().getTopicsOfInterestCount() + " )");
                            }

                            if (favorite_count_model.getPayload().getCompanyCount() == 0) {
                                binding.companycount.setText("" + "(" + 0 + ")");
                            } else {
                                binding.companycount.setText("" + "( " + favorite_count_model.getPayload().getCompanyCount() + " )");
                            }


                            if (favorite_count_model.getPayload().getSpeakerCount() == 0) {
                                binding.speakercount.setText("" + "(" + 0 + ")");
                            } else {
                                binding.companycount.setText("" + "( " + favorite_count_model.getPayload().getSpeakerCount() + " )");
                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.ivbanner, favorite_count_model.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                });


    }

    public void ConfirmationPopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.exit_validation));


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                dialog.cancel();
                getActivity().finish();
                System.exit(0);


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }
}
