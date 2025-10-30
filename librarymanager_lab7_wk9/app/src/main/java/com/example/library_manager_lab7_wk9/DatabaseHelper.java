package com.example.library_manager_lab7_wk9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// This class manages the SQLite database for our library app
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_YEAR = "year";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER"
                + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }

    // Called when the database version is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        // Recreate table
        onCreate(db);
    }

    // CRUD OPERATIONS

    // Insert a new book
    public long addBook(String title, String author, String genre, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);

        long id = db.insert(TABLE_BOOKS, null, values);
        db.close();
        return id;
    }

    // Get all books
    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKS, null, null, null, null, null, COLUMN_TITLE + " ASC");
    }

    // Update an existing book
    public int updateBook(int id, String title, String author, String genre, int year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_YEAR, year);

        return db.update(TABLE_BOOKS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a book
    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
