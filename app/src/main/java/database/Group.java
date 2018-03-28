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
    private int grouppic;

    public void setName(String name) {
        this.name = name;
    }

    public void setGrouppic(int grouppic) {
        this.grouppic = grouppic;
    }

    public String getName() {
        return name;
    }

    public int getGrouppic() {
        return grouppic;
    }
}