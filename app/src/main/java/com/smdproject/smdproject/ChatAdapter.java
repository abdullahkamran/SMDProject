package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import database.Message;
import database.Post;

/**
 * Created by Abdullah on 2/28/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private ArrayList<Message> items;
    private int itemLayoutIn;
    private int itemLayoutOut;
    private MainActivity context;

    public ChatAdapter(ArrayList<Message> items, int itemLayoutIn, int itemLayoutOut, MainActivity context) {
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
            holder.dp.setImageURI(items.get(position).getSender().dp);

            if(items.get(position).getGroup().getNicknames().containsKey(items.get(position).getSender().getUid()))
                holder.nickname.setText(items.get(position).getGroup().getNicknames().get(items.get(position).getSender().getUid()));
            else holder.nickname.setText(items.get(position).getSender().getName());

            holder.timestamp.setText(items.get(position).getStamp().toString());
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