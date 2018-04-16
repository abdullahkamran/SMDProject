package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import database.Post;

/**
 * Created by Abdullah on 2/28/2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {
    private List<Post> items;
    private int itemLayout;

    public FeedAdapter(List<Post> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder,int position){
        if(items!=null && holder!=null){
            holder.dp.setImageURI(items.get(position).getPostman().dp);
            holder.name.setText(items.get(position).getPostman().getName());
            holder.nickname.setText("@"+items.get(position).getGroup().getNicknames().get(items.get(position).getPostman().getUid()));
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