package database;

import android.arch.persistence.room.Entity;
import android.net.Uri;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "post")
public class Post {
    Group group;
    User postman;
    String text;
    Uri image;
    Uri video;
    Date stamp;
    ArrayList<Comment> comments;

    public Post(Group group, User postman, String text, Uri image, Uri video, Date stamp) {
        this.group = group;
        this.postman = postman;
        this.text = text;
        this.image = image;
        this.video = video;
        this.stamp = stamp;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getPostman() {
        return postman;
    }

    public void setPostman(User postman) {
        this.postman = postman;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Uri getVideo() {
        return video;
    }

    public void Video(Uri videos) {
        this.video = videos;
    }

    //public Timestamp getTextStamp() {
    //  stamp.getTimestamp()
    //}

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

}