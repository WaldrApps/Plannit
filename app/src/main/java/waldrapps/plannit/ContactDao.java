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
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("DELETE FROM contacts")
    void deleteAllContacts();

    @Query("SELECT * from contacts")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * from contacts WHERE id = :id")
    Contact getContactById(int id);
}
