package com.contest.competition.classes.models;

import com.contest.competition.classes.Converter;

/**
 * Created by M Ahmed Mushtaq on 7/29/2018.
 */

public class CommentData {
    private String commentBody,commenterName,commenterUsername,commenterProfilePic,commentTime;
    private int commentId;
    private double noOfComments;

    public CommentData(String commentBody, String commenterName, String commenterUsername, String commenterProfilePic, String commentTime,double noOfComments, int commentId) {
        this.commentBody = commentBody;
        this.commenterName = commenterName;
        this.commenterUsername = commenterUsername;
        this.commenterProfilePic = commenterProfilePic;
        this.commentTime = commentTime;
        this.commentId = commentId;
        this.noOfComments = noOfComments;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public String getCommenterProfilePic() {
        return commenterProfilePic;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public int getCommentId() {
        return commentId;
    }

    public double getNoOfCommentsInt(){return noOfComments;}

    public String getNoOfComments() {
        return Converter.convertIntegerIntoK(noOfComments);
    }
}
