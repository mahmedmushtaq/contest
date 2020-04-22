package com.contest.competition.utils.fragments.dialogfragment;


import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contest.competition.R;

import com.contest.competition.adapters.rv.BoostRv;
import com.contest.competition.classes.interfaces.OnSuccessfulListener;
import com.contest.competition.classes.interfaces.listenerCaller.OnClickBoostRvListenerCaller;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.BoostData;
import com.contest.competition.requests.data.boostreq.LoadBoostData;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;

import java.util.ArrayList;


/**
 * Created by M Ahmed Mushtaq on 9/12/2018.
 */

public class BoostPostAlertFragment extends DialogFragment {

    private String postId;
    public static final String BOOST_POST_ID = "BOOST_POST_ID";
    private LoginSharedPrefer mPrefer;
    private RecyclerView recyclerView;
    private BoostRv rv;
    private ArrayHolder mArrayHolder;

    public static BoostPostAlertFragment newInstance(int postId) {
        BoostPostAlertFragment frag = new BoostPostAlertFragment();
        Bundle args = new Bundle();
        args.putString(BOOST_POST_ID, postId+"");
        frag.setArguments(args);
        return frag;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        postId = bundle.getString(BOOST_POST_ID);
        View view = inflater.inflate(R.layout.boost_alert_layout,container,false);
        TextView tv = view.findViewById(R.id.boost_alert_layout_tv);
        tv.setText("Post boosted to ");

        mPrefer = new LoginSharedPrefer(getActivity());
       // ArrayHolder.PostBoostData.clear();

          recyclerView = view.findViewById(R.id.boost_alert_rv);


          mArrayHolder = new ArrayHolder();


        rv = new BoostRv(BoostRv.POST_BOOST);
        rv.setArrayHolder(mArrayHolder);
        OnClickBoostRvListenerCaller onClickBoostRvListener = new OnClickBoostRvListenerCaller(getActivity(),mPrefer.getUsername(),Integer.parseInt(postId));
        onClickBoostRvListener.setOnSuccessfulListener(new OnSuccessfulListener() {
            @Override
            public void onSuccessful() {
                dismiss();
            }
        });


        rv.setBoostRvListener(onClickBoostRvListener);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),manager.getOrientation()));
        recyclerView.setAdapter(rv);

        loadBoostData();

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }


    private void loadBoostData(){
        LoadBoostData.setBoostListener(new LoadBoostData.LoadBoostDataListener() {
            @Override
            public void onRetrieve(final ArrayList<BoostData> boostData) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mArrayHolder.setPostBoostData(boostData);

                        rv.setArrayHolder(mArrayHolder);

                        rv.notifyDataSetChanged();
                    }
                });

            }
        });

        LoadBoostData.loadBoostData();
    }
}
