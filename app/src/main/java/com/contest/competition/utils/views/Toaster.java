package com.contest.competition.utils.views;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by M Ahmed Mushtaq on 3/10/2018.
 */

public class Toaster {
    public static void setToaster(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
