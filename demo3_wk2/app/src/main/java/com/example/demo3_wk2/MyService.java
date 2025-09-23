package com.example.demo3_wk2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {

    // this service is to run a work to toast a message every five seconds

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(MyService.this , "Service is running", Toast.LENGTH_SHORT).show();
            handler.postDelayed(this, 5000); // repeat every 5 seconds
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
