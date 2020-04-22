package com.contest.competition.classes.interfaces;

import android.widget.Button;
import android.widget.ProgressBar;

import com.contest.competition.classes.models.ProfileHeaderData;
import com.mikhaellopez.circularimageview.CircularImageView;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by M Ahmed Mushtaq on 6/23/2018.
 */

public interface ProfileRvListener {
    void updateProfile(CircularImageView imageView, ProgressBar progressBar,ProfileHeaderData data);
    void viewProfile(CircularImageView imageView,ProfileHeaderData data);
    void viewFollowingsUsers();
    void viewFollowers();
   // void startFollowing(FancyButton followBtn,FancyButton acceptBtn,FancyButton waitBtn);
    void startFollowing(Button followBtn);
   // void cancelFollowing(FancyButton cancelFollowBtn,FancyButton followBtn,FancyButton waitBtn);
    void cancelFollowing(Button cancelFollowBtn);
  //  void cancelFollowRequest(FancyButton cancelFollowBtn,FancyButton followBtn,FancyButton waitBtn);
    void cancelFollowRequest(Button cancelFollowBtn);
    void onClickCredits();
    void onClickBoostFollowers(ProfileHeaderData data);
    void onClickBackIcon();
    void onClickEditIcon();
    void onClickSendMessage(ProfileHeaderData data);



}
