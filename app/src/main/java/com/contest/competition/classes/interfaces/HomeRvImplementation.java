package com.contest.competition.classes.interfaces;

import android.app.Activity;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;
import com.contest.competition.adapters.adapterdataclasses.SinglePostsRvData;
import com.contest.competition.classes.VisitProfile;
import com.contest.competition.utils.views.PostView;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;

import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.requests.data.contestreq.DeleteContest;
import com.contest.competition.requests.data.contestreq.LoadNewCommentsVotesAndSeenReq;
import com.contest.competition.requests.data.contestreq.Vote;
import com.contest.competition.requests.data.postreq.DeleteSimplePost;
import com.contest.competition.utils.activities.front.ProfileActivity;
import com.contest.competition.utils.activities.front.posts.CheckWhoVotesCommentsAndSeenActivity;
import com.contest.competition.utils.activities.front.posts.CommentActivity;
import com.contest.competition.utils.fragments.dialogfragment.BoostPostAlertFragment;
import com.contest.competition.utils.views.Menu;
import com.contest.competition.utils.views.Toaster;


/**
 * Created by M Ahmed Mushtaq on 9/7/2018.
 */

public class HomeRvImplementation implements HomeRvListener {

    private Context mContext;
    private String loginUsername;
    private FragmentManager mManager;



    private SuccessHomeRvImplementationListener mListener;




    public interface SuccessHomeRvImplementationListener{
        void Successful(int position);
        void loadingMore(int position);
        void remove(int position);

    }

    public void setListener(SuccessHomeRvImplementationListener listener){
        mListener = listener;
    }

    public HomeRvImplementation(Context context,String loginUsername){
        mContext = context;
        this.loginUsername = loginUsername;
        mManager = ((Activity)context).getFragmentManager();

    }





    @Override
    public void onClickCreatorProfile(PostData data) {
        if(data instanceof ContestData)
            creatorProfile(((ContestData)data).getCreator());
        if(data instanceof SimplePostData)
            creatorProfile(((SimplePostData)data).getPostedBy());
    }

    @Override
    public void onClickLeftSideName(PostData data) {
        clickLeftName(((ContestData)data).getLeftSideUsername());

    }

    @Override
    public void onClickRightSideName(PostData data) {
        clickRightName(((ContestData)data).getRightSideUsername());
    }

    @Override
    public void onClickVoteBtn(PostData data, int position,ImageView voteBtn) {
        if(data instanceof ContestData){
            clickVoteBtn(data,position,voteBtn);
        }else if(data instanceof SimplePostData){
            directVoteSimplePost((SimplePostData)data,data,position,voteBtn);

        }



    }

    @Override
    public void onClickCommentBtn(PostData data) {
        if(data instanceof ContestData)
            clickCommentBtn(data.getPostId(),"contest");
        else if(data instanceof SimplePostData){
            clickCommentBtn(data.getPostId(),"single post");
        }
    }



    @Override
    public void onReachingBottom(int position) {

        setLoadingMore(position);

    }

    @Override
    public void onClickBoostBtn(PostData data) {
        Menu.hideMenu();
        DialogFragment newFragment =  BoostPostAlertFragment.newInstance(data.getPostId());
        newFragment.show(((Activity)mContext).getFragmentManager(),"tag");
    }

    @Override
    public void onClickDeleteBtn(PostData data, int position) {

        Menu.hideMenu();
        if(data instanceof ContestData)
            sendDeleteContestReqToServer((ContestData) data,position);
        else if(data instanceof SimplePostData){
            SimplePostData data1 = (SimplePostData) data;
            directDeleteSimplePost(data1,position);
        }
    }






    @Override
    public void onClickVotesCommentAndSeenLl(PostData data) {
        Intent intent = new Intent(mContext, CheckWhoVotesCommentsAndSeenActivity.class);
        intent.putExtra(KeyStore.PASS_DATA,data.getPostId()+"");
        mContext.startActivity(intent);
        Animatoo.animateSlideUp(mContext);
    }

    @Override
    public void onClickDoubleTapLeftPicVote(PostData data, int position, ImageView voteBtn) {
        sendVoteToServer(data,(ContestData)data,null,position,"yes","no","contest",voteBtn);

    }

    @Override
    public void onClickDoubleTapRightPicVote(PostData data, int position, ImageView voteBtn) {
        sendVoteToServer(data,(ContestData)data,null,position,"no","yes","contest",voteBtn);

    }


    private void refreshSingleRvChild(final PostData data, final int position){
        LoadNewCommentsVotesAndSeenReq.loadNewData(data.getPostId()+"",mContext);
        LoadNewCommentsVotesAndSeenReq.setOnLoadNewContestData(new LoadNewCommentsVotesAndSeenReq.onLoadNewContestData() {
            @Override
            public void onSuccess(int totalComments, int totalVotes, int totalSeen) {
                data.updateTotalSeen(totalSeen);
                data.updateTotalVotes(totalVotes);
                data.updateTotalComments(totalComments);
                if(mListener != null)
                    mListener.Successful(position);
            }

            @Override
            public void onFail(String reason) {

            }


        });


    }

    private void setLoadingMore(int position) {
        if(mListener != null)
            mListener.loadingMore(position);
    }


    private void setMenuViewClick(final PostData data, final int abovePosition, final View view) {
        List<PowerMenuItem> items = new ArrayList<>();
        items.add(new PowerMenuItem("Remove",false));
        Menu.createMenu(mContext,items,view,Menu.SHOWN_AS_DROP_DOWN);
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                if(position == 0) {

                    Menu.hideMenu();
                    if(data instanceof ContestData)
                        sendDeleteContestReqToServer((ContestData) data,abovePosition);
                    else if(data instanceof SimplePostData){
                        SimplePostData data1 = (SimplePostData) data;
                           directDeleteSimplePost(data1,abovePosition);
                    }
                }
            }
        });

    }


    private void sendDeleteContestReqToServer(ContestData data,int position) {
        Log.e("yes", "sendDeleteContestReqToServer: deleting contest id are = "+data.getContestId() );
        DeleteContest.deleteContest( mContext,data.getContestId()+"",loginUsername);

         if(mListener != null)
             mListener.remove(position);


    }


    private void creatorProfile(String username){
//        Intent intent = new Intent(mContext,ProfileActivity.class);
//        intent.putExtra(KeyStore.PASS_DATA,username);
//        mContext.startActivity(intent);
        VisitProfile.with(mContext).visitUserProfile(username);
    }

    private void clickLeftName(String username){
//        Intent intent = new Intent(mContext,ProfileActivity.class);
//        intent.putExtra(KeyStore.PASS_DATA,username);
//        mContext.startActivity(intent);
        VisitProfile.with(mContext).visitUserProfile(username);
    }

    private void clickRightName(String username){
//        Intent intent = new Intent(mContext,ProfileActivity.class);
//        intent.putExtra(KeyStore.PASS_DATA,username);
//        mContext.startActivity(intent);
        VisitProfile.with(mContext).visitUserProfile(username);
    }

    private void clickCommentBtn(int id,String type){
        Intent intent = new Intent(mContext,CommentActivity.class);
        intent.putExtra(KeyStore.PASS_DATA,id+"");
        intent.putExtra(KeyStore.COMMENT_TYPE,type);
        mContext.startActivity(intent);

        Animatoo.animateSlideUp(mContext);
    }

    private void directVoteSimplePost(SimplePostData simplePostData,PostData postData,int position,ImageView voteBtn){
        sendVoteToServer(postData,null,simplePostData,position,"","","single post",voteBtn);

    }

    private void directDeleteSimplePost(SimplePostData simplePostData, final int position){
        DeleteSimplePost.setDeleteInterface(new DeleteSimplePost.DeleteSimplePostInterface() {
            @Override
            public void onSuccessful() {
                if(mListener != null)
                    mListener.remove(position);
            }

            @Override
            public void onFail() {

            }
        });

        DeleteSimplePost.delete(mContext,simplePostData.getPostId()+"",loginUsername);
    }

    private void clickVoteBtn(PostData postData,int position,ImageView  view){
        ContestData data = (ContestData) postData;
        final List<PowerMenuItem> voteUerList = new ArrayList<>();
        PowerMenuItem leftSideNameItem = new PowerMenuItem(data.getLeftSideName(),false);
        PowerMenuItem RightSideNameItem = new PowerMenuItem(data.getRightSideName(),false);
        voteUerList.add(leftSideNameItem);
        voteUerList.add(RightSideNameItem);

       setVote(postData,data,position,voteUerList,view);
    }

    private void setVote(final PostData postData, final ContestData data, final int rvPosition, List<PowerMenuItem> itemsList, final ImageView voteBtn) {
        Menu.createMenu( mContext, itemsList, voteBtn, Menu.SHOWN_AS_ANCHOR_VIEW_CENTER);
        Menu.setMenuClickListener(new Menu.menuClickListener() {
            @Override
            public void onMenuClick(int position, PowerMenuItem item) {
                if (position == 0) {
                    sendVoteToServer(postData,data,null,rvPosition,"yes","no","contest",voteBtn);
                    Menu.hideMenu();
                } else {
                    sendVoteToServer(postData,data,null,rvPosition,"no","yes","contest",voteBtn);


                    Menu.hideMenu();

                }
            }
        });
    }




    private void sendVoteToServer(final PostData postData, final ContestData data, final SimplePostData simplePostData, final int position, final String leftPicVote, final String rightPicVote, final String type, final ImageView view){
        Vote.vote(mContext,loginUsername,postData.getPostId()+"",leftPicVote,rightPicVote,type);



        Vote.setVoteListener(new Vote.voteListener() {
            @Override
            public void onSuccess(String result, String reason) {
                if(Validator.validateWebResult(result)){
                    // voteBtn.setBackgroundColor(getResources().getColor(R.color.peter));
                    view.setColorFilter( mContext.getResources().getColor(R.color.peter));

                    if(leftPicVote.equals("yes")){
                        if(data != null) {

                            double increase = data.getTotalVotes();
                            double leftPicVote = data.getLeftPicNumUserVotes();
                            leftPicVote++;
                            increase += 1;
                            postData.updateTotalVotes(increase);
                            postData.updateLeftPicVote(leftPicVote);
                            postData.updateAlreadyVote(PostView.ALREADY_VOTE);

                            if(mListener != null){

                                mListener.Successful(position);
                            }

                        }

                    }else if(rightPicVote.equals("yes")){
                        if(data != null) {
                            double increase = data.getTotalVotes();
                            double rightPicVote = data.getRightPicNumUserVotes();
                            rightPicVote++;
                            increase += 1;
                            postData.updateTotalVotes(increase);
                            postData.updateRightPicVote(rightPicVote);
                            postData.updateAlreadyVote(PostView.ALREADY_VOTE);

                            if(mListener != null)
                                mListener.Successful(position);
                        }

                    }else if(type.contains("single post")){
                        if(simplePostData != null){
                            double increase = simplePostData.getTotalVotes();
                            increase++;
                            postData.updateTotalVotes(increase);
                            postData.updateAlreadyVote(PostView.ALREADY_VOTE);


                            if(mListener != null)
                                mListener.Successful(position);
                        }
                    }
                }else{
                    Toaster.setToaster( mContext,reason);
                }
            }
        });
    }






}
