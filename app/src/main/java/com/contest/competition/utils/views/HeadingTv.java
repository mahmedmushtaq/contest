package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by M Ahmed Mushtaq on 3/5/2018.
 */

public class HeadingTv extends TextView {
    public HeadingTv(Context context) {
        super(context);
        SetFonts.setQuickSandBold(getContext(),this);
    }

    public HeadingTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setQuickSandBold(getContext(),this);
    }

    public HeadingTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setQuickSandBold(getContext(),this);
    }

    public HeadingTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setQuickSandBold(getContext(),this);
    }
}
