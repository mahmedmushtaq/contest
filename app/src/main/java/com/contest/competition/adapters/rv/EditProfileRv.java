package com.contest.competition.adapters.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.R;

import com.contest.competition.adapters.holders.EditProfileRvHolder;
import com.contest.competition.adapters.rvData.PutEditProfileData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.RecyclerViewItemListener;
import com.contest.competition.classes.models.ArrayHolder;

/**
 * Created by M Ahmed Mushtaq on 6/28/2018.
 */

public class EditProfileRv extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerViewItemListener mListener;

    private ArrayHolder mArrayHolder;

    public void setRecyclerViewItemListener(RecyclerViewItemListener listener){
        mListener = listener;
    }

    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  EditProfileRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_rv_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(position < mArrayHolder.getEditProfileData().size()) {
            String text = mArrayHolder.getEditProfileData().get(position);
            EditProfileRvHolder rvHolder = (EditProfileRvHolder) holder;

            PutEditProfileData putEditProfileData = new PutEditProfileData();

            putEditProfileData.setEditProfileRvHolder(rvHolder);
            putEditProfileData.setPosition(position);
            putEditProfileData.setText(text);
            putEditProfileData.setRecyclerViewItemListener(mListener);

            putEditProfileData.putData();
        }


    }

    @Override
    public int getItemCount() {
        if(mArrayHolder != null) {
            return mArrayHolder.getEditProfileData().size();
        }else return 0;
    }


}
