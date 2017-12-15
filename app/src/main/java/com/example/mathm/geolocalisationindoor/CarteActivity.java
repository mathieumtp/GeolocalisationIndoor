package com.example.mathm.geolocalisationindoor;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class CarteActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public MarkerOptions options = new MarkerOptions();
    public ArrayList<LatLng> latlong = new ArrayList<>();
    private SimplePedometerActivity pedometerActivity;
    private Compass compass;
    public double value=2.528584;
    public double BaseLat= 49.400260;
    public double BaseLong= 2.800128;

    //porte 49.400218, 2.800132

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        final Intent intent = getIntent();
        BaseLat = intent.getFloatExtra("lat",0);
        BaseLong = intent.getFloatExtra("long",0);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pedometerActivity = new SimplePedometerActivity(CarteActivity.this);
        compass = new Compass(CarteActivity.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        pedometerActivity.sensorManager.registerListener(pedometerActivity, pedometerActivity.accel, SensorManager.SENSOR_DELAY_FASTEST);
        compass.sensorManager.registerListener(compass, compass.accelerometer, SensorManager.SENSOR_DELAY_UI);
        compass.sensorManager.registerListener(compass, compass.magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        pedometerActivity.sensorManager.unregisterListener(pedometerActivity);
        compass.sensorManager.unregisterListener(compass);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public LatLng calculNewPosition() {

        float taillePas = 0.74f; //En m
        float normeValue = (1/111111f)* taillePas; //111 111 metres = 1 degré latitude
        float az = compass.getAzimuth()*360/(2*3.14159f);
        BaseLat += cos(Math.toRadians(az))* normeValue;
        BaseLong += sin(Math.toRadians(az))* normeValue;


        value += normeValue;
        return new LatLng(BaseLat, BaseLong);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //PG1 entrée 49.400260, 2.800128


        LatLng compiegne = new LatLng(BaseLat,BaseLong);
        latlong.add(compiegne);
/*
        LatLng debutCouloir = new LatLng(49.400383, 2.800404);
        LatLng finCouloir = new LatLng(49.400511, 2.800589);
        LatLng finMi12 = new LatLng(49.400757, 2.800352);


        latlong.add(debutCouloir);
        latlong.add(finCouloir);
        latlong.add(finMi12);
*/
        for (LatLng point : latlong) {
            options.position(point);
            options.title("PG1");
            options.snippet("Départ");

            mMap.addMarker(options);
        }

        PolylineOptions rectOptions = new PolylineOptions()
                .addAll(latlong)
                .color(Color.BLUE);
        Polyline polyline = mMap.addPolyline(rectOptions);

        /*
        mMap.addMarker(new MarkerOptions().position(compiegne).title("PG1 entrée"));


        */

        float zoom = 22.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(compiegne,zoom));

    }
}
