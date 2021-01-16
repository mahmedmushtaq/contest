package com.contest.competition.adapters.rv;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.contest.competition.adapters.holders.NotificationRvHolder;
import com.contest.competition.adapters.rvData.NotificationRvData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.NotificationRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.NotificationData;

import com.contest.competition.R;

/**
 * Created by M Ahmed Mushtaq on 6/19/2018.
 */

public class NotificationRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private NotificationRvListener mListener;
    private int SHOW_BODY = 1;
    private int SHOW_PB = 0;
    private ArrayHolder mArrayHolder;



    public void setArrayHolder(ArrayHolder holder){
        mArrayHolder = holder;
    }

    public void setListener(NotificationRvListener rvListener){
        mListener = rvListener;
    }









    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new NotificationRvHolder(LayoutInflater.from(mContext).inflate(R.layout.notification_rv_layout,parent,false));


    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holdr, int position) {

        if(position < mArrayHolder.getNotificationData().size()) {

            NotificationRvHolder holder = (NotificationRvHolder) holdr;
            Log.d("notificationRv", "onBindViewHolder: "+mArrayHolder.getNotificationData().size());
            NotificationData data = mArrayHolder.getNotificationData().get(position);

            NotificationRvData rvData = new NotificationRvData();
            rvData.setNotificationData(data);
            rvData.setListener(mListener);
            rvData.setArrayHolder(mArrayHolder);
            rvData.setContext(mContext);
            rvData.setHolder(holder);
            rvData.setPosition(position);
            rvData.putNotificationData();





        }



    }

    @Override
    public int getItemCount() {
        if(mArrayHolder != null)
        return mArrayHolder.getNotificationData().size();
        else return 0;
    }




    private class ShowPb extends RecyclerView.ViewHolder{
      ProgressBar pb;
        public ShowPb(View itemView) {
            super(itemView);
            pb = itemView.findViewById(R.id.show_pb);
        }
    }


}
