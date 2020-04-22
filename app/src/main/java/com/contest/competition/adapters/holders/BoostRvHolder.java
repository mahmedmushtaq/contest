package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;

public class BoostRvHolder  extends RecyclerView.ViewHolder {
    TextView noOfReachedUsers,noOfCredits;
    LinearLayout boostContainer;

    public BoostRvHolder(View itemView) {
        super(itemView);

        noOfReachedUsers = itemView.findViewById(R.id.boostAlertRv_noOfReachedUsers);
        noOfCredits = itemView.findViewById(R.id.boostAlertRv_noOfBoostedCredits);
        boostContainer = itemView.findViewById(R.id.boost_rv_layout_container);

    }

    public TextView getNoOfCredits() {
        return noOfCredits;
    }

    public TextView getNoOfReachedUsers() {
        return noOfReachedUsers;
    }

    public LinearLayout getBoostContainer() {
        return boostContainer;
    }
}
