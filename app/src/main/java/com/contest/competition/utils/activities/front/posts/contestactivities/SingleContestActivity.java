package com.contest.competition.utils.activities.front.posts.contestactivities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.requests.data.contestreq.RetrieveSpecificContest;
import com.contest.competition.requests.data.contestreq.Vote;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.activities.front.posts.CheckWhoVotesCommentsAndSeenActivity;
import com.contest.competition.utils.activities.front.posts.CommentActivity;
import com.contest.competition.utils.views.Menu;
import com.contest.competition.utils.views.PostView;
import com.contest.competition.utils.views.SetFonts;
import com.contest.competition.utils.views.Toaster;

import com.contest.competition.R;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;



public class SingleContestActivity extends BaseToolbarActivity {
    private NotificationData mData;
    private String contestId,retrieveDataCheck;
    private int postId;
    private LoginSharedPrefer mPrefer;
    private TextView mTillTime,mLeftSideVotes, mRightSideVotes, mLeftSideName, mRightSideName,homeVoteTv,creatorName,contestDateTime,totalVotes,totalComments,totalSeen;
    private ImageView leftSideContestPic,rightSideContestPic, leftUserWinIv, rightUserWinIv,equalVotes,creatorProfile;
    private ProgressBar leftSidePb,rightSidePb;
    private ImageView voteBtn,commentBtn,contestEditing;
    private double leftPicNumUserVotes,rightPicNumUserVotes;
    private SwipeRefreshLayout contestSwipe;
    private LinearLayout contest_postTotalLl;
    private Button contestFeatures;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mData = getIntent().getParcelableExtra(KeyStore.PASS_OBJECT);
        String[] linkParts = mData.getLink().split("=");
        contestId = linkParts[1];


        mPrefer = new LoginSharedPrefer(this);

        NotificationsReq.notificationOpened(mData.getId(),mPrefer.getUsername());






        creatorProfile = findViewById(R.id.contest_creatorProfile);
        creatorName = findViewById(R.id.contest_creatorName);
        contestDateTime = findViewById(R.id.contest_dataTime);
        mTillTime = findViewById(R.id.contest_tillTime);
        mLeftSideName = findViewById(R.id.contest_leftSideName);
        mRightSideName = findViewById(R.id.contest_rightSideName);
        mLeftSideVotes = findViewById(R.id.contest_leftSideVote);
        mRightSideVotes = findViewById(R.id.contest_rightSideVote);
        leftSidePb = findViewById(R.id.contest_leftSidePb);
        rightSidePb = findViewById(R.id.contest_rightSidePb);

        leftSideContestPic = findViewById(R.id.contest_leftSidePic);
        rightSideContestPic = findViewById(R.id.contest_rightSidePic);
        voteBtn = findViewById(R.id.contest_vote);
        commentBtn = findViewById(R.id.contest_comment);
        contestFeatures = findViewById(R.id.contest_features_btn);
        totalVotes = findViewById(R.id.contest_totalNumVotes);
        totalComments = findViewById(R.id.contest_totalComments);
        totalSeen = findViewById(R.id.contest_totalSeen);
        contest_postTotalLl = findViewById(R.id.contest_postTotalLl);
        contestSwipe = findViewById(R.id.contestActivity_swipeRefreshLayout);


        leftUserWinIv = findViewById(R.id.contest_leftSideUserWin);
        rightUserWinIv = findViewById(R.id.contest_rightSideUserWin);
        equalVotes = findViewById(R.id.contest_tie);


        contestSwipe.setRefreshing(false);





         voteBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(retrieveDataCheck != null){
                     afterRetrieveDataSetVoteClick();
                 }else{
                     Toaster.setToaster(getBaseContext(),"Contest is loading please wait");
                 }
             }
         });

         contestSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 retrieveData();
             }
         });

         commentBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(retrieveDataCheck != null){

                     setComment();
                 }else{
                     Toaster.setToaster(getBaseContext(),"Contest is loading please wait");
                 }

             }
         });

         contest_postTotalLl.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getBaseContext(), CheckWhoVotesCommentsAndSeenActivity.class);
                 intent.putExtra(KeyStore.PASS_DATA,postId+"");
                 startActivity(intent);

             }
         });






    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    private void setComment() {
        Intent intent = new Intent(getBaseContext(),CommentActivity.class);
        intent.putExtra(KeyStore.PASS_DATA,postId+"");
        intent.putExtra(KeyStore.COMMENT_TYPE,"contest");
        startActivity(intent);
        Animatoo.animateSlideUp(SingleContestActivity.this);

    }

    private void afterRetrieveDataSetVoteClick(){
        final List<PowerMenuItem> voteUerList = new ArrayList<>();
        PowerMenuItem leftSideNameItem = new PowerMenuItem(mLeftSideName.getText().toString(),false);
        PowerMenuItem RightSideNameItem = new PowerMenuItem(mRightSideName.getText().toString(),false);
        voteUerList.add(leftSideNameItem);
        voteUerList.add(RightSideNameItem);
        setVote(voteUerList,voteBtn);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Menu.hideMenu();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Menu.hideMenu();
    }

    private void setVote(List<PowerMenuItem> itemsList, View view) {
        Menu.createMenu(this,itemsList,view,Menu.SHOWN_AS_ANCHOR_VIEW_CENTER);
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                if(position == 0){
                    leftPicUserVote();
                     Menu.hideMenu();
                }else{
                    rightPicUserVote();
                    Menu.hideMenu();

                }
            }
        });

    }

    private void rightPicUserVote() {
        Vote.vote(SingleContestActivity.this,mPrefer.getUsername(),postId+"","no","yes","contest");

        Vote.setVoteListener(new Vote.voteListener() {
            @Override
            public void onSuccess(String result, String reason) {
                if(Validator.validateWebResult(result)){
                    rightPicNumUserVotes++;
                    mRightSideVotes.setText(rightPicNumUserVotes+" votes");
                    setVoteColourFilter();

                }else{
                    Toaster.setToaster(getBaseContext(),reason);
                }
            }
        });

    }

    private void setVoteColourFilter(){
        int color =  getResources().getColor(R.color.peter); //The color u want
        voteBtn.setColorFilter(color);
    }

    private void leftPicUserVote() {
        Vote.vote(SingleContestActivity.this,mPrefer.getUsername(),postId+"","yes","no","contest");

        Vote.setVoteListener(new Vote.voteListener() {
            @Override
            public void onSuccess(String result, String reason) {
                if(Validator.validateWebResult(result)){
                    leftPicNumUserVotes++;
                    mLeftSideVotes.setText(leftPicNumUserVotes+ "votes");
                    setVoteColourFilter();
                }else{
                    Toaster.setToaster(getBaseContext(),reason);
                }
            }
        });




    }




    private void retrieveData(){
        RetrieveSpecificContest.loadSpecificContest(SingleContestActivity.this,contestId+"",mPrefer.getUsername());
        RetrieveSpecificContest.setDataListener(new RetrieveSpecificContest.RetrieveSpecificContestListener() {
            @Override
            public void onRetrieveData(PostData data) {
                contestSwipe.setRefreshing(false);
                 ContestData contestData = (ContestData) data;
                 setDataAfterLoad(contestData);

            }

            @Override
            public void onFail() {
                contestSwipe.setRefreshing(false);
            }
        });
    }

    private void setDataAfterLoad(ContestData contestData){
        leftPicNumUserVotes = contestData.getLeftPicNumUserVotes();
        rightPicNumUserVotes = contestData.getRightPicNumUserVotes();
        postId = contestData.getPostId();
        Log.e("post", "setDataAfterLoad: post id are = "+postId );
        retrieveDataCheck = "yes";
        mLeftSideVotes.setText(contestData.getLeftPicNumUserVotes()+" votes");
        mRightSideVotes.setText(contestData.getRightPicNumUserVotes()+" votes");
        mLeftSideName.setText(contestData.getLeftSideName());
        mRightSideName.setText(contestData.getRightSideName());
        mTillTime.setText(contestData.getTillTime());
        creatorName.setText(contestData.getCreatorName());
        contestDateTime.setText(contestData.getDateTime());


        Glide.with(getApplicationContext()).load(Addresses.getWebAddress()+contestData.getCreatorProfile()).into(creatorProfile);

        //totalVotesAndComments.setText(HighLighter.setTotalVotesAndCommentsAndTotalSeen(contestData.getTotalVotesIntoK()+" votes","&",contestData.getTotalCommentsIntoK()+" comments",contestData.getTotalSeenIntoK()+" seen",this));
        //totalVotesAndComments.setText(HighLighter.setTotalVotesAndCommentsAndTotalSeen(contestData.getTotalVotesIntoK()+" votes","&",contestData.getTotalCommentsIntoK()+" comments",contestData.getTotalSeenIntoK()+" seen",this));

        totalVotes.setText(contestData.getTotalVotesIntoK());
        totalComments.setText(contestData.getTotalCommentsIntoK());
        totalSeen.setText(contestData.getTotalSeenIntoK());

        if(contestData.getContestComplete().equals("yes"))
            setAfterContestComplete();
        else{
            equalVotes.setVisibility(View.GONE);
            rightUserWinIv.setVisibility(View.GONE);
            leftUserWinIv.setVisibility(View.GONE);
        }

        Glide.with(getApplicationContext()).load(Addresses.getWebAddress()+contestData.getLeftSideImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                leftSidePb.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                rightSidePb.setVisibility(View.GONE);
                return false;
            }
        }).into(leftSideContestPic);

     Glide.with(getApplicationContext()).load(Addresses.getWebAddress()+contestData.getRightSideImage()).listener(new RequestListener<Drawable>() {
         @Override
         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
             rightSidePb.setVisibility(View.GONE);
             return false;
         }

         @Override
         public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
             return false;
         }
     }).into(rightSideContestPic);



         if(contestData.getAlreadyVote() == PostView.ALREADY_VOTE){
             setVoteColourFilter();
         }

    }



    private void setAfterContestComplete() {
        voteBtn.setEnabled(false);
        voteBtn.setClickable(false);
        if(leftPicNumUserVotes == rightPicNumUserVotes){
            equalVotes.setVisibility(View.VISIBLE);
            mTillTime.setText("tie");
        }else if(leftPicNumUserVotes < rightPicNumUserVotes){
            rightUserWinIv.setVisibility(View.VISIBLE);

            mTillTime.setText(mRightSideName.getText().toString()+" won");



        }else{
            leftUserWinIv.setVisibility(View.VISIBLE);

                mTillTime.setText(mLeftSideName.getText().toString()+" won");
        }




    }


    @Override
    protected int setLayout() {
        return R.layout.activity_contest;
    }

    @Override
    protected String setActivityName() {
        return "Contest";
    }

    @Override
    protected int useWhiteToolbar() {
        return  WHITE_TOOLBAR;
    }
}
