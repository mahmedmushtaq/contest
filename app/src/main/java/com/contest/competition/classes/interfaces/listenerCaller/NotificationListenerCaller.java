package com.contest.competition.classes.interfaces.listenerCaller;

import android.content.Context;
import android.content.Intent;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.VisitProfile;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.NotificationRvListener;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.requests.data.notificationsreq.NotificationRetrieveInActivity;
import com.contest.competition.requests.data.notificationsreq.NotificationsReq;
import com.contest.competition.utils.activities.front.posts.SimplePostActivity;
import com.contest.competition.utils.activities.front.posts.contestactivities.NotificationContestReqActivity;
import com.contest.competition.utils.activities.front.posts.contestactivities.SingleContestActivity;
import com.contest.competition.utils.activities.notificationactivities.FollowReqActivity;
import com.contest.competition.utils.views.Toaster;

public class NotificationListenerCaller implements NotificationRvListener{
    private Context mContext;
    private String mLoginUsername;
    private NotificationListenerCallerInterface mNotificationListenerCallerInterface;
    private int lastEndId = 0;
    private NotificationRetrieveInActivity mRetrieve;



    public interface NotificationListenerCallerInterface{
        void onRemove(NotificationData notificationData);
    }





    public NotificationListenerCaller(Context context,String username,NotificationRetrieveInActivity retrieveInActivity,NotificationListenerCallerInterface notificationListenerCallerInterface){
        mLoginUsername = username;
        mContext = context;
        mNotificationListenerCallerInterface = notificationListenerCallerInterface;
        mRetrieve = retrieveInActivity;
    }


    @Override
    public void onViewLastItem(NotificationData data, int position) {
        if(data.getId() != lastEndId) {
            Toaster.setToaster(mContext, "Loading more notifications");
            mRetrieve.retrieveNotification(mLoginUsername,"yes", data.getId());
            lastEndId = data.getId();
        }
    }

    @Override
    public void onItemClick(NotificationData data) {
            linkCheck(data);
    }

    @Override
    public void onProfileClick(NotificationData data) {
           VisitProfile.with(mContext).visitUserProfile(data.getUsername());
    }

    private void linkCheck(NotificationData data){
        if(Validator.isLinkUserId(data.getLink())){
            linkToUserId(data);
        }else if(Validator.isLinkUserFollowReq(data.getLink())){
            linkToFollowReq(data);

        }else if(Validator.isLinkContestReq(data.getLink())){
            linkToContestReq(data);
        }else if(Validator.isLinkToContest(data.getLink())){
            linkToContest(data);
        }else if(Validator.isLinkContestReqDeleted(data.getLink())) {
            if(mNotificationListenerCallerInterface != null)
                mNotificationListenerCallerInterface.onRemove(data);
        }else if(Validator.isLinkToSinglePost(data.getLink())){
            linkToSinglePost(data);
        }



    }

    private void linkToSinglePost(NotificationData data) {
        Intent intent = new Intent(mContext, SimplePostActivity.class);
        intent.putExtra(KeyStore.PASS_OBJECT,data);
        mContext.startActivity(intent);
    }








    private void linkToUserId(NotificationData data){
        NotificationsReq.notificationOpened(data.getId(),mLoginUsername);
        VisitProfile.with(mContext).visitUserProfile(data.getUsername());
    }

    private void linkToFollowReq(NotificationData data){
        NotificationsReq.notificationOpened(data.getId(),mLoginUsername);
        Intent intent = new Intent(mContext, FollowReqActivity.class);
        intent.putExtra(KeyStore.PASS_OBJECT,data);
        mContext.startActivity(intent);
    }

    private void linkToContestReq(NotificationData data){

        Intent intent = new Intent(mContext, NotificationContestReqActivity.class);
        intent.putExtra(KeyStore.PASS_OBJECT,data);
        mContext.startActivity(intent);
    }

    private void linkToContest(NotificationData data) {

        Intent intent = new Intent(mContext, SingleContestActivity.class);
        intent.putExtra(KeyStore.PASS_OBJECT,data);
        mContext.startActivity(intent);
    }
}

