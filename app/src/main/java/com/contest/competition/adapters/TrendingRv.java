package com.contest.competition.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.R;
import com.contest.competition.adapters.adapterdataclasses.ContestRvData;
import com.contest.competition.adapters.adapterdataclasses.SinglePostsRvData;
import com.contest.competition.adapters.holders.ContestDataRvHolder;
import com.contest.competition.adapters.holders.SinglePostRvHolder;
import com.contest.competition.classes.interfaces.HomeRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.ContestData;
import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.PostView;

public class TrendingRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HomeRvListener rvListener;
    private Context context;
    private LoginSharedPrefer mSharedPrefer;
    public void setRvListener(HomeRvListener listener){
        rvListener = listener;
    }

    private ArrayHolder mArrayHolder;


    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();

        if(viewType == PostView.SHOW_CONTEST){
            return new ContestDataRvHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contest_layout,viewGroup,false));

        }else if(viewType == PostView.SHOW_SIMPLE_POST){
          //  return new PostBodyRvHolder(LayoutInflater.from(context).inflate(R.layout.home_rv_post,viewGroup,false));
            return new SinglePostRvHolder(LayoutInflater.from(context).inflate(R.layout.single_post_rv_layout,viewGroup,false));

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
             mSharedPrefer = new LoginSharedPrefer(context);
             PostData data = mArrayHolder.getTrendingData().get(i);
             if(data instanceof ContestData){
                 ContestRvData.setArrayHolder(mArrayHolder);
                 ContestRvData.putContestData(viewHolder,i,rvListener,context,mSharedPrefer.getUsername(),SinglePostsRvData.TRENDING_RV);
             }else if(data instanceof SimplePostData) {
                 SinglePostsRvData.setArrayHolder(mArrayHolder);
                 SinglePostsRvData.putPostData(viewHolder, i, rvListener, context, SinglePostsRvData.TRENDING_RV);
             }


    }

    @Override
    public int getItemViewType(int position) {
        PostData data = mArrayHolder.getTrendingData().get(position);
        if(data instanceof ContestData){
            return PostView.SHOW_CONTEST;
        }else if(data instanceof SimplePostData){
            return PostView.SHOW_SIMPLE_POST;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mArrayHolder.getTrendingData().size();
    }
}
