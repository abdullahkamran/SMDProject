package database;

import android.arch.persistence.room.Entity;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ahmad on 28-Mar-18.
 */

//@Entity(tableName = "group")
public class Group {
    private ArrayList<User> members;
    private String name;
    private ArrayList<Post> posts;
    private ArrayList<Event> events;
    private ArrayList<Message> messages;
    private HashMap<Integer,String> nicknames;
    private int groupPic;


    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public HashMap<Integer, String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(HashMap<Integer, String> nicknames) {
        this.nicknames = nicknames;
    }

    public int getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(int groupPic) {
        this.groupPic = groupPic;
    }

}