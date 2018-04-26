package database;

import java.util.Date;

/**
 * Created by Ahmad on 28-Mar-18.
 */

public class Comment {
    private int cid;
    private int postid;
    private String userid;
    private String text;
    private Date stamp;
    private User commentator;
    private Post p;

    public Comment(String text, User commentator, Date stamp, Post p) {
        this.text = text;
        this.commentator = commentator;
        this.userid = commentator.getUid();
        this.postid = p.getPid();
        this.stamp = stamp;
        this.p = p;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public User getCommentator() {
        return commentator;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }

    public Post getP() {
        return p;
    }

    public void setP(Post p) {
        this.p = p;
    }
}