package com.contest.competition.utils.activities.forms;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.contest.competition.R;

import com.contest.competition.classes.KeyStore;
import com.contest.competition.classes.Validator;
import com.contest.competition.classes.webfiles.Addresses;
import com.contest.competition.classes.webfiles.FormsFiles;
import com.contest.competition.utils.views.Dialoger;
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

public class SetNewPasswordActivity extends AppCompatActivity {
    public static final String OLD_PASSWORD = "OLD_PASSWORD";
    String oldPassword,email;
    private EditText newPassword;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);
        oldPassword = getIntent().getStringExtra(OLD_PASSWORD);
        email = getIntent().getStringExtra(KeyStore.PASS_EMAIL_FOR_CFRM);

        newPassword = findViewById(R.id.passwordSetUpActivity_sign_up_et);
        confirmBtn = findViewById(R.id.setNewPassword_Btn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewPassword();
            }
        });


    }

    private void setNewPassword() {
        if(Validator.isValidPassword(newPassword.getText().toString())){
            setPassword();
        }else{
            Toaster.setToaster(getBaseContext(),"Password length b/w 4 and 20 character");
        }
    }

    private void setPassword() {
        Dialoger.setAlerter(SetNewPasswordActivity.this,"Password","Please wait old password is updating");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("email",email)
                .add("old_password",oldPassword)
                .add("new_password",newPassword.getText().toString())
                .build();

        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FormsFiles.setNewPasswordFile).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               final String body = response.body().string();
                Log.e("new password", "onResponse: "+body );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Dialoger.hideAlerter();
                            JSONObject object = new JSONObject(body);
                            String result = object.getString("result");
                            String reason = object.getString("reason");
                            Toaster.setToaster(getBaseContext(),reason);
                            if(Validator.validateWebResult(result)){
                                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
