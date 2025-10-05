package com.example.preferences_demo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String SHARED_PREF = "user_preferences";

    private static final String KEY_NAME = "key_name";

    private static final String KEY_NAMES = "key_names";
    private static final String KEY_IS_STUDENT = "key_is_student";

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

        initPreferences();

    }

    public void initPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        String name = "Test name";

        // Store String into preferences
        // Write into preferences
        /*sharedPreferences
                .edit()
                .putString(KEY_NAME, name)
                .apply();

        sharedPreferences
                .edit()
                .putBoolean(KEY_IS_STUDENT, true)
                .apply();*/

        // Read from shared preferences
        String retrievedName = sharedPreferences.getString(KEY_NAME, "No-Name");
        Boolean isStudent = sharedPreferences.getBoolean(KEY_IS_STUDENT, false);

        Log.i(TAG, "initPreferences: " + retrievedName + "\n" + isStudent);

        ((TextView) findViewById(R.id.text)).setText(String.format("%s\n%s", retrievedName, isStudent));

        List<String> names = new ArrayList<>(Arrays.asList("Name1", "Name2", "Name3", "Name4", "Name5"));

        Gson gson = new Gson();

        String name2 = gson.toJson(names);

        /*sharedPreferences
                .edit()
                .putString(KEY_NAMES, name2)
                .apply();*/

        String retrievedNames = sharedPreferences
                .getString(KEY_NAMES, "");

        String[] namesArray = gson.fromJson(retrievedNames, String[].class);

        Log.i(TAG, "initPreferences: " + retrievedNames);
        Log.i(TAG, "initPreferences: " + Arrays.toString(namesArray));


    }
}