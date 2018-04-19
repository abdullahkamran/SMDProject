package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

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

    private Group group;
    private User sender;
    private String text;
    private Date stamp;

    public Message(Group group, User sender, String text, Date stamp) {
        this.group = group;
        this.sender = sender;
        this.text = text;
        this.stamp = stamp;
        this.gid = group.getGroupId();
        this.senderid = sender.getUid();
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
