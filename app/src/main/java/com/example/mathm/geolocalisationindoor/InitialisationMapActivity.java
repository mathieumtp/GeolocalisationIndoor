package com.example.mathm.geolocalisationindoor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InitialisationMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialisation_map);
    }

    public void onInitialisationButtonClicked(View v){
        Intent intent = new Intent(InitialisationMapActivity.this, CarteActivity.class);
        intent.putExtra("lat", (float)49.400260);
        intent.putExtra("long",(float)2.800128);
        startActivity(intent);
    }

    public void onSecondButtonClicked(View v){
        Intent intent = new Intent(InitialisationMapActivity.this, CarteActivity.class);
        intent.putExtra("lat", (float)49.400383);
        intent.putExtra("long",(float)2.800404);
        startActivity(intent);
    }

    public void onThirdButtonClicked(View v){
        Intent intent = new Intent(InitialisationMapActivity.this, CarteActivity.class);
        intent.putExtra("lat", (float)49.400511);
        intent.putExtra("long",(float)2.800589);
        startActivity(intent);
    }

    public void onFourthButtonClicked(View v){
        Intent intent = new Intent(InitialisationMapActivity.this, CarteActivity.class);
        intent.putExtra("lat", (float)49.400757);
        intent.putExtra("long",(float)2.800352);
        startActivity(intent);
    }

}

