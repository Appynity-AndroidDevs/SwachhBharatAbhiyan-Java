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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleService;

import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Swapnil Lanjewar on 23-Nov-2022
 */

public class GIS_LocationService extends LifecycleService {
    private static final String TAG = "GIS_LocationService";
    FusedLocationProviderClient fusedLocationClient;
    LocationManager locationManager;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    String userTypeId;
    private LocationRepository mLocationRepository;
    private HousePointRepo mHousePointRepo;
    private List<LocationEntity> mAllLocations = new ArrayList<>();
    private List<HouseLocationEntity> mAllHouses;
    String auth_token = "Bearer " + Prefs.getString(AUtils.BEARER_TOKEN, null);
    private Location lastLocation;
    private String mHardware;

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {
                Log.e(TAG, "onLocationResult: lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", accuracy: " + location.getAccuracy());
                Toast.makeText(GIS_LocationService.this, "new location received with Accuracy: " + location.getAccuracy() + " , Speed: " + location.getSpeed() + " & Provider: " + location.getProvider(), Toast.LENGTH_SHORT).show();

                if (mHardware.equals("qcom")) {
                    if (location.hasAccuracy() && location.getAccuracy() <= 12)
                        insertToDB(location);
                } else {
                    if (location.hasAccuracy() && location.getAccuracy() <= 12 && location.getSpeed() > 0.0)
                        insertToDB(location);
                }
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void insertToDB(Location location) {
        Toast.makeText(GIS_LocationService.this, "Inserting- lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", Accuracy: " + location.getAccuracy(), Toast.LENGTH_SHORT).show();
        lastLocation = location;
        LocationEntity locEntity = new LocationEntity();
        locEntity.setLineElement(location.getLongitude() + " " + location.getLatitude());
        locEntity.setLat(location.getLatitude());
        locEntity.setLon(location.getLongitude());
        locEntity.setAccuracy(location.getAccuracy());
        locEntity.setTimeStamp(AUtils.getGisDateTime());
        locEntity.setSpeed(location.getSpeed());
        mLocationRepository.insert(locEntity);
    }


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
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    protected LocationRequest createLocationRequest() {

        return new LocationRequest.Builder(1000)
                .setIntervalMillis(0)
                .setMinUpdateDistanceMeters(10F)
//                .setWaitForAccurateLocation(true)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationRepository = new LocationRepository(getApplication());
        mHousePointRepo = new HousePointRepo(getApplication());
        new Notification();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = createLocationRequest();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    float speedAccuracy = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        speedAccuracy = location.getSpeedAccuracyMetersPerSecond();
                    }
                    Log.e(TAG, "onLocationResult: lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", accuracy: " + location.getAccuracy());
                    Toast.makeText(GIS_LocationService.this, "new location received with accuracy: " + location.getAccuracy() + " & speedAccuracy: " + speedAccuracy, Toast.LENGTH_SHORT).show();

                    if (location.hasAccuracy() && location.getAccuracy() <= 10) {
                        Toast.makeText(GIS_LocationService.this, "Inserting- lat: " + location.getLatitude() + ", lon: " + location.getLongitude() + ", Accuracy: " + location.getAccuracy(), Toast.LENGTH_SHORT).show();

                        insertToDB(location);
                    }

                }

            }
        };

        final Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        //API level 9 and up
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);

        String fusedProvider = locationManager.getBestProvider(criteria, true);
        String gpsProvider = LocationManager.GPS_PROVIDER;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (Build.HARDWARE.contains("mt")) {
                mHardware = "mt";
                startLocationUpdates();
            } else if (Build.HARDWARE.contains("qcom")) {
                mHardware = "qcom";
                locationManager.requestLocationUpdates(gpsProvider, 0, 12F, locationListenerGPS);
            } else {
                locationManager.requestLocationUpdates(fusedProvider, 0, 12F, locationListenerGPS);
            }
        } else {
            Toast.makeText(this, "Please turn on the GPS!", Toast.LENGTH_SHORT).show();
        }

        //        Log.e(TAG, "onCreate: userDetailPojo: " + userDetailPojo.toString());

//        startLocationUpdates();

        mLocationRepository.getAllLocations().observe(GIS_LocationService.this, locationEntities -> {
            mAllLocations.clear();
            mAllLocations = locationEntities;
        });

        mHousePointRepo.getAllHousePoint().observe(GIS_LocationService.this, houseLocationEntities -> {
            mAllHouses = houseLocationEntities;
            for (HouseLocationEntity houseEntity : houseLocationEntities) {
                Log.e(TAG, "onChanged: houseId: " + houseEntity.getHouseId() + ", UserName: " + houseEntity.getUserName() + ", Latlng: " + houseEntity.getLocationPoint());
                GISRequestDTO housePointDto = new GISRequestDTO();
                housePointDto.setCreateUser(Integer.parseInt(Prefs.getString(AUtils.PREFS.USER_ID, null)));
                housePointDto.setHouseId(Integer.parseInt(houseEntity.getHouseId()));
                housePointDto.setGeom("POINT (" + houseEntity.getLocationPoint() + ")");
                GISWebService service = NetworkConnection.getInstance().create(GISWebService.class);
                service.sendHousePoint(housePointDto).enqueue(new Callback<GISResponseDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<GISResponseDTO> call, @NonNull Response<GISResponseDTO> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("Success")) {
                                mHousePointRepo.deleteHouse(houseEntity.getHouseId());
                            }
                        }
                        Log.e(TAG, "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<GISResponseDTO> call, @NonNull Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

        Timer mTimer = new Timer();
        int nInterval = 1000 * 60 * 2;
        mTimer.schedule(new TtSendLocations(), 10, nInterval);
    }

    private void sendLocations() {
        StringBuilder mLineString = new StringBuilder("Linestring (");
        if (mAllLocations != null && !mAllLocations.isEmpty()) {
            if (mAllLocations.size() > 2) {
                for (int i = 0; i < mAllLocations.size(); i++) {
                    if (i != mAllLocations.size() - 1)
                        mLineString.append(mAllLocations.get(i).getLineElement()).append(", ");
                }

                mLineString.append(mAllLocations.get(mAllLocations.size() - 1).getLineElement()).append(")");
                Log.e(TAG, "sendLocations: LineString: " + mLineString);
                Log.e(TAG, "sendLocations: userId: " + Prefs.getString(AUtils.PREFS.USER_ID, null));
                GISRequestDTO gisRequestDTO = new GISRequestDTO();
                gisRequestDTO.setTrailId(AUtils.getTrailId());
                gisRequestDTO.setStartTs(Prefs.getString(AUtils.GIS_START_TS, null));
                gisRequestDTO.setEndTs(AUtils.getGisDateTime());
                gisRequestDTO.setCreateUser(Integer.parseInt(Prefs.getString(AUtils.PREFS.USER_ID, null)));
                gisRequestDTO.setCreateTs(AUtils.getGisDateTime());
                gisRequestDTO.setUpdateUser(Integer.parseInt(Prefs.getString(AUtils.PREFS.USER_ID, null)));
                gisRequestDTO.setUpdateTs(AUtils.getGisDateTime());
                gisRequestDTO.setGeom(mLineString.toString());

                List<GISRequestDTO> gisRequestDTOList = new ArrayList<>();
                gisRequestDTOList.add(gisRequestDTO);
                Log.e(TAG, "houseMappingTrail body: " + gisRequestDTOList.get(0).toString());

                /*if (Prefs.contains(AUtils.GIS_END_TS)) {
                    if (!AUtils.isNullString(Prefs.getString(AUtils.GIS_END_TS, null))
                            || !Prefs.getString(AUtils.GIS_END_TS, null).isEmpty())
                        gisRequest.setEndTs(Prefs.getString(AUtils.GIS_END_TS, null));
                    Prefs.remove(AUtils.GIS_END_TS);
                }*/

                GISWebService service = NetworkConnection.getInstance().create(GISWebService.class);

                if (userTypeId.equals(AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY)) {
                    sendHouseMapTrail(service, gisRequestDTO);
                } else {
                    sendGarbageTrail(service, gisRequestDTO);
                }
            }
        }
    }

    public void sendHouseMapTrail(GISWebService mService, /*List<*/GISRequestDTO/*>*/ gisRequestDTOList) {
        mService.sendHouseMapTrail(auth_token, Prefs.getString(AUtils.APP_ID, null), gisRequestDTOList).enqueue(new Callback<List<GISResponseDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<GISResponseDTO>> call, @NonNull Response<List<GISResponseDTO>> response) {
                if (response.body() != null) {
                    if (response.body().get(0).getStatus().equals("Success"))
                        mLocationRepository.delete();
                    mAllLocations.clear();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GISResponseDTO>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void sendGarbageTrail(GISWebService mService, GISRequestDTO gisRequestDTO) {
        mService.sendGarbageTrail(gisRequestDTO).enqueue(new Callback<GISResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<GISResponseDTO> call, @NonNull Response<GISResponseDTO> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equals("Success")) mLocationRepository.delete();
                    mAllLocations.clear();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GISResponseDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        userTypeId = Prefs.getString(AUtils.PREFS.USER_TYPE_ID, AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI);
        String channelName = "Location Service for GIS";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        startForeground(getNotificationId(), getNotification(this));
        Log.e(TAG, "started successfully!");
        Toast.makeText(this, TAG + " started successfully!", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
        locationManager.removeUpdates(locationListenerGPS);
        Log.e(TAG, "onDestroy: service done!");
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
