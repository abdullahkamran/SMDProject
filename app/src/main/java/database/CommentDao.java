package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    ArrayList<Comment> getAll();

    @Query("SELECT COUNT(*) from comment")
    int count();

    @Insert
    void insertAll(Comment... comments);

    @Delete
    void delete(Comment comment);
}
