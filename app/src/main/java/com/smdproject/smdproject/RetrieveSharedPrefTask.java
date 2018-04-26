package com.smdproject.smdproject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ishrat.fatima on 3/7/2018.
 */

    public class RetrieveSharedPrefTask extends AsyncTask<Void,Void,Void> {
        SharedPreferences sp;
        EditText txtemail;
        EditText txtpassword;
        String e = null;
        String p = null;

    public RetrieveSharedPrefTask(SharedPreferences sharedPref, EditText email, EditText pwd) {
        txtemail = email;
        txtpassword = pwd;
        sp = sharedPref;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        e = sp.getString("Email",null);
        p = sp.getString("Pwd",null);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(e!= null)
            txtemail.setText(e);
        if (p != null)
            txtpassword.setText(p);
    }
}
