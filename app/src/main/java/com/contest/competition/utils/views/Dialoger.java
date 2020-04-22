package com.contest.competition.utils.views;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.contest.competition.R;
import com.tapadoo.alerter.Alerter;

/**
 * Created by M Ahmed Mushtaq on 3/24/2018.
 */

public class Dialoger  {
    private static MaterialDialog dialog;
    private static dialogListener mDialogListener;


    public static interface dialogListener{
        void onAccept(MaterialDialog dialog);
    }

    public static void setDialogListener(dialogListener listener){
        mDialogListener = listener;
    }

    public static void setAlerter(Context context,String heading, String message){
        Alerter.create((Activity)context)
                .setTitle(heading)
                .setText(message)
                .setBackgroundColorRes(R.color.app_theme_second_color) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }


    public static void setDialog(Activity activity,String heading,String message){
        dialog = new MaterialDialog.Builder(activity)
                .title(heading)
                .content(message)
                .titleColorRes(R.color.black)
                .contentColorRes(R.color.peter)
                .backgroundColorRes(R.color.white)
                .progress(true,0)
                .show();
    }

    public static void setConfirmDialog(Activity activity, String heading, String message){
        new MaterialDialog.Builder(activity)
               .onPositive(new MaterialDialog.SingleButtonCallback() {
                   @Override
                   public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       if(mDialogListener != null)
                       mDialogListener.onAccept(dialog);
                   }
               })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .title(heading)
                .titleColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .positiveColorRes(R.color.silverHighDark)
                 .negativeColorRes(R.color.silverDark)
                .contentColorRes(R.color.wetAsphalt)
                .content(message)
                .positiveText("Yes")
                .negativeText("No")
                .show();

    }

    public static void setOnlyPositiveBtnDialog(Activity activity,String heading,String message){
        new MaterialDialog.Builder(activity)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .title(heading)
                .titleColorRes(R.color.black)
                .backgroundColorRes(R.color.white)
                .positiveColorRes(R.color.silverHighDark)
                .contentColorRes(R.color.wetAsphalt)
                .content(message)
                .positiveText("Yes")
                .show();
    }

    public static void dismissDialog(){
        if(dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public static void hideAlerter(){
        if(Alerter.isShowing())
            Alerter.hide();
    }
}
