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
import com.contest.competition.requests.data.CheckWhoSawReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;

/**
 * Created by M Ahmed Mushtaq on 8/24/2018.
 */

public class WhoSawFragment extends Fragment {
    private LoginSharedPrefer mPrefer;
    public static final String PASS_FRAGMENT_DATA = "PASS_FRAGMENT_DATA";
    private CheckWhoActivityRv rv;
    private Handler mHandler;
    String postId;
    private SwipeRefreshLayout mRefreshLayout;
    private ArrayHolder mArrayHolder;
    private CheckWhoSawReq mCheckWhoSawReq;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        postId = getArguments().getString(PASS_FRAGMENT_DATA);
        Log.e("WhoSawFragment", "onCreateView: contest id are in who saw fragment"+ postId);
        return inflater.inflate(R.layout.who_saw_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView sawRv = view.findViewById(R.id.sawFragment_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        sawRv.setLayoutManager(manager);

         mArrayHolder = new ArrayHolder();
         mArrayHolder.getCheckWhoSawData().clear();
         mCheckWhoSawReq = new CheckWhoSawReq();

         rv = new CheckWhoActivityRv(CheckWhoActivityRv.SAW_FRAGMENT);
         rv.setArrayHolder(mArrayHolder);
         mRefreshLayout = view.findViewById(R.id.sawFragment_swipe);
         mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 if(mArrayHolder != null) {
                     mArrayHolder.getCheckWhoSawData().clear();
                 }
                mCheckWhoSawReq.whoSawData(getActivity(),postId,"");
             }
         });
         mHandler = new Handler(Looper.getMainLooper());
         mCheckWhoSawReq.setOnLoadingCheckSawAndVoteData(new OnLoadingCheckSawAndVoteData() {
             @Override
             public void load(ArrayList<SawVoteCheckData> sawVoteCheckData) {
                 mRefreshLayout.setRefreshing(false);
                 mArrayHolder.setCheckWhoSawData(sawVoteCheckData);
                 rv.setArrayHolder(mArrayHolder);
                 rv.notifyDataSetChanged();
             }
         });

         CheckActivityRvListenerCaller listener = new CheckActivityRvListenerCaller(getContext(),postId);
         listener.setCheckWhoSawReq(mCheckWhoSawReq);
         rv.setListener(listener);


         mPrefer = new LoginSharedPrefer(getContext());
         sawRv.setAdapter(rv);

         BackgroundAsnc bg = new BackgroundAsnc();
         bg.execute();



    }





    private class BackgroundAsnc extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            mCheckWhoSawReq.whoSawData(getActivity(),postId,"");
            return null;
        }
    }
}
