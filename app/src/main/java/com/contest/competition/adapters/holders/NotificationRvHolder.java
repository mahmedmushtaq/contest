package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class NotificationRvHolder extends RecyclerView.ViewHolder {
    CircularImageView notificationProfile;
    TextView notificationTv;
    LinearLayout mLinearLayout;
    public NotificationRvHolder(View itemView) {
        super(itemView);

        notificationProfile = itemView.findViewById(R.id.notification_profileIv);
        notificationTv = itemView.findViewById(R.id.notification_tv);
        mLinearLayout = itemView.findViewById(R.id.notification_rv_layout_Ll);


    }

    public CircularImageView getNotificationProfile() {
        return notificationProfile;
    }

    public TextView getNotificationTv() {
        return notificationTv;
    }

    public LinearLayout getLinearLayout() {
        return mLinearLayout;
    }
}
