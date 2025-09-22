package com.example.demo3_wk2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_CUSTOM_BROADCAST = "com.example.broadcast_demo.CUSTOM_BROADCAST";

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ){
        if(ACTION_CUSTOM_BROADCAST.equals(intent.getAction())){
            String message = intent.getStringExtra("msg");
            Toast.makeText(context, "Received: " + message, Toast.LENGTH_SHORT).show();
            Log.d("MyReceiver", "onReceive: " + message);
        }
    }
}
