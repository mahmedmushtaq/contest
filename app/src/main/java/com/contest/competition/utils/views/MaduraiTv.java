package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by M Ahmed Mushtaq on 5/22/2018.
 */

public class MaduraiTv extends TextView{
    public MaduraiTv(Context context) {
        super(context);
        SetFonts.setHindMaduraiLight(context,this);
    }

    public MaduraiTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setHindMaduraiLight(context,this);
    }

    public MaduraiTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setHindMaduraiLight(context,this);
    }

    public MaduraiTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setHindMaduraiLight(context,this);
    }
}
