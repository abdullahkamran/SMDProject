package com.smdproject.smdproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GetName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);
    }

    public void onClickUp(View v){
        EditText e=findViewById(R.id.p_name);
        if(e.getText()!=null) {
            String s = e.getText().toString();
            Intent i = new Intent();
            i.putExtra("p_name",s);
            this.setResult(RESULT_OK,i);
            finish();
        }
        else{
            Toast.makeText(this, "Name Feild Cannot be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
