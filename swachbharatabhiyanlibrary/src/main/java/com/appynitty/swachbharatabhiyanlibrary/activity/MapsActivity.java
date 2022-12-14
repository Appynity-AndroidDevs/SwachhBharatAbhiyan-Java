package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pixplicity.easyprefs.library.Prefs;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Toolbar toolbar;
    private Double newLat, newLong;
    LatLng oldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        toolbar = findViewById(R.id.toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in currentLocation and move the camera
        LatLng currentLocation = new LatLng(Double.parseDouble(Prefs.getString(AUtils.LAT, null)),
                Double.parseDouble(Prefs.getString(AUtils.LONG, null)));

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current location").draggable(true));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, mMap.getMaxZoomLevel()));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                LatLng newPosition = marker.getPosition(); //
                calcDistance(newPosition);
                Log.e(TAG, "onMarkerDragEnd: Lat: " + newPosition.latitude + " Long: " + newPosition.latitude);
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });
    }

    private void calcDistance(LatLng newPosition) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(Prefs.getString(AUtils.LAT, null)));
        startPoint.setLongitude(Double.parseDouble(Prefs.getString(AUtils.LONG, null)));

        Location endPoint = new Location("locationA");
        if (newPosition != null) {
            endPoint.setLatitude(newPosition.latitude);
            endPoint.setLongitude(newPosition.longitude);
        }

        double distance = startPoint.distanceTo(endPoint);
        Log.e(TAG, "onMarkerDrag: Distance: " + distance);
    }


}