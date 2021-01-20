package com.contest.competition.classes.models;

import com.contest.competition.requests.forms.CreateUserByGoogleSignIn;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public class ArrayHolder {
    private ArrayList<SearchData> mSearchData = new ArrayList<>();
    private ArrayList<NotificationData> mNotificationData = new ArrayList<>();
    private ArrayList<FollowData> mFollowingData = new ArrayList<>();
    private ArrayList<FollowData> mFollowerData = new ArrayList<>();
    private ArrayList<String> mEditProfileData = new ArrayList<>();
    private ArrayList<CommentData> mCommentData = new ArrayList<>();
    private ArrayList<SawVoteCheckData> mCheckWhoVotedData = new ArrayList<>();
    private ArrayList<SawVoteCheckData> mCheckWhoSawData = new ArrayList<>();
    private ArrayList<ProfileHeaderData> mProfileHeaderData = new ArrayList<>();
    private ArrayList<PostData> mOwnProfileData = new ArrayList<>();
    private ArrayList<PostData> mOtherUserProfileData = new ArrayList<>();
    private ArrayList<BoostData> mPostBoostData = new ArrayList<>();
    private ArrayList<BoostData> mProfileBoost = new ArrayList<>();
    private ArrayList<CreditsData> mCreditsData = new ArrayList<>();
    private ArrayList<PostData> mHomePostdata = new ArrayList<>();
    private ArrayList<PostData> mTrendingData = new ArrayList<>();
    private ArrayList<PostData> mFeedData = new ArrayList<>();


    public void setPostBoostData(ArrayList<BoostData> data){
        mPostBoostData.addAll(data);
    }

    public void setFeedData(ArrayList<PostData> feedData) {
        this.mFeedData = feedData;
    }

    public ArrayList<PostData> getFeedData() {
        return mFeedData;
    }

    public void setHomePostdata(PostData homePostdata){
        mHomePostdata.add(homePostdata);
    }

    public void setTrendingData(ArrayList<PostData> trendingData){
        mTrendingData.addAll(trendingData);
    }

    public void setProfileBoost(BoostData data){
        mProfileBoost.add(data);
    }


    public void setCreditsData(ArrayList<CreditsData> data){
        mCreditsData.addAll(data);
    }



    public void setCommentData(ArrayList<CommentData> commentData){
        mCommentData.addAll(commentData);
    }



    public void setProfileHeaderData(ProfileHeaderData data){
        mProfileHeaderData.add(data);
    }

    public void setCheckWhoVotedData(SawVoteCheckData checkWhoVotedData){
        mCheckWhoVotedData.add(checkWhoVotedData);
    }

    public void setCheckWhoSawData(SawVoteCheckData checkWhoSawData){
        mCheckWhoSawData.add(checkWhoSawData);
    }

    public void setSearchDataArrayList(SearchData data){
        mSearchData.add(data);
    }

    public ArrayList<SearchData> getSearchDataArrayList(){
        return mSearchData;
    }

    public void setFollowingData(FollowData data){
        mFollowingData.add(data);
    }

    public void setFollowerData(FollowData data){
        mFollowerData.add(data);
    }

    public void setEditProfileData(String data){
        mEditProfileData.add(data);
    }


    public void setNotificationData(NotificationData data){
        mNotificationData.add(data);
    }

    public ArrayList<NotificationData> getNotificationData(){
        return mNotificationData;
    }

    public ArrayList<FollowData> getFollowingData(){
        return mFollowingData;
    }

    public ArrayList<FollowData> getFollowerData(){
        return mFollowerData;
    }


    public ArrayList<String> getEditProfileData(){
        return mEditProfileData;
    }


    public ArrayList<CommentData> getCommentData(){
        return mCommentData;
    }

    public ArrayList<SawVoteCheckData> getCheckWhoVotedData(){
        return  mCheckWhoVotedData;
    }

    public ArrayList<SawVoteCheckData> getCheckWhoSawData(){
        return  mCheckWhoSawData;
    }

    public ArrayList<ProfileHeaderData> getProfileHeaderData(){ return mProfileHeaderData; }


    public ArrayList<PostData> getOwnProfileData(){
        //it is used to put and get arraylist
        return mOwnProfileData;
    }

    public ArrayList<PostData> getOtherUserProfileData(){
        //it is used to put and get arraylist
        return mOtherUserProfileData;
    }


    public ArrayList<BoostData> getPostBoostData(){ return mPostBoostData; }
    public ArrayList<BoostData> getProfileBoost(){ return mProfileBoost; }
    public ArrayList<CreditsData> getCreditsData(){ return mCreditsData; }

    public ArrayList<PostData> getHomePostdata(){
        return mHomePostdata;
    }

    public ArrayList<PostData> getTrendingData(){
        return mTrendingData;
    }

    public void setCheckWhoSawData(ArrayList<SawVoteCheckData> checkWhoSawData) {
        mCheckWhoSawData = checkWhoSawData;
    }

    public void setCheckWhoVotedData(ArrayList<SawVoteCheckData> checkWhoVotedData) {
        mCheckWhoVotedData = checkWhoVotedData;
    }

    public void setCommentData(CommentData commentData){
        mCommentData.add(commentData);
    }

    public void setFollowerData(ArrayList<FollowData> followerData) {
        mFollowerData = followerData;
    }

    public void setFollowingData(ArrayList<FollowData> followingData) {
        mFollowingData = followingData;
    }

    public void setNotificationData(ArrayList<NotificationData> notificationData) {
        mNotificationData = notificationData;
    }
}
