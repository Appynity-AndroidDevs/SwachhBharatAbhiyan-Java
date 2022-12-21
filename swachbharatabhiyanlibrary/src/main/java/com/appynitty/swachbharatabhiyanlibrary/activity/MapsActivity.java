package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private Double oldLat, newLat, oldLong, newLong;
    Button btnOk;
    Runnable mRunnable;
    Handler mHandler;
    Polyline polyline;

    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DOT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        oldLat = Double.parseDouble(bundle.getString("lat"));
        oldLong = Double.parseDouble(bundle.getString("lon"));

        btnOk = findViewById(R.id.btnOk);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
//                hide();
                btnOk.setVisibility(View.VISIBLE);
            }
        };

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("newLat", String.valueOf(newLat));
                intent.putExtra("newLong", String.valueOf(newLong));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
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

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        try {
            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.gokulpeth_area, MapsActivity.this);
            layer.addLayerToMap();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        LatLng currentLocation = new LatLng(oldLat, oldLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20.0f));

        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 1000);

        // Add a marker in currentLocation and move the camera

//        addMarker();


        /*mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                LatLng newPosition = marker.getPosition(); //
                double dist = calcDistance(newPosition);

                if (dist > 10) {
//                    Toast.makeText(MapsActivity.this, "You've exceeded the allowed distance", Toast.LENGTH_SHORT).show();
                    AUtils.warning(MapsActivity.this, "You've exceeded the 10 meters distance");
                    marker.remove();
                    addMarker();
                }
                Log.e(TAG, "onMarkerDragEnd: Lat: " + newPosition.latitude + " Long: " + newPosition.longitude);
                newLat = newPosition.latitude;
                newLong = newPosition.longitude;
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
                marker.hideInfoWindow();
            }
        });*/

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling
                LatLng midLatLng = mMap.getCameraPosition().target;
                Log.e(TAG, "onCameraIdle: lat: " + midLatLng.latitude + ", lon: " + midLatLng.longitude);
                newLat = midLatLng.latitude;
                newLong = midLatLng.longitude;

                if (polyline != null)
                    polyline.remove();

                PolylineOptions options = new PolylineOptions().pattern(PATTERN_POLYGON_ALPHA).width(15);

                polyline = googleMap.addPolyline(options.add(new LatLng(oldLat, oldLong), midLatLng));

                if (calcDistance(midLatLng) > 30) {
                    polyline.remove();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20.0f));
                    Toast.makeText(MapsActivity.this, "Distance exceeded the given limit!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void addMarker() {
        LatLng currentLocation = new LatLng(oldLat, oldLong);
        Objects.requireNonNull(mMap.addMarker(new MarkerOptions().position(currentLocation).title("info").snippet(getResources().getString(R.string.map_snippet)).draggable(true))).showInfoWindow();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f)); "Drag to change location!"
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, mMap.getMaxZoomLevel()));
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


}