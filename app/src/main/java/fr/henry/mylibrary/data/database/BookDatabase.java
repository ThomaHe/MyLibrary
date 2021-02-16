package fr.henry.mylibrary.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BookDB.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDBDao bookDBDao();

    private static volatile BookDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}