package com.contest.competition.utils.activities.forms;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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

public class ConfirmEmailForFpActivity extends AppCompatActivity {
    //Fp forgot password

    private EditText cnfrmCode;
    private Button cnfrmBtn;
    private TextView resendCnfrmCodeTv;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);

        cnfrmCode = findViewById(R.id.email_cnfrm_code);
        cnfrmBtn = findViewById(R.id.cnfrmEmail_btn);
        resendCnfrmCodeTv = findViewById(R.id.sendAgain_cnfrmEmailCodeTv);

        email = getIntent().getStringExtra(KeyStore.PASS_EMAIL_FOR_CFRM);

        cnfrmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnfrm();
            }
        });

        resendCnfrmCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                againSendConfirmEmail();
            }
        });

//        setContentView(R.layout.activity_confirm_email_for_fp);
//
//
//        cnfrmCode = findViewById(R.id.email_cnfrm_codeForFp);
//        cnfrmBtn = findViewById(R.id.cnfrmEmailForFp_btn);
//        resendCnfrmCodeTv = findViewById(R.id.sendAgain_cnfrmEmailForFpCodeTv);
//
//        email = getIntent().getStringExtra(KeyStore.PASS_EMAIL_FOR_CFRM);
//
//        cnfrmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cnfrm();
//            }
//        });
//
//        resendCnfrmCodeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                againSendConfirmEmail();
//            }
//        });



    }

    private void againSendConfirmEmail(){
        Dialoger.setDialog(ConfirmEmailForFpActivity.this,"Confirm email","Please wait.Again email is sending");
        OkHttpClient client = new OkHttpClient();
        final RequestBody body = new FormBody.Builder().add("email",email).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FormsFiles.againSendCodeForFp).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e("again for fp", "onResponse: "+body );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Dialoger.dismissDialog();
                            JSONObject object = new JSONObject(body);
                            String reason = object.getString("reason");
                            Toaster.setToaster(getBaseContext(),reason);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

    private void cnfrm() {
        if(cnfrmCode.getText().toString().length() == 7){
            cnfrmEmail();
        }else{
            Toaster.setToaster(getBaseContext(),"Enter correct code");
        }
    }

    private void cnfrmEmail() {
        Dialoger.setAlerter(ConfirmEmailForFpActivity.this,"Confirm email","Please wait process is doing");
        OkHttpClient client = new OkHttpClient();
//        check = update mean update users that email is check
        RequestBody body = new FormBody.Builder().add("email",email).add("code",cnfrmCode.getText().toString()).build();
        Request request = new Request.Builder().url(Addresses.getWebAddress()+ FormsFiles.confirmEmailForFp).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                Log.e("cnfirm for fp email", "onResponse: "+body );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Dialoger.hideAlerter();
                            JSONObject object = new JSONObject(body);
                            String reason = object.getString("reason");
                            Toaster.setToaster(getBaseContext(),reason);
                            String result = object.getString("result");

                            if(Validator.validateWebResult(result)){
                                String oldPassword = object.getString("old_password");
                                Intent intent = new Intent(getBaseContext(),SetNewPasswordActivity.class);
                                intent.putExtra(SetNewPasswordActivity.OLD_PASSWORD,oldPassword);
                                intent.putExtra(KeyStore.PASS_EMAIL_FOR_CFRM,email);
                                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(ConfirmEmailForFpActivity.this);
    }
}
