package com.example.db_demo.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Name.class}, version = 1, exportSchema = false)
public abstract class NameRoomDb extends RoomDatabase {

    private static final String DB_NAME = "NameRoomDb";

    public abstract NameDao dao();

    public static volatile NameRoomDb INSTANCE;

    public static final int NUMBER_OF_THREADS = 4;
    // Executor service that helps work in background thread
    public static final ExecutorService databaseExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NameRoomDb getINSTANCE(
            final Context context
            ){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NameRoomDb.class,
                            DB_NAME
                    )
                            // First when the db is created what should be done
                            .addCallback(callback)
                            .allowMainThreadQueries()
                            .build();
                }

                return INSTANCE;
    }

    // Call back to do some pre-creation of db
    private static final RoomDatabase.Callback callback
            = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            databaseExecutor.execute(() -> {
                NameDao dao = INSTANCE.dao();
                dao.deleteAll();
            });

        }
    };

}
