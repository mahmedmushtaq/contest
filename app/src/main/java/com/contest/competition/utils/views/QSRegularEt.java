package com.contest.competition.utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by M Ahmed Mushtaq on 3/24/2018.
 */

public class QSRegularEt extends EditText {
    public QSRegularEt(Context context) {
        super(context);
        SetFonts.setQuickSandRegular(context,this);
    }

    public QSRegularEt(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetFonts.setQuickSandRegular(context,this);
    }

    public QSRegularEt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetFonts.setQuickSandRegular(context,this);
    }

    public QSRegularEt(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        SetFonts.setQuickSandRegular(context,this);
    }
}
