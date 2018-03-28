package database;

import android.arch.persistence.room.Entity;

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
    //ArrayList<Image> images;
    //ArrayList<Video> videos;
    Timestamp stamp;
    ArrayList<Comment> comments;

}