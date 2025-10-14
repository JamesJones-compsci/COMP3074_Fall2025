package com.example.db_wk6_labex5;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.jar.Attributes;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DBHelper dbHelper;

    private EditText nameText;

    private ListView listView;

    private ArrayAdapter<Name> adapter;

    private List<Name> names;

    // Shared preferences keys and instance
    public static final String PREFS_NAME = "user_prefs";
    public static final String KEY_USER_NAME = "username";

    private SharedPreferences sharedPreferences;

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

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        checkAndSaveUserName();

        // Initialize DBHelper (DBHelper seeds hardcoded items in onCreate)
        dbHelper = new DBHelper(this);

        initUi();
    }

    /**
     * Check for saved username in SharedPreferences, if absent, request it via dialog and save it
     */
    private void checkAndSaveUserName(){
        String stored = sharedPreferences.getString(KEY_USER_NAME, null);
        if (stored == null){
            // Prompt user to enter a name
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setHint("Enter Your Name");

            builder.setTitle("Welcome")
                    .setMessage("Please enter your name to personalize the app")
                    .setView(input)
                    .setCancelable(false)
                    .setPositiveButton("Save", (d, i) -> {
                        String val = input.getText().toString().trim();
                        if (!val.isBlank()){
                            sharedPreferences.edit().putString(KEY_USER_NAME, val).apply();
                            Toast.makeText(this, "Name Saved: " + val, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
                            checkAndSaveUserName();
                        }
                    })
                    .show();
        } else {
            // Greet user with saved name
            Toast.makeText(this, "Welcome Back, " + stored + "!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initUi(){

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
                .setTitle("Edit Name: " + name.name)
                .setMessage("Do you want to edit or delete " + name.name)
                .setPositiveButton("Edit", (d, i) -> {
                    String newName = nameText.getText().toString().trim();
                    if (!newName.isBlank()){
                        if (dbHelper.updateName(name.id, newName)){
                            Toast.makeText(this, "The name has been updated to " + newName, Toast.LENGTH_SHORT).show();
                            showNames();
                        } else {
                            Toast.makeText(this, "The name has not been updated", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "The input text is empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Delete", (d, i) -> {
                    if (dbHelper.deleteName(name.id)){
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                        showNames();
                    } else {
                        Toast.makeText(this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Dismiss", null)
                .create()
                .show();
    }

    @Override
    public void onClick(View v){

        int id = v.getId();
        if (id == R.id.addBtn){
            addName();
        } else if (id == R.id.showBtn) {
            showNames();
        }
    }


    private void addName(){

        String name = nameText.getText().toString().trim();
        if (name.isBlank()){
            // Show error on the edit text
            nameText.setError("Name field is mandatory");
            nameText.requestFocus();
            return;
        }

        if (dbHelper.addName(name)){
            Toast.makeText(this, "Name has been added", Toast.LENGTH_SHORT).show();
            nameText.setText("");
            showNames();
        } else {
            Toast.makeText(this, "Name has not been added", Toast.LENGTH_SHORT).show();
        }
    }


    private void showNames(){

        names.clear();

        Cursor cursor = dbHelper.getAllNames();
        if (cursor.moveToFirst()){
            // Read from the beginning
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
            // Either no rows in the table or fetch failed
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
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
            return name;
        }
    }
}