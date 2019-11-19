package com.entigrity.activity;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.entigrity.R;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

public class UniversalVideoViewActivity extends AppCompatActivity implements UniversalVideoView.VideoViewCallback {

    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    //    private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private static final String VIDEO_URL = "http://testing-website.in//uploads//webinar_video//1568125719.mp4";

    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;

    View mBottomLayout;
    View mVideoLayout;
    TextView mStart;

    private int mSeekPosition;
    private int mSeekPositionMedia;
    private int cachedHeight;
    private boolean isFullscreen;

    private Button btnGetCurrentTime, btnGetTotalLenght;
    private boolean checkPause = false;

    //    private static final String VIDEO_URL = "http://testing-website.in//uploads//webinar_video//1568125719.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_video_view);

        mVideoLayout = findViewById(R.id.video_layout);
        mBottomLayout = findViewById(R.id.bottom_layout);

        btnGetCurrentTime = (Button) findViewById(R.id.btnGetCurrentTime);
        btnGetTotalLenght = (Button) findViewById(R.id.btnGetTotalLenght);

        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(this);
        mStart = (TextView) findViewById(R.id.start);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }
                checkPause = false;
                mVideoView.start();
//                mMediaController.setTitle("Big Buck Bunny");
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion ");
            }
        });

        btnGetCurrentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGetTotalLenght.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        Log.e("*+*+*","onPause is called Activity");
        Log.e("*+*+*","onPause is called seek pos : "+mSeekPosition);
        Log.e("*+*+*","onPause is called seek pos : "+mSeekPosition);
        Log.e("*+*+*","onPause is called Media player last pos : "+mVideoView.getCurrentPosition());
        mSeekPositionMedia = mVideoView.getCurrentPosition();
        if (mVideoView != null && mVideoView.isPlaying()) {
//            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
            checkPause = true;
        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(VIDEO_URL);
                mVideoView.requestFocus();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("*+*+*","onResume is called");
        Log.e("*+*+*","onResume is called seek position : "+mSeekPosition);
        Log.e("*+*+*","onResume is called media player pos : "+mVideoView.getCurrentPosition());
        Log.e("*+*+*","onResume is called new Var pos : "+mSeekPositionMedia);
        if(mVideoView != null){
            if(checkPause)
            Log.e("*+*+*","onResume is called condition is satisfied");
//            mVideoView.seekTo(mSeekPosition);
//            mVideoView.seekTo(mVideoView.getCurrentPosition());
            mVideoView.seekTo(mSeekPositionMedia);
            mVideoView.start();
        }
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {

            Log.d(TAG, "full Screen is called Position=" + mSeekPosition);

            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.GONE);

            /*if (mVideoView != null && mVideoView.isPlaying()) {
                mSeekPosition = mVideoView.getCurrentPosition();
                Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
//                mVideoView.pause();
                mVideoView.start();
            }*/

        } else {

            Log.d(TAG, "small Screen is called Position=" + mSeekPosition);

            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            mBottomLayout.setVisibility(View.VISIBLE);

            /*if (mVideoView != null && mVideoView.isPlaying()) {
                mSeekPosition = mVideoView.getCurrentPosition();
                Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
//                mVideoView.pause();
                mVideoView.start();
            }*/
        }

        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.e("*+*+*","onPause is called Media player");
        Log.e("*+*+*","onPause is called Media player Seek Position : "+mSeekPosition);
        Log.e("*+*+*","onPause is called Media player last pos : "+mVideoView.getCurrentPosition());
        Log.d(TAG, "onPause UniversalVideoView callback");
        mSeekPositionMedia = mVideoView.getCurrentPosition();
//        mSeekPosition = mVideoView.getCurrentPosition();
        if(mVideoView != null && mVideoView.isPlaying()){
            checkPause = true;
        }
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

}
