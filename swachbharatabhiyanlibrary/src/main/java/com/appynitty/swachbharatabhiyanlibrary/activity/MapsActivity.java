package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LatLong;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
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

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    public static final int DISTANCE_LIMIT = 50;
    private GoogleMap mMap;
    private Double oldLat, newLat, oldLong, newLong;
    private float bearing;
    Button btnConfirmLocation;

    Polyline polyline;

    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DOT);
    private ArrayList<LatLong> locationArrayList;
    private List<LatLng> prevLatLongList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();
        oldLat = Double.parseDouble(bundle.getString("lat"));
        oldLong = Double.parseDouble(bundle.getString("lon"));
        bearing = Float.parseFloat(Prefs.getString(AUtils.BEARING, null));
        locationArrayList = bundle.getParcelableArrayList("marked_lat_long");

        if (locationArrayList != null && !locationArrayList.isEmpty()) {

            for (LatLong latLong : locationArrayList) {

                LatLng currentLocation = new LatLng(Double.parseDouble(latLong.getLatitude()), Double.parseDouble(latLong.getLongitude()));
                prevLatLongList.add(currentLocation);

            }
        }

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

                // below line is use to zoom our camera on map.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(20.0f));

                // below line is use to move our camera to the specific location.
                mMap.moveCamera(CameraUpdateFactory.newLatLng(prevLatLongList.get(i)));

            }

        }


        LatLng currentLocation = new LatLng(oldLat, oldLong);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20.0f));
        updateCameraBearing(mMap, bearing);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20.0f));
                    String text = getString(R.string.distance_warning, DISTANCE_LIMIT);
                    AUtils.info(MapsActivity.this, text);
                }

            }
        });

    }


    private void updateCameraBearing(GoogleMap googleMap, float bearing) {
        if (googleMap == null) return;
        CameraPosition camPos = CameraPosition
                .builder(
                        googleMap.getCameraPosition() // current Camera
                )
                .bearing(bearing)
                .tilt(0)
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
}