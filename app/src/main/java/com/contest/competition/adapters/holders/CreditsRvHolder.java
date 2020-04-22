package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;

public class CreditsRvHolder extends RecyclerView.ViewHolder {

    TextView noOfCreditsEarn,price;
    LinearLayout container;

    public CreditsRvHolder(View itemView) {
        super(itemView);

        noOfCreditsEarn = itemView.findViewById(R.id.boostAlertRv_noOfReachedUsers);//here this used as how many credits earn
        price = itemView.findViewById(R.id.boostAlertRv_noOfBoostedCredits);//here this used as how many price are required to buy credits
        container = itemView.findViewById(R.id.boost_rv_layout_container);

    }

    public TextView getNoOfCreditsEarn() {
        return noOfCreditsEarn;
    }

    public TextView getPrice() {
        return price;
    }

    public LinearLayout getContainer() {
        return container;
    }
}
