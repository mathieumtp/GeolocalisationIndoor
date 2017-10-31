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
    private Float azimuth; // Angle between the device's current compass direction and magnetic north.

    public Float getAzimuth(){
        return azimuth;
    }

    /*
    public class CompassView extends View {
        Paint paint = new Paint();
        public CompassView(Context context) {
            super(context);
            paint.setColor(0xff00ff00);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
        };

        protected void onDraw(Canvas canvas) {
            int width = getWidth();
            int height = getHeight();
            int centerx = width/2;
            int centery = height/2;
            canvas.drawLine(centerx, 0, centerx, height, paint);
            canvas.drawLine(0, centery, width, centery, paint);

            // Rotate the canvas with the northOrientation
            if (azimuth != null)
                canvas.rotate(-azimuth*360/(2*3.14159f), centerx, centery);
            paint.setColor(0xff0000ff);
            canvas.drawLine(centerx, -1000, centerx, +1000, paint);
            canvas.drawLine(-1000, centery, 1000, centery, paint);
            canvas.drawText("N", centerx+5, centery-10, paint);
            canvas.drawText("S", centerx-10, centery+15, paint);
            paint.setColor(0xff00ff00);
        }
    }

*/
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
                azimuth = orientation[0]; // orientation contains: azimuth, pitch and roll
            }
        }
        //compassView.invalidate();
    }
}
