package com.contest.competition.utils.activities.accountsettingsactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.contest.competition.R;

import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.UpdateDataFiles;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.activities.BaseToolbarActivity;
import com.contest.competition.utils.views.Toaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

public class UpdateAccountActivity extends BaseToolbarActivity{
    private Button updateNameOnly,updateEmailOnly,updatePasswordOnly;
    private Button updateBtn;
    private LoginSharedPrefer mPrefer;
    private String checkUpdateName = "no";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateNameOnly = findViewById(R.id.update_name_only);
        updateEmailOnly = findViewById(R.id.update_email_only);
        updatePasswordOnly = findViewById(R.id.update_password_only);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            //mean login through firebase
           updateEmailOnly.setClickable(false);
           updatePasswordOnly.setClickable(false);
           updateNameOnly.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   startActivity(new Intent(getBaseContext(),UpdateNameOnly.class));
               }
           });

        }else{


            updateNameOnly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(),UpdateNameOnly.class));
                }
            });
            updateEmailOnly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(),UpdateEmailOnly.class));
                }
            });
            updatePasswordOnly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getBaseContext(),UpdatePasswordOnly.class));
                }
            });
        }






     //   updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateDataCheck();
//            }
//        });



    }

    @Override
    protected int setLayout() {
        return R.layout.activity_update_account;
    }

    @Override
    protected String setActivityName() {
        return "Update Info";
    }

    @Override
    protected int useWhiteToolbar() {
        return 0;
    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//            case R.id.update_name_only:
//
//                startActivity(new Intent(getBaseContext(),UpdateNameOnlyReq.class));
//                break;
//            case R.id.update_email_only:
//                startActivity(new Intent(getBaseContext(),UpdateEmailOnlyReq.class));
//                break;
//
//            case R.id.update_password_only:
//                startActivity(new Intent(getBaseContext(),UpdatePasswordOnlyReq.class));
//                break;
//        }
//
//    }


//    private void updateData() {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("username",mPrefer.getUsername())
//                .add("full_name",updateName.getText().toString())
//                .add("email",updateEmail.getText().toString())
//                .add("password",updatePassword.getText().toString())
//                .build();
//        Request request = new Request.Builder().url(Addresses.getWebAddress()+ UpdateDataFiles.updateInfo).post(body).build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//              final String body = response.body().string();
//                Log.e("Tag", "onResponse: "+body );
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            JSONObject object = new JSONObject(body);
//                            String result = object.getString("result");
//                            String reason = object.getString("reason");
//                            if(Validator.validateWebResult(result)){
//                                mPrefer.setName(updateName.getText().toString());
//
//                                clearEditText();
//                                Toaster.setToaster(getBaseContext(),"Update data successfully.Please refresh a profile");
//                            }else{
//                                Toaster.setToaster(getBaseContext(),reason);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//        });
//
//    }


//    private void clearEditText(){
//        updateName.setText("");
//        updateEmail.setText("");
//        updatePassword.setText("");
//    }


}
