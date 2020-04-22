package com.contest.competition.utils.activities.launching;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;
import com.contest.competition.utils.activities.BaseToolbarActivity;

import java.util.ArrayList;

public class FeaturesActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ListView listView = findViewById(R.id.features_activity_lv);
        ArrayList<String> textString = new ArrayList<>();
        textString.add("Post pictures");
        textString.add("=> Create pictures contest with friends");
        textString.add("Comment on a posts");
        textString.add("Vote a posts");
        textString.add("Vote a contest and decide who win contest");
        textString.add("=> Viral your pictures by boosting posts");
        textString.add("Search a friends");
        textString.add("=> We are continue developing our app please support us by sharing app");
        textString.add("=> Show top trending pictures");

        ArrayAdapter adapter = new ArrayAdapter<>(this,R.layout.simple_rv_layout,textString);
        listView.setAdapter(adapter);

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_features;
    }

    @Override
    protected String setActivityName() {
        return "Features";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Animatoo.animateSlideRight(FeaturesActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(FeaturesActivity.this);
    }
}
