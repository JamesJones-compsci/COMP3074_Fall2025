package com.example.demo3_wk2;

import static com.example.demo3_wk2.MainActivity.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v){
        int id = v.getId();

        Intent intent = null;

        if (id == R.id.button1){
            // Open yahoo website
            intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://yahoo.com")
            );
            startActivity(intent);
            return;
        }

        if (id == R.id.button2){
            // Phone call
            // Todo: Complete this next session
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d(TAG, "Activity 2: onCreate is called");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        TextView textView = findViewById(R.id.name);
        textView.setText(name);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }



    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart: Activity 2");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume: Activity 2");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause: Activity 2");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop: Activity 2");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart: Activity 2");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity 2");
    }
}