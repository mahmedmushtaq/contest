package com.contest.competition.storage.sharedpreferences.sharedpreferencesetter;

import android.content.Context;

import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;

public class LoginSharedPreferenceSetter {
    public static void setLoginSharedPreference(Context context,String username,String name,int id,String profilePath,String  openFirePassword,String email,String  password,String subscriptionTopic,boolean mRememberMe){
        LoginSharedPrefer login = new LoginSharedPrefer(context);
        login.setUsername(username);

        login.setName(name);
        login.setId(id);
        login.setProfilePath(profilePath);
        login.setOpenfirePassword(openFirePassword);
        login.setEmail(email);
        login.setPassword(password);
        login.setRememberMe(mRememberMe);
        login.setSubscriptionTopic(subscriptionTopic);
    }
}
