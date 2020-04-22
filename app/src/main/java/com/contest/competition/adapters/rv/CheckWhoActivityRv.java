package com.contest.competition.adapters.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.R;

import com.contest.competition.adapters.holders.CheckWhoActivityRvHolder;
import com.contest.competition.adapters.rvData.CheckWhoActivityRvData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CheckActivityRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SawVoteCheckData;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 8/24/2018.
 */

public class CheckWhoActivityRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int VOTED_FRAGMENT = 1;
    public static int SAW_FRAGMENT = 2;
    private int mCheck;
    private Context mContext;
    private CheckActivityRvListener mListener;
    private ArrayHolder mArrayHolder;


    public CheckWhoActivityRv(int check){
        mCheck = check;
    }

    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    public void setListener(CheckActivityRvListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          mContext = parent.getContext();
        return new CheckWhoActivityRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.general_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



         if(mCheck == VOTED_FRAGMENT){
             //who voted
             if(position < mArrayHolder.getCheckWhoVotedData().size()) {
                 final SawVoteCheckData data = mArrayHolder.getCheckWhoVotedData().get(position);
                 CheckWhoActivityRvHolder rvHolder = (CheckWhoActivityRvHolder) holder;
                 CheckWhoActivityRvData rvData = new CheckWhoActivityRvData();
                 rvData.setArrayHolder(mArrayHolder);
                 rvData.setCheckActivityRvListener(mListener);
                 rvData.setHolder(rvHolder);
                 rvData.setPosition(position);
                 rvData.setSawVoteCheckData(data);
                 rvData.putData();

             }else Toaster.setToaster(mContext,"Please refresh a page");



         }else if(mCheck == SAW_FRAGMENT){
             //who saw
             if(position < mArrayHolder.getCheckWhoSawData().size()) {
                 final SawVoteCheckData data = mArrayHolder.getCheckWhoSawData().get(position);
                 CheckWhoActivityRvHolder rvHolder = (CheckWhoActivityRvHolder) holder;


                 CheckWhoActivityRvData rvData = new CheckWhoActivityRvData();
                 rvData.setArrayHolder(mArrayHolder);
                 rvData.setCheckActivityRvListener(mListener);
                 rvData.setHolder(rvHolder);
                 rvData.setPosition(position);
                 rvData.setSawVoteCheckData(data);
                 rvData.putData();

             }else Toaster.setToaster(mContext,"Please refresh a page");



         }

    }

    @Override
    public int getItemCount() {
        if(mArrayHolder != null) {
            if (mCheck == VOTED_FRAGMENT)
                return mArrayHolder.getCheckWhoVotedData().size();
            else return mArrayHolder.getCheckWhoSawData().size();
        }else return 0;
    }


}
