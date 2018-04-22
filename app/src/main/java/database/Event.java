package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import com.google.android.gms.maps.model.LatLng;
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

    @ColumnInfo(name = "desc")
    private String description;

    @ColumnInfo(name = "dtime")
    @TypeConverters({TimestampConverter.class})
    private Date stamp;

    @ColumnInfo(name = "location")
    @TypeConverters({LatLngConverter.class})
    private LatLng location;

    @Ignore
    Group group;
    @Ignore
    String address;

    public Event(){

    }

    @Ignore
    public Event(Group g,String ad, String name,String desc, Date stamp, LatLng location) {
        this.name = name;
        this.stamp = stamp;
        this.address=ad;
        this.location = location;
        this.group = g;
        this.gid = g.getGroupId();
        this.description = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
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