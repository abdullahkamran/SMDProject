package database;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */


public class Event implements Serializable {
    private String eid;
    private String gid;
    private String name;
    private String description;
    private Date stamp;
    private LatLng location;
    Group group;
    String address;

    public Event(Group g,String ad, String name,String desc, Date stamp, LatLng location) {
        this.name = name;
        this.stamp = stamp;
        this.address=ad;
        this.location = location;
        this.group = g;
        this.gid = g.getGroupId();
        this.description = desc;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}