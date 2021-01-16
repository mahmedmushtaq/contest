package com.contest.competition.adapters.rvData;

import android.content.Context;
import android.text.Spannable;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.contest.competition.R;
import com.contest.competition.adapters.holders.NotificationRvHolder;
import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.NotificationRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.NotificationData;
import com.contest.competition.classes.webfiles.Addresses;

public class NotificationRvData {
    private NotificationRvHolder holder;
    private Context mContext;
    private int position;
    private ArrayHolder mArrayHolder;
    private NotificationRvListener mListener;
    private NotificationData data;


    public void setListener(NotificationRvListener listener){
        this.mListener = listener;
    }


    public void setArrayHolder(ArrayHolder arrayHolder) {
        mArrayHolder = arrayHolder;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setNotificationData(NotificationData data){
        this.data = data;
    }

    public void setHolder(NotificationRvHolder holder) {
        this.holder = holder;
    }


    public void putNotificationData(){
  //   if(data == null) return;

//        Log.e("ntoficiation rv data", "putNotificationData: "+data.getName() );


        if (position == mArrayHolder.getNotificationData().size() - 1) {
            mListener.onViewLastItem(data,position);
        }

        Glide.with(mContext).load(Addresses.getWebAddress() + data.getProfilePics()).into(holder.getNotificationProfile());

//       try{
//
//       }catch(NullPointerException e){
//           Log.e("tag", "putNotificationData: Null Pointer exception " );
//       }
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
