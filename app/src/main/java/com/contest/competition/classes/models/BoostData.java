package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 9/12/2018.
 */

public class BoostData {
    private String noOfUsersReached,noOfCreditsUsed;
    private int check,noOfCredits;

    public BoostData(String noOfUsersReached, String noOfCreditsUsed, int check,int noOfCredits) {
        this.noOfUsersReached = noOfUsersReached;
        this.noOfCreditsUsed = noOfCreditsUsed;
        this.check = check;
        this.noOfCredits = noOfCredits;
    }

    public int getNoOfCredits() {
        return noOfCredits;
    }

    public String getNoOfCreditsUsed() {
        return noOfCreditsUsed;
    }

    public String getNoOfUsersReached() {
        return noOfUsersReached;
    }

    public int getCheck() {
        return check;
    }
}
