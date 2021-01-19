package com.contest.competition.adapters.holders;


import androidx.annotation.NonNull;

import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.contest.competition.R;
import com.contest.competition.utils.views.SetFonts;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class SinglePostRvHolder extends RecyclerView.ViewHolder {
    public TextView name,dateTime,totalSeen,totalVotes,totalComments;
    public EmojiTextView postedText;
    public ImageView  voteBtn,commentBtn;
    public CircularImageView profile;
    public SimpleDraweeView postedImage;
    public ImageView   doubleClickPostedVote;//,postedImage;
    public Button features;
    public LinearLayout totalLl;
    public ProgressBar singlePost_progressBar;


    public SinglePostRvHolder(@NonNull View v) {
        super(v);
        profile = v.findViewById(R.id.single_postProfileUser);
        name = v.findViewById(R.id.single_postPostedByUser);
        dateTime = v.findViewById(R.id.single_postDataTime);
        totalSeen = v.findViewById(R.id.single_postTotalSeen);
        totalComments = v.findViewById(R.id.single_postTotalComments);
        totalVotes = v.findViewById(R.id.single_postTotalVotes);
        postedImage = v.findViewById(R.id.single_postPostedImage);
        postedText = v.findViewById(R.id.single_postPostedText);
        commentBtn = v.findViewById(R.id.single_postCommentIv);
        voteBtn = v.findViewById(R.id.single_postVoteIv);
        features = v.findViewById(R.id.single_postFeaturesBtn);
        totalLl = v.findViewById(R.id.single_postTotalLl);
       // singlePost_progressBar = v.findViewById(R.id.singlePost_progressBar);

        doubleClickPostedVote = v.findViewById(R.id.double_clickPostedVote);

        SetFonts.setMontserratLight(v.getContext(),postedText);





        doubleClickPostedVote.setVisibility(View.GONE);



    }
}
