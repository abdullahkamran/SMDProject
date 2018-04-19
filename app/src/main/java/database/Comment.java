package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "comment",
        foreignKeys = {@ForeignKey(entity = Post.class,parentColumns = "pid",childColumns = "p_id"),
                       @ForeignKey(entity = User.class,parentColumns = "uid",childColumns = "u_id")})

public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "p_id")
    private int postid;

    @ColumnInfo(name = "u_id")
    private int userid;

    //@ColumnInfo(name = "time")
    private Timestamp stamp;

    private User commentator;
    private Post p;
    private String text;

    public Comment(String text, User commentator, Timestamp stamp,Post p) {
        this.text = text;
        this.commentator = commentator;
        this.userid = commentator.getUid();
        this.postid = p.getPid();
        this.stamp = stamp;
        this.p = p;
    }

    public int getUserid() {
        return userid;
    }

    public int getCid() {
        return cid;
    }

    public String getText() {
        return text;
    }

    public User getCommentator() {
        return commentator;
    }

    public Timestamp getStamp() {
        return stamp;
    }

    public void setText(String text) {
        this.text = text;
    }
}