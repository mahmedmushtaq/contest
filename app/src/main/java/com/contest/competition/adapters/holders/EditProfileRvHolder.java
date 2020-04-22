package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;

public class EditProfileRvHolder extends RecyclerView.ViewHolder {
    TextView text;
    public EditProfileRvHolder(View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.simple_rv_text);
    }

    public TextView getText() {
        return text;
    }
}
