package database;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ahmad on 28-Mar-18.
 */

public class Post implements Serializable{
    private String pid;
    private String uid;
    private String gid;
    private String text;
    private String image;
    private String stamp;
    private Group group;
    private User postman;
    private List<Comment> comments;

    public Post(){

    }

    public Post(Group group, User postman, String text, String image, String stamp) {
        this.group = group;
        this.postman = postman;
        this.text = text;
        this.image = image;
        this.stamp = stamp;
        this.uid = postman.getUid();
        this.gid = group.getGroupId();
        comments=new ArrayList<>();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}