package com.contest.competition.utils.activities.profileactivities;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.contest.competition.R;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.webfiles.Addresses;

public class ProfileViewActivity extends AppCompatActivity {
    private ImageView profilePic;
    private ProgressBar profileViewPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        String profilePicPath = getIntent().getStringExtra(KeyStore.PASS_DATA);

        profilePic = findViewById(R.id.popup_imageViewActivity);
        profileViewPb = findViewById(R.id.profileView_pB);


//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;




        Glide.with(this).asBitmap().load(Addresses.getWebAddress()+profilePicPath).apply(new RequestOptions().centerCrop()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
             profileViewPb.setVisibility(View.GONE);
             profilePic.setImageBitmap(resource);
            }
        });



    }
}
