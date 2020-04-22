package com.contest.competition.adapters.rvData;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.contest.competition.adapters.holders.FollowFeatureRvHolder;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.FollowRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.FollowData;
import com.contest.competition.classes.webfiles.Addresses;

public class PutFollowFeatureRvData {
    private FollowFeatureRvHolder holder;
    private Context mContext;
    private FollowData data;
    private FollowRvListener mFollowRvListener;
    private int CHECK_ACTIVITY,position;
    private ArrayHolder mArrayHolder;

    public void setArrayHolder(ArrayHolder arrayHolder) {
        mArrayHolder = arrayHolder;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCHECK_ACTIVITY(int CHECK_ACTIVITY) {
        this.CHECK_ACTIVITY = CHECK_ACTIVITY;
    }

    public void setFollowRvListener(FollowRvListener followRvListener) {
        mFollowRvListener = followRvListener;
    }

    public void setFollowData(FollowData followData) {
        data = followData;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setHolder(FollowFeatureRvHolder holder) {
        this.holder = holder;
    }

    public void putData(){


        if(position == mArrayHolder.getFollowingData().size()-1 || position == mArrayHolder.getFollowerData().size()-1)
            mFollowRvListener.onViewEndRvItem(data,position);


        Glide.with(mContext).load(Addresses.getWebAddress()+data.getProfilePic()).into(holder.getProfile());
        holder.getName().setText(data.getName());
        holder.getUsername().setText(data.getUsername());

        holder.getProfile().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFollowRvListener.viewProfileListener(data);
            }
        });

        if(CHECK_ACTIVITY != 1){//1 for following activity this ensure user must sent contest request to follow user not following user
            holder.getNamAndUsernameContainerLl().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFollowRvListener.onNameAndUsernameContainerListener(data);
                }
            });
        }

    }
}
