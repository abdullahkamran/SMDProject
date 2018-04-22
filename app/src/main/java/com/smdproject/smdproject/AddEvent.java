package com.smdproject.smdproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.util.Date;


public class AddEvent extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    public void selectLocation(View v){


    }

    public void selectTime(View v){

        TimeDialog dialog=new TimeDialog((TextView)findViewById(R.id.etime));
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        dialog.show(ft, "TimePicker");

    }

    public void selectDate(View v){

        DateDialog dialog=new DateDialog((TextView)findViewById(R.id.edate));
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        dialog.show(ft, "DatePicker");

    }

    public void onPost(View view) {
        EditText ename = (EditText) findViewById(R.id.ename);
        EditText edes = (EditText) findViewById(R.id.edescription);
        TextView etime = (TextView) findViewById(R.id.etime);
        TextView edate = (TextView) findViewById(R.id.edate);
        TextView txtPlace = (TextView) findViewById(R.id.eplace);

        if(ename.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Event name can not be empty.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(etime.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Error: Please set event time.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(edate.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Error: Please set event date.",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i=new Intent();

        i.putExtra("ename",ename.getText().toString());
        i.putExtra("edes",edes.getText().toString());
        i.putExtra("etime",etime.getText().toString());
        i.putExtra("edate",edate.getText().toString());
        //i.putExtra("eplace",place.getLatLng().latitude+","+place.getLatLng().longitude);
        i.putExtra("eadd",txtPlace.getText().toString());
        this.setResult(RESULT_OK,i);
        finish();
    }
}
