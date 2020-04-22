package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by M Ahmed Mushtaq on 3/18/2018.
 */

public class QuickSandRegularTv extends TextView {
    public QuickSandRegularTv(Context context) {
        super(context);
        SetFonts.setQuickSandRegular(context,this);
    }

    public QuickSandRegularTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setQuickSandRegular(context,this);
    }

    public QuickSandRegularTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setQuickSandRegular(context,this);
    }

    public QuickSandRegularTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setQuickSandRegular(context,this);
    }
}
