package com.contest.competition.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.contest.competition.R;

public class CommentRvHolder extends RecyclerView.ViewHolder {
    ImageView commenterProfile,commentRemove;
    TextView commenterName,commentTime;
    EmojiTextView commenterBody;


    public CommentRvHolder(View itemView) {
        super(itemView);

        commenterProfile = itemView.findViewById(R.id.commenter_profilePic);
        commenterName = itemView.findViewById(R.id.commenter_name);
        commentTime = itemView.findViewById(R.id.comment_time);
        commenterBody = itemView.findViewById(R.id.comment_body);
        commentRemove = itemView.findViewById(R.id.commentRemove);


    }

    public ImageView getCommenterProfile() {
        return commenterProfile;
    }

    public ImageView getCommentRemove() {
        return commentRemove;
    }

    public TextView getCommenterName() {
        return commenterName;
    }

    public TextView getCommentTime() {
        return commentTime;
    }

    public EmojiTextView getCommenterBody() {
        return commenterBody;
    }
}
