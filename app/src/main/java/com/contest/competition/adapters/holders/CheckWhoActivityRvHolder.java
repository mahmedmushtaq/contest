package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;

public class CheckWhoActivityRvHolder  extends RecyclerView.ViewHolder {
    TextView name,username;
    LinearLayout generalLayout;

    public CheckWhoActivityRvHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name_general);
        username = itemView.findViewById(R.id.username_general);
        generalLayout = itemView.findViewById(R.id.general_layout_lL);

    }

    public TextView getName() {
        return name;
    }

    public TextView getUsername() {
        return username;
    }

    public LinearLayout getGeneralLayout() {
        return generalLayout;
    }

}
