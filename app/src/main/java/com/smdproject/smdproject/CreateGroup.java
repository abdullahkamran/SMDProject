package com.smdproject.smdproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void createGroup(View v){
        EditText gn = findViewById(R.id.groupname);
        if(gn.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Group name can not be empty.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent();
        i.putExtra("g_name",gn.getText().toString());
        this.setResult(RESULT_OK,i);
        finish();
    }

}
