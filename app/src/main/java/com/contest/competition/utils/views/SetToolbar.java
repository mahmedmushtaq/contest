package com.contest.competition.utils.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.contest.competition.R;

public class SetToolbar {
    public static void setToolbarOnlyText(View view, String name){
        TextView textView = view.findViewById(R.id.toolbar_for_all_text);
        textView.setText(name);
    }

    public static void setToolbarWithBackBtn(final Context context, View view , String name){

    }
}
