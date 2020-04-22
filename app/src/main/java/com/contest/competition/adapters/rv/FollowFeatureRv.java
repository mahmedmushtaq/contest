package com.contest.competition.adapters.rv;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.adapters.holders.FollowFeatureRvHolder;
import com.contest.competition.adapters.rvData.PutFollowFeatureRvData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.FollowRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.FollowData;

import com.contest.competition.R;

/**
 * Created by M Ahmed Mushtaq on 6/27/2018.
 */

public class FollowFeatureRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int CHECK_ACTIVITY ;
    private Context mContext;
    private FollowRvListener mFollowRvListener;
    public  static int Following_Activity = 1;
    public  static int Follower_Activity = 0;

    public ArrayHolder mArrayHolder;


    public FollowFeatureRv(int checkActivity){
        this.CHECK_ACTIVITY =  checkActivity;
    }

    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    public void setFollowRvListener(FollowRvListener rvListener){
        this.mFollowRvListener = rvListener;
    }

//    public void setSearchRvListener(SearchRvListener listener){
//        this.mFollowRvListener = listener;
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new FollowFeatureRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rv_layout,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(CHECK_ACTIVITY == Following_Activity){
            FollowData data = mArrayHolder.getFollowingData().get(position);
            FollowFeatureRvHolder rvHolder = (FollowFeatureRvHolder) holder;
            putDataIntoRvData(position,rvHolder,data);

           // setBindView(holder,data);
        }else {
            FollowData data = mArrayHolder.getFollowerData().get(position);

            FollowFeatureRvHolder rvHolder = (FollowFeatureRvHolder) holder;

            putDataIntoRvData(position,rvHolder,data);




        }

    }

    @Override
    public int getItemCount() {
        if(mArrayHolder != null) {
            if (CHECK_ACTIVITY == Following_Activity) {
                return mArrayHolder.getFollowingData().size();
            }
            return mArrayHolder.getFollowerData().size();
        }else return 0;
    }



    private void putDataIntoRvData(int position,FollowFeatureRvHolder rvHolder,FollowData data){
        PutFollowFeatureRvData rvData = new PutFollowFeatureRvData();
        rvData.setArrayHolder(mArrayHolder);
        rvData.setPosition(position);
        rvData.setCHECK_ACTIVITY(CHECK_ACTIVITY);
        rvData.setFollowRvListener(mFollowRvListener);
        rvData.setFollowData(data);
        rvData.setContext(mContext);
        rvData.setHolder(rvHolder);
        rvData.putData();

    }


}
