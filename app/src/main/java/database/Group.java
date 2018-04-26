package database;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Created by Ahmad on 28-Mar-18.
 */

public class Group implements Serializable {
    private String groupId;
    private String name;
    private Uri groupPic;
    private List<User> members;
    private List<Post> posts;
    private List<Event> events;
    private List<Message> messages;
    private HashMap<Integer,String> nicknames;
    private User admin;

    public Group() {

    }

    public Group(String name,User u) {
        this.name = name;
        members=new ArrayList<>();
        posts=new ArrayList<>();
        events=new ArrayList<>();
        messages=new ArrayList<>();
        nicknames=new HashMap<Integer,String>();
        admin = u;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(Uri groupPic) {
        this.groupPic = groupPic;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public HashMap<Integer, String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(HashMap<Integer, String> nicknames) {
        this.nicknames = nicknames;
    }
}