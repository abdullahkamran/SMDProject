package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.net.Uri;

import java.security.Timestamp;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "event",
        foreignKeys = @ForeignKey(entity = Group.class,parentColumns = "groupId",childColumns = "g_id"))
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int eid;

    @ColumnInfo(name = "g_id")
    private int gid;

    @ColumnInfo(name = "name")
    private String name;

    //@ColumnInfo(name = "time")
    private Timestamp stamp;

    //@ColumnInfo(name = "location")
    private Location location;

    public Uri ep;
    Group group;

    public Event(Group g, String name, Timestamp stamp, Location location, Uri ep) {
        this.name = name;
        this.stamp = stamp;
        this.location = location;
        this.ep = ep;
        this.group = g;
        this.gid = g.getGroupId();
    }

    public Uri getEp() {
        return ep;
    }

    public int getEid() {
        return eid;
    }

    public String getName() {
        return name;
    }

    public Timestamp getStamp() {
        return stamp;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }
}