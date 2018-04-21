package com.smdproject.smdproject;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        EditText txtDate=findViewById(R.id.edate);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    DateDialog dialog=new DateDialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });

        EditText txtTime=findViewById(R.id.etime);
        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean hasfocus){
                if(hasfocus){
                    TimeDialog dialog=new TimeDialog(view);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                    dialog.show(ft, "TimePicker");
                }
            }
        });

    }

    public void onPost(View view) {
        EditText ename = findViewById(R.id.ename);
        EditText edes = findViewById(R.id.edescription);
        EditText etime = findViewById(R.id.etime);
        EditText edate = findViewById(R.id.edate);

        Intent i=new Intent();

        i.putExtra("ename",ename.getText().toString());
        i.putExtra("edes",edes.getText().toString());
        i.putExtra("etime",etime.getText().toString());
        i.putExtra("edate",edate.getText().toString());
        this.setResult(123,i);
        finish();
    }
}
