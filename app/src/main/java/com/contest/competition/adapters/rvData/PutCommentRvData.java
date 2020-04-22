package com.contest.competition.adapters.rvData;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.contest.competition.adapters.holders.CommentRvHolder;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CommentRvIistener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CommentData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;

public class PutCommentRvData {
    private CommentData data;
    private CommentRvHolder body;
    private int position;
    private CommentRvIistener mCommentRvListener;
    private Context mContext;
    private ArrayHolder mArrayHolder;

    public void setArrayHolder(ArrayHolder arrayHolder) {
        mArrayHolder = arrayHolder;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setCommentRvListener(CommentRvIistener commentRvListener) {
        mCommentRvListener = commentRvListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBody(CommentRvHolder body) {
        this.body = body;
    }

    public void setCommentData(CommentData commentData) {
        data = commentData;
    }


    public void putData() {
        if (data.getCommenterUsername() != null) {

            Glide.with(mContext).load(Addresses.getWebAddress() + data.getCommenterProfilePic()).into(body.getCommenterProfile());
            body.getCommenterBody().setText(data.getCommentBody());
            body.getCommenterName().setText(data.getCommenterName());
            body.getCommentTime().setText(data.getCommentTime());

            LoginSharedPrefer prefer = new LoginSharedPrefer(mContext);
            if (data.getCommenterUsername().equals(prefer.getUsername())) {
                body.getCommentRemove().setVisibility(View.VISIBLE);
            } else {
                body.getCommentRemove().setVisibility(View.GONE);
            }

            body.getCommentRemove().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCommentRvListener != null)
                        mCommentRvListener.onRemoveComments(data, position);
                }
            });

            if (position == mArrayHolder.getCommentData().size() - 1) {
                if (mCommentRvListener != null)
                    mCommentRvListener.loadMoreComments(data, position);
            }

            body.getCommenterProfile().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCommentRvListener != null)
                        mCommentRvListener.onClickProfile(data);
                }
            });
        }
    }



}
