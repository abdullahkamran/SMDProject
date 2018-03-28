package com.smdproject.smdproject.classes;


import java.security.Timestamp;
import java.util.ArrayList;

/**
 * Created by Abdullah on 3/21/2018.
 */

public class Post {

    Group group;
    User postman;
    String text;
    //ArrayList<Image> images;
    //ArrayList<Video> videos;
    Timestamp stamp;
    ArrayList<Comment> comments;

}
