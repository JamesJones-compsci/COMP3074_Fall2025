package com.example.db_demo;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DBHelper dbHelper;

    private EditText nameText;

    private ListView listView;

    private ArrayAdapter<Name> adapter;

    private List<Name> names;

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

        dbHelper = new DBHelper(this);
        initUi();
    }

    private void initUi() {

        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                names
        );

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showUpdateOrDeleteNameDialog(names.get(position));
            return true;
        });

        nameText = findViewById(R.id.name);
        findViewById(R.id.addBtn).setOnClickListener(this);
        findViewById(R.id.showBtn).setOnClickListener(this);
    }

    private void showUpdateOrDeleteNameDialog(Name name){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Edit name: " + name.name)
                .setMessage("Do you want to edit or delete " + name.name)
                .setPositiveButton("Edit", (d, i) -> {
                    String newName = nameText.getText().toString().trim();
                    if (!newName.isBlank()){
                        if (dbHelper.updateName(name.id, newName)) {
                            Toast.makeText(this, "The name is updated to " + newName, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "The name is not updated", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "The input text is empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Delete", (d, i) -> {
                    if (dbHelper.deleteName(name.id)){
                        showNames();
                    }
                })
                .setNeutralButton("Dismiss", null)
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.addBtn) {
            addName();
        } else if (id == R.id.showBtn){
            showNames();
        }
    }


    private void addName() {
        String name = nameText.getText().toString().trim();
        if (name.isBlank()) {
            // Show error on the edit text
            nameText.setError("Name field is mandatory");
            nameText.requestFocus();
            return;
        }

        if (dbHelper.addName(name)){
            Toast.makeText(this, "Name is added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Name not added", Toast.LENGTH_SHORT).show();
        }

        nameText.setText("");
    }

    private void showNames() {
        names.clear();

        Cursor cursor = dbHelper.getAllNames();
        if (cursor.moveToFirst()){
            // We are reading from the beginning
            do {

                names.add(
                        new Name(
                                cursor.getInt(0),
                                cursor.getString(1)
                        )
                );

            } while (cursor.moveToNext());
            // Close the cursor after the transaction
            cursor.close();

            // Notify the adapter that the dataset has changed
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, "The fetch process failed", Toast.LENGTH_SHORT).show();
        }
    }



    private class Name {

        private int id;

        private String name;


        public Name(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

// View -> tool Windows -> App Inspection