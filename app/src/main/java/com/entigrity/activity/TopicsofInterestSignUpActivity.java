package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.adapter.SignUpInterestAdapter;
import com.entigrity.model.topicsofinterestn.TagsItem;
import com.entigrity.model.topicsofinterestn.TopicOfInterestsItem;
import com.entigrity.model.topicsofinterestn.Topicsofinterest;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.view.SimpleDividerItemDecoration;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TopicsofInterestSignUpActivity extends AppCompatActivity {
    public Context context;
    private APIService mAPIService;
    private static final String TAG = TopicsofInterestSignUpActivity.class.getName();
    ProgressDialog progressDialog;
    ImageView ivback;
    RecyclerView rvtopics_of_interest;
    TextView tv_submit;
    public ArrayList<TopicOfInterestsItem> mlist = new ArrayList<TopicOfInterestsItem>();


    public ArrayList<TagsItem> topicsofinterestitem = new ArrayList<TagsItem>();
    private int user_type = 0;
    public boolean checkprivacypolicystatus = false;

    LinearLayoutManager linearLayoutManager;
    SignUpInterestAdapter adapter;
    public EditText edt_search;
    private String fromscreen = "";

    private String fname, lname, email, password, confirm_password, firmname, mobilenumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.entigrity.R.layout.activity_signup_topicsofinterest);
        mAPIService = ApiUtilsNew.getAPIService();
        context = TopicsofInterestSignUpActivity.this;

        init();
        onclick();

        Intent intent = getIntent();
        if (intent != null) {
            fromscreen = intent.getStringExtra(getResources().getString(R.string.str_get_key_screen));


            if (fromscreen.equalsIgnoreCase(getResources().getString(R.string.from_sign_up_screen))) {
                tv_submit.setText(getResources().getString(R.string.str_register));
                fname = intent.getStringExtra(getResources().getString(R.string.reg_firstname));
                lname = intent.getStringExtra(getResources().getString(R.string.reg_lastname));
                email = intent.getStringExtra(getResources().getString(R.string.reg_email));
                password = intent.getStringExtra(getResources().getString(R.string.reg_password));
                confirm_password = intent.getStringExtra(getResources().getString(R.string.reg_confirm_password));
                firmname = intent.getStringExtra(getResources().getString(R.string.reg_firmname));
                mobilenumber = intent.getStringExtra(getResources().getString(R.string.reg_mobilenumber));
                user_type = intent.getIntExtra(getResources().getString(R.string.reg_whoyouare), 0);
                checkprivacypolicystatus = intent.getBooleanExtra(getResources().getString(R.string.reg_isaccepted), false);
            } else {
                tv_submit.setText(getResources().getString(R.string.str_submit));

            }


        }


        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvtopics_of_interest.setLayoutManager(linearLayoutManager);
        rvtopics_of_interest.addItemDecoration(new SimpleDividerItemDecoration(context));


        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(com.entigrity.R.string.progrees_msg));
            GetTopicsofInterest();
        } else {
            Snackbar.make(tv_submit, getResources().getString(com.entigrity.R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

    }

    private void onclick() {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fromscreen.equalsIgnoreCase(getResources().getString(R.string.from_sign_up_screen))) {
                    if (Constant.arraylistselectedvalue.size() != 0) {
                        if (Constant.isNetworkAvailable(context)) {

                            String topicsofinterest = android.text.TextUtils.join(",", Constant.arraylistselectedvalue);
                            System.out.println(topicsofinterest);

                            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));

                       /*     RegisterPost(fname, lname, email, password, confirm_password, firmname, mobilenumber,
                                    topicsofinterest, user_type, AppSettings.get_device_id(context), AppSettings.get_device_token(context), Constant.device_type);*/
                        } else {
                            Snackbar.make(tv_submit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();

                        }
                    } else {
                        Snackbar.make(tv_submit, getResources().getString(R.string.val_topics), Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    if (Constant.arraylistselectedvalue.size() != 0) {
                        finish();
                    } else {
                        Snackbar.make(tv_submit, getResources().getString(R.string.val_topics), Snackbar.LENGTH_SHORT).show();
                    }

                }


            }
        });

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.arraylistselectedvalue.clear();
                finish();


            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter != null) {
                    adapter.getFilter().filter(s.toString());
                }

            }
        });


    }

   /* public void RegisterPost(String first_name, String last_name, String email, String password, String confirm_password,
                             String firm_name, String contact_no, String tags, int user_type,
                             String device_id, String device_token, String device_type
    ) {

        // RxJava
        mAPIService.Register(getResources().getString(R.string.accept), first_name, last_name
                , email, password, confirm_password, firm_name, contact_no, tags, user_type, device_id, device_token, device_type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegistrationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);
                        Snackbar.make(tv_submit, message, Snackbar.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onNext(RegistrationModel registrationModel) {
                        if (registrationModel.isSuccess()) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Constant.arraylistselectedvalue.clear();

                            AppSettings.set_login_token(context, registrationModel.getPayload().getUser().getToken());
                            AppSettings.set_email_id(context, registrationModel.getPayload().getUser().getEmail());


                            Intent i = new Intent(context, MainActivity.class);
                            startActivity(i);
                            finish();


                        } else if (!registrationModel.isSuccess()) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            Snackbar.make(tv_submit, registrationModel.getMessage(), Snackbar.LENGTH_SHORT).show();


                        }


                    }
                });

    }*/


    private void init() {
        ivback = (ImageView) findViewById(com.entigrity.R.id.ivback);
        rvtopics_of_interest = (RecyclerView) findViewById(R.id.rvtopics_of_interest);
        tv_submit = (TextView) findViewById(com.entigrity.R.id.tv_register);
        edt_search = (EditText) findViewById(R.id.edt_search);
    }

    public void GetTopicsofInterest() {
        mAPIService.GetTopicsofInterestsSignUp(getResources().getString(com.entigrity.R.string.accept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Topicsofinterest>() {
                    @Override
                    public void onCompleted() {
                        adapter = new SignUpInterestAdapter(context, topicsofinterestitem);
                        rvtopics_of_interest.setAdapter(adapter);


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        String message = Constant.GetReturnResponse(context, e);
                        Snackbar.make(tv_submit, message, Snackbar.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onNext(Topicsofinterest topicsofinterest) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        topicsofinterestitem.clear();


                        if (topicsofinterest.isSuccess()) {
                            if (topicsofinterest.getPayload().getTopicOfInterests().size() > 0) {
                                for (int i = 0; i < topicsofinterest.getPayload().getTopicOfInterests().size(); i++) {
                                    TopicOfInterestsItem topicOfInterestsItem = new TopicOfInterestsItem();
                                    topicOfInterestsItem.setName(topicsofinterest.getPayload().getTopicOfInterests().get(i).getName());
                                    topicOfInterestsItem.setId(topicsofinterest.getPayload().getTopicOfInterests().get(i).getId());
                                    topicOfInterestsItem.setTags(topicsofinterest.getPayload().getTopicOfInterests().get(i).getTags());
                                    mlist.add(topicOfInterestsItem);
                                }
                            }


                            for (int k = 0; k < mlist.size(); k++) {
                                for (int j = 0; j < mlist.get(k).getTags().size(); j++) {
                                    TagsItem tagsItem = new TagsItem();
                                    tagsItem.setId(mlist.get(k).getTags().get(j).getId());
                                    tagsItem.setName(mlist.get(k).getTags().get(j).getName());
                                    tagsItem.setIsChecked(mlist.get(k).getTags().get(j).isIsChecked());
                                    topicsofinterestitem.add(tagsItem);

                                }
                            }

                        } else {
                            Snackbar.make(tv_submit, topicsofinterest.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });
    }


}
