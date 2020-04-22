package com.contest.competition.utils.activities.forms;

import android.content.Intent;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.classes.Network;
import com.contest.competition.classes.Validator;
import com.contest.competition.requests.forms.LoginProcessRequest;
import com.contest.competition.storage.sharedpreferences.LoginSharedPrefer;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Snack;


import com.contest.competition.R;


public class LoginActivity extends AppCompatActivity {

    private TextView forgotPassword;//,needAnAccount,loginTv;
    private EditText email,password;
    private Button login;
    private LinearLayout container;
    private CheckBox rememberMeCheckBox;
    private String TAG = LoginActivity.class.getSimpleName();



    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setCode();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Animatoo.animateSlideRight(LoginActivity.this);
    }

    private void setCode(){
        setContentView(R.layout.login_layout);


        forgotPassword = findViewById(R.id.forgotPassword_tv);


        login = findViewById(R.id.loginBtn2);
        email = findViewById(R.id.email_login_et);
        password = findViewById(R.id.password_login_et);

        container = findViewById(R.id.loginContainer);
        rememberMeCheckBox = findViewById(R.id.remember_me);

        LoginSharedPrefer sharedPrefer = new LoginSharedPrefer(this);


        if(sharedPrefer.getSharedEmail() != null && sharedPrefer.getPassword() != null && sharedPrefer.getRememberMe()){

            email.setText(sharedPrefer.getSharedEmail());
            password.setText(sharedPrefer.getPassword());
            rememberMeCheckBox.setChecked(sharedPrefer.getRememberMe());
        }




      forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getBaseContext(),ForgotPasswordActivity.class));
               Animatoo.animateSlideUp(LoginActivity.this);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ShareApp.shareApplication(getApplicationContext());
                String em = email.getText().toString();
                String pas =   password.getText().toString();
                if(!Validator.isValidEmail(em)){
                    Snack.topSnackBar(container, "Email address is incorrect");
                } else if(pas.length() <= 4 || pas.length() >= 20 ) {
                    Snack.topSnackBar(container, "Password must be between 4 and 20 character");
                }else {
                    if(Network.isAvailable(getBaseContext()))
                       setLogin(em,pas);

                    else Snack.topSnackBar(container, "Network is not available.");
                }

            }
        });


    }

    private void setLogin(final String email, final String password){
        Dialoger.setAlerter(this,"Logging","Please wait we are signing in you");

     //  Dialoger.setDialog(LoginActivity.this,"Login","Please wait your are logging in");
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               //submit your login form

                LoginProcessRequest.setRememberMe(rememberMeCheckBox.isChecked());
                LoginProcessRequest.setLoginProcessReq(email,password,LoginActivity.this);

            }
        },500);


    }













}
