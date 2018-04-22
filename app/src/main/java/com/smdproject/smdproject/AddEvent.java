package com.smdproject.smdproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;


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

        TimeDialog dialog=new TimeDialog(v);
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        dialog.show(ft, "TimePicker");

    }

    public void selectDate(View v){

        DateDialog dialog=new DateDialog(v);
        FragmentTransaction ft =getFragmentManager().beginTransaction();
        dialog.show(ft, "DatePicker");

    }

    public void onPost(View view) {
        EditText ename = findViewById(R.id.ename);
        EditText edes = findViewById(R.id.edescription);
        EditText etime = findViewById(R.id.etime);
        EditText edate = findViewById(R.id.edate);
        EditText txtPlace=findViewById(R.id.eplace);


        Intent i=new Intent();

        i.putExtra("ename",ename.getText().toString());
        i.putExtra("edes",edes.getText().toString());
        i.putExtra("etime",etime.getText().toString());
        i.putExtra("edate",edate.getText().toString());
        //i.putExtra("eplace",place.getLatLng().latitude+","+place.getLatLng().longitude);
        i.putExtra("eadd",txtPlace.getText().toString());
        this.setResult(123,i);
        finish();
    }
}
