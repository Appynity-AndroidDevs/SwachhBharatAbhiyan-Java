package com.appynitty.swachbharatabhiyanlibrary.services;

import static com.appynitty.swachbharatabhiyanlibrary.utils.NotificationCreator.CHANNEL_ID;
import static com.appynitty.swachbharatabhiyanlibrary.utils.NotificationCreator.getNotification;
import static com.appynitty.swachbharatabhiyanlibrary.utils.NotificationCreator.getNotificationId;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

public class GIS_LocationService extends Service {

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    protected LocationRequest createLocationRequest() {
        return new LocationRequest.Builder(0)
                .setIntervalMillis(1000 * 60)
                .setMinUpdateDistanceMeters(5F)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Notification();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = createLocationRequest();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                Toast.makeText(getApplicationContext(),
                        "Lat: " + location.getLatitude() + '\n' +
                                "Long: " + location.getLongitude(), Toast.LENGTH_LONG).show();
            }
        };
        startLocationUpdates();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String channelName = "Location Service for GIS";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(
                    CHANNEL_ID,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chan);
        }

        startForeground(getNotificationId(), getNotification(this));


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
