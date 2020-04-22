package com.contest.competition;

import android.app.Activity;
import android.app.Application;
import android.app.ApplicationErrorReport;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.FirebaseApp;

/**
 * Created by M Ahmed Mushtaq on 7/18/2018.
 */

public class CompetitionApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Fresco.initialize(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


}
