package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EventViewHolder extends RecyclerView.ViewHolder {

    //public ImageView eventPic;
    public TextView eventName;
    public TextView timePlace;
    public TextView intrPerson;
    //public Button b;

    public EventViewHolder(View view){
        super(view);
        //eventPic = view.findViewById(R.id.eventImage);
        eventName = view.findViewById(R.id.eventName);
        timePlace = view.findViewById(R.id.eventTime);
        intrPerson = view.findViewById(R.id.persons);
    }
}
