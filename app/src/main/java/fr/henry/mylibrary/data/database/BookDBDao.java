package fr.henry.mylibrary.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDBDao {
    @Query("SELECT * FROM bookdb")
    List<BookDB> getAll();

    @Insert
    void insertAll(BookDB... bookDBs);

    @Insert
    void insert(BookDB bookDB);

    @Delete
    void delete(BookDB bookDB);
}
