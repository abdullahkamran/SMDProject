package database;

import android.arch.persistence.room.Entity;
import android.net.Uri;

import java.security.Timestamp;
import java.util.ArrayList;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "post")
public class Post {

    Group group;
    User postman;
    String text;
    ArrayList<Uri> images;
    ArrayList<Uri> videos;
    Timestamp stamp;
    ArrayList<Comment> comments;

    public Post(Group group, User postman, String text, ArrayList<Uri> images, ArrayList<Uri> videos, Timestamp stamp) {
        this.group = group;
        this.postman = postman;
        this.text = text;
        this.images = images;
        this.videos = videos;
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

    public ArrayList<Uri> getImages() {
        return images;
    }

    public void setImages(ArrayList<Uri> images) {
        this.images = images;
    }

    public ArrayList<Uri> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Uri> videos) {
        this.videos = videos;
    }

    //public Timestamp getTextStamp() {
      //  stamp.getTimestamp()
    //}

    public Timestamp getStamp() {
        return stamp;
    }

    public void setStamp(Timestamp stamp) {
        this.stamp = stamp;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

}