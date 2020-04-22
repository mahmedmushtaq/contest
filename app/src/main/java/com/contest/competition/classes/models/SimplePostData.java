package com.contest.competition.classes.models;

import com.contest.competition.classes.Converter;

/**
 * Created by M Ahmed Mushtaq on 9/1/2018.
 */

public class SimplePostData extends PostData {
    private String mPostedBy,mPostedName,mPostedProfile,mPostedText,mPostedImage,mPostedDateTime;
    private int isPostBoosted;

    public void setIsPostBoosted(int isPostBoosted) {
        this.isPostBoosted = isPostBoosted;
    }

    public int getIsPostBoosted() {
        return isPostBoosted;
    }

    public void setPostedBy(String username){
        mPostedBy = username;
    }

    public void setPostedProfile(String profile){
        mPostedProfile = profile;
    }

    public void setPostId(int id){
        postId = id;
    }

    public void setPostedName(String name){
        mPostedName = name;
    }

    public void setPostedDateTime(String dateTime){
        mPostedDateTime = dateTime;
    }

    public void setPostedText(String  text){
        mPostedText = text;
    }

    public void setPostedImage(String postedImage){
        mPostedImage = postedImage;
    }

    public void setTotalVotes(double totalVotes){
        mTotalVotes = totalVotes;
    }

    public void setTotalComments(double totalComments){
        mTotalComments = totalComments;
    }

    public void setTotalSeen(double totalSeen){
        mTotalSeen = totalSeen;
    }

    public void setAlreadyVote(int alreadyVote){
        checkAlreadyVote = alreadyVote;
    }


    public String getPostedBy(){
        return  mPostedBy;
    }

    public int getTotalVotes(){return (int)mTotalVotes;}

    public int getAlreadyVote(){return checkAlreadyVote;}

    public int getPostedId(){return  postId;}

    public String getPostedProfile(){
        return mPostedProfile;
    }

    public String getPostedName(){
        return  mPostedName;
    }

    public String getPostedDateTime(){
        return mPostedDateTime;
    }
    public String getPostedText(){
        return mPostedText;
    }

    public String getPostedImage(){
        return mPostedImage;
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

}
