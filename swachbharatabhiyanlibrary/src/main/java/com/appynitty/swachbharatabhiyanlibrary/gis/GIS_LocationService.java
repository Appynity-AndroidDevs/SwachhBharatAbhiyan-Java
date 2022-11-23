package com.appynitty.swachbharatabhiyanlibrary.gis;

import static com.appynitty.swachbharatabhiyanlibrary.utils.NotificationCreator.CHANNEL_ID;
import static com.appynitty.swachbharatabhiyanlibrary.utils.NotificationCreator.getNotification;
import static com.appynitty.swachbharatabhiyanlibrary.utils.NotificationCreator.getNotificationId;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GIS_LocationService extends LifecycleService {
    private static final String TAG = "GIS_LocationService";
    private final String URL = "http://114.143.244.130:9091";
    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    private LocationRepository mLocationRepository;
    private List<LocationEntity> mAllLocations;

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
        mLocationRepository = new LocationRepository(getApplication());
        new Notification();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = createLocationRequest();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.e(TAG, "onLocationResult: lat: " + location.getLatitude()
                            + ", lon: " + location.getLongitude()
                            + ", accuracy: " + location.getAccuracy());

                    LocationEntity locEntity = new LocationEntity();
                    locEntity.setLatLng(location.getLatitude() + " " + location.getLongitude());
                    mLocationRepository.insert(locEntity);
                }

            }
        };
        startLocationUpdates();

        mLocationRepository.getAllLocations().observe(GIS_LocationService.this,
                new Observer<List<LocationEntity>>() {
                    @Override
                    public void onChanged(List<LocationEntity> locationEntities) {
                        mAllLocations = locationEntities;
                    }
                });

        Timer mTimer = new Timer();
        int nInterval = 1000 * 60 * 2;
        mTimer.schedule(new TtSendLocations(), 10, nInterval);
    }

    private void sendLocations() {
        Log.e(TAG, "sendLocations: At- " + AUtils.getDateAndTime());
        StringBuilder mLineString = new StringBuilder("Linestring (");
        if (mAllLocations != null && !mAllLocations.isEmpty()) {
            if (mAllLocations.size() > 2) {
                for (int i = 0; i < mAllLocations.size(); i++) {
                    if (i != mAllLocations.size() - 1)
                        mLineString.append(mAllLocations.get(i).getLatLng()).append(", ");
                }

                mLineString.append(mAllLocations.get(mAllLocations.size() - 1).getLatLng()).append(")");
                Log.e(TAG, "sendLocations: LineString: " + mLineString);

                GISRequestDTO gisRequest = new GISRequestDTO();
                gisRequest.setStartTs("2022-11-23T16:54:29.9217753");
                gisRequest.setEndTs(null);
                gisRequest.setCreateUser("Swapy");
                gisRequest.setGeom(mLineString.toString());

                GISWebService service = Connection.createService(GISWebService.class, URL);
                service.sendGISData(gisRequest).enqueue(new Callback<GISResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<GISResponseDTO> call, @NonNull Response<GISResponseDTO> response) {
                        if (response.body() != null) {
                            Log.e(TAG, "onResponse: Status: " + response.body().getStatus());
                            if (response.body().getStatus().equals("Success"))
                                mLocationRepository.delete();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GISResponseDTO> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String channelName = "Location Service for GIS";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
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
    public IBinder onBind(@NonNull Intent intent) {
        super.onBind(intent);
        return null;
    }

    private class TtSendLocations extends TimerTask {

        @Override
        public void run() {
            sendLocations();
        }
    }


}
