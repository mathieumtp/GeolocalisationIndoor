package com.example.mathm.geolocalisationindoor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

/**
 * Class which knows the azimuth of the smartphone(using accelerometer and magnetometer sensors).
 * @author Camille Quenin
 */
public class CompassActivity implements SensorEventListener {

    //private CompassView compassView; //TODO: once it is useless, the compassView must be deleted.
    public SensorManager sensorManager;
    public Sensor accelerometer;
    public Sensor magnetometer;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private float azimuth=0; // Angle between the device's current compass direction and magnetic north.

    public float getAzimuth(){
        return azimuth;
    }


    /* Methods of Activity */

    public CompassActivity(CarteActivity activity){
        sensorManager = (SensorManager)activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }


    /* Methods of SensorEventListener */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accelerometerReading = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magnetometerReading = event.values;
        }
        if (accelerometerReading != null && magnetometerReading != null) {
            float rotationMatrix[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(rotationMatrix, orientation);
                if(Math.abs(orientation[0] - azimuth) < 15)
                azimuth = orientation[0]; // orientation contains: azimuth, pitch and roll
            }
        }
        //compassView.invalidate();
    }
}
