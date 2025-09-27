package com.example.week4_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

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

        editText = findViewById(R.id.et1);
    }


    public void grabMyName(View view){
        String s =editText.getText().toString();
        displayDialog(s);
    }


    private void displayDialog(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Your Name")
                .setMessage(s)
                .setNegativeButton("OK", null)
                .setPositiveButton("Show Name", (dialog, w) -> {
                    // Display a SnackBar
                    displaySnackBar(s);
                })
                .create()
                .show();
    }

    private void displaySnackBar(String s){
        Snackbar
                .make(findViewById(R.id.main), s, Snackbar.LENGTH_LONG)
                .setAction("OK", (o) -> {
                    // To do some logic
                    Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
                })

                .setActionTextColor(ContextCompat.getColor(this, R.color.white))
                .show();
    }


}