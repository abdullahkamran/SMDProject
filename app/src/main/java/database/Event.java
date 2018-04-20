package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.location.Location;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.security.Timestamp;
import java.util.Date;

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

    @ColumnInfo(name = "dtime")
    @TypeConverters({TimestampConverter.class})
    private Date stamp;

    @Ignore
    private LatLng location;

    @ColumnInfo(name="epic")
    @TypeConverters({UriConverter.class})
    public Uri ep;

    @Ignore
    Group group;

    public Event(){

    }

    @Ignore
    public Event(Group g, String name, Date stamp, LatLng location, Uri ep) {
        this.name = name;
        this.stamp = stamp;
        this.location = location;
        this.ep = ep;
        this.group = g;
        this.gid = g.getGroupId();
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setEp(Uri ep) {
        this.ep = ep;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getStamp() {
        return stamp;
    }

    public LatLng getLocation() {
        return location;
    }

    public int getGid() {
        return gid;
    }

    public Group getGroup() {
        return group;
    }
}