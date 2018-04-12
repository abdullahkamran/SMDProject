package com.smdproject.smdproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();//firebase

        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.sign_in_button).setOnClickListener(this);//google sign in button

        findViewById(R.id.button4).setOnClickListener(this);

        //google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);//facebook login button
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookSignIn", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FacebookSignIn", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FacebookSignIn", "facebook:onError", error);
                // ...
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null){
//            //  updateUI(currentUser);
//            //make intent
//            //start activity
//            //finish();
//        }
//    }

    public  void onClick(View v){
        switch (v.getId()) {
            case R.id.sign_in_button:
                //Toast.makeText(this, "Google Sign in button clicked", Toast.LENGTH_LONG).show();
                Intent i=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(i,9001);
                break;
            case R.id.button4:
                Intent ii=new Intent(this,MobileAuth.class);
                startActivity(ii);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent){
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if(requestCode==9001){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(dataIntent);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("GoogleSignIn", "Google sign in failed", e);
                // ...
            }
        }
        else{
            mCallbackManager.onActivityResult(requestCode, resultCode, dataIntent);
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("GoogleSignIn", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("GoogleSignIn", "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent googleIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(googleIntent);
                    finish();
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("GoogleSignIn", "signInWithCredential:failure", task.getException());
                    //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                }

                // [START_EXCLUDE]
                //hideProgressDialog();
                        // [END_EXCLUDE]
                }
            });
    }
    // [END auth_with_google]

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FacebookSignIn", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FacebookSignIn", "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent googleIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(googleIntent);
                    finish();
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("FacebookSignIn", "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
                        // ...
                }
            });
    }
}
