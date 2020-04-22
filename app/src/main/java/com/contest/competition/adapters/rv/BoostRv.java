package com.contest.competition.adapters.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.R;

import com.contest.competition.adapters.holders.BoostRvHolder;
import com.contest.competition.adapters.rvData.PutBoostRvData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.OnClickBoostRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.BoostData;
import com.contest.competition.utils.views.Toaster;

/**
 * Created by M Ahmed Mushtaq on 9/12/2018.
 */

public class BoostRv extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int PROFILE_BOOST = 1;
    public static final int POST_BOOST = 2;
    private int mCheck;
    private Context mContext;
    private OnClickBoostRvListener mBoostRvListener;
    private ArrayHolder mArrayHolder;

    public BoostRv(int check){
        mCheck = check;
    }

    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    public void setBoostRvListener(OnClickBoostRvListener listener){
        mBoostRvListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new BoostRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.boost_alert_rv_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      if(position < mArrayHolder.getPostBoostData().size()) {

          if (mCheck == PROFILE_BOOST) {

              BoostData data =  mArrayHolder.getProfileBoost().get(position);

              BoostRvHolder holder1 = (BoostRvHolder) holder;
              PutBoostRvData putBoostRvData = new PutBoostRvData();
              putBoostRvData.setBoostData(data);
              putBoostRvData.setPosition(position);
              putBoostRvData.setBoostRvListener(mBoostRvListener);
              putBoostRvData.setBoostRvHolder(holder1);

              putBoostRvData.putData();
          } else if (mCheck == POST_BOOST) {
              BoostData data =  mArrayHolder.getPostBoostData().get(position);


              BoostRvHolder holder1 = (BoostRvHolder) holder;
              PutBoostRvData putBoostRvData = new PutBoostRvData();
              putBoostRvData.setBoostData(data);
              putBoostRvData.setPosition(position);
              putBoostRvData.setBoostRvListener(mBoostRvListener);
              putBoostRvData.setBoostRvHolder(holder1);

              putBoostRvData.putData();
          }
      }else Toaster.setToaster(mContext,"Please refresh a page");
    }

    @Override
    public int getItemCount() {
        if(mCheck == PROFILE_BOOST)
        return mArrayHolder.getProfileBoost().size();
        else if(mCheck == POST_BOOST)
            return mArrayHolder.getPostBoostData().size();
        else return 0;
    }






}
