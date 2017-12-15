package com.example.mathm.geolocalisationindoor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Class which determines the azimuth of the smartphone (using accelerometer and magnetometer sensors).
 * @author Camille Quenin
 */
public class Compass implements SensorEventListener {

    public SensorManager sensorManager;
    public Sensor accelerometer;
    public Sensor magnetometer;
    private float[] accelerometerReading;
    private float[] magnetometerReading;
    private float azimuth=0; // Angle between the device's current compass direction and magnetic north.

    public float getAzimuth(){
        return azimuth;
    }

    public Compass(CarteActivity activity){
        sensorManager = (SensorManager)activity.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

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
    }
}
