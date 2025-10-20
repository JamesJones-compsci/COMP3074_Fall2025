package com.example.locationawaredemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * MainActivity
 * -------------
 * This version adds:
 * ✅ Proper permission handling (waits for user approval before starting updates)
 * ✅ Requests both GPS_PROVIDER and NETWORK_PROVIDER (for indoor/outdoor accuracy)
 * ✅ Adds a live “Updated: Xs ago” timer so you can verify continuous updates
 */
public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    public static final int REQUEST_CODE = 1;

    // 🕒 Used for “Updated Xs ago” timer feature
    private long lastUpdateTime = 0;
    private final Handler handler = new Handler();
    private Runnable updateTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Preserve edge-to-edge padding setup (your existing code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 🔹 Define our LocationListener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateUi(location); // Update UI when new location arrives
            }
        };

        // ✅ Step 1: Check permission before requesting updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // If permission not granted, request it
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE
            );
        } else {
            // If permission already granted, start updates
            startGetLocationUpdates();
        }

        // 🕒 Step 2: Initialize “Updated: Xs ago” timer
        setupUpdateTimer();
    }

    /**
     * 🧩 This method requests location updates from both GPS and Network providers.
     * It also uses the last known location if available.
     */
    private void startGetLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return; // Exit if still no permission
        }

        // 🧭 Request both providers for better reliability
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locationListener);

        // 🗺️ Try to show last known location immediately
        Location lastKnownGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location lastKnownNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Location best = (lastKnownGPS != null) ? lastKnownGPS : lastKnownNet;
        if (best != null) {
            updateUi(best);
        }
    }

    /**
     * Handles the result of permission requests.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // ✅ Start listening to location once permission is granted
                startGetLocationUpdates();
            } else {
                // 🚫 Permission denied - you can show a Snackbar here if desired
            }
        }
    }

    /**
     * 🕒 Timer setup that updates a “Updated: Xs ago” label every second.
     */
    private void setupUpdateTimer() {
        updateTimer = new Runnable() {
            @Override
            public void run() {
                if (lastUpdateTime != 0) {
                    long secondsAgo = (System.currentTimeMillis() - lastUpdateTime) / 1000;
                    TextView timeText = findViewById(R.id.update_time_label);
                    timeText.setText("Updated: " + secondsAgo + "s ago");
                }
                handler.postDelayed(this, 1000); // Update every second
            }
        };
        handler.post(updateTimer);
    }

    /**
     * Updates all UI labels (lat, lng, acc, alt, address, and timer)
     */
    private void updateUi(Location location) {
        TextView latText = findViewById(R.id.lat_label);
        TextView lngText = findViewById(R.id.lng_label);
        TextView accText = findViewById(R.id.acc_label);
        TextView altText = findViewById(R.id.alt_label);
        TextView addressText = findViewById(R.id.address_label);
        TextView timeText = findViewById(R.id.update_time_label);

        latText.setText("Latitude: " + location.getLatitude());
        lngText.setText("Longitude: " + location.getLongitude());
        accText.setText("Accuracy: " + location.getAccuracy());
        altText.setText("Altitude: " + location.getAltitude());

        // 🕒 Reset timer display to 0 when new data arrives
        lastUpdateTime = System.currentTimeMillis();
        timeText.setText("Updated: 0s ago");

        // 🌍 GeoCoder - Convert coordinates to address
        String address = "Address not available";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1
            );

            if (addressList != null && addressList.size() > 0) {
                address = "\n";
                if (addressList.get(0).getThoroughfare() != null)
                    address += addressList.get(0).getThoroughfare() + "\n";
                if (addressList.get(0).getLocality() != null)
                    address += addressList.get(0).getLocality() + " ";
                if (addressList.get(0).getPostalCode() != null)
                    address += addressList.get(0).getPostalCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        addressText.setText(address);
    }
}
