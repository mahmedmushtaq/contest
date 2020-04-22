package com.contest.competition.classes.interfaces.listenerCaller;

import android.content.Context;

import com.contest.competition.classes.VisitProfile;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.FollowRvListener;
import com.contest.competition.classes.models.FollowData;
import com.contest.competition.requests.data.followfeature.RetrieveFollowersReq;
import com.contest.competition.requests.data.followfeature.RetrieveFollowingsReq;

public class FollowRvListenerCaller implements FollowRvListener {
    private Context mContext;
    private RetrieveFollowersReq mRetrieveFollowersReq;
    private RetrieveFollowingsReq mRetrieveFollowingsReq;
    private String username;
    private OnSuccessfulFollowRvCaller mOnSuccessfulListener;

    public interface OnSuccessfulFollowRvCaller{
        void onSuccessful(FollowData data);
    }





    public FollowRvListenerCaller(Context context,  String username, OnSuccessfulFollowRvCaller onSuccessfulListener){
        mContext = context;

        mOnSuccessfulListener = onSuccessfulListener;
        this.username = username;
    }

    public void setRetrieveFollowersReq(RetrieveFollowersReq retrieveFollowerActivityReq) {
        mRetrieveFollowersReq = retrieveFollowerActivityReq;
    }


    public void setRetrieveFollowingsReq(RetrieveFollowingsReq retrieveFollowingsReq) {
        mRetrieveFollowingsReq = retrieveFollowingsReq;
    }

    @Override
    public void viewProfileListener(FollowData data) {
        VisitProfile.with(mContext).visitUserProfile(data.getUsername());
    }

    @Override
    public void onNameAndUsernameContainerListener(FollowData data) {
        if(mOnSuccessfulListener != null)
            mOnSuccessfulListener.onSuccessful(data);
            //
       //mean this feature is only used to send contest to those users who follow me not i follow him






    }

    @Override
    public void onViewEndRvItem(FollowData data, int position) {
        if(mRetrieveFollowersReq != null)
            mRetrieveFollowersReq.getData(username,data.getId() + "");
        else mRetrieveFollowingsReq.getData(username,data.getId()+"");
    }




}
