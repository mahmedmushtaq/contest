package com.contest.competition.adapters.rvData;

import android.view.View;

import com.contest.competition.adapters.holders.CreditsRvHolder;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CreditsRvIistener;
import com.contest.competition.classes.models.CreditsData;

public class PutCreditsData {
    private CreditsRvHolder mCreditsRvHolder;
    private CreditsData mCreditsData;
    private CreditsRvIistener mCreditsRvInterface;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCreditsRvInterface(CreditsRvIistener creditsRvInterface) {
        mCreditsRvInterface = creditsRvInterface;
    }

    public void setCreditsData(CreditsData creditsData) {
        mCreditsData = creditsData;
    }

    public void setCreditsRvHolder(CreditsRvHolder creditsRvHolder) {
        mCreditsRvHolder = creditsRvHolder;
    }


    public void  putData(){


        mCreditsRvHolder.getNoOfCreditsEarn().setText(mCreditsData.getNoOfCreditsString()+"");
        mCreditsRvHolder.getPrice().setText(mCreditsData.getPriceWithDollarSign());

        mCreditsRvHolder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCreditsRvInterface != null)
                    mCreditsRvInterface.onClick(mCreditsData,position);
            }
        });
    }
}
