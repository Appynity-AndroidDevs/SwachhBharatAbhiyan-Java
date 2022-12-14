package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    private String newLat, newLong;
    LatLng oldPosition;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        newLat = bundle.getString("lat");
        newLong = bundle.getString("lon");

//        newLat = Prefs.getString(AUtils.LAT, null);
//        newLong = Prefs.getString(AUtils.LONG, null);

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newLat", newLat);
                intent.putExtra("newLong", newLong);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
        LatLng currentLocation = new LatLng(Double.parseDouble(newLat),
                Double.parseDouble(newLong));

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
                Log.e(TAG, "onMarkerDragEnd: Lat: " + newPosition.latitude + " Long: " + newPosition.longitude);
                newLat = String.valueOf(newPosition.latitude);
                newLong = String.valueOf(newPosition.longitude);
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