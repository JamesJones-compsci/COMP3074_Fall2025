package com.example.demo4_wk4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username, passwordText;

    ViewGroup root;

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

        root = findViewById(R.id.main);

        username = findViewById(R.id.user_name);
        passwordText = findViewById(R.id.password);

        CheckBox checkBox = findViewById(R.id.chkbx);
        RadioGroup rg = findViewById(R.id.items);

        findViewById(R.id.select).setOnClickListener( v -> {
            if (checkBox.isChecked()){
                int itemId = rg.getCheckedRadioButtonId();
                String text = ((RadioButton) findViewById(itemId)).getText().toString();
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You need to select a checkbox", Toast.LENGTH_SHORT).show();
            }
        });

        checkBox.setOnCheckedChangeListener((v, isChecked) -> {
            Toast.makeText(this, isChecked ? "Is checked" : "Not checked", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.bread).setOnClickListener(this);
        findViewById(R.id.milk).setOnClickListener(this);
        findViewById(R.id.eggs).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String text;
        int id = v.getId();
        if (id == R.id.bread){
            text = "Bread is clicked";
        }
        else if (id == R.id.milk){
            text = "Milk is clicked";
        }
        else if (id == R.id.eggs){
            text = "Eggs is clicked";
        } else {
            text = "Nothing was selected";
        }



        Snackbar
                .make(root,text, Snackbar.LENGTH_SHORT)
                .setAction(
                        "OK",
                        view -> {
                            new AlertDialog.Builder(this)
                                    .setIcon(R.drawable.baseline_add_link_24)
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        // Just to show a message
                                        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                                    })
                                    .setTitle("Title: " + text)
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                )

                .show();
    }
}