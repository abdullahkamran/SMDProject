package database;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Ahmad on 28-Mar-18.
 */

public class User implements Serializable{
    public String dp;
    private String uid;
    private String name;
    private LatLng location;
    private Boolean isAdmin;
    private String phone;


    public User(){}

    public User(String uid,String dp, String name,String p) {
        this.dp = dp;
        this.uid = uid;
        this.name = name;
        this.phone = p;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}