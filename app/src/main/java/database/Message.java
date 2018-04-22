package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "message",
        foreignKeys = {@ForeignKey(entity = Group.class,parentColumns = "groupId",childColumns = "g_id"),
                @ForeignKey(entity = User.class,parentColumns = "uid",childColumns = "u_id")})

public class Message {
    @PrimaryKey(autoGenerate = true)
    private int mid;

    @ColumnInfo(name = "g_id")
    private int gid;

    @ColumnInfo(name = "u_id")
    private int senderid;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "dtime")
    @TypeConverters({TimestampConverter.class})
    private Date stamp;

    @Ignore
    private Group group;
    @Ignore
    private User sender;

    public Message(){

    }

    @Ignore
    public Message(Group group, User sender, String text, Date stamp) {
        this.group = group;
        this.sender = sender;
        this.text = text;
        this.stamp = stamp;
        this.gid = group.getGroupId();
        this.senderid = sender.getUid();
    }

    public int getMid() {
        return mid;
    }

    public int getGid() {
        return gid;
    }

    public int getSenderid() {
        return senderid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
