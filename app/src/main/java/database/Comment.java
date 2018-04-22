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

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "dtime")
    @TypeConverters({TimestampConverter.class})
    private Date stamp;

    @Ignore
    private User commentator;
    @Ignore
    private Post p;

    public Comment(){

    }

    @Ignore
    public Comment(String text, User commentator, Date stamp, Post p) {
        this.text = text;
        this.commentator = commentator;
        this.userid = commentator.getUid();
        this.postid = p.getPid();
        this.stamp = stamp;
        this.p = p;
    }

    public int getPostid() {
        return postid;
    }

    public Post getP() {
        return p;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }

    public void setP(Post p) {
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

    public Date getStamp() {
        return stamp;
    }

    public void setText(String text) {
        this.text = text;
    }
}