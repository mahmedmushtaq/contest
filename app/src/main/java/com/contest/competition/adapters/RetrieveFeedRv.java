package com.contest.competition.adapters;

import android.content.Context;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.contest.competition.adapters.adapterdataclasses.ContestRvData;
import com.contest.competition.adapters.adapterdataclasses.SinglePostsRvData;
import com.contest.competition.adapters.holders.ContestDataRvHolder;
import com.contest.competition.adapters.holders.SimpleTvRvHolder;
import com.contest.competition.adapters.holders.SinglePostRvHolder;
import com.contest.competition.classes.interfaces.HomeRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.ContestData;

import com.contest.competition.classes.models.PostData;
import com.contest.competition.classes.models.SimplePostData;
import com.contest.competition.classes.models.SimpleTvData;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.PostView;

import com.contest.competition.R;


/**
 * Created by M Ahmed Mushtaq on 7/6/2018.
 */

public class RetrieveFeedRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int BODY = 1;
    private Context mContext;
    private LoginSharedPrefer mPrefer;
    private ProgressBar pB;
    private HomeRvListener mHomeRvListener;
    private ArrayHolder mArrayHolder;


    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }


//    public void setCheckActivity(int checkActivity){
//        this.checkActivity = checkActivity;
//    }


    public void setHomeRvListener(HomeRvListener listener) {
        mHomeRvListener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        if( parent == null )
            Log.e("HomeRv", "onCreateViewHolder: null object parent null ");

        if (viewType == PostView.SHOW_CONTEST) {
            //return new ContestDataRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_contest_layout, parent, false));
            return new ContestDataRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_layout, parent, false));
        } else if (viewType == PostView.SHOW_SIMPLE_POST) {
            // return new PostBodyRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_rv_post, parent, false));
            return new SinglePostRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_rv_layout, parent, false));
        } else if(viewType == PostView.SHOW_SIMPLE_TV){
            return  new SimpleTvRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_layout,parent,false));

        }
        return new SinglePostRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_rv_layout, parent, false));

        //   return  new SimpleTvRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_layout,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        mPrefer = new LoginSharedPrefer(mContext);
        Log.e("homerv", "onBindViewHolder: check position in recyclerview "+position );


        if(position == mArrayHolder.getFeedData().size()-1){
            if(mHomeRvListener != null)
                mHomeRvListener.onReachingBottom(position);
        }


        if (holder instanceof ContestDataRvHolder) {

            putContestData(holder, position);

        } else if (holder instanceof SinglePostRvHolder) {
            putPostData(holder, position);
        } else if(holder instanceof SimpleTvRvHolder){
            putSimpleTv(holder,position);
        }


    }



    private void putPostData(RecyclerView.ViewHolder holder, int position) {
        SinglePostsRvData.setArrayHolder(mArrayHolder);
        SinglePostsRvData.putPostData(holder,position,mHomeRvListener,mContext,SinglePostsRvData.FEED_RV);
        //SimplePostRvData.HOME_RV);
    }

    private void putContestData(RecyclerView.ViewHolder holder, int position) {
        ContestRvData.setArrayHolder(mArrayHolder);
        ContestRvData.putContestData(holder,position,mHomeRvListener,mContext,mPrefer.getUsername(),SinglePostsRvData.FEED_RV);
        //SimplePostRvData.HOME_RV);
    }

    private void putSimpleTv(RecyclerView.ViewHolder holder, int position) {
        SimpleTvRvHolder body = (SimpleTvRvHolder) holder;
        PostData postData = mArrayHolder.getFeedData().get(position);
        SimpleTvData data = (SimpleTvData) postData;
        body.simpleTv.setText(data.getSimpleTv());

    }


    @Override
    public int getItemCount() {

        return mArrayHolder.getFeedData().size();
    }

    @Override
    public int getItemViewType(int position) {
        PostData dataChecker = mArrayHolder.getFeedData().get(position);
        if (dataChecker instanceof ContestData) {
            return PostView.SHOW_CONTEST;
        } else if (dataChecker instanceof SimplePostData) {
            return PostView.SHOW_SIMPLE_POST;
        } else if(dataChecker instanceof SimpleTvData){
            return PostView.SHOW_SIMPLE_TV;
        }

        Log.e("homeRv", "getItemViewType: nulll ---------------------------------" );
        return PostView.NULL_VIEW;
    }

    private class HomeProgressBar extends RecyclerView.ViewHolder {
        ProgressBar mProgressBar;

        public HomeProgressBar(View itemView) {
            super(itemView);
//
            mProgressBar = itemView.findViewById(R.id.show_pb);

        }
    }


    private class HomeTv extends RecyclerView.ViewHolder {
        TextView text;

        public HomeTv(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.simple_rv_text);
            text.setTextColor(mContext.getResources().getColor(R.color.lightSilver));
            text.setGravity(Gravity.CENTER_HORIZONTAL);
            ///text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }


}
