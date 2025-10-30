package com.example.library_manager_lab7_wk9;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listViewBooks;
    Button btnAddBook;
    DatabaseHelper dbHelper;
    ArrayList<String> bookList;
    ArrayAdapter<String> adapter;
    ArrayList<Integer> bookIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Initialize views and database AFTER Edge-to-Edge ---
        listViewBooks = findViewById(R.id.listViewBooks);
        btnAddBook = findViewById(R.id.btnAddBook);
        dbHelper = new DatabaseHelper(this);

        // Add book button click
        btnAddBook.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            startActivity(intent);
        });

        // ListView item click to edit
        listViewBooks.setOnItemClickListener((parent, view, position, id) -> {
            int bookId = bookIds.get(position);
            Intent intent = new Intent(MainActivity.this, AddEditBookActivity.class);
            intent.putExtra("BOOK_ID", bookId);
            startActivity(intent);
        });

        // Long click to delete
        listViewBooks.setOnItemLongClickListener((parent, view, position, id) -> {
            int bookId = bookIds.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteBook(bookId);
                        loadBooks();
                        Toast.makeText(MainActivity.this, "Book deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks(); // Refresh ListView when returning to this activity
    }

    private void loadBooks() {
        Cursor cursor = dbHelper.getAllBooks();
        bookList = new ArrayList<>();
        bookIds = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR));
                bookList.add(title + " - " + author);
                bookIds.add(id);
            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        listViewBooks.setAdapter(adapter);
        cursor.close();
    }
}