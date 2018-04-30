package com.smdproject.smdproject;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import database.Group;
import database.Message;
import database.MyDatabase;

public class MessageSaveAsync extends AsyncTask<Message,Void,Void> {
    Context c;

    MessageSaveAsync(Context con) {
        c = con;
    }

    @Override
    protected Void doInBackground(Message... messages) {
        MyDatabase myDb = MyDatabase.getAppDatabase(c);
        myDb.messageDao().insertAll(messages);
        return null;
    }
}
