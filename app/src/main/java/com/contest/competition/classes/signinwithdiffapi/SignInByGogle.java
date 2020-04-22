package com.contest.competition.classes.signinwithdiffapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.contest.competition.requests.forms.CreateUserByGoogleSignIn;
import com.contest.competition.utils.views.Dialoger;
import com.contest.competition.utils.views.Toaster;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInByGogle {
    private static String TAG = SignInByGogle.class.toString();

    // [START declare_auth]
    private static FirebaseAuth mAuth;
    // [END declare_auth]

    private static GoogleSignInClient mGoogleSignInClient;
    private static Context mContext;
    private static final int RC_SIGN_IN = 9001;

    public static void setFireBaseSAuth(FirebaseAuth auth){
        mAuth = auth;
    }

    public static void setContext(Context context){
        mContext = context;
    }


    public static void googleSignInClient(GoogleSignInClient client){
        mGoogleSignInClient = client;
    }


    public static void onActivityResult(int requestCode,Intent data){
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...

            }
        }
    }

    private static void firebaseAuthWithGoogle( GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            Dialoger.setAlerter(((Activity)mContext),"Signing In","Please wait we are signing in you");
                            CreateUserByGoogleSignIn.sendReq(currentUser.getEmail(),"12345",currentUser.getDisplayName()
                                    ,currentUser.getUid(),mContext,mGoogleSignInClient
                            );

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toaster.setToaster(mContext,"Sign in failed");
                            //  updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public static  void signIn(Activity activity) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}
