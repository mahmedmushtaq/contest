package com.contest.competition.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.front.OtherUserProfileActivity;
import com.contest.competition.utils.activities.front.ProfileActivity;

public class VisitProfile {
    private static Context mContext;
    static volatile VisitProfile visitProfile;
    private static String mLoginUsername,mVisitUserProfileName;
    public VisitProfile(Context context){
        mContext = context;
    }

    public static VisitProfile with(Context context){
        if(visitProfile == null){
            synchronized (VisitProfile.class){
                if(visitProfile == null)
                    visitProfile = new Builder(context).build();
            }
        }

        return visitProfile;
    }
    public    void visitUserProfile(String passUsername){
        Intent intent;
        if(passUsername.equals(getLoginUsername())){
            intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra(KeyStore.PASS_DATA,getLoginUsername());


        }else{
            intent = new Intent(mContext, OtherUserProfileActivity.class);
            intent.putExtra(KeyStore.PASS_DATA,passUsername);

        }

        mContext.startActivity(intent);
    }

    public void check(){

    }

    private  String getLoginUsername(){
        LoginSharedPrefer prefer = new LoginSharedPrefer(mContext);
        return prefer.getUsername();
    }


    private static class Builder{
        private  Context mContext;

        public Builder(Context context){
            if(context == null)
                throw  new IllegalArgumentException("Context not be null");
            else mContext = context;
        }

        public VisitProfile build(){
          return   new VisitProfile(mContext);
        }

    }
}
