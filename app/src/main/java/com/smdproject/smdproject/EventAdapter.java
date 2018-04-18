package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import database.Event;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<Event> items;
    private int itemLayout;

    public EventAdapter(List <Event> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder,int position){
        if(items!=null && holder!=null){
            //holder.eventPic.setImageURI(items.get(position).getEp());
            holder.eventName.setText(items.get(position).getName());
            //holder.timePlace.setText(items.get(position).getStamp().toString()+" at "+items.get(position).getLocation().toString());
            //holder.intrPerson.setText();
        }
    }

    @Override
    public int getItemCount(){
        if(items!=null)
            return items.size();
        else return 0;
    }
}