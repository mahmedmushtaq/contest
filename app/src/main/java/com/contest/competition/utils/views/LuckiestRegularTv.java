package com.contest.competition.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by M Ahmed Mushtaq on 6/6/2018.
 */

public class LuckiestRegularTv extends TextView {
    public LuckiestRegularTv(Context context) {
        super(context);
        SetFonts.luckiestGuyRegular(context,this);
    }

    public LuckiestRegularTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.luckiestGuyRegular(context,this);
    }

    public LuckiestRegularTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.luckiestGuyRegular(context,this);
    }

    public LuckiestRegularTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.luckiestGuyRegular(context,this);
    }
}
