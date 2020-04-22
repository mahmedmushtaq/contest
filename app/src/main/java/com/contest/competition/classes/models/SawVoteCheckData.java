package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 8/24/2018.
 */

public class SawVoteCheckData {
    private String mName,mUsername;
    private int id;
    public SawVoteCheckData(String name, String username,int id){
        mName = name;
        mUsername = username;
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public int getId() {
        return id;
    }
}
