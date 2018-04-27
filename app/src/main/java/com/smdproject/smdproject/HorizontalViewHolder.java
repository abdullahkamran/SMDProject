package com.smdproject.smdproject;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HorizontalViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public Button b;

    public HorizontalViewHolder(View view){
        super(view);
        name = view.findViewById(R.id.nameH);
        b=view.findViewById(R.id.imageH);

    }


}
