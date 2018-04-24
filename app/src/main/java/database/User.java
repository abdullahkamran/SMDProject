package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="epic")
    @TypeConverters({UriConverter.class})
    public Uri dp;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    @TypeConverters({LatLngConverter.class})
    private LatLng location=null;

    public User(){

    }

    @Ignore
    public User(Uri dp, String Name) {
        this.dp = dp;
        this.name = Name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Uri getDp() {
        return dp;
    }

    public void setDp(Uri dp) {
        this.dp = dp;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}