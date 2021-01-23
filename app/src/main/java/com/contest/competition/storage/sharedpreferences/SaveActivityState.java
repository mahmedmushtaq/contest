package com.contest.competition.storage.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.contest.competition.classes.KeyStore;

public class SaveActivityState {
    private SharedPreferences mSharedPreferences;


    public SaveActivityState(Context context){
        mSharedPreferences = context.getSharedPreferences(KeyStore.SHARED_PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
    }


}
