package com.contest.competition.utils.activities.forms;

import android.os.Handler;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.contest.competition.R;

import com.contest.competition.classes.Network;
import com.contest.competition.classes.Validator;
import com.contest.competition.requests.forms.SignUpProcessRequests;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.NonSwipeAbleViewPager;
import com.contest.competition.utils.views.Snack;


public class SignUpActivity extends AppCompatActivity {
   // private NonSwipeAbleViewPager mNonSwipeAbleViewPager;
    private Button signUpBtn;
    private EditText mName, mEmail, mPassword, mConfirmPassword;
    private LinearLayout container;
    private CheckBox agreeToTermsAndConditions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

       // mNonSwipeAbleViewPager = findViewById(R.id.signUp_viewPager);
        signUpBtn = findViewById(R.id.signUpBtn);
        container = findViewById(R.id.sign_up_layout_container);
        mName = findViewById(R.id.full_name_signUpEt);
        mEmail = findViewById(R.id.email_signUpEt);
        mPassword = findViewById(R.id.password_signUpEt);
        mConfirmPassword = findViewById(R.id.repeatPassword_signUpEt);
        agreeToTermsAndConditions = findViewById(R.id.checkbox_signUp);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (agreeToTermsAndConditions.isChecked()) {

                    signUpProcess(mName.getText().toString()
                            , mEmail.getText().toString()
                            , mPassword.getText().toString()
                            , mConfirmPassword.getText().toString()
                    );

                }else{
                    Snack.topSnackBar(container,"It is necessary to agree with our terms ");
                }


            }
        });


//        final NameFragment name = new NameFragment();
//        final EmailFragment email = new EmailFragment();
//        final PasswordFragment password = new PasswordFragment();
//        final ConfirmPasswordFragment confirmPassword = new ConfirmPasswordFragment();
//
//        mNonSwipeAbleViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                if(position == 0) return name;
//                else if(position == 1) return email;
//                else if(position == 2) return password;
//                else if(position == 3) return confirmPassword;
//                else return null;
//            }
//
//            @Override
//            public int getCount() {
//                return 4;
//            }
//        });
//
//
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToNextFramgent();
//
//            }
//        });


    }

    private void signUpProcess(final String name, final String email, final String password, String repeatPass) {

       if(name == null || name.length() < 2 || name.length() > 25)
           Snack.topSnackBar(container,"Please enter your full name ");
       else if(!Validator.isValidEmail(email))
           Snack.topSnackBar(container,"Please enter your valid email ");
       else if(password == null || password.length() < 5)
           Snack.topSnackBar(container,"Your password must be greater than 4 digits");
       else if(!password.contentEquals(repeatPass))
           Snack.topSnackBar(container,"Your password and repeat password must be same");
       else {
           if(Network.isAvailable(this)){
               Dialoger.setAlerter(this,"Signing up","Please wait we are created your account");
               SignUpProcessRequests.sendReq(email,password,name,this);
           }
       }



    }











//    private void goToNextFramgent() {
//        if (mNonSwipeAbleViewPager.getCurrentItem() == 0) {
//            if (mName != null)
//                mNonSwipeAbleViewPager.setCurrentItem(1);
//            else Snack.topSnackBar(container, "Enter your name between 5 and 20 character");
//
//        } else if (mNonSwipeAbleViewPager.getCurrentItem() == 1) {
//
//            if (mEmail != null)
//                mNonSwipeAbleViewPager.setCurrentItem(2);
//            else Snack.topSnackBar(container, "Enter your correct Email Addresses");
//        } else if (mNonSwipeAbleViewPager.getCurrentItem() == 2) {
//
//            if (mPassword != null) {
//                mNonSwipeAbleViewPager.setCurrentItem(3);
//                // nextBtn.setText("Sign Up");
//            } else Snack.topSnackBar(container, "Password length is short");
//        } else if (mNonSwipeAbleViewPager.getCurrentItem() == 3) {
//
//            if (mConfirmPassword != null && mPassword.equals(mConfirmPassword)) {
//                // Toaster.setToaster(this, "Sign Up");
//                if (Network.isAvailable(this)) {
//                    Dialoger.setDialog(SignUpActivity.this, "Signing up", "Please wait your account is creating now");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            //submit your form
//                            //s  SignUpProcessRequests.sendReq(mEmail,mPassword,mName.trim(),SignUpActivity.this);
//
//                        }
//                    }, 500);
//                } else {
//                    Snack.topSnackBar(container, "Network is not available");
//                }
//
//
//            } else Snack.topSnackBar(container, "Password does not match");
//        }
//    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(SignUpActivity.this);
    }

    //    @Override
//    public void onBackPressed() {
//        if(mNonSwipeAbleViewPager.getCurrentItem() == 3){
//            mNonSwipeAbleViewPager.setCurrentItem(2);
//            nextBtn.setText("Next");
//
//        }
//        else if(mNonSwipeAbleViewPager.getCurrentItem() == 2) {
//
//            mNonSwipeAbleViewPager.setCurrentItem(1);
//        }
//        else if(mNonSwipeAbleViewPager.getCurrentItem() == 1){
//
//            mNonSwipeAbleViewPager.setCurrentItem(0);
//        }
//        else if(mNonSwipeAbleViewPager.getCurrentItem() == 0){
//
//            finish();
//        }
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//
//        Animatoo.animateSlideRight(SignUpActivity.this);
//
//    }
//
//    @Override
//    public void onWritingName(String name) {
//        mName = name;
//    }
//
//    @Override
//    public void onWritingEmail(String email) {
//        mEmail = email;
//    }
//
//    @Override
//    public void onWritingPassword(String password) {
//        mPassword = password;
//    }
//
//    @Override
//    public void onWritingConfirmPassword(String cnPassword) {
//        mConfirmPassword = cnPassword;
//    }


//    @Override
//    protected int setLayout() {
//        return R.layout.activity_sign_up;
//    }
//
//    @Override
//    protected String setActivityName() {
//        return "Sign Up";
//    }
//
//    @Override
//    protected int useWhiteToolbar() {
//        return 0;
//    }


}
