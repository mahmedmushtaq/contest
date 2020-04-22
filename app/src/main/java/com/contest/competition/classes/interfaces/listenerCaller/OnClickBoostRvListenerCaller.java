package com.contest.competition.classes.interfaces.listenerCaller;

import android.app.Activity;
import android.content.Context;

import com.contest.competition.classes.interfaces.recyclerviewinterfaces.OnClickBoostRvListener;
import com.contest.competition.classes.interfaces.OnSuccessfulListener;
import com.contest.competition.classes.models.BoostData;
import com.contest.competition.requests.data.boostreq.BoostPost;
import com.contest.competition.utils.views.Dialoger;

public class OnClickBoostRvListenerCaller implements OnClickBoostRvListener {
    private Context mContext;
    private int mId;
    private String mUsername;
    private OnSuccessfulListener mOnSuccessfulListener;

    public void setOnSuccessfulListener(OnSuccessfulListener onSuccessfulListener) {
        mOnSuccessfulListener = onSuccessfulListener;

    }

    public OnClickBoostRvListenerCaller(Context context, String username, int id){
        mContext  = context;
        mUsername = username;
        mId = id;
    }



    @Override
    public void onSelectedForPost(int position, BoostData data) {
        setBoostPost(data);
    }

    private void setBoostPost(BoostData data){
        Dialoger.setDialog((Activity)mContext,"Boost Post","Please wait your post is boosting..");
        BoostPost.boostPost(mContext,mId+"",mUsername,data.getNoOfCredits()+"",data.getNoOfUsersReached());
        if(mOnSuccessfulListener != null)
            mOnSuccessfulListener.onSuccessful();

    }
}
