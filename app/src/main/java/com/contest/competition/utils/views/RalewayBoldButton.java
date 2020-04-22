package com.contest.competition.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by M Ahmed Mushtaq on 3/18/2018.
 */

public class RalewayBoldButton extends Button {

    public RalewayBoldButton(Context context) {
        super(context);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setRalewayBold(context,this);
    }

    public RalewayBoldButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setRalewayBold(context,this);
    }
}
