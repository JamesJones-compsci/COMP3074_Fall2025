package com.example.demo3_wk2;

import static com.example.demo3_wk2.MainActivity.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MyReceiver receiver = new MyReceiver();

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
   //         intent = new Intent(
   //                 Intent.ACTION_CALL,
   //                 Uri.parse("tel:5555555555")
   //         );
   //         startActivity(intent);
   //
            // return;

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

                    != PackageManager.PERMISSION_GRANTED
            ){
                // request for the permission
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1
                );


            } else{
                // permission already granted
                callDummyNumber();
            }
        }

    }

    private void callDummyNumber() {
        Intent intent = new Intent(
                Intent.ACTION_CALL,
                 Uri.parse("tel:555555555")
        );
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                callDummyNumber();
            } else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
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

        registerMyReceiver();
    }

    private void registerMyReceiver(){
        // register the my receiver
        IntentFilter filter = new IntentFilter(MyReceiver.ACTION_CUSTOM_BROADCAST);
        registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED);


        Button button = findViewById(R.id.message1);
        button.setOnClickListener(v ->{
            Intent intent = new Intent(MyReceiver.ACTION_CUSTOM_BROADCAST)
                    .setPackage(getPackageName());
            intent.putExtra("msg", "Hello message broadcasted");
            sendBroadcast(intent);
        });
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