package com.smdproject.smdproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import database.Group;
import database.MyDatabase;
import database.User;

public class GroupSaveAsyncTask extends AsyncTask <Group,Void,Void>{
    Context c;

    GroupSaveAsyncTask(Context con) {
        c = con;
    }

    @Override
    protected Void doInBackground(Group... groups) {

        MyDatabase myDb = MyDatabase.getAppDatabase(c);

        myDb.groupDao().insertAll(groups);

        return null;
    }
}