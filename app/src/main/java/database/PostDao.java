package database;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    ArrayList<Post> getAll();

    @Query("SELECT COUNT(*) from post")
    int count();

    @Insert
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);
}
