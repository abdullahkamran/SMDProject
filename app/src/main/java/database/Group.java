package database;

import android.arch.persistence.room.Entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "group")
public class Group {
    ArrayList<User> members;
    String name;
    ArrayList<Post> posts;
    ArrayList<Event> events;
    ArrayList<Message> messages;
    HashMap<Integer,String> nicknames;

}