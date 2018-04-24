package com.smdproject.smdproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void createGroup(View v){
        EditText gn = findViewById(R.id.groupname);
        Intent i = new Intent();
        i.putExtra("g_name",gn.getText().toString());
        this.setResult(1122,i);
        finish();
    }
}
