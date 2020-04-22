package com.contest.competition.storage.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.contest.competition.classes.KeyStore;

public class IntroSharedPreference {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    public IntroSharedPreference(Context context){
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(KeyStore.SHARED_PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
    }

    public void setSelfieSharedPreference(boolean selfieSharedPreference){
        mSharedPreferences.edit().putBoolean(KeyStore.SELFIE_CONTEST_INTRO,selfieSharedPreference).apply();
    }

    public boolean getSelfieSharedPreference(){
        return mSharedPreferences.getBoolean(KeyStore.SELFIE_CONTEST_INTRO,false);
    }
}
