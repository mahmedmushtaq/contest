package com.contest.competition.utils.activities.forms;

import android.os.Handler;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.classes.Validator;
import com.contest.competition.requests.forms.ForgotPasswordProcessRequest;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText registeredEmail;
    private Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        registeredEmail = findViewById(R.id.registered_email_forgotPassword);
        confirmBtn = findViewById(R.id.fp_sendCodeBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRegisteredEmail();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(ForgotPasswordActivity.this);
    }

    private void sendRegisteredEmail() {
        if(Validator.isValidEmail(registeredEmail.getText().toString())){
            Dialoger.setAlerter(ForgotPasswordActivity.this,"Forgot Password","Please wait reset password code sending to your email");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                 //submit your forgot password form
                    ForgotPasswordProcessRequest.setForgotPasswordReq(registeredEmail.getText().toString(),ForgotPasswordActivity.this);


                }
            },1000);
        }else Toaster.setToaster(this,"Please enter your valid email address");
    }
}
