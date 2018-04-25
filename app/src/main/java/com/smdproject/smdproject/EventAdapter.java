package com.smdproject.smdproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import database.Event;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<Event> items;
    private int itemLayout;

    private MainActivity context;


    public EventAdapter(List <Event> items, int itemLayout, MainActivity context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context=context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position){


        if(items!=null && holder!=null){
            holder.eventName.setText(items.get(position).getName());
            holder.description.setText(items.get(position).getDescription());
            String s=items.get(position).getStamp().toString();
            s=s.substring(0,s.length()-15);
            String[] strings=s.split(" ");
            s=strings[0]+" "+strings[1]+" "+strings[2]+"    @"+strings[3].substring(0,strings[3].length()-3);
            holder.time.setText(s);
            holder.place.setText(items.get(position).getAddress()+"\n");

            holder.b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    //String uri=
                      //      String.format
                        //            (Locale.ENGLISH,"geo:%f,%f",items.get(position).getLocation().latitude,items.get(position).getLocation().longitude);

                    String uri="http://maps.google.com/maps?daddr="+items.get(position).getLocation().latitude
                            +","+items.get(position).getLocation().longitude;

                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    context.startActivity(intent);

                }
            });

        }
    }

    @Override
    public int getItemCount(){
        if(items!=null)
            return items.size();
        else return 0;
    }
}