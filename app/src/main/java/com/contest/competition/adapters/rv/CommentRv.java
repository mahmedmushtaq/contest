package com.contest.competition.adapters.rv;

import android.content.Context;
import androidx.annotation.NonNull;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.R;

import com.contest.competition.adapters.holders.CommentRvHolder;
import com.contest.competition.adapters.rvData.PutCommentRvData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CommentRvIistener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CommentData;

/**
 * Created by M Ahmed Mushtaq on 7/29/2018.
 */

public class CommentRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int HEADER = 0,BODY=1;
    public String noOfComments = "0";

    private Context mContext;

    private CommentRvIistener mCommentRvListener;
    private ArrayHolder mArrayHolder;


    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }

    public void setCommentRvListener(CommentRvIistener listener){
        this.mCommentRvListener = listener;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new CommentRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_rv_body,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof CommentRvHolder){

            if(position < mArrayHolder.getCommentData().size()) {

                CommentRvHolder body = (CommentRvHolder) holder;
                final CommentData data = mArrayHolder.getCommentData().get(position);

                PutCommentRvData rvData = new PutCommentRvData();
                rvData.setArrayHolder(mArrayHolder);
                rvData.setBody(body);
                rvData.setCommentData(data);
                rvData.setCommentRvListener(mCommentRvListener);
                rvData.setContext(mContext);
                rvData.setPosition(position);
                rvData.putData();


                }
            }


        }








    @Override
    public int getItemCount() {
        if(mArrayHolder != null) {
            return mArrayHolder.getCommentData().size();
        }else return 0;
    }







}
