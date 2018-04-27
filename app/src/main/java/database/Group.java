package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ahmad on 28-Mar-18.
 */

public class Group implements Serializable{
    private String groupId;
    private String name;
    private String groupPic;
    private String adminId;
    private List<User> members;
    private List<Post> posts;
    private List<Event> events;
    private List<Message> messages;
    private Map<Integer,String> nicknames;

    public Group() {

    }

    public Group(String name,String u) {
        this.name = name;
        members=new ArrayList<>();
        posts=new ArrayList<>();
        events=new ArrayList<>();
        messages=new ArrayList<>();
        nicknames=new HashMap<Integer,String>();
        adminId = u;
    }

    public String getAdmin() {
        return adminId;
    }

    public void setAdmin(String admin) {
        this.adminId = admin;
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

    public String getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(String groupPic) {
        this.groupPic = groupPic;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public Map<Integer, String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(HashMap<Integer, String> nicknames) {
        this.nicknames = nicknames;
    }
}