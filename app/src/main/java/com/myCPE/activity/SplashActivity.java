package com.myCPE.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivitySplashBinding;
import com.myCPE.model.check_version.VersionCheck;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ActivitySplashBinding binding;
    public Context context;
    private int webinar_id = 0;
    private String webinar_type = "";
    private APIService mAPIService;
    private static final String TAG = LoginActivity.class.getName();
    private String update_msg = "";

    private String webinar_id_dl = "";
    private String webinar_type_dl = "";
    private String redirect_dl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mAPIService = ApiUtilsNew.getAPIService();
        context = SplashActivity.this;

        Constant.isCpdSelected = false;
        Constant.is_cpd = 0;
        Constant.isFromCSPast = false;

        // URL that we are getting is
        // As of now we are just thinking about the live and self_study webinars..
        // https://my-cpe.com/v2/dl/webinar_id:1423/webinar_type:live/mycpe://webinardetailsview
        // https://my-cpe.com/v2/dl/webinar_id:1423/webinar_type:self_study/mycpe://webinardetailsview
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            List<String> params = uri.getPathSegments();

            if(params.size()>2) {
                String webinar_id_part = params.get(2);
                String webinar_type_part = params.get(3);
                redirect_dl = params.get(params.size() - 1);

                String[] arr_webinar_id = webinar_id_part.split(":");
                Log.e("*+*+*", "Size for arr_webinar_id : " + arr_webinar_id.length);
                if (arr_webinar_id.length > 1) {
                    webinar_id_dl = arr_webinar_id[1];
                    Log.e("*+*+*", "Data after webinar_id : " + webinar_id_dl);
                }

                String[] arr_webinar_type = webinar_type_part.split(":");
                if (arr_webinar_type.length > 1) {
                    webinar_type_dl = arr_webinar_type[1];
                    Log.e("*+*+*", "Data after webinar_type : " + webinar_type_dl);
                }

                Log.e("*+*+*", "Data after redirect dl : " + redirect_dl);

                Constant.isFromDeepLink = true;
            }
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;

        Constant.progWidth = displayMetrics.widthPixels;
//        Constant.progHeigth = (float) (Constant.progWidth/1.69);
        Constant.progHeigth = (float) (Constant.progWidth / 2);

//        DisplayVersionName();

//        Navigation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        DisplayVersionName();

    }

    private void CheckVersionAPI(final String version) {

        mAPIService.get_version(getResources().getString(R.string.accept), Constant.device_type, "" + version).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VersionCheck>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //handle failure response

                        String message = Constant.GetReturnResponse(context, e);
                        Snackbar.make(binding.tvbuildnumber, message, Snackbar.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(VersionCheck versionCheck) {
                        if (versionCheck.isSuccess()) {
                            // Here we are getting the response as success == true..
                            // Now we have to compare the is_update flag from here..
                            update_msg = versionCheck.getPayload().getData().getUpdateMessage();
                            // First we have to check for the user as guest user or logged in user..
                            // If the user is guest user then we have to check for the isUpdate the app..
                            if (!AppSettings.get_login_token(context).isEmpty()) {
                                // User is logged in..
                                if (versionCheck.getPayload().getData().isLogout()) {
                                    if (Constant.isNetworkAvailable(context)) {
                                        AppSettings.removeFromSharedPreferences(context, getResources().getString(R.string.str_token));
                                        AppSettings.set_login_token(context, "");
                                        AppSettings.set_device_id(context, "");
                                        AppSettings.set_email_id(context, "");

                                        Constant.webinartype = "";
                                        Constant.search = "";
                                        Constant.price_filter = "";
                                        Constant.date_filter = "";


                                        Constant.webinartype = "live";
                                        Constant.arrsavebooleanstate.clear();
                                        Constant.arraysavefilter.clear();
                                        Constant.arraypricefilter.clear();
                                        Constant.arraylistHomeDateFilter.clear();

                                        boolean isUpdate = versionCheck.getPayload().getData().isIsUpdate();
                                        boolean isForceUpdate = versionCheck.getPayload().getData().isIsForceUpdate();

                                        callUpdateCheck(isUpdate, isForceUpdate);
                                    } else {
                                        Constant.ShowPopUp(getResources().getString(R.string.please_check_internet_condition), context);
                                    }
                                } else {
                                    boolean isUpdate = versionCheck.getPayload().getData().isIsUpdate();
                                    boolean isForceUpdate = versionCheck.getPayload().getData().isIsForceUpdate();

                                    callUpdateCheck(isUpdate, isForceUpdate);
                                }
                            } else {
                                // User is not logged in.. Have to redirect to the update conditions..
                                boolean isUpdate = versionCheck.getPayload().getData().isIsUpdate();
                                boolean isForceUpdate = versionCheck.getPayload().getData().isIsForceUpdate();

                                callUpdateCheck(isUpdate, isForceUpdate);
                            }
                        } else {
                            Snackbar.make(binding.tvbuildnumber, versionCheck.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    private void callUpdateCheck(boolean isUpdate, boolean isForceUpdate) {

                        if (isUpdate) {
                            // There is the update here now have to check for the force update here..
                            // Show the popup for the update..
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            if (isForceUpdate) {
                                // This is the force update case..
//                                    alertDialog.setMessage(getResources().getString(R.string.str_forceupdate_1));
                                alertDialog.setTitle("myCPE");
                                alertDialog.setMessage("" + update_msg);
                                alertDialog.setCancelable(false);
                                alertDialog.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                        final String appPackageName = getPackageName(); // package name of the app
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }

                                    }
                                });
                                /*alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            finishAffinity();
                                            System.exit(0);
                                        } else {
                                            finish();
                                        }
                                    }
                                });*/

                                alertDialog.show();
                            } else {
                                // This is the normal update only..

//                                    alertDialog.setMessage(getResources().getString(R.string.str_update_1));
                                alertDialog.setTitle("myCPE");
                                alertDialog.setMessage("" + update_msg);
                                alertDialog.setCancelable(false);
                                alertDialog.setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                        final String appPackageName = getPackageName(); // package name of the app
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }

                                    }
                                });
                                alertDialog.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        newNavigation();
                                    }
                                });

                                alertDialog.show();
                            }
                        } else {
                            // There is no need to update the app and allow user to access the app as normally..
                            newNavigation();
                        }

                    }
                });
    }


    public void DisplayVersionName() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
            binding.tvbuildnumber.setText(version);

            CheckVersionAPI(version);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newNavigation() {
        if (Constant.isFromDeepLink) {
            Log.e("*+*+*", "Is from Deeplink : " + Constant.isFromDeepLink);
            if (redirect_dl.equalsIgnoreCase("")) {
                Constant.isFromDeepLink = false;
                Log.e("*+*+*", "Data after newNavigation method Oops something went wrong");
                Toast.makeText(context, "Oops something went wrong with the link", Toast.LENGTH_SHORT).show();

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, PreLoginActivity.class);
                    startActivity(i);
                    finish();
                }
            } else {
                if (redirect_dl.equalsIgnoreCase("webinardetailsview")) {
                    // Here we need to redirect user to the webinar details screen..
                    // Based on the webinar type live or self-study..
                    if (webinar_id_dl.equalsIgnoreCase("") || webinar_type_dl.equalsIgnoreCase("")) {
                        Log.e("*+*+*", "Data after newNavigation method Oops something went wrong");
                        Toast.makeText(context, "Oops something went wrong with the link", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!AppSettings.get_login_token(context).isEmpty()) {
                            Log.e("*+*+*", "Data after point 5");
                            try {
                                Log.e("*+*+*", "Data after point 6");
                                webinar_type = webinar_type_dl;
                                webinar_id = Integer.parseInt(webinar_id_dl);

                                Intent mIntent;
                                mIntent = new Intent(SplashActivity.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mIntent.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                                mIntent.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                                mIntent.putExtra(getResources().getString(R.string.str_is_notification), true);
                                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mIntent);
                            } catch (Exception e) {
                                Log.e("*+*+*", "Data after point 7");
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("*+*+*", "Data after point 8");
                            Intent i = new Intent(SplashActivity.this, PreLoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                } else {
                    // As we don't have the logic for redirecting to the specific page..
                    // So based on the default condition we have to check for the user is logged in or not..
                    // Then have to redirect to either listing screen or Prelogin screen..

                    if (!AppSettings.get_login_token(context).isEmpty()) {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(SplashActivity.this, PreLoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        } else {
            if (getIntent().getExtras() != null && getIntent().hasExtra(getResources().getString(R.string.pass_webinar_id))) {
                webinar_type = getIntent().getExtras().getString(getResources().getString(R.string.pass_webinar_type));
                webinar_id = getIntent().getExtras().getInt(getResources().getString(R.string.pass_webinar_id), 0);

                try {
                    Intent mIntent;
                    mIntent = new Intent(SplashActivity.this, WebinarDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    mIntent.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                    mIntent.putExtra(getResources().getString(R.string.str_is_notification), true);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mIntent);
                } catch (Exception e) {
                    e.printStackTrace();

                }

            } else {

            /*if (!AppSettings.get_walkthrough(context)) {

                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();

            } else {*/
                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, PreLoginActivity.class);
                    startActivity(i);
                    finish();
                }
//            }


            }
        }
    }
}
