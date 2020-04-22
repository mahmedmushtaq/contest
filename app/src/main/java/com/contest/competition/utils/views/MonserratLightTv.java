package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;



public class MonserratLightTv extends TextView {
    public MonserratLightTv(Context context) {
        super(context);
        SetFonts.setMontserratLight(context,this);
    }

    public MonserratLightTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setMontserratLight(context,this);
    }

    public MonserratLightTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setMontserratLight(context,this);
    }

    public MonserratLightTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setMontserratLight(context,this);
    }
}
