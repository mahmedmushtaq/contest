package com.contest.competition.utils.activities.launching;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;
import com.contest.competition.utils.activities.front.FeedActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import com.contest.competition.classes.Network;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.front.HomeActivity;


public class LauncherActivity extends AppCompatActivity {
    private Handler mHandler;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        multiplePermission();

        mAuth = FirebaseAuth.getInstance();


    }

    private void openIntroActivity(){
       if(Network.isAvailable(this)) {
           startActivity(new Intent(getBaseContext(), FrontActivity.class));
           finish();

           Animatoo.animateSlideLeft(LauncherActivity.this);
       }else{
           startActivity(new Intent(getBaseContext(), ShowOfflineActivity.class));
           finish();
           Animatoo.animateSlideLeft(LauncherActivity.this);
       }


    }

    private void setSharedPrefer() {

        mHandler = new Handler();
        LoginSharedPrefer mSharedPrefer = new LoginSharedPrefer(this);
        if (mSharedPrefer.getUsername() == null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openIntroActivity();
                }
            },1000);

        } else {
            // save value for online


            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openHomeActivity();
                }
            },1000);

        }






    }

    private void openHomeActivity(){
        if(Network.isAvailable(this)) {
            startActivity(new Intent(getBaseContext(), FeedActivity.class));
            finish();
           Animatoo.animateSlideLeft(LauncherActivity.this);
        }else{
            startActivity(new Intent(getBaseContext(), ShowOfflineActivity.class));
            finish();
           Animatoo.animateSlideLeft(LauncherActivity.this);
        }
    }

    private void multiplePermission(){
        Permissions.check(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                "Read And write permission required", new Permissions.Options()
                        .setSettingsDialogTitle("Warning!").setRationaleDialogTitle("Info"),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        //do your task

                        setSharedPrefer();

                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        super.onDenied(context, deniedPermissions);
                        finish();
                    }
                });
    }
}
