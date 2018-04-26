package com.smdproject.smdproject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import database.Group;
import database.User;


/**
 * Created by ishrat.fatima on 3/7/2018.
 */

public class SaveSharedPrefTask extends AsyncTask<Bundle,Void,Boolean> {

    SharedPreferences sp = null;
    User currentUser = null;
    Group currentGroup = null;
    Set<Group> joinedGroups = null;

    public SaveSharedPrefTask(SharedPreferences sp, User currentUser, Group currentGroup, Set<Group> joinedGroups) {
        this.sp = sp;
        this.currentUser = currentUser;
        this.currentGroup = currentGroup;
        this.joinedGroups = joinedGroups;
    }

    @Override
    protected Boolean doInBackground(Bundle... bundles) {
        try {
            SharedPreferences.Editor editor = sp.edit();
                //editor.put
                //editor.putString("Pwd",p);
            editor.commit();
            return true;
        }
        catch(Exception exp)
        {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        //if(aBoolean)
        //txtLog.setText("Data Saved successfully");
        //else
          //  txtLog.setText("Failed");
    }
}
