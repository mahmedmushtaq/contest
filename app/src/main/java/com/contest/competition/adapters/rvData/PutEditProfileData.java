package com.contest.competition.adapters.rvData;

import android.view.View;

import com.contest.competition.adapters.holders.EditProfileRvHolder;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.RecyclerViewItemListener;

public class PutEditProfileData {
    private EditProfileRvHolder mEditProfileRvHolder;
    private RecyclerViewItemListener mRecyclerViewItemListener;
    private String text;
    private  int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRecyclerViewItemListener(RecyclerViewItemListener recyclerViewItemListener) {
        mRecyclerViewItemListener = recyclerViewItemListener;
    }

    public void setEditProfileRvHolder(EditProfileRvHolder editProfileRvHolder) {
        mEditProfileRvHolder = editProfileRvHolder;
    }

    public void putData(){
        mEditProfileRvHolder.getText().setText(text);
        mEditProfileRvHolder.getText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewItemListener.onItemClickListener(position);
            }
        });

    }
}
