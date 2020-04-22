package com.contest.competition.classes.models;

import com.contest.competition.classes.Converter;

/**
 * Created by M Ahmed Mushtaq on 8/30/2018.
 */

public class ContestData extends PostData {

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

    public void setContestComplete(String contestComplete) {
        mContestComplete = contestComplete;
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

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public void setContestLink(String contestLink){
         mPostLink = contestLink;
    }

    public void setLeftPicNumUserVotes(double leftPicNumUserVotes) {
        mLeftPicNumUserVotes = leftPicNumUserVotes;
    }

    public void setRightPicNumUserVotes(double rightPicNumUserVotes) {
        mRightPicNumUserVotes = rightPicNumUserVotes;
    }

    public void setTotalVotes(double totalVotes) {
        mTotalVotes = totalVotes;
    }

    public void setTotalComments(double totalComments) {
        mTotalComments = totalComments;
    }

    public void setTotalSeen(double totalSeen) {
        mTotalSeen = totalSeen;
    }

    public void setPostId(int postId){
        this.postId = postId;
    }

    public void setAlreadyVote(int alreadyVote){
        checkAlreadyVote = alreadyVote;
    }

    public int getPostId(){
        return postId;
    }

    public String getTotalVotesIntoK(){
        return Converter.convertIntegerIntoK(mTotalVotes);
    }

    public String getTotalCommentsIntoK(){
        return Converter.convertIntegerIntoK(mTotalComments);
    }

    public String getTotalSeenIntoK(){
        return Converter.convertIntegerIntoK(mTotalSeen);
    }

    public String getLeftPicNumUserVotesIntoK(){
        return Converter.convertIntegerIntoK(mLeftPicNumUserVotes);
    }

    public String getRightPicNumUserVotesIntoK(){
        return Converter.convertIntegerIntoK(mRightPicNumUserVotes);
    }

    public int getAlreadyVote(){
        return checkAlreadyVote;
    }


    public String getLeftSideName() {
        if(mLeftSideName.length() <= 15)
        return mLeftSideName;
        else return mLeftSideName.substring(0,14)+"..";
    }

    public String getRightSideName() {
        if(mRightSideName.length() <= 15)
        return mRightSideName;
        else return mRightSideName.substring(0,14)+"..";
    }

    public String getLeftSideUsername() {
        return mLeftSideUsername;
    }

    public String getRightSideUsername() {
        return mRightSideUsername;
    }

    public String getLeftSideImage() {
        return mLeftSideImage;
    }

    public String getRightSideImage() {
        return mRightSideImage;
    }

    public String getTillTime() {
        return mTillTime;
    }

    public String getContestComplete() {
        return mContestComplete;
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

    public int getLeftPicNumUserVotes() {
        return (int)mLeftPicNumUserVotes;
    }

    public int getRightPicNumUserVotes() {
        return (int)mRightPicNumUserVotes;
    }

    public int getTotalVotes() {
        return (int)mTotalVotes;
    }

    public int getTotalComments() {
        return (int)mTotalComments;
    }

    public int getTotalSeen() {
        return  (int)mTotalSeen;
    }

    public String getContestLink(){
        return mPostLink;
    }
}
