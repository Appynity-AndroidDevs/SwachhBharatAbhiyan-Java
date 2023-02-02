package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.houseOnMap.dao.EmpHouseOnMapDao;
import com.appynitty.swachbharatabhiyanlibrary.houseOnMap.db.HouseScanifyDb;
import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseOnMapPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LatLong;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {

    private static final String TAG = "MapsActivity";
    public static final int DISTANCE_LIMIT = 500;
    private GoogleMap mMap;
    private Double oldLat, newLat, oldLong, newLong;
    private float bearing;
    Button btnConfirmLocation;
    Polyline polyline;
    private SensorManager mSensorManager;
    private Sensor orientationMeter;

    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DOT);
    private ArrayList<LatLong> locationArrayList;
    private List<LatLng> prevLatLongList = new ArrayList<>();
    private float[] mRotationMatrix = new float[16];
    private float mDeclination;
    private float lastBearingAngle = 0;
    private LatLng currentLocation;
    private View transparentView;
    private ProgressBar mProgressBar;
    private FusedLocationProviderClient fusedLocationClient;
    private EmpHouseOnMapDao empHouseOnMapDao;
    LiveData<List<HouseOnMapPojo>> observable;

    @Override
    protected void onResume() {
        super.onResume();
//
        if (mSensorManager != null) {
            mSensorManager.registerListener(this, orientationMeter, SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        empHouseOnMapDao = HouseScanifyDb.getmInstance(getApplication()).empHouseOnMapDao();

        mProgressBar = findViewById(R.id.mapsProgressBar);
        transparentView = findViewById(R.id.transparentWhiteBgMaps);
        transparentView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        //   getLastLocation();

        mDeclination = Float.parseFloat(Prefs.getString(AUtils.Declination, null));
//
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orientationMeter = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        Bundle bundle = getIntent().getExtras();
        oldLat = Double.parseDouble(bundle.getString("lat"));
        oldLong = Double.parseDouble(bundle.getString("lon"));
        locationArrayList = bundle.getParcelableArrayList("marked_lat_long");

        if (locationArrayList != null && !locationArrayList.isEmpty()) {

            for (LatLong latLong : locationArrayList) {

                LatLng currentLocation = new LatLng(Double.parseDouble(latLong.getLatitude()), Double.parseDouble(latLong.getLongitude()));
                prevLatLongList.add(currentLocation);

            }
        } else {
            getHouseOnMapFromRoom();
        }


        currentLocation = new LatLng(oldLat, oldLong);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        transparentView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        if (mapFragment != null) {
            mapFragment.getMapAsync(MapsActivity.this);
        }


        btnConfirmLocation = findViewById(R.id.btnOk);
        btnConfirmLocation.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("newLat", String.valueOf(newLat));
            intent.putExtra("newLong", String.valueOf(newLong));

            setResult(RESULT_OK, intent);
            finish();
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;


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

//        try {
//            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.pk_buildings, MapsActivity.this);
//            layer.getDefaultPolygonStyle().setStrokeColor(Color.WHITE);
//            layer.addLayerToMap();
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);


        if (!prevLatLongList.isEmpty()) {
            for (int i = 0; i < prevLatLongList.size(); i++) {
                // below line is use to add marker to each location of our array list.
                mMap.addMarker(new MarkerOptions().position(prevLatLongList.get(i)).title(locationArrayList.get(i).getReferenceId()).icon(BitmapFromVector(getApplicationContext(), R.drawable.icn_house)));
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 19.0f));
        //  updateCameraBearing(mMap, bearing);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnCameraIdleListener(() -> {

            btnConfirmLocation.setVisibility(View.VISIBLE);
            LatLng midLatLng = mMap.getCameraPosition().target;
            Log.e(TAG, "onCameraIdle: lat: " + midLatLng.latitude + ", lon: " + midLatLng.longitude);
            newLat = midLatLng.latitude;
            newLong = midLatLng.longitude;

            if (polyline != null) polyline.remove();

            PolylineOptions options = new PolylineOptions().pattern(PATTERN_POLYGON_ALPHA).color(Color.RED).width(15);

            polyline = googleMap.addPolyline(options.add(new LatLng(oldLat, oldLong), midLatLng));

            if (calcDistance(midLatLng) > DISTANCE_LIMIT) {
                polyline.remove();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 19.0f));
                String text = getString(R.string.distance_warning, DISTANCE_LIMIT);
                AUtils.info(MapsActivity.this, text);
            }

        });

    }


    private void updateCameraBearing(GoogleMap googleMap, float bearing) {
        if (googleMap == null) return;
        CameraPosition camPos = CameraPosition
                .builder(
                        googleMap.getCameraPosition() // current Camera
                )
                .tilt(0)
                .bearing(bearing)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }

    private double calcDistance(LatLng newPosition) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(oldLat);
        startPoint.setLongitude(oldLong);

        Location endPoint = new Location("locationB");
        if (newPosition != null) {
            endPoint.setLatitude(newPosition.latitude);
            endPoint.setLongitude(newPosition.longitude);
        }

        double distance = startPoint.distanceTo(endPoint);
        Log.e(TAG, "onMarkerDrag: Distance: " + distance);
        return distance;
    }


    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.

        Bitmap bitmap = null;
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        if (vectorDrawable != null)
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        if (vectorDrawable != null) {

            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            // below line is use to add bitmap in our canvas.
            Canvas canvas = new Canvas(bitmap);
            // below line is use to draw our
            // vector drawable in canvas.
            vectorDrawable.draw(canvas);
        }


        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(
                    mRotationMatrix, sensorEvent.values);
            float[] orientation = new float[3];
            SensorManager.getOrientation(mRotationMatrix, orientation);
            float bearing = (float) Math.toDegrees(orientation[0]);


            if (bearing < 0) {
                if (bearing < -25) {

                    if (lastBearingAngle == 0) {
                        updateCameraBearing(mMap, bearing);
                        lastBearingAngle = bearing;
                    } else {

                        if (Math.abs(Math.abs(lastBearingAngle) - Math.abs(bearing)) > 10) {
                            updateCameraBearing(mMap, bearing);
                            lastBearingAngle = bearing;
                        }

                    }
                    Log.i("BEARING_MAP", "onSensorChanged: " + bearing);
                }
            } else {
                if (bearing > 25) {
                    if (lastBearingAngle == 0) {
                        updateCameraBearing(mMap, bearing);
                        lastBearingAngle = bearing;
                    } else {
                        if (Math.abs(Math.abs(lastBearingAngle) - Math.abs(bearing)) > 10) {
                            updateCameraBearing(mMap, bearing);
                            lastBearingAngle = bearing;
                        }
                    }

                    Log.i("BEARING_MAP", "onSensorChanged: " + bearing);

                }
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSensorManager.unregisterListener(this);
        if (observable != null) {
            observable.removeObservers(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (observable != null) {
            observable.removeObservers(this);
        }
    }

    private void getHouseOnMapFromRoom() {

        locationArrayList = new ArrayList<>();
        observable = empHouseOnMapDao.getAllHouseOnMapData();
        observable.observe(this, new Observer<List<HouseOnMapPojo>>() {
            @Override
            public void onChanged(List<HouseOnMapPojo> houseOnMapPojos) {

                if (!houseOnMapPojos.isEmpty()) {
                    for (int i = 0; i < houseOnMapPojos.size(); i++) {

                        LatLong latLong = new LatLong(houseOnMapPojos.get(i).getLatitude(), houseOnMapPojos.get(i).getLongitude(), houseOnMapPojos.get(i).getRefferenceId());
                        locationArrayList.add(latLong);

                        LatLng currentLocation = new LatLng(Double.parseDouble(latLong.getLatitude()), Double.parseDouble(latLong.getLongitude()));
                        prevLatLongList.add(currentLocation);
                    }

                    if (!prevLatLongList.isEmpty()) {
                        for (int i = 0; i < prevLatLongList.size(); i++) {
                            // below line is use to add marker to each location of our array list.
                            mMap.addMarker(new MarkerOptions().position(prevLatLongList.get(i)).title(locationArrayList.get(i).getReferenceId()).icon(BitmapFromVector(getApplicationContext(), R.drawable.icn_house)));
                        }
                    }
                }
            }
        });

    }
}