package waldrapps.plannit;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactDatabaseSingleton extends RoomDatabase{

    private static volatile ContactDatabaseSingleton INSTANCE;

    public abstract ContactDoa contactDoa();

    static ContactDatabaseSingleton getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabaseSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactDatabaseSingleton.class, "contact_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
