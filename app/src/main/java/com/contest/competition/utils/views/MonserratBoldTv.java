package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;



public class MonserratBoldTv extends TextView {
    public MonserratBoldTv(Context context) {
        super(context);
        SetFonts.setRalewayBold(context,this);
    }

    public MonserratBoldTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setMontserratBold(context,this);
    }

    public MonserratBoldTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setMontserratBold(context,this);
    }

    public MonserratBoldTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setMontserratBold(context,this);
    }
}
