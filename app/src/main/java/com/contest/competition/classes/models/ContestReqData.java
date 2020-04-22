package com.contest.competition.classes.models;

/**
 * Created by M Ahmed Mushtaq on 8/29/2018.
 */

public class ContestReqData  extends PostData {

    public void setLeftSideName(String leftSideName) {
        mLeftSideName = leftSideName;
    }

    public void setRightSideName(String rightSideName) {
        mRightSideName = rightSideName;
    }

    public void setLeftSideUsername(String leftSideUsername) {
        mLeftSideUsername = leftSideUsername;
    }

    public void setRightSideUsername(String rightSideUsername) {
        mRightSideUsername = rightSideUsername;
    }

    public void setLeftSideImage(String leftSideImage) {
        mLeftSideImage = leftSideImage;
    }

    public void setRightSideImage(String rightSideImage) {
        mRightSideImage = rightSideImage;
    }

    public void setTillTime(String tillTime) {
        mTillTime = tillTime;
    }



    public void setCreator(String creator) {
        mCreator = creator;
    }

    public void setCreatorName(String creatorName) {
        mCreatorName = creatorName;
    }

    public void setCreatorProfile(String creatorProfile) {
        mCreatorProfile = creatorProfile;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }













    public String getLeftSidePic(){
        return mLeftSideImage;
    }

    public String getRightSidePic(){
        return mRightSideImage;
    }

    public String getLeftSideName() {
        return mLeftSideName;
    }

    public String getRightSideName() {
        return mRightSideName;
    }



    public String getTillTime() {
        return mTillTime;
    }



    public String getCreator() {
        return mCreator;
    }

    public String getCreatorName() {
        return mCreatorName;
    }

    public String getCreatorProfile() {
        return mCreatorProfile;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public int getContestId() {
        return contestId;
    }





//    public double getTotalSeen() {
//        return mTotalSeen;
//    }




}
