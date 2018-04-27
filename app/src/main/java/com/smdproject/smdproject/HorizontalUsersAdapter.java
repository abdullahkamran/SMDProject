package com.smdproject.smdproject;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import database.Event;
import database.User;

public class HorizontalUsersAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {
    private List<User> items;
    private int itemLayout;

    private MainActivity context;


    public HorizontalUsersAdapter(List <User> items, int itemLayout, MainActivity context) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context=context;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        return new HorizontalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, final int position){


        if(items!=null && holder!=null){

            holder.name.setText(((User) items.get(position)).getName());
            holder.b.setBackgroundColor(Color.TRANSPARENT);

            if(((User) items.get(position)).dp!=null) {

                try {
                    InputStream io = context.getContentResolver().openInputStream(Uri.parse(((User) items.get(position)).dp));
                    holder.b.setBackground(Drawable.createFromStream(io,((User) items.get(position)).dp));

                } catch (IOException e) {
                    Toast.makeText(context,"Failed to load image.",Toast.LENGTH_SHORT).show();
                    //holder.b.setBackground(context.getResources().getDrawable(R.id.defaultdp));
                }
            }//else holder.b.setBackground(context.getResources().getDrawable(R.id.defaultdp));

        }
    }

    @Override
    public int getItemCount(){
        if(items!=null)
            return items.size();
        else return 0;
    }
}