package com.entigrity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entigrity.activity.LoginActivity;
import com.entigrity.activity.PreLoginActivity;
import com.entigrity.activity.SignUpActivity;
import com.entigrity.fragments.AccountFragment;
import com.entigrity.fragments.HomeAllFragment;
import com.entigrity.fragments.MyCreditsFragment;
import com.entigrity.fragments.MyFavoriteScreenFragment;
import com.entigrity.fragments.MyWebinarFragment;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;

import static com.entigrity.utility.Constant.checkmywebinardotstatusset;

public class MainActivity extends AppCompatActivity {


    AccountFragment accountFragment;
    MyCreditsFragment myCreditsFragment;
    HomeAllFragment homeAllFragment;
    MyWebinarFragment myWebinarFragment;

    public Context context;
    private static MainActivity instance;
    public RelativeLayout rel_top_bottom;
    public ImageView iv_mycredit, iv_mywebinar, iv_home, iv_premium, iv_account;
    public int setselectedtab = 0;
    public int selectmywebinardtab = 0;
    public Dialog myDialog;
    public TextView tv_login, tv_cancel, tv_create_account;
    Intent intent;
    public String webinar_type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = MainActivity.this;
        intent = getIntent();


        rel_top_bottom = (RelativeLayout) findViewById(R.id.rel_top_bottom);
        iv_mycredit = (ImageView) findViewById(R.id.iv_mycredit);
        iv_mywebinar = (ImageView) findViewById(R.id.iv_mywebinar);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_premium = (ImageView) findViewById(R.id.iv_premium);
        iv_account = (ImageView) findViewById(R.id.iv_account);


        context = MainActivity.this;
        Constant.setLightStatusBar(MainActivity.this);


        iv_mycredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Constant.arraylistselectedsubjectareahomeID.clear();
                    Constant.hashmap_subject_home_area.clear();
                    setselectedtab = 0;
                    selectmywebinardtab = 0;
                    SetImageBackground(0);
                    myCreditsFragment = new MyCreditsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myCreditsFragment, getResources()
                            .getString(R.string.mycreditfragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();
                } else {
                    ShowPopUp();
                }


            }
        });

        iv_mywebinar.setOnClickListener(new View.OnClickListener() {
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


        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setselectedtab = 0;
                selectmywebinardtab = 0;
                checkmywebinardotstatusset = false;
                SetImageBackground(2);
                Constant.price_filter = "";
                Constant.date_filter = "";
                Constant.search = "";
                SetDefault("home");

            }
        });


        iv_premium.setOnClickListener(new View.OnClickListener() {
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

        iv_account.setOnClickListener(new View.OnClickListener() {
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

            Intent i = new Intent(context, PreLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        } else {
            Constant.ShowPopUp(getResources().getString(R.string.please_check_internet_condition), context);
        }
    }


    public void SetCreditScreen() {
        setselectedtab = 0;
        selectmywebinardtab = 0;
        SetImageBackground(0);
        myCreditsFragment = new MyCreditsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, myCreditsFragment, getResources()
                .getString(R.string.mycreditfragment)).addToBackStack(getResources().getString(R.string.add_to_back_stack)).commit();

    }


    public void SetImageBackground(int position) {

        if (position == 0) {
            iv_mycredit.setImageResource(R.mipmap.footer_certificate_select_orange);
            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
            iv_premium.setImageResource(R.mipmap.footer_premium);
            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 1) {
            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars_select);
            iv_premium.setImageResource(R.mipmap.footer_premium);
            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 2) {
            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
            iv_premium.setImageResource(R.mipmap.footer_premium);
            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 3) {
            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
            iv_premium.setImageResource(R.mipmap.footer_premium_select_orange);
            iv_account.setImageResource(R.mipmap.footer_account);
        } else if (position == 4) {
            iv_mycredit.setImageResource(R.mipmap.footer_certificate);
            iv_mywebinar.setImageResource(R.mipmap.footer_mywebinars);
            iv_premium.setImageResource(R.mipmap.footer_premium);
            iv_account.setImageResource(R.mipmap.footer_account_select);
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


        myDialog.show();


    }


}
