package com.contest.competition.adapters.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.contest.competition.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ContestDataRvHolder extends RecyclerView.ViewHolder {
    public CircularImageView creatorProfile;
    public TextView creatorName,dateTime,contestTillTime,leftSideName,rightSideName,leftSidePicVotes,rightSidePicVotes,totalVotes,totalComments,totalSeen;
    public ProgressBar leftSidePb,rightSidePb;
    public ImageView leftUserWin,rightUserWin,equalVotes,leftSidePic,rightSidePic,doubleTapLeftPic,doubleTapRightPic;//shareContest
    public  ImageView voteBtn,commentBtn;
    public Button contestFeatures;
    public LinearLayout contest_postTotalLl;
    public ContestDataRvHolder(View v) {
        super(v);

        creatorProfile = v.findViewById(R.id.contest_creatorProfile);
        creatorName = v.findViewById(R.id.contest_creatorName);
        dateTime = v.findViewById(R.id.contest_dataTime);
        contestTillTime = v.findViewById(R.id.contest_tillTime);
        leftSideName = v.findViewById(R.id.contest_leftSideName);
        rightSideName = v.findViewById(R.id.contest_rightSideName);
        leftSidePicVotes = v.findViewById(R.id.contest_leftSideVote);
        rightSidePicVotes = v.findViewById(R.id.contest_rightSideVote);
        leftSidePb = v.findViewById(R.id.contest_leftSidePb);
        rightSidePb = v.findViewById(R.id.contest_rightSidePb);

        leftSidePic = v.findViewById(R.id.contest_leftSidePic);
        rightSidePic = v.findViewById(R.id.contest_rightSidePic);
        voteBtn = v.findViewById(R.id.contest_vote);
        commentBtn = v.findViewById(R.id.contest_comment);
        contestFeatures = v.findViewById(R.id.contest_features_btn);
        totalVotes = v.findViewById(R.id.contest_totalNumVotes);
        totalComments = v.findViewById(R.id.contest_totalComments);
        totalSeen = v.findViewById(R.id.contest_totalSeen);
        contest_postTotalLl = v.findViewById(R.id.contest_postTotalLl);


        leftUserWin = v.findViewById(R.id.contest_leftSideUserWin);
        rightUserWin = v.findViewById(R.id.contest_rightSideUserWin);
        equalVotes = v.findViewById(R.id.contest_tie);

        doubleTapLeftPic = v.findViewById(R.id.contest_leftSideDoubleTapStar);
        doubleTapRightPic = v.findViewById(R.id.contest_rightSideDoubleTapStar);
//        shareContest = v.findViewById(R.id.contest_share);



        // voteBtnTv = v.findViewById(R.id.homeBodyVoteTv);
        //commentBtnTv = v.findViewById(R.id.homeBodyCommentTv);

        leftUserWin.setVisibility(View.GONE);
        rightUserWin.setVisibility(View.GONE);
        equalVotes.setVisibility(View.GONE);
        doubleTapLeftPic.setVisibility(View.GONE);
        doubleTapRightPic.setVisibility(View.GONE);
    }
}
