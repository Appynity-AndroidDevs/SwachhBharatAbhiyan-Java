package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    public static final int DISTANCE_LIMIT = 50;
    private GoogleMap mMap;
    private Double oldLat, newLat, oldLong, newLong;
    Button btnConfirmLocation;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        try {
            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.pk_buildings, MapsActivity.this);
            layer.getDefaultPolygonStyle().setStrokeColor(Color.WHITE);
            layer.addLayerToMap();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }



        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng currentLocation = new LatLng(oldLat, oldLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20.0f));


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                btnConfirmLocation.setVisibility(View.VISIBLE);
                LatLng midLatLng = mMap.getCameraPosition().target;
                Log.e(TAG, "onCameraIdle: lat: " + midLatLng.latitude + ", lon: " + midLatLng.longitude);
                newLat = midLatLng.latitude;
                newLong = midLatLng.longitude;

                if (polyline != null)
                    polyline.remove();

                PolylineOptions options = new PolylineOptions().pattern(PATTERN_POLYGON_ALPHA)
                        .color(Color.RED)
                        .width(15);

                polyline = googleMap.addPolyline(options.add(new LatLng(oldLat, oldLong), midLatLng));

                if (calcDistance(midLatLng) > DISTANCE_LIMIT) {
                    polyline.remove();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20.0f));
                    String text = getString(R.string.distance_warning, DISTANCE_LIMIT);
                    AUtils.info(MapsActivity.this, text);
                }

            }
        });

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