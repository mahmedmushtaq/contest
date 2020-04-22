package com.contest.competition.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class RalewayBoldEt extends EditText {
    public RalewayBoldEt(Context context) {
        super(context);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldEt(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldEt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldEt(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setRalewayBold(context,this);
    }
}
