package com.entigrity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.entigrity.R;
import com.entigrity.databinding.ActivityViewProfileBinding;
import com.entigrity.model.view_topics_of_interest.TopicOfInterestsItem;
import com.entigrity.model.viewprofile_proffesional_credential.modelViewProfileProfesional;
import com.entigrity.utility.Constant;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity {

    ActivityViewProfileBinding binding;
    ProgressDialog progressDialog;
    private static final String TAG = ViewProfileActivity.class.getName();
    public Context context;
    public String firstname = "", lastname = "", email = "", firmname = "", mobilenumber = "", zipcode = "", country = "", ptin_number = "";
    public int country_id = 0, state_id = 0, city_id = 0, jobtitle_id = 0, industry_id = 0;
    public String whoyouarevalue = "";
    public String state = "", city = "";
    private int country_pos = 0;
    private int state_pos = 0;
    private int city_pos = 0;
    private int who_you_are_pos = 0;
    private int job_title_pos = 0;
    private int industry_pos = 0;
    private ArrayList<TopicOfInterestsItem> topicsofinterestitem = new ArrayList<TopicOfInterestsItem>();
    public ArrayList<modelViewProfileProfesional> professionalcredential = new
            ArrayList<>();
    private ArrayList<String> arraylistsubcategory = new ArrayList<>();
    public int subcategoryremains = 0;
    public String subcategory = "";
    public String job_titile, industry = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_profile);
        context = ViewProfileActivity.this;


        Intent intent = getIntent();
        if (intent != null) {
            firstname = intent.getStringExtra(getResources().getString(R.string.pass_fname));
            lastname = intent.getStringExtra(getResources().getString(R.string.pass_lname));
            email = intent.getStringExtra(getResources().getString(R.string.pass_email));
            firmname = intent.getStringExtra(getResources().getString(R.string.pass_firm_name));
            country = intent.getStringExtra(getResources().getString(R.string.pass_country_text));
            mobilenumber = intent.getStringExtra(getResources().getString(R.string.pass_mobile_number));

            state = intent.getStringExtra(getResources().getString(R.string.pass_state_text));
            city = intent.getStringExtra(getResources().getString(R.string.pass_city_text));
            zipcode = intent.getStringExtra(getResources().getString(R.string.pass_zipcode));
            ptin_number = intent.getStringExtra(getResources().getString(R.string.pass_ptin_number));
            country_pos = intent.getIntExtra(getResources().getString(R.string.pass_country), 0);
            state_pos = intent.getIntExtra(getResources().getString(R.string.pass_state), 0);
            city_pos = intent.getIntExtra(getResources().getString(R.string.pass_city), 0);
            /*who_you_are_pos = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are), 0);*/
            job_title_pos = intent.getIntExtra(getResources().getString(R.string.pass_job_title), 0);
            industry_pos = intent.getIntExtra(getResources().getString(R.string.pass_industry), 0);
            job_titile = intent.getStringExtra(getResources().getString(R.string.pass_job_title_text));
            industry = intent.getStringExtra(getResources().getString(R.string.pass_industry_text));


            topicsofinterestitem = intent.getParcelableArrayListExtra(getResources().getString(R.string.pass_view_topics_of_interest));
            professionalcredential = intent.getParcelableArrayListExtra(getResources().getString(R.string.pass_who_you_are_text));

            if (topicsofinterestitem.size() > 0) {
                for (int i = 0; i < topicsofinterestitem.size(); i++) {
                    for (int j = 0; j < topicsofinterestitem.get(i).getTags().size(); j++) {
                        arraylistsubcategory.add(topicsofinterestitem.get(i).getTags().get(j).getName());
                    }
                }


                if (arraylistsubcategory.size() > 0) {

                    if (arraylistsubcategory.size() == 1) {
                        binding.tvTopics.setVisibility(View.GONE);
                        binding.tvTopicsMore.setVisibility(View.VISIBLE);
                        subcategory = arraylistsubcategory.get(0);
                        binding.tvTopicsMore.setText(subcategory);

                    } else {
                        binding.tvTopics.setVisibility(View.VISIBLE);
                        subcategory = arraylistsubcategory.get(0);
                        binding.tvTopics.setText(subcategory);
                        subcategoryremains = arraylistsubcategory.size() - 1;
                        binding.tvTopicsMore.setVisibility(View.VISIBLE);
                        binding.tvTopicsMore.setText(("+" + subcategoryremains
                                + " more"));
                    }


                } else {
                    binding.tvTopics.setVisibility(View.GONE);
                    binding.tvTopicsMore.setVisibility(View.GONE);

                }

            }


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


        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate_EditProfile();
            }
        });
        binding.relTopicsOfInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate_Topicsofinterest();

            }
        });

    }

    private void SetData() {

        if (!firstname.equalsIgnoreCase("") && firstname != null) {
            binding.tvFirstname.setText(firstname);
        }


        if (!lastname.equalsIgnoreCase("") && lastname != null) {
            binding.tvLastname.setText(lastname);
        }


        if (!email.equalsIgnoreCase("") && email != null) {
            binding.tvEmailname.setText(email);
        }

        if (!firmname.equalsIgnoreCase("") && firmname != null) {
            binding.tvFirmname.setText(firmname);
        }

        if (!mobilenumber.equalsIgnoreCase("") && mobilenumber != null) {
            binding.tvMobilenumber.setText(mobilenumber);
        }


        if (!ptin_number.equalsIgnoreCase("") && ptin_number != null) {
            binding.tvPtinNumber.setText(ptin_number);
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


        if (job_title_pos != 0) {
            jobtitle_id = job_title_pos;
        }


        if (industry_pos != 0) {
            industry_id = industry_pos;
        }


        if (!country.equalsIgnoreCase("") && country != null) {
            binding.tvContry.setText("" + country);

        }
        if (!state.equalsIgnoreCase("") && state != null) {
            binding.tvState.setText("" + state);
        }

        if (!job_titile.equalsIgnoreCase("") && job_titile != null) {
            binding.tvJobTitle.setText(job_titile);
        }


        if (!industry.equalsIgnoreCase("") && industry != null) {
            binding.tvIndustry.setText(industry);
        }

        if (!city.equalsIgnoreCase("") && city != null) {
            binding.tvCity.setText("" + city);
        }


        if (professionalcredential.size() > 0) {
            binding.tvWhoYouAre.setText("" + professionalcredential.get(0).getName());
        }


        if (!zipcode.equalsIgnoreCase("") && zipcode != null) {
            binding.tvZipcode.setText(zipcode);
        }

    }


    public void Navigate_EditProfile() {
        Intent i = new Intent(context, EditProfileActivity.class);
        i.putExtra(getResources().getString(R.string.pass_fname), firstname);
        i.putExtra(getResources().getString(R.string.pass_lname), lastname);
        i.putExtra(getResources().getString(R.string.pass_email), email);
        i.putExtra(getResources().getString(R.string.pass_firm_name), firmname);
        i.putExtra(getResources().getString(R.string.pass_mobile_number), mobilenumber);
        i.putExtra(getResources().getString(R.string.pass_ptin_number), ptin_number);
        i.putExtra(getResources().getString(R.string.pass_country), country_id);
        i.putExtra(getResources().getString(R.string.pass_state), state_id);
        i.putExtra(getResources().getString(R.string.pass_city), city_id);
        i.putExtra(getResources().getString(R.string.pass_job_title_id), jobtitle_id);
        i.putExtra(getResources().getString(R.string.pass_industry_id), industry_id);
        i.putExtra(getResources().getString(R.string.pass_state_text), state);
        i.putExtra(getResources().getString(R.string.pass_city_text), city);
        i.putExtra(getResources().getString(R.string.pass_zipcode), zipcode);
        i.putExtra(getResources().getString(R.string.pass_who_you_are), professionalcredential.get(0).getId());
        i.putStringArrayListExtra(getResources().getString(R.string.pass_selected_list), arraylistsubcategory);
        startActivity(i);
        finish();
    }

    public void Navigate_Topicsofinterest() {
        Intent i = new Intent(context, ViewTopicsOfInterestActivity.class);
        i.putExtra(getResources().getString(R.string.str_get_key_screen), getResources().getString(R.string.from_view_profile));
        i.putParcelableArrayListExtra(getResources().getString(R.string.pass_view_topics_of_interest), topicsofinterestitem);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
