package com.contest.competition.utils.activities.accountsettingsactivities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.contest.competition.R;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.UpdateDataFiles;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FollowerSettingsActivity extends AppCompatActivity {
    private Switch mFollowerSettingSwitch;
    private TextView mShowTv;
    private LoginSharedPrefer mPrefer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_settings);
        Toolbar toolbar =  findViewById(R.id.followerSettings_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);


        toolbar.setNavigationIcon(R.drawable.back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mFollowerSettingSwitch = findViewById(R.id.followerSetting_switch);
        mShowTv = findViewById(R.id.followerSetting_showTv);


        mPrefer = new LoginSharedPrefer(this);

        loadFollowerSettings();

        mFollowerSettingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateValue("public");
                }else updateValue("private");
            }
        });


    }

    private void updateValue(final String privacy){
        Log.e(KeyStore.TAG, "updateValue: privacy value"+privacy );


        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username",mPrefer.getUsername()).add("privacy",privacy).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+UpdateDataFiles.updateFollowerSettingsFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e(KeyStore.TAG, "onResponse: "+body );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            if(Validator.validateWebResult(result)){
                                if(privacy.contains("public")){
                                    mShowTv.setText("Everyone can follow me without my permission");
                                }else{
                                    mShowTv.setText("Everyone can follow me with my permission");
                                }
                            }else{
                                Toaster.setToaster(getBaseContext(),reason);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void loadFollowerSettings(){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("username", mPrefer.getUsername()).build();
        Request request  = new Request.Builder().url(Addresses.getWebAddress()+ UpdateDataFiles.loadFollowerSettingsFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String body = response.body().string();
                Log.e(KeyStore.TAG, "onResponse: "+body );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject object = new JSONObject(body);
                                String result = object.getString("result");
                                String reason = object.getString("reason");
                                if(Validator.validateWebResult(result)){
                                   String privacy = object.getString("privacy");
                                   if(privacy.contains("public")){
                                       setChecking(true,true,true);
                                       mShowTv.setText("Everyone can follow me without my permission");
                                   }else{
                                      setChecking(false,true,true);
                                       mShowTv.setText("Everyone can follow me with my permission");
                                   }
                                }else{
                                    Toaster.setToaster(getBaseContext(),reason);
                                    mShowTv.setText("..");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });





            }
        });
    }

    private void setChecking(boolean checking,boolean enable,boolean clickable){
        mFollowerSettingSwitch.setChecked(checking);
        mFollowerSettingSwitch.setEnabled(enable);
        mFollowerSettingSwitch.setClickable(clickable);
    }
}
