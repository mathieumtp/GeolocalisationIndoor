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
        startActivity(intent);

    }


}
