package com.example.demo3_wk2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Activity_Lifecycle";

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

        Log.d(TAG, "Activity 1: onCreate is called");

        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            // Navigate from Activity 1 to Activity 2
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("name", "app demo 2");
            startActivity(intent);

            // Toast.makeText(this, "Button Clicked!", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: Activity 1");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: Activity 1");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: Activity 1");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop: Activity 1");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart: Activity 1");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity 1");
    }
}