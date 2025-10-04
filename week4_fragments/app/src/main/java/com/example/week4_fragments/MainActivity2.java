package com.example.week4_fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

public class MainActivity2 extends AppCompatActivity {
    
    public static final String TAG = "MAIN.MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Add fragments programmatically
        // 1. Get the fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // 2. Begin the transaction on the fragment manager
        fm.beginTransaction()
                .add(R.id.container, new MainFragment(), "FIRST")
                .commit();

        // Replace the fragment with a different fragment
        findViewById(R.id.replacebtn).setOnClickListener((v) -> {
        //  replaceFragment();

            String label = ((EditText) findViewById(R.id.label)).getText().toString();
            sendLabelText(label);
        });
    }

    private void sendLabelText(String text){
        // Pass value (string) from the activity to the fragment
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentByTag("FIRST");
        mainFragment.displayText(text);
    }

    private void replaceFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new MainFragment2(), "SECOND")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }
}