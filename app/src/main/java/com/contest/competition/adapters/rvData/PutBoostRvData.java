package com.contest.competition.adapters.rvData;

import android.view.View;

import com.contest.competition.adapters.holders.BoostRvHolder;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.OnClickBoostRvListener;
import com.contest.competition.classes.models.BoostData;

public class PutBoostRvData {
    private BoostRvHolder mBoostRvHolder;
    private OnClickBoostRvListener mBoostRvListener;
    private BoostData mBoostData;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBoostData(BoostData boostData) {
        mBoostData = boostData;
    }


    public void setBoostRvListener(OnClickBoostRvListener boostRvListener) {
        mBoostRvListener = boostRvListener;
    }

    public void setBoostRvHolder(BoostRvHolder boostRvHolder) {
        mBoostRvHolder = boostRvHolder;
    }


    public void putData(){
        mBoostRvHolder.getNoOfCredits().setText(mBoostData.getNoOfCreditsUsed());
        mBoostRvHolder.getNoOfReachedUsers().setText(mBoostData.getNoOfUsersReached());

        mBoostRvHolder.getBoostContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBoostRvListener != null)
                    mBoostRvListener.onSelectedForPost(position,mBoostData);
            }
        });

    }
}
