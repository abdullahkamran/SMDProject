package database;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    ArrayList<Event> getAll();

    @Query("SELECT COUNT(*) from event")
    int count();

    @Insert
    void insertAll(Event... events);

    @Delete
    void delete(Event event);
}
