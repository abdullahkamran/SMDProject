package database;

import android.arch.persistence.room.Entity;
import android.net.Uri;

import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "message")
public class Message {

    Group group;
    User sender;
    String text;
    Date stamp;

    public Message(Group group, User sender, String text, Date stamp) {
        this.group = group;
        this.sender = sender;
        this.text = text;
        this.stamp = stamp;
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
