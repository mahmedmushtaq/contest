package com.contest.competition.utils.fragments.checkactivityfragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contest.competition.R;

import java.util.ArrayList;

import com.contest.competition.adapters.rv.CheckWhoActivityRv;
import com.contest.competition.classes.interfaces.requestinterfaces.OnLoadingCheckSawAndVoteData;
import com.contest.competition.classes.interfaces.listenerCaller.CheckActivityRvListenerCaller;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.SawVoteCheckData;
import com.contest.competition.requests.data.CheckWhoVoteReq;

/**
 * Created by M Ahmed Mushtaq on 8/24/2018.
 */

public class WhoVotedFragment extends Fragment {
    String postId;
    private CheckWhoActivityRv rv;
    private Handler mHandler;
    private SwipeRefreshLayout mRefreshLayout;

    private ArrayHolder mArrayHolder;
    private CheckWhoVoteReq mReq;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        postId = getArguments().getString(WhoSawFragment.PASS_FRAGMENT_DATA);
        Log.e("WhoSawFragment", "onCreateView: contest id are in who saw fragment"+ postId);

        return inflater.inflate(R.layout.who_voted_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        RecyclerView voteRv = v.findViewById(R.id.votedFragment_rv);
         mArrayHolder = new ArrayHolder();
         mReq = new CheckWhoVoteReq();

         mArrayHolder.getCheckWhoVotedData().clear();
        rv = new CheckWhoActivityRv(CheckWhoActivityRv.VOTED_FRAGMENT);
        mHandler = new Handler(Looper.getMainLooper());
        mRefreshLayout = v.findViewById(R.id.votedFragment_swipe);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mArrayHolder != null) {
                    mArrayHolder.getCheckWhoVotedData().clear();
                }
                mReq.whoVotedData(getContext(),postId,"");
            }
        });

        CheckActivityRvListenerCaller rvListener = new CheckActivityRvListenerCaller(getContext(),postId);
        rvListener.setCheckWhoVoteReq(mReq);
        rv.setListener(rvListener);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        voteRv.setLayoutManager(manager);
        voteRv.setAdapter(rv);


        BackgroundAsnc asnc = new BackgroundAsnc();
        asnc.execute();

        mReq.setOnLoadingCheckSawAndVoteData(new OnLoadingCheckSawAndVoteData() {
            @Override
            public void load(ArrayList<SawVoteCheckData> sawVoteCheckData) {
                mRefreshLayout.setRefreshing(false);

                mArrayHolder.setCheckWhoVotedData(sawVoteCheckData);
                rv.setArrayHolder(mArrayHolder);
                rv.notifyDataSetChanged();
            }
        });
    }




    private class BackgroundAsnc extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            mReq.whoVotedData(getContext(),postId,"");
            return null;
        }
    }
}
