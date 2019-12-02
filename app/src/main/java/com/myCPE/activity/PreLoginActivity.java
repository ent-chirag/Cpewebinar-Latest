package com.myCPE.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.databinding.ActivityPreloginBinding;

public class PreLoginActivity extends AppCompatActivity implements Animation.AnimationListener {

    ActivityPreloginBinding binding;
    /* SignInButton signInButton;
     private GoogleApiClient googleApiClient;
     private static final int RC_SIGN_IN = 1;*/
    // Animation
    Animation animMoveToTop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prelogin);

      /*  // load the animation
        animMoveToTop = AnimationUtils.loadAnimation(PreLoginActivity.this, R.anim.move);

        // set animation listener
        animMoveToTop.setAnimationListener(this);

        binding.ivMycpe.setVisibility(View.VISIBLE);
        binding.ivMycpe.startAnimation(animMoveToTop);*/


       /* final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                binding.strsignup.setVisibility(View.VISIBLE);
                binding.strlogin.setVisibility(View.VISIBLE);


                binding.strsignup.startAnimation(animMoveToTop);
                binding.strlogin.startAnimation(animMoveToTop);
            }
        }, 3000);
*/


       /* GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        signInButton=(SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
*/


        binding.lvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreLoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        binding.strlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreLoginActivity.this, LoginActivity.class);
                startActivity(i);
//                finish();


            }
        });
        binding.strsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PreLoginActivity.this, SignUpActivity.class);
                startActivity(i);
//                finish();
            }
        });


    }

    /*@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }
    private void gotoProfile(){
        Intent intent=new Intent(PreLoginActivity.this,ProfileActivity.class);
        startActivity(intent);
    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        /*if (animation == animMoveToTop) {
            Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
        }*/
       //binding.ivMycpe.clearAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
