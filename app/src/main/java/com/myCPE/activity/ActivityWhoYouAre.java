package com.myCPE.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.myCPE.R;
import com.myCPE.adapter.WhoYouAreAdapter;
import com.myCPE.databinding.ActivityWhoShouldAttendBinding;
import com.myCPE.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class ActivityWhoYouAre extends AppCompatActivity {

    ActivityWhoShouldAttendBinding binding;
    public ArrayList<String> whoshouldattend = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    public Context context;
    WhoYouAreAdapter whoYouAreAdapter;
    public int webinar_id = 0;
    public String webinar_type = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_who_should_attend);
        context = ActivityWhoYouAre.this;

        Intent intent = getIntent();
        if (intent != null) {
            whoshouldattend = intent.getStringArrayListExtra(getResources().getString(R.string.pass_who_you_are_list));
            webinar_id = intent.getIntExtra(getResources().getString(R.string.pass_who_you_are_list_review_question), 0);
            webinar_type = intent.getStringExtra(getResources().getString(R.string.pass_webinar_type));
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            binding.rvWhoattendlist.setLayoutManager(linearLayoutManager);
            binding.rvWhoattendlist.addItemDecoration(new SimpleDividerItemDecoration(context));
            binding.rvWhoattendlist.setItemAnimator(new DefaultItemAnimator());

            if (whoshouldattend.size() > 0) {
                binding.rvWhoattendlist.setVisibility(View.VISIBLE);
                binding.tvNodatafound.setVisibility(View.GONE);
                whoYouAreAdapter = new WhoYouAreAdapter(context, whoshouldattend);
                binding.rvWhoattendlist.setAdapter(whoYouAreAdapter);
            } else {
                binding.rvWhoattendlist.setVisibility(View.GONE);
                binding.tvNodatafound.setVisibility(View.VISIBLE);
            }

        }

        binding.ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!webinar_type.equalsIgnoreCase("")) {
                    Intent i = new Intent(context, WebinarDetailsActivity.class);
                    i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
                    i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
                    startActivity(i);
                    finish();
                } else {
                    finish();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!webinar_type.equalsIgnoreCase("")) {
            Intent i = new Intent(context, WebinarDetailsActivity.class);
            i.putExtra(getResources().getString(R.string.pass_webinar_id), webinar_id);
            i.putExtra(getResources().getString(R.string.pass_webinar_type), webinar_type);
            startActivity(i);
            finish();
        } else {
            finish();
        }

    }

}
