package com.contest.competition.utils.activities;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contest.competition.R;

public abstract class
BaseToolbarActivity extends AppCompatActivity {
    public static final int WHITE_TOOLBAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
        setContentView(R.layout.activity_base_toolbar);

        Toolbar toolbar =  findViewById(R.id.baseToolbarActivity_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);




        LinearLayout baseLl = findViewById(R.id.baseToolbarActivity_Ll);
        View view = getLayoutInflater().inflate(setLayout(),null);
        baseLl.addView(view);


        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tv = findViewById(R.id.toolbar_for_all_text);
        tv.setText(setActivityName());

        if(useWhiteToolbar() == WHITE_TOOLBAR){
            toolbar.setBackgroundColor(getResources().getColor(R.color.white));
            tv.setTextColor(getResources().getColor(R.color.black));
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.black_back));
        }

    }

    protected abstract int setLayout();
    protected abstract String setActivityName();
    protected abstract int useWhiteToolbar();

}
