package com.contest.groupe.utils.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.contest.competition.utils.views.SetFonts;

/**
 * Created by M Ahmed Mushtaq on 6/6/2018.
 */

public class EaterRegluarTv extends TextView {
    public EaterRegluarTv(Context context) {
        super(context);
        SetFonts.eaterRegularTv(context,this);
    }

    public EaterRegluarTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        SetFonts.eaterRegularTv(context,this);
    }

    public EaterRegluarTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.eaterRegularTv(context,this);
    }

    public EaterRegluarTv(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.eaterRegularTv(context,this);
    }
}
