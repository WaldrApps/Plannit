package waldrapps.plannit;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contactOld);

    @Delete
    void delete(Contact contactOld);

    @Query("DELETE FROM Contact")
    void deleteAllContacts();

    @Query("SELECT * from Contact")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * from Contact WHERE id = :id")
    Contact getContactById(String id);
}
