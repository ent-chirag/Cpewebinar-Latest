package com.myCPE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myCPE.activity.LoginActivity;
import com.myCPE.activity.PreLoginActivity;
import com.myCPE.activity.SignUpActivity;
import com.myCPE.adapter.HomeALLAdapter;
import com.myCPE.adapter.TopicsFilterHomePopUpAdapter;
import com.myCPE.fragments.AccountFragment;
import com.myCPE.fragments.HomeAllFragment;
import com.myCPE.fragments.MyCreditsFragment;
import com.myCPE.fragments.MyWebinarFragment;
import com.myCPE.model.postfeedback.PostFeedback;
import com.myCPE.model.subjects_store.Model_Subject_Area;
import com.myCPE.utility.AppSettings;
import com.myCPE.utility.Constant;
import com.myCPE.view.DialogsUtils;
import com.myCPE.webservice.APIService;
import com.myCPE.webservice.ApiUtilsNew;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.myCPE.utility.Constant.checkmywebinardotstatusset;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    AccountFragment accountFragment;
    MyCreditsFragment myCreditsFragment;
    HomeAllFragment homeAllFragment;
    MyWebinarFragment myWebinarFragment;

    public Context context;
    private static MainActivity instance;
    public RelativeLayout rel_top_bottom;
    public ImageView iv_mycredit, iv_mywebinar, iv_home, iv_premium, iv_account;
    public ImageView imgTabCertificate, imgTabWebinars, imgTabHome, imgTabPremium, imgTabProfile;
    private LinearLayout linCertificate, linMyWebinars, linHome, linPremium, linProfile;
    private TextView txtCertificate, txtMyWebinar, txtHome, txtPremium, txtProfile;
    public int setselectedtab = 0;
    public int selectmywebinardtab = 0;
    public Dialog myDialog;
    public TextView tv_login, tv_cancel, tv_create_account;
    Intent intent;
    public String webinar_type = "";

    private RelativeLayout relPopupView, relPopupSubmit;
    private LinearLayout linPopup;
    private TextView txtPopupCancel, txtPopupTitle;
    private RecyclerView rvPopupList;

    private RecyclerView.LayoutManager layoutManager;

    private RelativeLayout relPopupFeedback, relPopupSubmitFeedback;
    private LinearLayout linPopupFeedback;
    private TextView txtPopupFeedbackCancel;
    private EditText edtSubject, edtFeedback;

    private RelativeLayout relPopupReview, relPopupSubmitReview, relChecked_yes, relChecked_no;
    private LinearLayout linPopupReview;
    private TextView tv_yes, tv_no, txtPopupReviewCancel;
    private ImageView iv_one, iv_two,iv_three, iv_four, iv_five;
    private EditText edtReview;

    private int rating = 0;
    private boolean isLikeToKnowMore = false;
    private int is_like = 0;
    private String strReview = "";

    ProgressDialog progressDialog;
    private APIService mAPIService_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = MainActivity.this;
        intent = getIntent();

        mAPIService_new = ApiUtilsNew.getAPIService();

        rel_top_bottom = (RelativeLayout) findViewById(R.id.rel_top_bottom);
        iv_mycredit = (ImageView) findViewById(R.id.iv_mycredit);
        iv_mywebinar = (ImageView) findViewById(R.id.iv_mywebinar);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_premium = (ImageView) findViewById(R.id.iv_premium);
        iv_account = (ImageView) findViewById(R.id.iv_account);
//        imgTabCertificate, imgTabWebinars, imgTabHome, imgTabPremium, imgTabProfile
        imgTabCertificate = (ImageView) findViewById(R.id.imgTabCertificate);
        imgTabWebinars = (ImageView) findViewById(R.id.imgTabWebinars);
        imgTabHome = (ImageView) findViewById(R.id.imgTabHome);
        imgTabPremium = (ImageView) findViewById(R.id.imgTabPremium);
        imgTabProfile = (ImageView) findViewById(R.id.imgTabProfile);

//        linCertificate, linMyWebinars, linHome, linPremium, linProfile
        linCertificate = (LinearLayout) findViewById(R.id.linCertificate);
        linMyWebinars = (LinearLayout) findViewById(R.id.linMyWebinars);
        linHome = (LinearLayout) findViewById(R.id.linHome);
        linPremium = (LinearLayout) findViewById(R.id.linPremium);
        linProfile = (LinearLayout) findViewById(R.id.linProfile);

//        txtCertificate, txtMyWebinar, txtHome, txtPremium, txtProfile
        txtCertificate = (TextView) findViewById(R.id.txtCertificate);
        txtMyWebinar = (TextView) findViewById(R.id.txtMyWebinar);
        txtHome = (TextView) findViewById(R.id.txtHome);
        txtPremium = (TextView) findViewById(R.id.txtPremium);
        txtProfile = (TextView) findViewById(R.id.txtProfile);

        relPopupView = (RelativeLayout) findViewById(R.id.relPopupView);
        relPopupSubmit = (RelativeLayout) findViewById(R.id.relPopupSubmit);
        linPopup = (LinearLayout) findViewById(R.id.linPopup);
        txtPopupCancel = (TextView) findViewById(R.id.txtPopupCancel);
        txtPopupTitle = (TextView) findViewById(R.id.txtPopupTitle);
        rvPopupList = (RecyclerView) findViewById(R.id.rvPopupList);

        relPopupFeedback = (RelativeLayout) findViewById(R.id.relPopupFeedback);
        relPopupSubmitFeedback = (RelativeLayout) findViewById(R.id.relPopupFeedback);
        linPopupFeedback = (LinearLayout) findViewById(R.id.linPopupFeedback);

        txtPopupFeedbackCancel = (TextView) findViewById(R.id.txtPopupFeedbackCancel);

        edtSubject = (EditText) findViewById(R.id.edtSubject);
        edtFeedback = (EditText) findViewById(R.id.edtFeedback);

        relPopupReview = (RelativeLayout) findViewById(R.id.relPopupReview);
        relChecked_yes = (RelativeLayout) findViewById(R.id.relChecked_yes);
        relChecked_no = (RelativeLayout) findViewById(R.id.relChecked_no);
        relPopupSubmitReview = (RelativeLayout) findViewById(R.id.relPopupSubmitReview);

        linPopupReview = (LinearLayout) findViewById(R.id.linPopupReview);

        tv_yes = (TextView) findViewById(R.id.tv_yes);
        tv_no = (TextView) findViewById(R.id.tv_no);
        txtPopupReviewCancel = (TextView) findViewById(R.id.txtPopupReviewCancel);

        iv_one = (ImageView) findViewById(R.id.iv_one);
        iv_two = (ImageView) findViewById(R.id.iv_two);
        iv_three = (ImageView) findViewById(R.id.iv_three);
        iv_four = (ImageView) findViewById(R.id.iv_four);
        iv_five = (ImageView) findViewById(R.id.iv_five);

        edtReview = (EditText) findViewById(R.id.edtReview);

        relPopupSubmitReview.setOnClickListener(this);
        relChecked_yes.setOnClickListener(this);
        relChecked_no.setOnClickListener(this);
        linPopupReview.setOnClickListener(this);
        txtPopupReviewCancel.setOnClickListener(this);
        tv_yes.setOnClickListener(this);
        tv_no.setOnClickListener(this);
        iv_one.setOnClickListener(this);
        iv_two.setOnClickListener(this);
        iv_three.setOnClickListener(this);
        iv_four.setOnClickListener(this);
        iv_five.setOnClickListener(this);

        context = MainActivity.this;
//        Constant.setLightStatusBar(MainActivity.this);

//        iv_mycredit.setOnClickListener(new View.OnClickListener() {
//        imgTabCertificate.setOnClickListener(new View.OnClickListener() {
        linCertificate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Constant.arraylistselectedsubjectareahomeID.clear();
                    Constant.hashmap_subject_home_area.clear();
                    setselectedtab = 0;
                    selectmywebinardtab = 0;
//                    SetImageBackground(0);
                    SetImageBackground(2);
                    myCreditsFragment = new MyCreditsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myCreditsFragment, getResources()
                            .getString(R.string.mycreditfragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();
                } else {
                    ShowPopUp();
                }


            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;

        Constant.progWidth = displayMetrics.widthPixels;
//        Constant.progHeigth = (float) (Constant.progWidth/1.69);
        Constant.progHeigth = (float) (Constant.progWidth / 2);

//        iv_mywebinar.setOnClickListener(new View.OnClickListener() {
//        imgTabWebinars.setOnClickListener(new View.OnClickListener() {
        linMyWebinars.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Constant.arraylistselectedsubjectareahomeID.clear();
                    Constant.hashmap_subject_home_area.clear();
                    setselectedtab = 1;
                    selectmywebinardtab = 1;
                    checkmywebinardotstatusset = false;
                    SetImageBackground(1);
                    myWebinarFragment = new MyWebinarFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myWebinarFragment, getResources()
                            .getString(R.string.my_webinar_fragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();


                } else {
                    ShowPopUp();
                }


            }
        });


//        iv_home.setOnClickListener(new View.OnClickListener() {
//        imgTabHome.setOnClickListener(new View.OnClickListener() {
        linHome.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                setselectedtab = 0;
                selectmywebinardtab = 0;
                checkmywebinardotstatusset = false;
//                SetImageBackground(2);
                SetImageBackground(0);

                Constant.arrsavebooleanstate.clear();
                Constant.arraysavefilter.clear();
                Constant.arraypricefilter.clear();
                Constant.arraylistHomeDateFilter.clear();

                Constant.price_filter = "";
                Constant.date_filter = "";
                Constant.search = "";
                Constant.webinartype = "live";
                SetDefault("home");

            }
        });


//        iv_premium.setOnClickListener(new View.OnClickListener() {
//        imgTabPremium.setOnClickListener(new View.OnClickListener() {
        linPremium.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    setselectedtab = 2;
                    selectmywebinardtab = 0;
                    SetImageBackground(3);
                    SetDefault("premium");
                } else {
                    ShowPopUp();
                }


            }
        });

//        iv_account.setOnClickListener(new View.OnClickListener() {
//        imgTabProfile.setOnClickListener(new View.OnClickListener() {
        linProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Constant.arraylistselectedsubjectareahomeID.clear();
                    Constant.hashmap_subject_home_area.clear();
                    setselectedtab = 0;
                    selectmywebinardtab = 0;
                    SetImageBackground(4);
                    accountFragment = new AccountFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, accountFragment, getResources()
                            .getString(R.string.accountfragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();

                } else {
                    ShowPopUp();
                }


            }
        });

        relPopupSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSubject = edtSubject.getText().toString();
                String strFeedback = edtFeedback.getText().toString();

                if (strSubject.equalsIgnoreCase("")) {
                    Constant.toast(context, getResources().getString(R.string.val_subject));
                } else if (strFeedback.equalsIgnoreCase("")) {
                    Constant.toast(context, getResources().getString(R.string.val_review));
                } else {
                    if (Constant.isNetworkAvailable(context)) {
                        Log.e("*+*+*","API for sending feedback is called subject : "+strSubject+"  ::  \nFeedback : "+strFeedback);
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        PostFeedback(getResources().getString(R.string.accept), AppSettings.get_login_token(context),
                                strSubject, strFeedback);
                    } else {
                        Snackbar.make(relPopupFeedback, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtPopupFeedbackCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relPopupFeedback.setVisibility(View.GONE);
            }
        });

       /* relPopupFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relPopupFeedback.setVisibility(View.GONE);
            }
        });*/

        txtPopupCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relPopupView.setVisibility(View.GONE);
                HomeAllFragment.getInstance().cancelTopic();
            }
        });

        relPopupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<Integer> myArrayList = new ArrayList<Integer>(new LinkedHashSet<Integer>(Constant.arraylistselectedsubjectareahomeID));
                relPopupView.setVisibility(View.GONE);

                HomeAllFragment.getInstance().checkTopics();

                /*if (myArrayList.size() > 0) {

                    StringBuilder commaSepValueBuilder = new StringBuilder();

                    //Looping through the list
                    for (int i = 0; i < myArrayList.size(); i++) {
                        //append the value into the builder
                        commaSepValueBuilder.append(myArrayList.get(i));

                        //if the value is not the last element of the list
                        //then append the comma(,) as well
                        if (i != myArrayList.size() - 1) {
                            commaSepValueBuilder.append(",");
                        }
                    }

                    SubjectAreaFilter = commaSepValueBuilder.toString();
                    binding.btnTopics.setBackgroundResource(R.drawable.tag_selected);
                    binding.btnTopics.setTextColor(getResources().getColor(R.color.White));


                } else {
                    SubjectAreaFilter = "";
                    binding.btnTopics.setBackgroundResource(R.drawable.tag_unselected);
                    binding.btnTopics.setTextColor(getResources().getColor(R.color.home_tab_color_unselected));
                }*/
            }
        });

        SetDefault("default");


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkmywebinardotstatusset = false;
    }


    public void AutoLogout() {
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


            Intent i = new Intent(context, PreLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        } else {
            Constant.ShowPopUp(getResources().getString(R.string.please_check_internet_condition), context);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SetCreditScreen() {
        setselectedtab = 2;
        selectmywebinardtab = 2;
        SetImageBackground(2);
        myCreditsFragment = new MyCreditsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myCreditsFragment, getResources()
                .getString(R.string.mycreditfragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SetImageBackground(int position) {

        if (position == 2) {
            Log.e("*+*+*", "Position 2 Selected");
            imgTabCertificate.setImageResource(R.drawable.ic_tab_certificate);
            imgTabWebinars.setImageResource(R.drawable.ic_tab_my_webinar_un);
            imgTabHome.setImageResource(R.drawable.ic_tab_home_un);
            imgTabPremium.setImageResource(R.drawable.ic_tab_premium_un);
            imgTabProfile.setImageResource(R.drawable.ic_tab_account_un);

            txtCertificate.setTextColor(getResources().getColor(R.color.theme_background));
            txtMyWebinar.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtHome.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtPremium.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtProfile.setTextColor(getResources().getColor(R.color.color_icon_gray));
//            iv_mycredit.setImageResource(R.mipmap.footer_certificate_select_orange);
//            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
//            iv_premium.setImageResource(R.mipmap.footer_premium);
//            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 1) {
            Log.e("*+*+*", "Position 1 Selected");
            imgTabCertificate.setImageResource(R.drawable.ic_tab_certificate_un);
            imgTabWebinars.setImageResource(R.drawable.ic_tab_my_webinar);
            imgTabHome.setImageResource(R.drawable.ic_tab_home_un);
            imgTabPremium.setImageResource(R.drawable.ic_tab_premium_un);
            imgTabProfile.setImageResource(R.drawable.ic_tab_account_un);

            txtCertificate.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtMyWebinar.setTextColor(getResources().getColor(R.color.theme_background));
            txtHome.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtPremium.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtProfile.setTextColor(getResources().getColor(R.color.color_icon_gray));
//            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
//            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars_select);
//            iv_premium.setImageResource(R.mipmap.footer_premium);
//            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 0) {
            Log.e("*+*+*", "Position 0 Selected");
            imgTabCertificate.setImageResource(R.drawable.ic_tab_certificate_un);
            imgTabWebinars.setImageResource(R.drawable.ic_tab_my_webinar_un);
            imgTabHome.setImageResource(R.drawable.ic_tab_home);
            imgTabPremium.setImageResource(R.drawable.ic_tab_premium_un);
            imgTabProfile.setImageResource(R.drawable.ic_tab_account_un);

            txtCertificate.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtMyWebinar.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtHome.setTextColor(getResources().getColor(R.color.theme_background));
            txtPremium.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtProfile.setTextColor(getResources().getColor(R.color.color_icon_gray));
//            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
//            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
//            iv_premium.setImageResource(R.mipmap.footer_premium);
//            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 3) {
            Log.e("*+*+*", "Position 3 Selected");
            imgTabCertificate.setImageResource(R.drawable.ic_tab_certificate_un);
            imgTabWebinars.setImageResource(R.drawable.ic_tab_my_webinar_un);
            imgTabHome.setImageResource(R.drawable.ic_tab_home_un);
            imgTabPremium.setImageResource(R.drawable.ic_tab_premium);
            imgTabProfile.setImageResource(R.drawable.ic_tab_account_un);

            txtCertificate.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtMyWebinar.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtHome.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtPremium.setTextColor(getResources().getColor(R.color.theme_background));
            txtProfile.setTextColor(getResources().getColor(R.color.color_icon_gray));
//            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
//            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
//            iv_premium.setImageResource(R.mipmap.footer_premium_select_orange);
//            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 4) {
            Log.e("*+*+*", "Position 4 Selected");
            imgTabCertificate.setImageResource(R.drawable.ic_tab_certificate_un);
            imgTabWebinars.setImageResource(R.drawable.ic_tab_my_webinar_un);
            imgTabHome.setImageResource(R.drawable.ic_tab_home_un);
            imgTabPremium.setImageResource(R.drawable.ic_tab_premium_un);
            imgTabProfile.setImageResource(R.drawable.ic_tab_account);

            txtCertificate.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtMyWebinar.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtHome.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtPremium.setTextColor(getResources().getColor(R.color.color_icon_gray));
            txtProfile.setTextColor(getResources().getColor(R.color.theme_background));
//            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
//            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
//            iv_premium.setImageResource(R.mipmap.footer_premium);
//            iv_account.setImageResource(R.mipmap.footer_account_select);
        }


    }


    public static MainActivity getInstance() {
        return instance;

    }


    public void SetDefault(String value) {

        homeAllFragment = new HomeAllFragment();
        Bundle args = new Bundle();
        args.putString(context.getResources().getString(R.string.str_premium_lable), value);
        args.putString("actionsearch", "");
        homeAllFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, homeAllFragment, getResources()
                .getString(R.string.home_fragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void ShowPopUp() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.str_guest_user_dialog_title_new));
        builder.setMessage(getResources().getString(R.string.str_guest_user_dialog_msg_new));

//                        builder.setPositiveButton("OK", null);
        builder.setPositiveButton(getResources().getString(R.string.str_login_guest).toLowerCase(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        arg0.dismiss();

                        Intent i = new Intent(context, LoginActivity.class);
                        startActivity(i);
                        finish();

                    }
                });

        builder.setNegativeButton(getResources().getString(R.string.str_create_account).toLowerCase(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        Intent i = new Intent(context, SignUpActivity.class);
                        startActivity(i);
                        finish();

                    }
                });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

/*
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.guest_user_popup);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        tv_login = (TextView) myDialog.findViewById(R.id.tv_login_guest);
        tv_cancel = (TextView) myDialog.findViewById(R.id.tv_cancel_guest);
        tv_create_account = (TextView) myDialog.findViewById(R.id.tv_create_account);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }

                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }

                Intent i = new Intent(context, SignUpActivity.class);
                startActivity(i);
                finish();


            }
        });


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDialog.isShowing()) {
                    myDialog.dismiss();
                }

            }
        });


        myDialog.show();*/


    }

    public void showPopupTopics(ArrayList<Model_Subject_Area> arraylistModelSubjectArea) {
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_new);

//        linPopup.startAnimation(slide_up);
        relPopupView.setVisibility(View.VISIBLE);
        rvPopupList.setVisibility(View.VISIBLE);

        rvPopupList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvPopupList.setLayoutManager(layoutManager);

        if (arraylistModelSubjectArea.size() > 0) {
            TopicsFilterHomePopUpAdapter topicsFilterHomePopUpAdapter = new TopicsFilterHomePopUpAdapter(context,
                    arraylistModelSubjectArea);
            rvPopupList.setAdapter(topicsFilterHomePopUpAdapter);
        }
    }

    public void showPopupReview(String message) {
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_new);

        Log.e("*+*+*","Called method for showing popup from the MainAvtivity..");
        relPopupReview.setVisibility(View.VISIBLE);
        linPopupReview.startAnimation(slide_up);
    }

    public void showPopupFeedback() {
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up_new);

        relPopupFeedback.setVisibility(View.VISIBLE);
        linPopupFeedback.startAnimation(slide_up);

        edtFeedback.setText("");
        edtSubject.setText("");
    }

    public void PostFeedback(String accept, String authorization, String message, String subject) {

        mAPIService_new.PostContactUsFeedback(accept, getResources().getString(R.string.bearer) + " " + authorization, message,
                subject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostFeedback>() {
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
                            Snackbar.make(relPopupFeedback, message, Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(PostFeedback postFeedback) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        /*if (myDialog.isShowing()) {
                            myDialog.dismiss();
                        }*/

                        if (postFeedback.isSuccess()) {
                            Snackbar.make(relPopupFeedback, postFeedback.getMessage(), Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(relPopupFeedback, postFeedback.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }

                        edtFeedback.setText("");
                        edtSubject.setText("");
                        relPopupFeedback.setVisibility(View.GONE);
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linPopupReview:
                // Do nothing..
                break;

            case R.id.relPopupSubmitReview:
                break;

            case R.id.relChecked_yes:
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 1;
                break;

            case R.id.relChecked_no:
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 0;
                break;

            case R.id.tv_yes:
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 1;
                break;

            case R.id.tv_no:
                relChecked_no.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_checked));
                relChecked_yes.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                isLikeToKnowMore = true;
                is_like = 0;
                break;

            case R.id.txtPopupReviewCancel:
                relPopupReview.setVisibility(View.GONE);
                break;

            case R.id.iv_one:
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star);
                iv_three.setImageResource(R.mipmap.add_review_star);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                iv_one.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_two.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_three.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_four.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_five.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 1;
                break;

            case R.id.iv_two:
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                iv_one.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_two.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_three.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_four.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_five.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 2;
                break;

            case R.id.iv_three:
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star);
                iv_five.setImageResource(R.mipmap.add_review_star);

                iv_one.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_two.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_three.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_four.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_five.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 3;
                break;

            case R.id.iv_four:
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star_hover);
                iv_five.setImageResource(R.mipmap.add_review_star);

                iv_one.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_two.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_three.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_four.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_five.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 4;
                break;

            case R.id.iv_five:
                iv_one.setImageResource(R.mipmap.add_review_star_hover);
                iv_two.setImageResource(R.mipmap.add_review_star_hover);
                iv_three.setImageResource(R.mipmap.add_review_star_hover);
                iv_four.setImageResource(R.mipmap.add_review_star_hover);
                iv_five.setImageResource(R.mipmap.add_review_star_hover);

                iv_one.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_two.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_three.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_four.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));
                iv_five.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_rounded_btn_orange)));

                rating = 5;
                break;
        }
    }
}
