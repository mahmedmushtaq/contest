package com.contest.competition.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by M Ahmed Mushtaq on 2/26/2018.
 */

public class SetFonts {
    public static  void setHoltwoodOneSC(Context context, View view){
       Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/HoltwoodOneSC.ttf");
            if(view instanceof TextView) {
                TextView views = (TextView) view;
                views.setTypeface(typeface);
            }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setHindMaduraiLight(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/HindMadurai-Light.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setImFeEnrm(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/IMFeENrm28P.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setMateScRegular(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/MateSC-Regular.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setRalewayBold(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Bold.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setMontserratBold(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Bold.otf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setMontserratLight(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Light.otf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setTekoBold(Context context ,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Teko-Bold.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setFjallaOneRegular(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/FjallaOne-Regular.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setQuickSandRegular(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Regular.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setQuickSandBold(Context context,View view ){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setQuickSandMedium(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Medium.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setQuickSandLight(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Light.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void setRobotoBold(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Bold.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void luckiestGuyRegular(Context context,View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/LuckiestGuy-Regular.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }

    public static void eaterRegularTv(Context context,View view) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Eater-Regular.ttf");
        if (view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if (view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }

    }

    public static void setQuestrialRegularTv(Context context, View view){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Questrial-Regular.ttf");
        if(view instanceof TextView) {
            TextView views = (TextView) view;
            views.setTypeface(typeface);
        }
        if(view instanceof EditText) {
            TextView views = (EditText) view;
            views.setTypeface(typeface);
        }
    }




}
