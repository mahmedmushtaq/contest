package com.contest.competition.utils.activities.launching;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.contest.competition.R;

public class ShowOfflineActivity extends AppCompatActivity {

    private TextView retryTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_offline);

        retryTv = findViewById(R.id.offline_retry);

        retryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LauncherActivity.class));
                finish();
            }
        });



    }
}
