package com.example.db_wk6_labex5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NameDatabase";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "names";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_NAME = "name";

    public DBHelper (@Nullable Context context){

        // Cursor factory is when you are using your own cursor factory
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = " CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + "INTEGER NOT NULL CONSTRAINT name_pk PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " VARCHAR(200) NOT NULL" + ");";

        db.execSQL(sql);

        // Insert hardcoded items (seed data)
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES ('Amanda')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES ('Andrew')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES ('Matthew')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Upgrade is needed for the migration

        /* We drop the table and recreate it (just for this demo) */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");

        onCreate(db);
    }

    // CRUD Operations

    // Insert
    boolean addName(String name){
        // We need a writeable instance of the database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // We need content value instance to write into the database
        ContentValues cv = new ContentValues();

        // We pass the table names as keys to the Content Value (cv)
        cv.put(COLUMN_NAME, name);

        // The insert method in SQLite database returns the number of rows affected (inserted)
        // If the transaction is not successful we get -1
        return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;
    }

    // Read - Fetch data from the database
    Cursor getAllNames(){
        // We need a readonly instance of the database
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME;

        return sqLiteDatabase.rawQuery(sql, null);
    }


    // Update
    boolean updateName(int id, String name){
        // Writeable instance of the database
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);

        // Returns the number of rows affected
        return sqLiteDatabase.update(
                TABLE_NAME,
                cv,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }


    // Delete
    boolean deleteName(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // Returns the number of rows affected
        return sqLiteDatabase.delete(
                TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }


    boolean deleteAllNames(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(
                TABLE_NAME,
                null,
                null
        ) > 0;
    }
}
