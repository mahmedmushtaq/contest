package com.contest.competition.utils.activities.advertisements;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.contest.competition.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.contest.competition.adapters.rv.CreditsRv;
import com.contest.competition.classes.interfaces.recyclerviewinterfaces.CreditsRvIistener;
import com.contest.competition.classes.models.ArrayHolder;
import com.contest.competition.classes.models.CreditsData;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.ConsoleFiles;
import com.contest.competition.requests.data.creditsreq.AddMoreCreditsReq;
import com.contest.competition.requests.data.creditsreq.BuyCreditsReq;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

public class BuyCreditsActivity extends BaseToolbarActivity implements BillingProcessor.IBillingHandler{
    private RecyclerView mRecyclerView;
    private BillingProcessor bp;
    private CreditsRv rv;
    private LoginSharedPrefer mPrefer;
    private CreditsData mCreditsData;
    private ArrayHolder mArrayHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayHolder.creditsData.clear();
        mArrayHolder = new ArrayHolder();
        mPrefer = new LoginSharedPrefer(this);
        mRecyclerView = findViewById(R.id.buyCredits_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this,manager.getOrientation());
         rv = new CreditsRv();

         rv.setArrayHolder(mArrayHolder);

        Dialoger.setDialog(BuyCreditsActivity.this,"Loading","Please wait data is loading");

        loadLicenseKeyFromServer();


        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(rv);

        retrieveCreditsData();
        rv.setCreditsRvListener(new CreditsRvIistener() {
            @Override
            public void onClick(CreditsData data, int position) {
                mCreditsData = null;//to remove previously initialize data
                mCreditsData = data;
                bp.purchase(BuyCreditsActivity.this,data.getProductId());
                bp.consumePurchase(data.getProductId());

            }
        });







    }

    private void retrieveCreditsData() {
        BuyCreditsReq.setBuyCreditsListener(new BuyCreditsReq.BuyCreditReqListener() {
            @Override
            public void onRetrieve(final ArrayList<CreditsData> creditsData) {
                runOnUiThread(
                        new Runnable() {
                    @Override
                    public void run() {
                        mArrayHolder.setCreditsData(creditsData);
                        rv.setArrayHolder(mArrayHolder);
                        rv.notifyDataSetChanged();
                    }
                });
            }
        });

        BuyCreditsReq.loadData();
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_buy_credits;
    }

    @Override
    protected String setActivityName() {
        return "Buy Credits";
    }

    @Override
    protected int useWhiteToolbar() {
        return WHITE_TOOLBAR;
    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
       addMoreCredits(mCreditsData);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
           Toaster.setToaster(getBaseContext(),"Error occurred during purchasing "+mCreditsData.getNoOfCreditsEarn()+" credits");

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void addMoreCredits(CreditsData data){
        Dialoger.setDialog(BuyCreditsActivity.this,"Purchasing","Please wait..Don't quit this process");
        AddMoreCreditsReq.addMoreCredits(BuyCreditsActivity.this,data,mPrefer.getUsername());

    }

    private void loadLicenseKeyFromServer(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ ConsoleFiles.LicenseKeyFile).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                try {
                    JSONObject object = new JSONObject(body);
                    final String license_key  = object.getString("license_key");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dialoger.dismissDialog();
                            doRestProcess(license_key);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doRestProcess(String license_key) {
        Log.e("key", "doRestProcess: key are = "+license_key );

        bp = new BillingProcessor(BuyCreditsActivity.this,license_key,this);
        bp.initialize();
    }

}
