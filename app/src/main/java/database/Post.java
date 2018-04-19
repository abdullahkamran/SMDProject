package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "post",
        foreignKeys = {@ForeignKey(entity = User.class,parentColumns = "uid",childColumns = "u_id"),
                       @ForeignKey(entity = Group.class,parentColumns = "groupId",childColumns = "g_id")})
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int pid;

    @ColumnInfo(name = "u_id")
    private int uid;

    @ColumnInfo(name = "g_id")
    private int gid;
    private Group group;
    private User postman;
    private String text;
    private Uri image;
    private Uri video;
    private Date stamp;
    private ArrayList<Comment> comments;

    public Post(Group group, User postman, String text, Uri image, Uri video, Date stamp) {
        this.group = group;
        this.postman = postman;
        this.text = text;
        this.image = image;
        this.video = video;
        this.stamp = stamp;
        this.uid = postman.getUid();
        this.gid = group.getGroupId();
    }

    public int getPid() {
        return pid;
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