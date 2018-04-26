package com.smdproject.smdproject;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Date;
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
            if(items.get(position).getPostman().dp!=null)
                holder.dp.setImageURI(Uri.parse(items.get(position).getPostman().dp));

            holder.name.setText(items.get(position).getPostman().getName());

            if(items.get(position).getGroup().getNicknames().containsKey(items.get(position).getPostman().getUid()))
                holder.nickname.setText("@"+items.get(position).getGroup().getNicknames().get(items.get(position).getPostman().getUid()));


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

            if(items.get(position).getText().equalsIgnoreCase("")){
                holder.text.setVisibility(TextView.GONE);
            }
            else holder.text.setVisibility(TextView.VISIBLE);


            holder.image.setImageURI(items.get(position).getImage());

            if(items.get(position).getImage()==null){
                holder.image.setVisibility(ImageView.GONE);
            }
            else holder.image.setVisibility(ImageView.VISIBLE);

        }
    }

    @Override
    public int getItemCount(){
        if(items!=null)
            return items.size();
        else return 0;
    }
}