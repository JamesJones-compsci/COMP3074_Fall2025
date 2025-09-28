package com.example.week4_labsubmission;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        // Store the name in a variable
        String myName = "James Jones";

        // Set click listener for button
        showNameButton.setOnClickListener(v -> {
            // Display AlertDialog with my name
            new AlertDialog.Builder(this)
                    .setTitle("My Name")        // Dialog title
                    .setMessage(myName)  // Dialog message
                    // First button: OK closes the dialog
                    .setPositiveButton("OK", null)
                    // Second button: Show Toast
                    .setNeutralButton("Show Toast", (dialog, which) -> {
                        // This code runs when "Show Toast" is clicked
                        Toast.makeText(this, "Success! " +  myName, Toast.LENGTH_SHORT).show();
                    })
                    .show();                     // Display the dialog
        });

            // -------------------------------
            // 2️⃣ ListView + ArrayAdapter
            // -------------------------------
            ListView listView = findViewById(R.id.myListView);

            // Array of strings to display
            String[] items = {"Katana", "Ninja Star", "Nunchaku", "Bo Staff", "Kunai"};

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