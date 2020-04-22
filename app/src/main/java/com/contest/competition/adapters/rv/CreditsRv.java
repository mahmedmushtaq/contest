package com.contest.competition.adapters.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.contest.competition.R;

import com.contest.competition.adapters.holders.CreditsRvHolder;
import com.contest.competition.adapters.rvData.PutCreditsData;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CreditsRvIistener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CreditsData;

/**
 * Created by M Ahmed Mushtaq on 9/20/2018.
 */

public class CreditsRv extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private CreditsRvIistener mCreditsRvListener;
    private ArrayHolder mArrayHolder;

    public void setArrayHolder(ArrayHolder arrayHolder){
        mArrayHolder = arrayHolder;
    }



    public void setCreditsRvListener(CreditsRvIistener listener){
        this.mCreditsRvListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreditsRvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.boost_alert_rv_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder recylerViewHolder, final int position) {
        if(position < mArrayHolder.getCreditsData().size()) {
            final CreditsData data = mArrayHolder.getCreditsData().get(position);
                  CreditsRvHolder rvHolder = (CreditsRvHolder) recylerViewHolder;
            PutCreditsData putCreditsData = new PutCreditsData();
            putCreditsData.setCreditsData(data);
            putCreditsData.setCreditsRvHolder(rvHolder);
            putCreditsData.setPosition(position);
            putCreditsData.setCreditsRvInterface(mCreditsRvListener);
            putCreditsData.putData();


        }




    }

    @Override
    public int getItemCount() {
        return mArrayHolder.getCreditsData().size();
    }




}
