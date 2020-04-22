package com.contest.competition.classes.interfaces;

import android.widget.ImageView;

import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.skydoves.powermenu.PowerMenuItem;

/**
 * Created by M Ahmed Mushtaq on 8/5/2018.
 */

public interface HomeRvListener {

    void onClickCreatorProfile(PostData data);
    void onClickLeftSideName(PostData data);
    void onClickRightSideName(PostData data);
    void onClickCommentBtn(PostData data);
    void onClickBoostBtn(PostData data);
    void onClickDeleteBtn(PostData data,int position);
    void onReachingBottom(int position);
    void onClickVotesCommentAndSeenLl(PostData data);
    void onClickDoubleTapLeftPicVote(PostData data, int position, ImageView voteBtn);
    void onClickDoubleTapRightPicVote(PostData data, int position, ImageView voteBtn);
    void onClickVoteBtn(PostData data,int position,ImageView voteBtn);



}
