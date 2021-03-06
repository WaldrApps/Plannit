package waldrapps.plannit;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM events")
    void deleteAllEvents();

    @Query("SELECT * from events")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * from events WHERE id = :id")
    Event getEventById(int id);

    @Query("SELECT * from events WHERE contact_id = :contact_id AND day = :day")
    LiveData<List<Event>> getEventsByContactIDAndDay(String contact_id, int day);

    @Query("SELECT * from events WHERE contact_id = :contact_id")
    LiveData<List<Event>> getEventsByContactIDLive(String contact_id);

    @Query("SELECT * from events WHERE contact_id = :contact_id")
    List<Event> getEventsByContactID(String contact_id);
}
