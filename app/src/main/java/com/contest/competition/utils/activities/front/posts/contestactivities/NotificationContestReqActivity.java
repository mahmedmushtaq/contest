package com.contest.competition.utils.activities.front.posts.contestactivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.contest.competition.R;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.SelectCropImageFromGallery;
import com.contest.competition.classes.models.ContestReqData;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.requests.data.contestreq.AcceptNewContest;
import com.contest.competition.requests.data.contestreq.CancelContestReq;
import com.contest.competition.requests.data.contestreq.RetrieveContestReq;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

import java.io.File;

public class NotificationContestReqActivity extends BaseToolbarActivity {
    private NotificationData mData;
    private LoginSharedPrefer mPrefer;
    private String contestReqId;
    private TextView mTillTime,leftSideVotes, mRightSideVotes, mLeftSideName, mRightSideName,creatorName,contestDateTime;
    private ImageView leftSideContestPic,rightSideContestPic,creatorProfile,refreshContest;
    private Button accept,cancel;
    private LinearLayout notificationContest_acceptAndCancelBtnLl, contestReqContainer;
    private ProgressBar leftSidePb,rightSidePb;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mData = getIntent().getParcelableExtra(KeyStore.PASS_OBJECT);

         mPrefer = new LoginSharedPrefer(this);

        NotificationsReq.notificationOpened(mData.getId(),mPrefer.getUsername());


        String[] linkParts = mData.getLink().split("=");
        contestReqId = linkParts[1];


//        contestDateTime = findViewById(R.id.dateTimeHomeRvContest);
//        mTillTime = findViewById(R.id.contestTillTimeHomeRv);
//        mLeftSideName = findViewById(R.id.leftSideNameTv);
//        mRightSideName = findViewById(R.id.rightSideNameTv);
//        refreshContest = findViewById(R.id.refresh_contest);
//        refreshContest.setVisibility(View.GONE);
//
//        leftSidePb = findViewById(R.id.leftSidePicPb);
//        rightSidePb = findViewById(R.id.rightSidePicPb);
//        leftSideContestPic = findViewById(R.id.leftSidePic);
//        rightSideContestPic = findViewById(R.id.rightSidePic);


        creatorProfile = findViewById(R.id.contest_creatorProfile);
        creatorName = findViewById(R.id.contest_creatorName);
        contestDateTime = findViewById(R.id.contest_dataTime);
        mTillTime = findViewById(R.id.contest_tillTime);
        mLeftSideName = findViewById(R.id.contest_leftSideName);
        mRightSideName = findViewById(R.id.contest_rightSideName);
        leftSideVotes = findViewById(R.id.contest_leftSideVote);
        mRightSideVotes = findViewById(R.id.contest_rightSideVote);
        leftSidePb = findViewById(R.id.contest_leftSidePb);
        rightSidePb = findViewById(R.id.contest_rightSidePb);

        leftSideContestPic = findViewById(R.id.contest_leftSidePic);
        rightSideContestPic = findViewById(R.id.contest_rightSidePic);







        accept = findViewById(R.id.contestNotificationActivity_accept);
        cancel = findViewById(R.id.contestNotificationActivity_cancel);


        notificationContest_acceptAndCancelBtnLl = findViewById(R.id.notificationContest_acceptAndCancelBtnLl);
        contestReqContainer = findViewById(R.id.notificationContest_container);





        retrieveData();
        
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 acceptContest();

            }
        });
        
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelContest();
                notificationContest_acceptAndCancelBtnLl.setVisibility(View.GONE);

            }
        });





    }



    private void acceptContest() {
       //

        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                dialog.dismiss();
                selectImage();
            }
        });

        Dialoger.setConfirmDialog(NotificationContestReqActivity.this,"Contest","Do you want to create a contest ?");


    }

    private void selectImage(){
        SelectCropImageFromGallery.selectImage(NotificationContestReqActivity.this,true);
        SelectCropImageFromGallery.setFileSelected(new SelectCropImageFromGallery.fileSelected() {
            @Override
            public void onSelected(Bitmap selectedImage, File selectedImageFile) {
                if(selectedImageFile != null)
                sendAcceptReq(selectedImageFile);
                else Toaster.setToaster(getBaseContext(),"image not selected");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SelectCropImageFromGallery.onActivityResult(requestCode,resultCode,data);
    }

    private void cancelContest(){


        Dialoger.setConfirmDialog(NotificationContestReqActivity.this,"Contest","Do you want to cancel contest req ?");
        Dialoger.setDialogListener(new Dialoger.dialogListener() {
            @Override
            public void onAccept(MaterialDialog dialog) {
                dialog.dismiss();
                cancelContestReq();
            }
        });


    }

    private void cancelContestReq(){
        Dialoger.setDialog(NotificationContestReqActivity.this,"Contest","please wait contest is cancelling");
        CancelContestReq.cancelContestReq(NotificationContestReqActivity.this,contestReqId);
        contestReqContainer.setVisibility(View.GONE);

    }

    private void sendAcceptReq(File imageFile){
        Dialoger.setDialog(NotificationContestReqActivity.this,"Contest","please wait contest is accepting");

        AcceptNewContest.contestReqAccepted(NotificationContestReqActivity.this,imageFile,contestReqId,mPrefer.getUsername(),rightSideContestPic);
        contestReqContainer.setVisibility(View.GONE);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_notification_contest_req;
    }

    @Override
    protected String setActivityName() {
        return "Contest Req";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }


    private void retrieveData(){
        RetrieveContestReq.loadContestReq(NotificationContestReqActivity.this,contestReqId);
        RetrieveContestReq.setRetrieveContestReqListener(new RetrieveContestReq.RetrieveContestReqListener() {
            @Override
            public void onRetrieveData(PostData data) {
                ContestReqData contestReqData = (ContestReqData) data;
                setDataAfterLoad(contestReqData);

            }
        });
    }

    private void setDataAfterLoad(ContestReqData contestReqData){


        mLeftSideName.setText(contestReqData.getLeftSideName());
        mRightSideName.setText(contestReqData.getRightSideName());
        mTillTime.setText(contestReqData.getTillTime());
        creatorName.setText(contestReqData.getCreatorName());
        contestDateTime.setText(contestReqData.getDateTime());
        Glide.with(this).load(Addresses.getWebAddress()+contestReqData.getCreatorProfile()).into(creatorProfile);

        Glide.with(getBaseContext()).load(Addresses.getWebAddress()+contestReqData.getLeftSidePic()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                leftSidePb.setVisibility(View.GONE);
                leftSideContestPic.setImageDrawable(resource);
            } });

        Glide.with(getBaseContext()).load(Addresses.getWebAddress()+contestReqData.getRightSidePic()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                rightSidePb.setVisibility(View.GONE);
                rightSideContestPic.setImageDrawable(resource);
            } });//.into(rightSideContestPic);


    }


}
