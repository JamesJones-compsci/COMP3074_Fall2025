package com.example.library_manager_lab7_wk9;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditBookActivity extends AppCompatActivity {

    EditText etTitle, etAuthor, etGenre, etYear;
    Button btnSave;
    DatabaseHelper dbHelper;
    int bookId = -1; // -1 indicates new book

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        // Check if this is editing an existing book
        Intent intent = getIntent();
        if (intent.hasExtra("BOOK_ID")) {
            bookId = intent.getIntExtra("BOOK_ID", -1);
            loadBookData(bookId);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBook();
            }
        });
    }

    private void loadBookData(int id) {
        Cursor cursor = dbHelper.getAllBooks();
        if (cursor.moveToFirst()) {
            do {
                int currentId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                if (currentId == id) {
                    etTitle.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE)));
                    etAuthor.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR)));
                    etGenre.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENRE)));
                    etYear.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR)));
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private void saveBook() {
        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        String genre = etGenre.getText().toString();
        int year = Integer.parseInt(etYear.getText().toString());

        if (bookId == -1) {
            dbHelper.addBook(title, author, genre, year);
            Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateBook(bookId, title, author, genre, year);
            Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
        }
        finish(); // Return to MainActivity
    }
}
