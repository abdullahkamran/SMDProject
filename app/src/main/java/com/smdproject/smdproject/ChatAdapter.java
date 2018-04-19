package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import database.Message;
import database.Post;

/**
 * Created by Abdullah on 2/28/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Message> items;
    private int itemLayout;

    public ChatAdapter(List<Message> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
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