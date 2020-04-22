package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 7/20/2018.
 */

public class FollowData {
    private String mName,mUsername,mProfilePic,mUserPrivacy;
    private int mId,mUserId;

    public FollowData(String name, String username, String profilePic, String userPrivacy, int id,int userId) {
        mName = name;
        mUsername = username;
        mProfilePic = profilePic;
        mUserId = userId;
        mId = id;
        mUserPrivacy = userPrivacy;

    }


    public String getName() {return mName;}

    public String getUsername() {return mUsername;}



    public String getUserPrivacy(){return  mUserPrivacy;}

    public String getProfilePic() {return mProfilePic;}


    public int getUserId(){return mUserId;}

    public int getId() {return mId;}

}
