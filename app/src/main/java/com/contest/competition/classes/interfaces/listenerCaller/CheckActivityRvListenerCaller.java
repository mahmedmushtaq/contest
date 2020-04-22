package com.contest.competition.classes.interfaces.listenerCaller;


import android.content.Context;

import com.contest.competition.classes.VisitProfile;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CheckActivityRvListener;
import com.contest.competition.classes.models.SawVoteCheckData;
import com.contest.competition.requests.data.CheckWhoSawReq;
import com.contest.competition.requests.data.CheckWhoVoteReq;


public class CheckActivityRvListenerCaller implements CheckActivityRvListener {
    private Context mContext;
    private CheckWhoSawReq mCheckWhoSawReq;
    private CheckWhoVoteReq mCheckWhoVoteReq;
    private String postId;
    public CheckActivityRvListenerCaller(Context context, String postId){
        mContext = context;

       this.postId = postId;
    }

    public void setCheckWhoSawReq(CheckWhoSawReq checkWhoSawReq) {
        mCheckWhoSawReq = checkWhoSawReq;
    }

    public void setCheckWhoVoteReq(CheckWhoVoteReq checkWhoVoteReq) {
        mCheckWhoVoteReq = checkWhoVoteReq;
    }

    @Override
    public void onReachingBottom(SawVoteCheckData data) {
        if(mCheckWhoSawReq != null)
            mCheckWhoSawReq.whoSawData(mContext,postId,data.getId()+"");
        else mCheckWhoVoteReq.whoVotedData(mContext,postId,data.getId()+"");
    }

    @Override
    public void visitProfileOnClick(SawVoteCheckData data) {
        VisitProfile.with(mContext).visitUserProfile(data.getUsername());
    }
}
