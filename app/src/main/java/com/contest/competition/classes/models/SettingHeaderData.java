package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 6/17/2018.
 */

public class SettingHeaderData {
    private String mProfilePath,mName,mUsername;
    private int mId;
    public SettingHeaderData(String profilePath, String name, String username, int id){
        this.mProfilePath = profilePath;
        this.mName = name;
        this.mUsername = username;
        this.mId = id;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public int getId() {
        return mId;
    }
}
