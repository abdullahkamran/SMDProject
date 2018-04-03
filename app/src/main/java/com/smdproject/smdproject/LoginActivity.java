package com.smdproject.smdproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{

    private GoogleApiClient gac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gac=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }


    public  void onClick(View v){
        switch (v.getId()) {
            case R.id.sign_in_button:
                Toast.makeText(this, "Google Sign in button clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.button3:
                Toast.makeText(this, "Facebook Sign in button clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.button4:
                Toast.makeText(this, "Mobile Sign in button clicked", Toast.LENGTH_LONG).show();
                break;
        }
//        Intent i=Auth.GoogleSignInApi.getSignInIntent(gac);
//        startActivityForResult(i,9001);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent){
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if(requestCode==9001){
            GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(dataIntent);
            if(result.isSuccess()){
                //handling result
                //moving to next activity
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
