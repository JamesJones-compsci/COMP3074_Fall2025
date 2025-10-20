package com.example.labex6_comp3074_wk7.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDb extends RoomDatabase {

    // Abstract method to get DAO
    public abstract StudentDao studentDao();

    // Singleton instance
    private static StudentDb INSTANCE;

    public static StudentDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StudentDb.class, "student_database")
                    .allowMainThreadQueries() // Only for labs; normally use background thread
                    .build();
        }
        return INSTANCE;
    }
}