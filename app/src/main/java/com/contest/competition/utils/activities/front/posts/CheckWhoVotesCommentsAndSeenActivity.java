package com.contest.competition.utils.activities.front.posts;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.fragments.checkactivityfragment.WhoSawFragment;
import com.contest.competition.utils.fragments.checkactivityfragment.WhoVotedFragment;
import com.google.android.material.tabs.TabLayout;


public class CheckWhoVotesCommentsAndSeenActivity extends BaseToolbarActivity {
    private ViewPager checkViewPager;
    private String postId;

    private TabLayout mTabLayout;
    private ArrayHolder mArrayHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postId = "";
        postId = getIntent().getStringExtra(KeyStore.PASS_DATA);
        Log.e("postId", "onCreate: post id are = "+postId );
//        ArrayHolder.checkWhoSawData.clear();
//        ArrayHolder.checkWhoVotedData.clear();
        mArrayHolder = new ArrayHolder();

        Bundle bundle = new Bundle();
        bundle.putString(WhoSawFragment.PASS_FRAGMENT_DATA, postId);


        final WhoSawFragment seenFragment = new WhoSawFragment();
        seenFragment.setArguments(bundle);
        final WhoVotedFragment votedFragment = new WhoVotedFragment();
        votedFragment.setArguments(bundle);

        checkViewPager = findViewById(R.id.check_whoVotesCommentsAndSeenVp);
        mTabLayout = findViewById(R.id.check_whoVotesCommentsAndSeenTabLayout);
        checkViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position == 0) return votedFragment;
                else if(position == 1) return seenFragment;
                return null;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 0) return "Vote";
                else if(position == 1) return "Seen";
                else return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });


        mTabLayout.setupWithViewPager(checkViewPager);
    }

    @Override
    public void finish() {
        super.finish();

        Animatoo.animateSlideDown(CheckWhoVotesCommentsAndSeenActivity.this);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_check_who_votes_comments_and_seen;
    }

    @Override
    protected String setActivityName() {
        return "Check";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }
}
