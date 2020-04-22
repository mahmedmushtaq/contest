package com.contest.competition.classes.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.contest.competition.classes.Converter;

import java.text.DecimalFormat;

/**
 * Created by M Ahmed Mushtaq on 6/20/2018.
 */

public class ProfileHeaderData implements Parcelable {
    private String mProfilePic,mUsername,mName,mFollowing,mSubscriptionTopic;
    private double  mNoOfFollowingUsers, mNoOFFollowerUsers, mNoOfPosts,mNoOfCredits,mNoOfContestWin,mNoContestLose;
    private DecimalFormat format = new DecimalFormat("0.#");
   // this is used when user send follow request and when he wants to cancel follow request then due to delete its data properly from table we will notificationId

    public ProfileHeaderData(String profilePic, String username, String name, String startFollowing, double noOfFollowingUsers, double noOFFollowerUsers,double noOfContests,double noOfCredits,double noOfContestWin,double noOfContestLose,String subscriptionTopic) {

        mProfilePic = profilePic;
        mUsername = username;
        mName = name;
        mFollowing = startFollowing;
        mNoOfPosts = noOfContests;
        mNoOfCredits = noOfCredits;
        mNoOfFollowingUsers = noOfFollowingUsers;
        mNoOFFollowerUsers = noOFFollowerUsers;
        mNoOfContestWin = noOfContestWin;
        mNoContestLose = noOfContestLose;
        mSubscriptionTopic = subscriptionTopic;
        Log.e("profileHeader", "ProfileHeaderData: set Subscription topic = "+subscriptionTopic );

    }


    protected ProfileHeaderData(Parcel in) {
        mProfilePic = in.readString();
        mUsername = in.readString();
        mName = in.readString();
        mFollowing = in.readString();
        mNoOfFollowingUsers = in.readDouble();
        mNoOFFollowerUsers = in.readDouble();
        mNoOfPosts = in.readDouble();
        mNoOfCredits = in.readDouble();
        mNoOfContestWin = in.readDouble();
        mNoContestLose = in.readDouble();
        mSubscriptionTopic = in.readString();
    }

    public static final Creator<ProfileHeaderData> CREATOR = new Creator<ProfileHeaderData>() {
        @Override
        public ProfileHeaderData createFromParcel(Parcel in) {
            return new ProfileHeaderData(in);
        }

        @Override
        public ProfileHeaderData[] newArray(int size) {
            return new ProfileHeaderData[size];
        }
    };

    public String getProfilePic() {
        return mProfilePic;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getName() {
        return mName;
    }

    public String getFollowing() {
        return mFollowing;
    }


    public String getSubscriptionTopic() {
        return mSubscriptionTopic;
    }

    public String getNoOfFollowingUsers(){

        return Converter.convertIntegerIntoK(mNoOfFollowingUsers);
    }

    public String getNoOfContestWin(){
        return Converter.convertIntegerIntoK(mNoOfContestWin);
    }

    public String getNoOfContestLose(){
        return Converter.convertIntegerIntoK(mNoContestLose);
    }


    public String getNoOfFollowerUsers(){
       return Converter.convertIntegerIntoK(mNoOFFollowerUsers);
    }

    public void updateNoOfPosts(double noOfPosts){
        mNoOfPosts = noOfPosts;
    }

   public int getNoOfPostsInInt(){
        return (int)mNoOfPosts;
   }


    public String getNoOfPosts(){
        if(mNoOfPosts > -1)
        return Converter.convertIntegerIntoK(mNoOfPosts);
        //to prevent -ve value hehe :-)
        else{
            return Converter.convertIntegerIntoK(0);

        }
    }


    public String getNoOfCredits(){
      return Converter.convertIntegerIntoK(mNoOfCredits);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProfilePic);
        dest.writeString(mUsername);
        dest.writeString(mName);
        dest.writeString(mSubscriptionTopic);
        dest.writeString(mFollowing);
        dest.writeDouble(mNoOfFollowingUsers);
        dest.writeDouble(mNoOFFollowerUsers);
        dest.writeDouble(mNoOfPosts);
        dest.writeDouble(mNoOfCredits);
        dest.writeDouble(mNoOfContestWin);
        dest.writeDouble(mNoContestLose);
    }
}
