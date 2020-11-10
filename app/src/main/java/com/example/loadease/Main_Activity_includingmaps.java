package com.example.loadease;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.loadeasex.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class Main_Activity_includingmaps extends AppCompatActivity implements OnMapReadyCallback, OnClickListener {
//ll
    SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    int loop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_drawer_main);
        initViewsID();
        setupDrawer();
        setupmap();
    }

    private void initViewsID() {
        findViewById(R.id.locfloat).setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker in Sydney and move the camera
        settingupzoomformap();
        setupmylocationonMap(null);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.locfloat:
                startLocationUpdates();
                break;
        }

    }

    private void setupDrawer(){

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        //l
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
    private void setupmap(){


        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void settingupzoomformap(){
//availible options
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
    }

    private void setupmylocationonMap(LatLng latLng){
        if (latLng!=null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("My def loc")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
            });
        }else {
       Lastknownloc();
        }
    }
    private void Lastknownloc(){
//        nulll ki jaga last known loc

        setupmylocationonMap(new LatLng(30.3753, 69.3451));
    }



    private void startLocationUpdates() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }else {
            mFusedLocationClient.requestLocationUpdates(setiingupGPSsetting(),
                    GpsCallbacks(),
                    Looper.getMainLooper());
        }
    }
    private LocationCallback GpsCallbacks() {


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (loop<4) {
                        loop++;
                        // Update UI with location data
                        setupmylocationonMap(new LatLng(location.getLatitude(), location.getLongitude()));
                    }else {
                        stopLocationUpdates();

                    }
                }
            }


        };

        return locationCallback;

    }
    private void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
        return;
    }
    private LocationRequest setiingupGPSsetting() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); //10 sec
        locationRequest.setFastestInterval(5 * 1000);// 5 sec

        return locationRequest;

    }


}