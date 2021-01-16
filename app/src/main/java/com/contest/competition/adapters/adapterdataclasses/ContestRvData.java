package com.contest.competition.adapters.adapterdataclasses;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.contest.competition.R;
import com.contest.competition.adapters.holders.ContestDataRvHolder;
import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.ShareApp;
import com.contest.competition.classes.interfaces.HomeRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.utils.views.Menu;
import com.contest.competition.utils.views.PostView;
import com.contest.competition.utils.views.Toaster;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;

public class ContestRvData {
    private static int profileCheck;
    private static ArrayHolder mArrayHolder;
    public static void setProfileCheck(int profileCheckInner){
        profileCheck = profileCheckInner;
    }

    public  static void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    public static void putContestData(RecyclerView.ViewHolder holder, final int position, final HomeRvListener mHomeRvListener, final Context mContext, String loginUsername, int check) {
        final PostData postData;
        if(check == SinglePostsRvData.PROFILE_RV) {
            if(profileCheck == KeyStore.MY_OWN_PROFILE) {
                if(position-1 <= mArrayHolder.getOwnProfileData().size()) {
                    postData = mArrayHolder.getOwnProfileData().get(position - 1);//position-1 is used to remove header on profile page
                    showRecyclerView(postData,holder,mContext,mHomeRvListener,position,loginUsername);
                }else Toaster.setToaster(mContext,"Please refresh a page");
            }
            else {
                if(position-1 <=mArrayHolder.getOtherUserProfileData().size()) {
                    postData = mArrayHolder.getOtherUserProfileData().get(position - 1);
                    showRecyclerView(postData,holder,mContext,mHomeRvListener,position,loginUsername);
                }else Toaster.setToaster(mContext,"Please refresh a page");
            }


        }
        else if(check == SinglePostsRvData.HOME_RV) {
            if(position <= mArrayHolder.getHomePostdata().size()) {
                postData = mArrayHolder.getHomePostdata().get(position);
                showRecyclerView(postData,holder,mContext,mHomeRvListener,position,loginUsername);
            }else Toaster.setToaster(mContext,"Please refresh a page");
        }
        else{
            if(position <= mArrayHolder.getTrendingData().size()){
                  postData =  mArrayHolder.getTrendingData().get(position);
                  showRecyclerView(postData,holder,mContext,mHomeRvListener,position,loginUsername);
            }else Toaster.setToaster(mContext,"Please refresh a page");
        }







    }

    private static void showRecyclerView(final PostData postData, final RecyclerView.ViewHolder holder, final Context mContext, final HomeRvListener mHomeRvListener, final int position,final String loginUsername){
        final ContestDataRvHolder body = (ContestDataRvHolder) holder;


        final ContestData data = (ContestData) postData;
        body.contestTillTime.setText(data.getTillTime()+" contest");
        body.creatorName.setText(HighLighter.capitalEachWordFirstLetter(data.getCreatorName()));
        body.dateTime.setText(data.getDateTime());
        body.leftSideName.setText(HighLighter.capitalEachWordFirstLetter(data.getLeftSideName()));
        body.rightSideName.setText(HighLighter.capitalEachWordFirstLetter(data.getRightSideName()));
        body.leftSidePicVotes.setText(data.getLeftPicNumUserVotes()+" votes");
        body.rightSidePicVotes.setText(data.getRightPicNumUserVotes()+" votes");

        String totalVotes = data.getTotalVotesIntoK();
        String totalComments = data.getTotalCommentsIntoK();
        String andText = "&";

        // body.totalVotesAndComments.setText(HighLighter.setTotalVotesAndCommentsAndTotalSeen(totalVotes+" votes",",",totalComments+" comments",data.getTotalSeen()+" seen",mContext));
        body.totalComments.setText(data.getTotalCommentsIntoK());
        body.totalSeen.setText(data.getTotalSeenIntoK());
        body.totalVotes.setText(data.getTotalVotesIntoK());




        RequestOptions options = new RequestOptions();




        Glide.with(mContext).load(Addresses.getWebAddress()+data.getLeftSideImage()).apply(options.centerCrop()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                body.leftSidePb.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                body.leftSidePb.setVisibility(View.GONE);

                return false;
            }
        }).into(body.leftSidePic);

        Glide.with(mContext).load(Addresses.getWebAddress()+data.getRightSideImage()).apply(options.centerCrop()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                body.rightSidePb.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                body.rightSidePb.setVisibility(View.GONE);

                return false;
            }
        }).into(body.rightSidePic);



        Glide.with(mContext).load(Addresses.getWebAddress()+data.getCreatorProfile()).apply(options.centerCrop()).into(body.creatorProfile);

        if(data.getAlreadyVote() == PostView.ALREADY_VOTE){
            int color = mContext.getResources().getColor(R.color.peter); //The color u want
            body.voteBtn.setColorFilter(color);
        }




        if(data.getContestComplete().equals("yes")){
            contestComplete(data,body);
//            if(mHomeRvListener != null)
//                mHomeRvListener.onCompletionContest(data,body,position);


        }else{
            body.equalVotes.setVisibility(View.GONE);
            body.rightUserWin.setVisibility(View.GONE);
            body.leftUserWin.setVisibility(View.GONE);
        }

        if(data.getCreator().equals(loginUsername)){
            body.contestFeatures.setVisibility(View.VISIBLE);
        }


        body.contestFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                featuresOnClick(mContext,body.contestFeatures,data,position,mHomeRvListener);
//                if(mHomeRvListener != null)
//                    mHomeRvListener.onPostEditingListener(data,position,body.contestEditing);
            }
        });

        body.creatorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickCreatorProfile(data);
            }
        });

        body.leftSideName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickLeftSideName(data);
            }
        });

        body.rightSideName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickRightSideName(data);
            }
        });



        body.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickCommentBtn(data);
            }
        });

        body.contest_postTotalLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener  != null)
                    mHomeRvListener.onClickVotesCommentAndSeenLl(data);
            }
        });


//        body.shareContest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShareApp.shareText((Activity)mContext,data.getContestLink());
//            }
//        });





//        body.refresh_contest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mHomeRvListener != null)
//                    mHomeRvListener.onRefreshing(data,position);
//            }
//        });


        body.voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeRvListener != null)
                    mHomeRvListener.onClickVoteBtn(data,position,body.voteBtn);
            }
        });

        body.leftSidePic.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    body.doubleTapLeftPic.setVisibility(View.VISIBLE);
                    setLeftSideDoubleTap(mHomeRvListener,postData,position,body);
                    return super.onDoubleTap(e);
                }

            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        body.rightSidePic.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    body.doubleTapRightPic.setVisibility(View.VISIBLE);
                    setRightSideDoubleTap(mHomeRvListener,postData,position,body);
                    return super.onDoubleTap(e);
                }

            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("TEST", "Raw event: " + event.getAction() + ", (" + event.getRawX() + ", " + event.getRawY() + ")");
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }


    private static void setLeftSideDoubleTap(HomeRvListener mHomeRvListener, PostData data, int position, final ContestDataRvHolder body){
        body.doubleTapLeftPic.postDelayed(new Runnable() {
            @Override
            public void run() {
                body.doubleTapLeftPic.setVisibility(View.GONE);
            }
        },700);

        if(mHomeRvListener != null)
            mHomeRvListener.onClickDoubleTapLeftPicVote(data,position,body.voteBtn);
    }

    private static void setRightSideDoubleTap(HomeRvListener mHomeRvListener, PostData data, int position, final ContestDataRvHolder body){
        body.doubleTapLeftPic.postDelayed(new Runnable() {
            @Override
            public void run() {
                body.doubleTapRightPic.setVisibility(View.GONE);
            }
        },700);

        if(mHomeRvListener != null)
            mHomeRvListener.onClickDoubleTapRightPicVote(data,position,body.voteBtn);
    }




    private static  void contestComplete(ContestData data,  ContestDataRvHolder body) {
        if(data.getLeftPicNumUserVotes() > data.getRightPicNumUserVotes()){
            body.leftUserWin.setVisibility(View.VISIBLE);
            body.rightUserWin.setVisibility(View.GONE);
            body.equalVotes.setVisibility(View.GONE);
            body.contestTillTime.setText(HighLighter.capitalEachWordFirstLetter(data.getLeftSideName()+" won"));
        }else if(data.getLeftPicNumUserVotes() == data.getRightPicNumUserVotes()){
            body.equalVotes.setVisibility(View.VISIBLE);
            body.rightUserWin.setVisibility(View.GONE);
            body.leftUserWin.setVisibility(View.GONE);
            body.contestTillTime.setText("Contest tied");
        }else{
            body.contestTillTime.setText(HighLighter.capitalEachWordFirstLetter(data.getRightSideName()+" won"));
            body.rightUserWin.setVisibility(View.VISIBLE);
            body.leftUserWin.setVisibility(View.GONE);
            body.equalVotes.setVisibility(View.GONE);
        }
    }


    private static void featuresOnClick(Context context, Button featureBtn, final PostData data, final int rvPosition, final HomeRvListener homeRvListener) {

        final List<PowerMenuItem> items =  new ArrayList<>();
        items.add(new PowerMenuItem(SinglePostsRvData.DELETE_POST,false));

        Menu.createMenu(context,items,featureBtn,Menu.SHOWN_AS_DROP_DOWN);
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                homeRvListener.onClickDeleteBtn(data,rvPosition);
            }
        });
    }


}
