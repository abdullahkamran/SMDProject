package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.security.Timestamp;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "comment")
public class Comment {
//    @PrimaryKey(autoGenerate = true)
    private int cid;
//
//    @ColumnInfo(name = "text")
    private String text;
//
//    //@ColumnInfo(name = "commentator")
    private User commentator;
//
//    //@ColumnInfo(name = "time")
    private Timestamp stamp;
//
//    public void setCid(int cid) {
//        this.cid = cid;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public void setCommentator(User commentator) {
//        this.commentator = commentator;
//    }
//
//    public void setStamp(Timestamp stamp) {
//        this.stamp = stamp;
//    }
//
//    public int getCid() {
//        return cid;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public User getCommentator() {
//        return commentator;
//    }
//
//    public Timestamp getStamp() {
//        return stamp;
//    }
}