package com.example.db_wk6_labex5.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Name name);

    @Query("DELETE FROM name_table")
    void deleteAll();

    @Query("SELECT * FROM name_table")
    List<Name> getAllNames();

    @Query("SELECT * FROM name_table WHERE name_id = :id")
    Name getName(int id);

    @Update
    void update(Name name);

    @Delete
    int delete(Name name);
}
