package com.contest.competition.utils.activities.front.posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.utils.activities.BaseToolbarActivity;


public class SelectContestActivity extends BaseToolbarActivity {


    private Button createNewContest,simplePost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNewContest = findViewById(R.id.createNewContestBtn);
        simplePost = findViewById(R.id.simple_postBtn);

        createNewContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),StartNewContestActivity.class));

            }
        });

        simplePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),CreateSimplePostActivity.class));

            }
        });

    }

    @Override
    public void finish() {
        super.finish();

        Animatoo.animateSlideRight(SelectContestActivity.this);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_select_contest;
    }

    @Override
    protected String setActivityName() {
        return "Select";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }
}
