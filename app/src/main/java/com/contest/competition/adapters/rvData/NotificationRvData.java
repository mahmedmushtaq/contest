package com.contest.competition.adapters.rvData;

import android.content.Context;
import android.text.Spannable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.contest.competition.R;
import com.contest.competition.adapters.holders.NotificationRvHolder;
import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.NotificationRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.webfiles.Addresses;

public class NotificationRvData {
    private NotificationRvHolder holder;
    private Context mContext;
    private int position;
    private ArrayHolder mArrayHolder;
    private NotificationRvListener mListener;
    private com.contest.competition.classes.models.NotificationData data;

    public void setArrayHolder(ArrayHolder arrayHolder) {
        mArrayHolder = arrayHolder;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setHolder(NotificationRvHolder holder) {
        this.holder = holder;
    }


    public void putNotificationData(){




        if (position == mArrayHolder.getNotificationData().size() - 1) {
            mListener.onViewLastItem(data,position);
        }

        Glide.with(mContext).load(Addresses.getWebAddress() + data.getProfilePics()).into(holder.getNotificationProfile());
        // holder.notificationTv.setText(data.getName()+" "+data.getMessage()+"  "+data.getDateTime());
        // String name = HighLighter.capitalFirstLetter(data.getName());
        String name = HighLighter.capitalEachWordFirstLetter(data.getName());
        Spannable spannable = HighLighter.threeThingsSpanner(data.getMessage(), name, data.getDateTime(), mContext);
        holder.getNotificationTv().setText(spannable);
        if (data.getOpened().contains("yes")) {
            holder.getLinearLayout().setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
//        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //also open new activity mean call a listener
//
//            }
//        });

        holder.getNotificationTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getLinearLayout().setBackgroundColor(mContext.getResources().getColor(R.color.white));
                mListener.onItemClick(data);
            }
        });

        holder.getNotificationProfile().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProfileClick(data);
            }
        });
    }

}
