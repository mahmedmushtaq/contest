package com.contest.competition.adapters.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.contest.competition.R;

/**
 * Created by M Ahmed Mushtaq on 9/8/2018.
 */

public class SimpleTvRvHolder extends RecyclerView.ViewHolder {
    public TextView simpleTv;
    public SimpleTvRvHolder(View itemView) {
        super(itemView);
        simpleTv = itemView.findViewById(R.id.simple_rv_text);

    }
}
