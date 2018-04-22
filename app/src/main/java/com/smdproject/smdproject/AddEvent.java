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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AddEvent extends AppCompatActivity {
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        EditText txtPlace=findViewById(R.id.eplace);
        txtPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(AddEvent.this), 11);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==11 && resultCode==RESULT_OK){
            place= PlacePicker.getPlace(data,this);
            String address =String.format("%s",place.getAddress());
            EditText txtPlace=findViewById(R.id.eplace);
            txtPlace.setText(address);
        }
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
        i.putExtra("eplace",place.getLatLng().latitude+","+place.getLatLng().longitude);
        i.putExtra("eadd",txtPlace.getText().toString());
        this.setResult(123,i);
        finish();
    }
}
