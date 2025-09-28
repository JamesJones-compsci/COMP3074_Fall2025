package com.example.week4_labsubmission;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        // Show button name

        Button showNameButton = findViewById(R.id.showNameButton);

        // Set click listener for button
        showNameButton.setOnClickListener(v -> {
                    // Display AlertDialog with my name
                    new AlertDialog.Builder(this)
                            .setTitle("My Name")        // Dialog title
                            .setMessage("James Jones")  // Dialog message
                            .setPositiveButton("OK", null) // OK button closes dialog
                            .show();                     // Display the dialog

                });

            // -------------------------------
            // 2️⃣ ListView + ArrayAdapter
            // -------------------------------
            ListView listView = findViewById(R.id.myListView);

            // Array of strings to display
            String[] items = {"Apple", "Banana", "Cherry", "Date", "Elderberry"};

            // ArrayAdapter: connects array of strings to ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,                       // Context
                    android.R.layout.simple_list_item_1, // Built-in layout for each item
                    items                        // Data array
            );

            // Set adapter for ListView
            listView.setAdapter(adapter);
    }
}