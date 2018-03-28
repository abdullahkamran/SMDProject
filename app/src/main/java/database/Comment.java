package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.security.Timestamp;

/**
 * Created by Ahmad on 28-Mar-18.
 */

@Entity(tableName = "comment")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "text")
    String text;
    User commentator;
    Timestamp stamp;

}