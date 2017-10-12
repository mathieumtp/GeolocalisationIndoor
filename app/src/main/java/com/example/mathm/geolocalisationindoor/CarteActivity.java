package com.example.mathm.geolocalisationindoor;

import android.graphics.Color;
import android.hardware.SensorManager;
import android.location.Location;
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
import android.graphics.Color;

import java.util.ArrayList;

public class CarteActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public MarkerOptions options = new MarkerOptions();
    public ArrayList<LatLng> latlong = new ArrayList<>();
    private SimplePedometerActivity pedometerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        pedometerActivity = new SimplePedometerActivity(CarteActivity.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        pedometerActivity.sensorManager.registerListener(pedometerActivity, pedometerActivity.accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        pedometerActivity.sensorManager.unregisterListener(pedometerActivity);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        //PG1 entrée 49.400260, 2.800128

        LatLng compiegne = new LatLng(49.400260, 2.800128);
        LatLng compiegne2 = new LatLng(49.400300, 2.800135);
        LatLng compiegne3 = new LatLng(49.400350, 2.800500);

        latlong.add(compiegne);
        latlong.add(compiegne2);
        latlong.add(compiegne3);

        for (LatLng point : latlong) {
            options.position(point);
            options.title("someTitle");
            options.snippet("someDesc");

            mMap.addMarker(options);
        }

        PolylineOptions rectOptions = new PolylineOptions()
                .addAll(latlong)
                .color(Color.BLUE);
        Polyline polyline = mMap.addPolyline(rectOptions);

        /*
        mMap.addMarker(new MarkerOptions().position(compiegne).title("PG1 entrée"));


        */

        float zoom = 20.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(compiegne,zoom));

    }
}
