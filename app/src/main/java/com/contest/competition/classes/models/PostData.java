package com.contest.competition.classes.models;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * Created by M Ahmed Mushtaq on 8/29/2018.
 */

public class PostData {
    protected String
            mLeftSideName
            ,mRightSideName
            ,mLeftSideUsername
            ,mRightSideUsername
            ,mLeftSideImage
            ,mRightSideImage
            , mTillTime
            ,mContestComplete
            ,mCreator
            ,mCreatorName
            ,mCreatorProfile
            ,mDateTime
            , mPostLink;


    protected int contestId,postId;
    protected double mLeftPicNumUserVotes
            , mRightPicNumUserVotes
            ,mTotalVotes
            ,mTotalComments
            ,mTotalSeen;



    protected int checkAlreadyVote;

    protected Format mFormat = new DecimalFormat("0.#");


    public int getPostId(){
        return postId;
    }

    public void updateTotalVotes(double totalVotes){
        mTotalVotes = totalVotes;
    }

    public void updateTotalComments(double totalComments){
        mTotalComments = totalComments;
    }

    public void updateTotalSeen(double totalSeen){
        mTotalSeen = totalSeen;
    }

    public void updateLeftPicVote(double leftPicNumUserVotes){
        mLeftPicNumUserVotes = leftPicNumUserVotes;
    }

    public void updateRightPicVote(double rightPicNumUserVotes){
        mRightPicNumUserVotes = rightPicNumUserVotes;
    }

    public void updateAlreadyVote(int checkAlreadyVote){
        this.checkAlreadyVote = checkAlreadyVote;
    }



}
