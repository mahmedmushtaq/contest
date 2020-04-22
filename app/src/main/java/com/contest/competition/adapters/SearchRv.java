package com.contest.competition.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.contest.competition.classes.HighLighter;
import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.interfaces.SearchRvListener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SearchData;
import com.contest.competition.classes.webfiles.Addresses;

import com.contest.competition.R;
import com.contest.competition.utils.views.FollowBtnStyle;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by M Ahmed Mushtaq on 6/16/2018.
 */

public class SearchRv extends RecyclerView.Adapter<SearchRv.Holder> {

    private SearchRvListener mListener;
    private Context mContext;

    private Holder mHolder;
    private ArrayHolder mArrayHolder;

    public void setListener(SearchRvListener listener){
        this.mListener = listener;
    }

    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rv_new_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final SearchData data  = mArrayHolder.getSearchDataArrayList().get(position);
        mHolder = holder;

        if(position == mArrayHolder.getSearchDataArrayList().size()-1){
            if(mListener != null)
            mListener.onViewEndRvItem(data,position);
        }

        Glide.with(mContext).load(Addresses.getWebAddress()+data.getProfilePic()).into(holder.mCircularImageView);
        holder.mName.setText(HighLighter.capitalEachWordFirstLetter(data.getName()));

        FollowBtnStyle.setFollowBtn(holder.followBtn,mContext);
       holder.followBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mListener != null)
               mListener.onAddFollowListener(data,holder.followBtn);
           }
       });

//        https://github.com/skydoves/PowerMenu


        holder.mCircularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.viewProfileListener(data);
            }
        });

        holder.searchUserOpenProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null)
                mListener.viewProfileListener(data);
            }
        });

//        if(data.getUserPrivacy().contains("private")){
//            holder.addFollow.setVisibility(View.GONE);
//            holder.privateAccount.setVisibility(View.VISIBLE);
//        }



        Log.e(KeyStore.TAG, "onBindViewHolder: "+ mArrayHolder.getSearchDataArrayList().size() );

        holder.mUserName.setText(data.getUsername());


    }




    @Override
    public int getItemCount() {
        if(mArrayHolder != null)
        return mArrayHolder.getSearchDataArrayList().size();
        else return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircularImageView mCircularImageView;
        TextView mName,mUserName;
        LinearLayout searchUserOpenProfile;
        Button followBtn;
        public Holder(View itemView) {
            super(itemView);

            mCircularImageView = itemView.findViewById(R.id.search_userProfilePic);
            mName = itemView.findViewById(R.id.search_userName);
            mUserName = itemView.findViewById(R.id.search_userUserName);
            followBtn = itemView.findViewById(R.id.search_userFollowBtn);
            searchUserOpenProfile = itemView.findViewById(R.id.search_userOpenProfile);



        }
    }



}
