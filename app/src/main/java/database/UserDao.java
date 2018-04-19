package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.ArrayList;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    ArrayList<User> getAll();

    @Query("SELECT COUNT(*) from user")
    int count();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
