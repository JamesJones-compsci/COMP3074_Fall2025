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

        Button showNameButton = findViewById(R.id.showNameButton);

        String myName = "James Jones";

        showNameButton.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("My Name")
                    .setMessage(myName)
                    .setPositiveButton("OK", null)
                    .setNeutralButton("Show Toast", (dialog, which) -> {
                        Toast.makeText(this, "Success! " +  myName, Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

            ListView listView = findViewById(R.id.myListView);

            String[] items = {"Katana", "Ninja Star", "Nunchaku", "Bo Staff", "Kunai"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    items
            );

            listView.setAdapter(adapter);
    }
}