package com.contest.competition.utils.views;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.Button;

import com.contest.competition.R;

public class FollowBtnStyle {

    public static void setFollowedBtn(Button followBtn, Context context){
        SetFonts.setQuickSandRegular(context,followBtn);
        followBtn.setText("Following");
        followBtn.setTextSize(10);
       // followBtn.setBackgroundColor(context.getResources().getColor(R.color.peter));
        followBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.followed_btn));
        followBtn.setTextColor(context.getResources().getColor(R.color.white));
    }

    public static void setRequestedBtn(Button followBtn,Context context){
        SetFonts.setQuickSandRegular(context,followBtn);
        followBtn.setText("Requested");
        followBtn.setTextSize(10);
        followBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.requested_btn));
        followBtn.setTextColor(context.getResources().getColor(R.color.white));
    }

    public static void setFollowBtn(Button followBtn,Context context){
        SetFonts.setQuickSandRegular(context,followBtn);
        followBtn.setText("Follow");
        followBtn.setTextSize(12);
        followBtn.setBackground(ContextCompat.getDrawable(context,R.drawable.follow_btn));
        followBtn.setTextColor(context.getResources().getColor(R.color.peter));
    }


}
