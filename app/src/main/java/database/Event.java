package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import java.security.Timestamp;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "event")
public class Event {
//    @PrimaryKey(autoGenerate = true)
    private int eid;
//
//    @ColumnInfo(name = "name")
    private String name;
//
//    @ColumnInfo(name = "time")
    private Timestamp stamp;
//
//    //@ColumnInfo(name = "location")
    private Location location;
//
//    public int getEid() {
//        return eid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Timestamp getStamp() {
//        return stamp;
//    }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public void setEid(int eid) {
//        this.eid = eid;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setStamp(Timestamp stamp) {
//        this.stamp = stamp;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
}