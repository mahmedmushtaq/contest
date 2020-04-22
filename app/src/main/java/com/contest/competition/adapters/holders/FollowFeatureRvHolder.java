package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class FollowFeatureRvHolder extends RecyclerView.ViewHolder {
    CircularImageView profile;
    TextView name,username;
    LinearLayout namAndUsernameContainerLl;

    public FollowFeatureRvHolder(View itemView) {
        super(itemView);

        profile = itemView.findViewById(R.id.search_profilePic);
        name = itemView.findViewById(R.id.search_name);
        username = itemView.findViewById(R.id.search_username);
        namAndUsernameContainerLl = itemView.findViewById(R.id.userNameAndName_searchLl);

    }

    public CircularImageView getProfile() {
        return profile;
    }

    public TextView getName() {
        return name;
    }

    public TextView getUsername() {
        return username;
    }

    public LinearLayout getNamAndUsernameContainerLl() {
        return namAndUsernameContainerLl;
    }
}
