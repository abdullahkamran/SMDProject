package com.smdproject.smdproject;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.Message;
import database.Post;

/**
 * Created by Abdullah on 2/28/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Message> items;
    private int itemLayoutIn;
    private int itemLayoutOut;
    private MainActivity context;

    public ChatAdapter(List<Message> items, int itemLayoutIn, int itemLayoutOut, MainActivity context) {
        this.items = items;
        this.itemLayoutIn = itemLayoutIn;
        this.itemLayoutOut = itemLayoutOut;
        this.context=context;
    }

    @Override
    public int getItemViewType(int position){
        if(items.get(position).getSender().getUid()==context.getCurrentUser().getUid())return 2;
        else return 1;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v=null;

        if(viewType==1)v=LayoutInflater.from(parent.getContext()).inflate(itemLayoutIn,parent,false);
        else v=LayoutInflater.from(parent.getContext()).inflate(itemLayoutOut,parent,false);

        return new ChatViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ChatViewHolder holder,int position){
        if(items!=null && holder!=null){
            if(items.get(position).getSender().dp!=null)
                holder.dp.setImageURI(Uri.parse(items.get(position).getSender().dp));

            if(items.get(position).getGroup().getNicknames().containsKey(items.get(position).getSender().getUid()))
                holder.nickname.setText(items.get(position).getGroup().getNicknames().get(items.get(position).getSender().getUid()));
            else holder.nickname.setText(items.get(position).getSender().getName());

            String timestamp=items.get(position).getStamp().toString();
            String today=(new Date()).toString();

            String[] todays=today.split(" ");
            String[] timestamps=timestamp.split(" ");

            if(todays[0].equalsIgnoreCase(timestamps[0]) && todays[1].equalsIgnoreCase(timestamps[1])
                    && todays[2].equalsIgnoreCase(timestamps[2])){
                timestamp=timestamps[3];
                timestamp=timestamp.substring(0,timestamp.length()-3);
            }
            else{
                timestamp=timestamps[3];
                timestamp=timestamp.substring(0,timestamp.length()-3);
                timestamp=timestamps[0]+timestamps[1]+timestamps[2]+timestamp;
            }

            holder.timestamp.setText(timestamp);
            holder.text.setText(items.get(position).getText());
        }
    }

    @Override
    public int getItemCount(){
        if(items!=null)
            return items.size();
        else return 0;
    }
}