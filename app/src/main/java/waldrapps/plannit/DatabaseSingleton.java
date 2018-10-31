package waldrapps.plannit;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Contact.class, Event.class}, version = 1, exportSchema = false)
@TypeConverters({waldrapps.plannit.utils.TypeConverters.class})
public abstract class DatabaseSingleton extends RoomDatabase {

    private static volatile DatabaseSingleton INSTANCE;

    public abstract ContactDao contactDao();
    public abstract EventDao eventDao();

    static DatabaseSingleton getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DatabaseSingleton.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DatabaseSingleton.class,
                            "plannit_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
