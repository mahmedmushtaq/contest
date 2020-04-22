package com.contest.competition.storage.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.contest.competition.classes.KeyStore;

import java.security.Key;

/**
 * Created by M Ahmed Mushtaq on 6/15/2018.
 */

public class LoginSharedPrefer {
    private SharedPreferences mSharedPreferences;

    public LoginSharedPrefer(Context context){
        this.mSharedPreferences = context.getSharedPreferences(KeyStore.SHARED_PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
    }

    public void setOpenfirePassword(String password){
        mSharedPreferences.edit().putString(KeyStore.OPENFIRE_PASSWORD,password).apply();
    }

    public void setUsername(String username){
        mSharedPreferences.edit().putString(KeyStore.SHARED_SET_USERNAME,username).apply();
    }

    public void setSubscriptionTopic(String topic){
        mSharedPreferences.edit().putString(KeyStore.SHARED_SET_SUBSCRIPTION,topic).apply();
    }

    public void setName(String name){
        mSharedPreferences.edit().putString(KeyStore.SHARED_SET_NAME,name).apply();
    }


    public void setEmail(String email){
        mSharedPreferences.edit().putString(KeyStore.SHARED_SET_EMAIL,email).apply();
    }

    public void setProfilePath(String profilePath){
        mSharedPreferences.edit().putString(KeyStore.SHARED_SET_PROFILE_PATH,profilePath).apply();
    }

    public void setId(int id){
        mSharedPreferences.edit().putInt(KeyStore.SHARED_SET_ID,id).apply();
    }

    public void setRememberMe(boolean rememberMe){
        mSharedPreferences.edit().putBoolean(KeyStore.SHARED_SET_REMEMBER_ME,rememberMe).apply();
    }

    public void setPassword(String password){
         mSharedPreferences.edit().putString(KeyStore.SHARED_SET_PASSWORD,password).apply();
    }

    public String getUsername(){
        return mSharedPreferences.getString(KeyStore.SHARED_SET_USERNAME,null);
    }

    public String getOpenFirePassword(){
        return mSharedPreferences.getString(KeyStore.OPENFIRE_PASSWORD,null);
    }

    public String  getSharedEmail(){
        return mSharedPreferences.getString(KeyStore.SHARED_SET_EMAIL,null);
    }

    public String getSubscriptionTopic(){
        return mSharedPreferences.getString(KeyStore.SHARED_SET_SUBSCRIPTION,null);
    }

    public boolean getRememberMe(){
        return  mSharedPreferences.getBoolean(KeyStore.SHARED_SET_REMEMBER_ME,false);
    }



    public String getPassword(){
        return mSharedPreferences.getString(KeyStore.SHARED_SET_PASSWORD,null);
    }

    public String getProfilePath(){
        return mSharedPreferences.getString(KeyStore.SHARED_SET_PROFILE_PATH,null);
    }

    public String getName(){
        return mSharedPreferences.getString(KeyStore.SHARED_SET_NAME,null);
    }

    public int getId(){
        return mSharedPreferences.getInt(KeyStore.SHARED_SET_ID,0);
    }



    public void removeSharedPreference(){

        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_USERNAME).apply();
        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_NAME).apply();
        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_ID).apply();
        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_PROFILE_PATH).apply();

    }

    public void removeEmailAndPassword(){
        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_PASSWORD).apply();
        mSharedPreferences.edit().remove(KeyStore.OPENFIRE_PASSWORD).apply();
        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_EMAIL).apply();
        mSharedPreferences.edit().remove(KeyStore.SHARED_SET_REMEMBER_ME).apply();
    }

}
