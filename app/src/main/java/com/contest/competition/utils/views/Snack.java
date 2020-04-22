package com.contest.competition.utils.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;

import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.github.mrengineer13.snackbar.SnackBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by M Ahmed Mushtaq on 3/2/2018.
 */

public class Snack {

    private static   RefreshActivity mRefreshActivity;

    public static interface  RefreshActivity{
        void onRefresh();
    }

    public static void setOnRefresh(RefreshActivity refresh){
        mRefreshActivity = refresh;
    }


    public static void setSnackBar(View view,String message){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show();
    }

    public static void topSnackBar(View view,String text){

        TSnackbar snackbar =  TSnackbar.make(view,text,TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor("#30336b"));

        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#95afc0"));
        snackbar.show();
    }

    public static void refreshLayoutSnack(final Context context){
        new SnackBar.Builder((Activity)context)
                .withOnClickListener(new SnackBar.OnMessageClickListener() {
                    @Override
                    public void onMessageClick(Parcelable token) {

                        mRefreshActivity.onRefresh();
                    }
                })
                .withMessage("Do You Want To Refresh A Layout") // OR
               .withActionMessage("Refresh") // OR
               .show();
    }
}
