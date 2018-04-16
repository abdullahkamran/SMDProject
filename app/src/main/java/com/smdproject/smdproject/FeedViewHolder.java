package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Abdullah on 2/28/2018.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView dp;
    public TextView name;
    public TextView nickname;
    public TextView timestamp;
    public TextView text;

    public FeedViewHolder(View view){
        super(view);
        dp = view.findViewById(R.id.dpOnPost);
        name = view.findViewById(R.id.nameOnPost);
        nickname = view.findViewById(R.id.nicknameOnPost);
        timestamp = view.findViewById(R.id.timestampOnPost);
        text = view.findViewById(R.id.textOnPost);
    }
}
