package ammyaman.roomsample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by CH-E01449 on 06-12-2017.
 */

@Database(entities = {Contact.class}, version = 3)
public abstract class ContactDatabase extends RoomDatabase {

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE contacts "
                    + " ADD COLUMN altr_number TEXT");
        }
    };
    private static ContactDatabase INSTANCE;

    public static ContactDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactDatabase.class) {
                if (INSTANCE == null) {
                    // if save into Memory
//                    INSTANCE =
//                            Room.inMemoryDatabaseBuilder(context.getApplicationContext(), ContactDatabase.class)
//                                    .allowMainThreadQueries()
//                                    .build();

                    // if save into external memory
                    INSTANCE = Room.databaseBuilder(context, ContactDatabase.class, "contacts_db")
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Call When the activity's onDestroy()
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract ContactDao contactDao();

}
