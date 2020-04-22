package com.contest.competition.adapters.rvData;

import android.util.Log;
import android.view.View;

import com.contest.competition.adapters.holders.CheckWhoActivityRvHolder;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CheckActivityRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SawVoteCheckData;

public class CheckWhoActivityRvData {
    private CheckWhoActivityRvHolder mHolder;
    private SawVoteCheckData mSawVoteCheckData;
    private int position;
    private ArrayHolder mArrayHolder;
    private CheckActivityRvListener mCheckActivityRvListener;

    public void setCheckActivityRvListener(CheckActivityRvListener checkActivityRvListener) {
        mCheckActivityRvListener = checkActivityRvListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setArrayHolder(ArrayHolder arrayHolder) {
        mArrayHolder = arrayHolder;
    }

    public void setHolder(CheckWhoActivityRvHolder holder) {
        mHolder = holder;
    }

    public void setSawVoteCheckData(SawVoteCheckData sawVoteCheckData) {
        mSawVoteCheckData = sawVoteCheckData;
    }


    public void putData(){

        mHolder.getName().setText(mSawVoteCheckData.getName());
        mHolder.getUsername().setText(mSawVoteCheckData.getUsername());


        if (position == mArrayHolder.getCheckWhoVotedData().size() - 1 || position ==  mArrayHolder.getCheckWhoSawData().size()-1) {
            Log.e("saw", "putData: on reaching bottom in saw activity" );
            if (mCheckActivityRvListener != null)
                mCheckActivityRvListener.onReachingBottom(mSawVoteCheckData);

        }
        mHolder.getGeneralLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckActivityRvListener != null)
                    mCheckActivityRvListener.visitProfileOnClick(mSawVoteCheckData);
            }
        });


    }
}
