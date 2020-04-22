package com.contest.competition.storage.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.contest.competition.classes.KeyStore;

/**
 * Created by M Ahmed Mushtaq on 6/18/2018.
 */

public class NoOfNotifications {
    private SharedPreferences mSharedPreferences;


    public NoOfNotifications(Context context){
        mSharedPreferences = context.getSharedPreferences(KeyStore.SHARED_PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
    }

    public void setNoOfNotifications(int no){
        mSharedPreferences.edit().putInt(KeyStore.NO_OF_NOTIFICATION,no).apply();


    }

    public int getNoOfNotifications(){
        return mSharedPreferences.getInt(KeyStore.NO_OF_NOTIFICATION,0);
    }

    public void removeNotifications(){
        mSharedPreferences.edit().remove(KeyStore.NO_OF_NOTIFICATION).apply();

    }
}
