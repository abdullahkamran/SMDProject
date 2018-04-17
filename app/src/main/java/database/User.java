package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "user")
public class User {

    public Uri dp;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    public User(Uri dp, int uid, String firstName, String lastName) {
        this.dp = dp;
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

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
}