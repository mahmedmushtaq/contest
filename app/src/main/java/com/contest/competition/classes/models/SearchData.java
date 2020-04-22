package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public class SearchData  {

    private String mName,mUsername,mProfilePic,mUserPrivacy,mSearchKeyWord;
    private int mId;

    public SearchData(String name, String username, String profilePic, String userPrivacy,String searchKeyWord, int id) {
        mName = name;
        mUsername = username;
        mProfilePic = profilePic;
        mSearchKeyWord = searchKeyWord;
        mId = id;
        mUserPrivacy = userPrivacy;

    }


    public String getName() {return mName;}

    public String getUsername() {return mUsername;}

    public String getPreviousSearchKeyWord(){return mSearchKeyWord;}

    public String getUserPrivacy(){return  mUserPrivacy;}

    public String getProfilePic() {return mProfilePic;}



    public int getId() {return mId;}





}