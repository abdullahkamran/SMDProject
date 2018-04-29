package com.smdproject.smdproject;

import android.os.AsyncTask;

import java.util.ArrayList;

import database.Group;
import database.Message;
import database.MyDatabase;

public class MessageGetAsyncTask extends AsyncTask<String,Void,ArrayList<Message>> {
    MainActivity c;
    String g;
    MessageGetAsyncTask(MainActivity con) {
        c = con;
    }

    @Override
    protected ArrayList<Message> doInBackground(String... strings) {
        MyDatabase myDb = MyDatabase.getAppDatabase(c);
        g=strings[0];
        return (ArrayList<Message>) myDb.messageDao().getAll(strings[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Message> gg){
        super.onPostExecute(gg);
        for(int i=0;i<c.getJoined().size();i++){
            if(c.getJoined().get(i).getGroupId().equals(g)) {
                c.getJoined().get(i).setMessages(gg);
                break;
            }
        }
    }
}
