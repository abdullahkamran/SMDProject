package com.smdproject.smdproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import database.Event;
import database.Group;
import database.Message;
import database.Post;
import database.User;

public class LoginGroup extends AppCompatActivity {

    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_group);

        u = (User)getIntent().getSerializableExtra("user");
    }

    public String getLoc(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                return location.getLatitude()+","+location.getLongitude();
            }
        }
        return null;
    }

    public void onClickGroupId(View v){
        EditText ed=findViewById(R.id.editText);
        if(ed.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this,"Please enter a Group ID.",Toast.LENGTH_SHORT).show();
            return;
        }
        Group g = new Group();
        g.setMembers(new ArrayList<User>());
        g.setNicknames(new HashMap<String, String>());
        g.setPosts(new ArrayList<Post>());
        g.setMessages(new ArrayList<Message>());
        g.setEvents(new ArrayList<Event>());

        g.setGroupId(ed.getText().toString());
        g.getMembers().add(u);
        u.setLocation(getLoc());
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("user", u);
        i.putExtra("group",g);
        startActivity(i);
        finish();
    }
}
