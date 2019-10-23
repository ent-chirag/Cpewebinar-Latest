package com.entigrity.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.entigrity.R;
import com.entigrity.adapter.WhoYouAreAdapter;
import com.entigrity.databinding.ActivityWhoShouldAttendBinding;
import com.entigrity.view.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class ActivityWhoYouAre extends AppCompatActivity {

    ActivityWhoShouldAttendBinding binding;
    public ArrayList<String> whoshouldattend = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    public Context context;
    WhoYouAreAdapter whoYouAreAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_who_should_attend);
        context = ActivityWhoYouAre.this;

        Intent intent = getIntent();
        if (intent != null) {
            whoshouldattend = intent.getStringArrayListExtra(getResources().getString(R.string.pass_who_you_are_list));
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
                finish();
            }
        });
    }
}
