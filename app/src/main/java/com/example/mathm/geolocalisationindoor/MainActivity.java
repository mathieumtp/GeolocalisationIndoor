package com.example.mathm.geolocalisationindoor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMapButtonClicked(View v){

        Intent intent = new Intent(MainActivity.this, InitialisationMapActivity.class);
        startActivity(intent);

    }

    public void onCompassButtonClicked(View v){

        Intent intent = new Intent(MainActivity.this, CompassActivity.class);
        startActivity(intent);

    }


}
