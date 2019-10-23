package com.entigrity.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.entigrity.MainActivity;
import com.entigrity.R;
import com.entigrity.databinding.ActivityEditProfileBinding;
import com.entigrity.model.Job_title.ModelJobTitle;
import com.entigrity.model.city.CityItem;
import com.entigrity.model.city.CityModel;
import com.entigrity.model.country.CountryItem;
import com.entigrity.model.country.CountryModel;
import com.entigrity.model.editProfile.EditProfileModel;
import com.entigrity.model.industry.Model_Industry;
import com.entigrity.model.state.StateItem;
import com.entigrity.model.state.StateModel;
import com.entigrity.model.usertype.UserTypeModel;
import com.entigrity.utility.AppSettings;
import com.entigrity.utility.Constant;
import com.entigrity.view.DialogsUtils;
import com.entigrity.view.UsPhoneNumberFormatter;
import com.entigrity.webservice.APIService;
import com.entigrity.webservice.ApiUtilsNew;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.entigrity.utility.Constant.checkmywebinardotstatusset;


public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    public Context context;
    private APIService mAPIService_new;


    private ArrayList<String> arrayLististusertype = new ArrayList<String>();
    private ArrayList<Integer> arrayLististusertypeid = new ArrayList<Integer>();


    private ArrayList<String> arrayListjobtitle = new ArrayList<String>();
    private ArrayList<Integer> arrayListjobtitleid = new ArrayList<Integer>();


    private ArrayList<String> arrayListindustry = new ArrayList<String>();
    private ArrayList<Integer> arrayListindustryid = new ArrayList<Integer>();


    public ArrayList<String> getcountryarraylist = new ArrayList<String>();
    public ArrayList<CountryItem> getcountryarray = new ArrayList<CountryItem>();

    public ArrayList<String> getstatearralist = new ArrayList<String>();
    public ArrayList<StateItem> getstatearray = new ArrayList<>();

    public ArrayList<String> getcityarraylist = new ArrayList<String>();
    public ArrayList<CityItem> getcityarray = new ArrayList<CityItem>();
    private ArrayList<String> arraylistsubcategory = new ArrayList<>();


    private int country_id = 0;
    private int state_id = 0;
    private int city_id = 0;
    private int who_you_are_id = 0;
    private int jobtitle_id = 0;
    private int industry_id = 0;


    private int country_pos = 0;
    private int state_pos = 0;
    private int city_pos = 0;
    public String firstname = "", lastname = "", email = "", firmname = "", mobilenumber = "", zipcode = "", ptin_number = "";
    private int who_you_are_pos = 0;
    private int jobtitle_id_pos = 0;
    private int industry_id_pos = 0;


    ProgressDialog progressDialog;


    public boolean boolean_country_spinner = true;
    public boolean boolean_state_spinner = true;
    public boolean boolean_city_spinner = true;
    public boolean boolean_usertype_spinner = true;


    public boolean boolean_jobtitle_spinner = true;
    public boolean boolean_industry_spinner = true;

    public boolean checkstatearray = false;
    public boolean checkcityarray = false;

    public int subcategoryremains = 0;
    public String subcategory = "";


    private String State, City;


    private int country_set = 0, state_set = 0, city_set = 0, whoyouare_set = 0, job_title_set = 0, industry_set = 0;
    public boolean checkflagset = false;


    private static final String TAG = EditProfileActivity.class.getName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        context = EditProfileActivity.this;
        mAPIService_new = ApiUtilsNew.getAPIService();

        Intent intent = getIntent();
        if (intent != null) {
            firstname = intent.getStringExtra(getResources().getString(R.string.pass_fname));
            lastname = intent.getStringExtra(getResources().getString(R.string.pass_lname));
            email = intent.getStringExtra(getResources().getString(R.string.pass_email));
            firmname = intent.getStringExtra(getResources().getString(R.string.pass_firm_name));
            mobilenumber = intent.getStringExtra(getResources().getString(R.string.pass_mobile_number));
            ptin_number = intent.getStringExtra(getResources().getString(R.string.pass_ptin_number));
            State = intent.getStringExtra(getResources().getString(R.string.pass_state_text));
            City = intent.getStringExtra(getResources().getString(R.string.pass_city_text));
            zipcode = intent.getStringExtra(getResources().getString(R.string.pass_zipcode));
            country_pos = intent.getIntExtra(getResources().getString(R.string.pass_country), 0);
            state_pos = intent.getIntExtra(getResources().getString(R.string.pass_state), 0);
            city_pos = intent.getIntExtra(getResources().getString(R.string.pass_city), 0);
            who_you_are_pos = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are), 0);
            jobtitle_id_pos = intent.getIntExtra(getResources().getString(R.string.pass_job_title_id), 0);
            industry_id_pos = intent.getIntExtra(getResources().getString(R.string.pass_industry_id), 0);
            arraylistsubcategory = intent.getStringArrayListExtra(getResources().getString(R.string.pass_selected_list));

            if (Constant.isNetworkAvailable(context)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // change UI elements here
                        SetData();
                    }
                });
            } else {
                Snackbar.make(binding.relTopicsOfInterest, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
            }

        }

        binding.edtMobileNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(binding.edtMobileNumber));
        binding.edtMobileNumber.addTextChangedListener(addLineNumberFormatter);


        binding.edtMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }

                if (s.length() == 14) {
                    Constant.hideKeyboard((Activity) context);
                }

            }
        });


        binding.relTopicsOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfileActivity.this, TopicsOfInterestActivity.class);
                i.putExtra(getResources().getString(R.string.str_get_key_screen_key), getResources().getString(R.string.from_edit_profile));
                startActivity(i);
                finish();

            }
        });

        binding.tvTopicsOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditProfileActivity.this, TopicsOfInterestActivity.class);
                i.putExtra(getResources().getString(R.string.str_get_key_screen_key), getResources().getString(R.string.from_edit_profile));
                startActivity(i);
                finish();
            }
        });


        binding.spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (boolean_country_spinner) {
                    boolean_country_spinner = false;
                } else {
                    if (getcountryarraylist.get(position).equalsIgnoreCase("Country")) {
                        country_id = 0;
                    } else {
                        country_id = getcountryarray.get(position - 1).getId();
                        checkflagset = true;

                        State = "";
                        state_set = 0;
                        state_pos = 0;
                        city_set = 0;
                        city_pos = 0;


                        if (Constant.isNetworkAvailable(context)) {
                            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                            GetState(country_id);
                        } else {
                            Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (boolean_state_spinner) {
                    boolean_state_spinner = false;
                } else {
                    if (getstatearralist.get(position).equalsIgnoreCase("State")) {
                        state_id = 0;
                    } else {
                        state_id = getstatearray.get(position - 1).getId();

                        checkflagset = true;

                        City = "";
                        city_set = 0;
                        city_pos = 0;


                        if (Constant.isNetworkAvailable(context)) {
                            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                            GetCity(state_id);
                        } else {
                            Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                        }
                    }


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (boolean_city_spinner) {
                    boolean_city_spinner = false;
                } else {
                    if (getcityarraylist.get(position).equalsIgnoreCase("City")) {
                        city_id = 0;
                    } else {
                        city_id = getcityarray.get(position - 1).getId();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (boolean_usertype_spinner) {
                    boolean_usertype_spinner = false;
                } else {

                    if (arrayLististusertype.get(position).equalsIgnoreCase(getResources()
                            .getString(R.string.str_who_you_are))) {
                        who_you_are_id = 0;
                    } else {
                        who_you_are_id = arrayLististusertypeid.get(position - 1);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerJobTitile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (boolean_jobtitle_spinner) {
                    boolean_jobtitle_spinner = false;
                } else {
                    if (arrayListjobtitle.get(position).equalsIgnoreCase("Job Title")) {
                        jobtitle_id = 0;
                    } else {
                        jobtitle_id = arrayListjobtitleid.get(position - 1);
                    }


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (boolean_industry_spinner) {
                    boolean_industry_spinner = false;
                } else {
                    if (arrayListindustry.get(position).equalsIgnoreCase("Industry")) {
                        industry_id = 0;
                    } else {
                        industry_id = arrayListindustryid.get(position - 1);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        if (Constant.isNetworkAvailable(context)) {
            progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
            GetCountry();
        } else {
            Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
        }

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation()) {
                    if (Constant.isNetworkAvailable(context)) {
                        progressDialog = DialogsUtils.showProgressDialog(context, getResources().getString(R.string.progrees_msg));
                        EditPost(getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context),
                                Constant.Trim(binding.edtFirstname.getText().toString()), Constant.Trim(binding.edtLastname.getText().toString()),
                                Constant.Trim(binding.edtEmailname.getText().toString()), Constant.Trim(binding.edtFirmname.getText().toString()), country_id, state_id, city_id, Integer.parseInt(Constant.Trim(binding.edtZipcode.getText().toString())), Constant.Trim(binding.edtMobileNumber.getText()
                                        .toString()), Constant.Trim(binding.edtPtinNumber.getText().toString()), who_you_are_id, jobtitle_id, industry_id);
                    } else {
                        Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }


    public void SetData() {

        if (!firstname.equalsIgnoreCase("") && firstname != null) {
            binding.edtFirstname.setText(firstname);
        }


        if (!ptin_number.equalsIgnoreCase("") && ptin_number != null) {
            binding.edtPtinNumber.setText(ptin_number);
        }


        if (!lastname.equalsIgnoreCase("") && lastname != null) {
            binding.edtLastname.setText(lastname);
        }


        if (!email.equalsIgnoreCase("") && email != null) {
            binding.edtEmailname.setText(email);
        }

        if (!firmname.equalsIgnoreCase("") && firmname != null) {
            binding.edtFirmname.setText(firmname);
        }

        if (!mobilenumber.equalsIgnoreCase("") && mobilenumber != null) {
            binding.edtMobileNumber.setText(mobilenumber);
        }


        if (country_pos != 0) {
            country_id = country_pos;
        }
        if (state_pos != 0) {
            state_id = state_pos;
        }

        if (city_pos != 0) {
            city_id = city_pos;
        }

        if (who_you_are_pos != 0) {
            who_you_are_id = who_you_are_pos;
        }

        if (jobtitle_id_pos != 0) {
            jobtitle_id = jobtitle_id_pos;
        }
        if (industry_id_pos != 0) {
            industry_id = industry_id_pos;
        }


        if (arraylistsubcategory.size() > 0) {
            subcategory = arraylistsubcategory.get(0);
            binding.tvTopics.setVisibility(View.VISIBLE);
            binding.tvTopics.setText(subcategory);
            if (arraylistsubcategory.size() > 1) {
                subcategoryremains = arraylistsubcategory.size() - 1;
                binding.tvTopicsMore.setVisibility(View.VISIBLE);
                binding.tvTopicsMore.setText(("+" + subcategoryremains
                        + " more"));
            } else {
                binding.tvTopicsMore.setVisibility(View.GONE);
            }
        } else {
            binding.tvTopics.setVisibility(View.GONE);
            binding.tvTopicsMore.setVisibility(View.GONE);
        }


        if (!zipcode.equalsIgnoreCase("") && zipcode != null) {
            binding.edtZipcode.setText(zipcode);
        }


    }


    public void EditPost(String Authorization, String first_name, String last_name, String email,
                         String firm_name, final int country_id, final int state_id, final int city_id,
                         int zipcode, String contact_no, String ptin_number, final int user_type, final int jobtitle_id, final int industry_id) {


        // RxJava
        mAPIService_new.Ediprofile(getResources().getString(R.string.accept), Authorization, first_name, last_name, email
                , firm_name, country_id, state_id, city_id, zipcode, contact_no, ptin_number, user_type, jobtitle_id, industry_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EditProfileModel>() {
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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(EditProfileModel editProfileModel) {
                        if (editProfileModel.isSuccess()) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            Snackbar.make(binding.btnsubmit, editProfileModel.getMessage(), Snackbar.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkmywebinardotstatusset = false;
                                    Constant.isdataupdate = true;
                                    finish();
                                }
                            }, 2000);


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Snackbar.make(binding.btnsubmit, editProfileModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }


    public void GetJobTitle() {

        mAPIService_new.GetJobTitle(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ModelJobTitle>() {
                    @Override
                    public void onCompleted() {

                        if (Constant.isNetworkAvailable(context)) {
                            GetIndustry();
                        } else {
                            Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                        }

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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onNext(ModelJobTitle modelJobTitle) {

                        if (modelJobTitle.isSuccess()) {
                            arrayListjobtitle.clear();

                            arrayListjobtitle.add("Job Title");


                            for (int i = 0; i < modelJobTitle.getPayload().getJobTitle().size(); i++) {
                                arrayListjobtitle.add(modelJobTitle.getPayload().getJobTitle().get(i).getName());
                                arrayListjobtitleid.add(modelJobTitle.getPayload().getJobTitle().get(i).getId());
                            }


                            if (jobtitle_id_pos == 0) {
                                jobtitle_id_pos = 0;
                                jobtitle_id = jobtitle_id_pos;
                            } else {
                                for (int i = 0; i < arrayListjobtitleid.size(); i++) {
                                    if (jobtitle_id_pos == arrayListjobtitleid.get(i)) {
                                        job_title_set = arrayListjobtitleid.indexOf(arrayListjobtitleid.get(i));
                                    }
                                }
                            }


                            Show_JobTitle_Adapter();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.btnsubmit, modelJobTitle.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }


    public void GetIndustry() {

        mAPIService_new.GetIndustryList(getResources().getString(R.string.accept), getResources().getString(R.string.bearer) + " " + AppSettings.get_login_token(context)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Model_Industry>() {
                    @Override
                    public void onCompleted() {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Show_Industry_Adapter();

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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onNext(Model_Industry model_industry) {

                        if (model_industry.isSuccess()) {
                            arrayListindustry.clear();
                            arrayListindustry.add("Industry");


                            for (int i = 0; i < model_industry.getPayload().getIndustriesList().size(); i++) {
                                arrayListindustry.add(model_industry.getPayload().getIndustriesList().get(i).getName());
                                arrayListindustryid.add(model_industry.getPayload().getIndustriesList().get(i).getId());
                            }


                            if (industry_id_pos == 0) {
                                industry_id_pos = 0;
                                industry_id = industry_id_pos;
                            } else {
                                for (int i = 0; i < arrayListindustryid.size(); i++) {
                                    if (industry_id_pos == arrayListindustryid.get(i)) {
                                        industry_set = arrayListindustryid.indexOf(arrayListindustryid.get(i));
                                    }
                                }
                            }

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.btnsubmit, model_industry.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }


    public void GetProffesionalCredential() {
        mAPIService_new.Getproffesionalcredential(getResources().getString(R.string.accept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserTypeModel>() {
                    @Override
                    public void onCompleted() {

                        if (Constant.isNetworkAvailable(context)) {
                            GetJobTitle();
                        } else {
                            Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                        }


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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(UserTypeModel userTypeModel) {

                        if (userTypeModel.isSuccess()) {
                            arrayLististusertype.clear();
                            arrayLististusertype.add(getResources().getString(R.string.str_who_you_are));

                            for (int i = 0; i < userTypeModel.getPayload().getUserType().size(); i++) {
                                arrayLististusertype.add(userTypeModel.getPayload().getUserType().get(i).getName());
                                arrayLististusertypeid.add(userTypeModel.getPayload().getUserType().get(i).getId());
                            }
                            if (who_you_are_pos == 0) {
                                who_you_are_pos = 0;
                                who_you_are_id = who_you_are_pos;
                            } else {
                                for (int i = 0; i < arrayLististusertypeid.size(); i++) {
                                    if (who_you_are_pos == arrayLististusertypeid.get(i)) {
                                        whoyouare_set = arrayLististusertypeid.indexOf(arrayLististusertypeid.get(i));
                                    }
                                }
                            }


                            ShowAdapter();
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Snackbar.make(binding.btnsubmit, userTypeModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    public void ShowAdapter() {
        if (arrayLististusertype.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, arrayLististusertype);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinner.setAdapter(aa);


            if (who_you_are_pos == 0) {
                binding.spinner.setSelection(0);
            } else {
                binding.spinner.setSelection(whoyouare_set + 1);
            }


        }
    }


    public void Show_JobTitle_Adapter() {
        if (arrayListjobtitle.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, arrayListjobtitle);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerJobTitile.setAdapter(aa);

            if (jobtitle_id_pos == 0) {
                binding.spinnerJobTitile.setSelection(0);
            } else {
                binding.spinnerJobTitile.setSelection(job_title_set + 1);
            }


        }
    }


    public void Show_Industry_Adapter() {
        if (arrayListindustry.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it


            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, arrayListindustry);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerIndustry.setAdapter(aa);

            if (industry_id_pos == 0) {
                binding.spinnerIndustry.setSelection(0);
            } else {
                binding.spinnerIndustry.setSelection(industry_set + 1);
            }


        }
    }

    public void Show_Country_Adapter() {
        if (getcountryarraylist.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, getcountryarraylist);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerCountry.setAdapter(aa);


            if (country_pos == 0) {
                binding.spinnerCountry.setSelection(0);
            } else {
                binding.spinnerCountry.setSelection(country_set + 1);
            }


        }


    }


    public void Show_City_else_Adapter() {
        if (getcityarraylist.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, getcityarraylist);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerCity.setAdapter(aa);


        }


    }

    public void Show_City_Adapter() {
        if (getcityarraylist.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, getcityarraylist);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerCity.setAdapter(aa);
            if (checkcityarray == true) {
                binding.spinnerCity.setSelection(1);
            } else {
                if (city_pos == 0) {
                    binding.spinnerCity.setSelection(0);
                } else {
                    binding.spinnerCity.setSelection(city_set + 1);
                }


            }


        }


    }


    public void Show_State_Adapter() {
        if (getstatearralist.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, getstatearralist);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerState.setAdapter(aa);

            if (checkstatearray == true) {
                binding.spinnerState.setSelection(1);
            } else {
                if (state_pos == 0) {
                    binding.spinnerState.setSelection(0);
                } else {
                    binding.spinnerState.setSelection(state_set + 1);
                }

            }


        }


    }


    public void Show_State_else_Adapter() {
        if (getstatearralist.size() > 0) {
            //Getting the instance of Spinner and applying OnItemSelectedListener on it

            //Creating the ArrayAdapter instance having the user type list
            ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, getstatearralist);
            aa.setDropDownViewResource(R.layout.spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            binding.spinnerState.setAdapter(aa);


        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void GetCountry() {

        mAPIService_new.GetCountry(getResources().getString(R.string.accept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CountryModel>() {
                    @Override
                    public void onCompleted() {

                        if (!checkflagset) {
                            if (Constant.isNetworkAvailable(context)) {
                                GetState(country_pos);
                            } else {
                                Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                            }
                        }


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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onNext(CountryModel CountryModel) {


                        if (CountryModel.isSuccess() == true) {
                            getcountryarraylist.clear();
                            getcountryarray.clear();

                            getcountryarraylist.add("Country");


                            if (CountryModel.getPayload().getCountry().size() > 0) {
                                for (int i = 0; i < CountryModel.getPayload().getCountry().size(); i++) {
                                    getcountryarraylist.add(CountryModel.getPayload().getCountry().get(i).getName());
                                }

                                for (int j = 0; j < CountryModel.getPayload().getCountry().size(); j++) {
                                    CountryItem countryItem = new CountryItem();
                                    for (int i = 0; i < CountryModel.getPayload().getCountry().size(); i++) {
                                        countryItem.setName(CountryModel.getPayload().getCountry().get(j).getName());
                                        countryItem.setId(CountryModel.getPayload().getCountry().get(j).getId());
                                    }
                                    getcountryarray.add(countryItem);
                                }


                                if (country_pos == 0) {
                                    country_pos = 0;
                                    country_id = country_pos;
                                } else {
                                    for (int i = 0; i < getcountryarray.size(); i++) {
                                        if (country_pos == getcountryarray.get(i).getId()) {
                                            country_set = getcountryarray.indexOf(getcountryarray.get(i));
                                        }
                                    }

                                }


                                Show_Country_Adapter();
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Snackbar.make(binding.btnsubmit, CountryModel.getMessage(), Snackbar.LENGTH_SHORT).show();

                        }


                    }
                });

    }


    public void GetCity(int state_id) {

        mAPIService_new.GetCity(getResources().getString(R.string.accept), state_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityModel>() {
                    @Override
                    public void onCompleted() {
                        if (!checkflagset) {
                            if (Constant.isNetworkAvailable(context)) {
                                GetProffesionalCredential();
                            } else {
                                Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                            }
                        }


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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(CityModel cityModel) {


                        if (cityModel.isSuccess() == true) {

                            if (checkflagset == true) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }


                            getcityarraylist.clear();
                            getcityarray.clear();

                            getcityarraylist.add("City");


                            if (cityModel.getPayload().getCity().size() > 0) {
                                for (int i = 0; i < cityModel.getPayload().getCity().size(); i++) {
                                    getcityarraylist.add(cityModel.getPayload().getCity().get(i).getName());
                                }

                                for (int j = 0; j < cityModel.getPayload().getCity().size(); j++) {
                                    CityItem cityItem = new CityItem();
                                    for (int i = 0; i < cityModel.getPayload().getCity().size(); i++) {
                                        cityItem.setId(cityModel.getPayload().getCity().get(j).getId());
                                        cityItem.setName(cityModel.getPayload().getCity().get(j).getName());
                                    }
                                    getcityarray.add(cityItem);

                                }


                                if (city_pos == 0) {
                                    city_pos = 0;
                                    city_id = city_pos;
                                } else {
                                    for (int i = 0; i < getcityarray.size(); i++) {
                                        if (city_pos == getcityarray.get(i).getId()) {
                                            city_set = getcityarray.indexOf(getcityarray.get(i));
                                        }
                                    }

                                }


                                checkcityarray = false;

                                Show_City_Adapter();

                            } else {

                                if (!City.equalsIgnoreCase("") && City != null) {
                                    checkcityarray = true;
                                    getcityarraylist.add(City);
                                    Show_City_Adapter();
                                } else {
                                    Show_City_else_Adapter();
                                }


                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Snackbar.make(binding.btnsubmit, cityModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    public void GetState(int country_id) {

        mAPIService_new.GetState(getResources().getString(R.string.accept), country_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StateModel>() {
                    @Override
                    public void onCompleted() {
                        if (!checkflagset) {

                            if (Constant.isNetworkAvailable(context)) {
                                GetCity(state_pos);
                            } else {
                                Snackbar.make(binding.btnsubmit, getResources().getString(R.string.please_check_internet_condition), Snackbar.LENGTH_SHORT).show();
                            }
                        }


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
                            Snackbar.make(binding.btnsubmit, message, Snackbar.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onNext(StateModel stateModel) {


                        if (stateModel.isSuccess() == true) {

                            if (checkflagset == true) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }

                            getstatearralist.clear();
                            getstatearray.clear();

                            getstatearralist.add("State");

                            if (stateModel.getPayload().getState().size() > 0) {
                                for (int i = 0; i < stateModel.getPayload().getState().size(); i++) {
                                    getstatearralist.add(stateModel.getPayload().getState().get(i).getName());

                                }

                                for (int j = 0; j < stateModel.getPayload().getState().size(); j++) {

                                    StateItem stateItem = new StateItem();

                                    for (int i = 0; i < stateModel.getPayload().getState().size(); i++) {
                                        stateItem.setId(stateModel.getPayload().getState().get(j).getId());
                                        stateItem.setName(stateModel.getPayload().getState().get(j).getName());

                                    }
                                    getstatearray.add(stateItem);
                                }


                                if (state_pos == 0) {
                                    state_pos = 0;
                                    state_id = state_pos;
                                } else {
                                    for (int i = 0; i < getstatearray.size(); i++) {
                                        if (state_pos == getstatearray.get(i).getId()) {
                                            state_set = getstatearray.indexOf(getstatearray.get(i));

                                        }
                                    }
                                }


                                checkstatearray = false;


                                Show_State_Adapter();
                            } else {

                                if (!State.equalsIgnoreCase("") && State != null) {
                                    checkstatearray = true;
                                    getstatearralist.add(State);
                                    Show_State_Adapter();
                                } else {
                                    Show_State_else_Adapter();
                                }


                            }


                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Snackbar.make(binding.btnsubmit, stateModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }


                    }
                });

    }


    public Boolean Validation() {
        if (Constant.Trim(binding.edtFirstname.getText().toString()).isEmpty()) {

            binding.edtLastname.clearFocus();
            binding.edtFirstname.requestFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();

            Snackbar.make(binding.edtFirstname, getResources().getString(R.string.val_firstname), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtLastname.getText().toString()).isEmpty()) {
            binding.edtLastname.requestFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();
            Snackbar.make(binding.edtLastname, getResources().getString(R.string.val_lastname), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtMobileNumber.getText().toString()).isEmpty()) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.requestFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();


            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.val_mobile_number), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (country_id == 0) {

            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();

            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.str_country), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (state_id == 0) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();
            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.str_state), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (city_id == 0) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();
            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.str_city), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtZipcode.getText().toString()).isEmpty()) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.requestFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();

            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.val_zipcode), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (jobtitle_id == 0) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();
            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.val_job_title), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (industry_id == 0) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();
            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.val_industry), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (who_you_are_id == 0) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.clearFocus();
            binding.edtPtinNumber.clearFocus();
            Snackbar.make(binding.edtMobileNumber, getResources().getString(R.string.val_user_type), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (Constant.Trim(binding.edtFirmname.getText().toString()).isEmpty()) {
            binding.edtLastname.clearFocus();
            binding.edtFirstname.clearFocus();
            binding.edtZipcode.clearFocus();
            binding.edtMobileNumber.clearFocus();
            binding.edtFirmname.requestFocus();
            binding.edtPtinNumber.clearFocus();


            Snackbar.make(binding.edtFirmname, getResources().getString(R.string.val_firm_name_register), Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }


    }


}
