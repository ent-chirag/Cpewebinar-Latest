package com.myCPE.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.myCPE.R;

public class WebinarDetailsActivityNew extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout relImgBack;
    // Live Webinar Controllers..
    private RelativeLayout relLiveWebinar;
    private TextView txtLiveWebinarTitle, txtLiveWebinarAuthor, txtWebinarDate;
    private ImageView imgUserModel;
    // SelfStudy Webinar Controllers..
    private RelativeLayout relOnDemandWebinar;
    private ImageView ivthumbhel, iv_play;
    private FrameLayout video_layout;
    public SimpleExoPlayerView exoplayer;
    private ProgressBar progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webinar_details_new);

        init();
    }

    private void init() {

        relImgBack = (RelativeLayout) findViewById(R.id.relImgBack);
        // Live Webinar controllers..
        relLiveWebinar = (RelativeLayout) findViewById(R.id.relLiveWebinar);
        txtLiveWebinarTitle = (TextView) findViewById(R.id.txtLiveWebinarTitle);
        txtLiveWebinarAuthor = (TextView) findViewById(R.id.txtLiveWebinarAuthor);
        txtWebinarDate = (TextView) findViewById(R.id.txtWebinarDate);
        imgUserModel = (ImageView) findViewById(R.id.imgUserModel);
        // OnDemandWebinar Controllers..
        relOnDemandWebinar = (RelativeLayout) findViewById(R.id.relOnDemandWebinar);
        ivthumbhel = (ImageView) findViewById(R.id.ivthumbhel);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        video_layout = (FrameLayout) findViewById(R.id.video_layout);
        exoplayer = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);


        relImgBack.setOnClickListener(this);
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
