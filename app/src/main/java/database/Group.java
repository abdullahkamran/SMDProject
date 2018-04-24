package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "groups")
public class Group implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int groupId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name="gpic")
    @TypeConverters({UriConverter.class})
    private Uri groupPic;

    @Ignore
    private ArrayList<User> members;
    @Ignore
    private ArrayList<Post> posts;
    @Ignore
    private ArrayList<Event> events;
    @Ignore
    private ArrayList<Message> messages;
    @Ignore
    private HashMap<Integer,String> nicknames;

    public Group(){

    }

    @Ignore
    public Group(String name) {
        this.name = name;
        members=new ArrayList<>();
        posts=new ArrayList<>();
        events=new ArrayList<>();
        messages=new ArrayList<>();
        nicknames=new HashMap<Integer,String>();
    }

    public int getGroupId() {
        return groupId;
    }

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

    public Uri getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(Uri groupPic) {
        this.groupPic = groupPic;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}