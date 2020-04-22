package com.contest.competition.utils.activities.advertisements;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.contest.competition.R;

public class AdvertisementMain extends AppCompatActivity {


    private Button buyCredits;
    public static final String LICENSE_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_main);


//
        Toolbar toolbar = findViewById( R.id.advertisement_mainToolbar);
        setSupportActionBar(toolbar);
        TextView tv = findViewById(R.id.toolbar_for_all_text);
        tv.setText("Advertisements");

//        TextView textView = findViewById(R.id.toolbar_for_all_text);
//        textView.setText("Advertisement");
//        ImageView imageView = findViewById(R.id.toolbar_backBtn);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });


        buyCredits = findViewById(R.id.advertisement_mainBuyCredits);

        buyCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getBaseContext(),BuyCreditsActivity.class));
            }
        });



    }




}
