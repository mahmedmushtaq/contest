package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;



public class RalewayBoldTv  extends TextView {
    public RalewayBoldTv(Context context) {
        super(context);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setRalewayBold(context,this);
    }
}
