package com.contest.competition.utils.activities.launching;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.classes.signinwithdiffapi.SignInByGogle;
import com.contest.competition.utils.activities.forms.LoginActivity;
import com.contest.competition.utils.activities.forms.SignUpActivity;
import com.contest.competition.utils.views.Toaster;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;


public class FrontActivity extends AppCompatActivity {

    //   private Button startSigningUpBtn;
    private TextView signIn;
    private LinearLayout signUplL, googleSignUp,facebookSignUpLl;
    private static final String TAG = "GoogleActivity";


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        signIn = findViewById(R.id.already_account_tv_front);

        // emailTv = findViewById(R.id.introFullNameEt);m
        //startSigningUpBtn = findViewById(R.id.simple_sign_up);

         signUplL = (LinearLayout) findViewById(R.id.signUp_ll_front);
        googleSignUp = (LinearLayout) findViewById(R.id.sign_up_with_googleLL);


         facebookSignUpLl = (LinearLayout) findViewById(R.id.sign_up_with_fbLl);
         facebookSignUpLl.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toaster.setToaster(getBaseContext(),"This feature is currently disabled.Please login through google");
             }
         });






        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignInByGogle.signIn(FrontActivity.this);

            }
        });

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth

        mAuth =  FirebaseAuth.getInstance();

        SignInByGogle.setFireBaseSAuth(mAuth);
        SignInByGogle.googleSignInClient(mGoogleSignInClient);
        SignInByGogle.setContext(this);




        signUplL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));

                Animatoo.animateSlideLeft(FrontActivity.this);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));

                Animatoo.animateSlideLeft(FrontActivity.this);
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        SignInByGogle.onActivityResult(requestCode,data);

    }

    @Override
    public void onStart() {
        super.onStart();

    }




  //  update ui





}
