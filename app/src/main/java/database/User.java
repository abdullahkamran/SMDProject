package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
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

    @ColumnInfo(name="epic")
    @TypeConverters({UriConverter.class})
    public Uri dp;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @Ignore
    private LatLng location=null;

    public User(){

    }

    @Ignore
    public User(Uri dp, int uid, String firstName, String lastName) {
        this.dp = dp;
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName(){
        return getFirstName()+" "+getLastName();
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