package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "comment")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "commentator")
    private User commentator;

    @ColumnInfo(name = "time")
    private Timestamp stamp;

    public Comment(String text, User commentator, Timestamp stamp) {
        this.text = text;
        this.commentator = commentator;
        this.stamp = stamp;
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
}