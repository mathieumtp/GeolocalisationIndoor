/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.mathm.geolocalisationindoor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class SimplePedometerActivity implements SensorEventListener, StepListener {
  private TextView textView;
  private SimpleStepDetector simpleStepDetector;
  public SensorManager sensorManager;
  public Sensor accel;
  private static final String TEXT_NUM_STEPS = "Number of Steps: ";
  private int numSteps;
  private CarteActivity m_activity;
  private long lastTime=0;

  public SimplePedometerActivity(CarteActivity activity){
    m_activity = activity;
    sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    simpleStepDetector = new SimpleStepDetector();
    simpleStepDetector.registerListener(this);
    numSteps = 0;
  }



  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      simpleStepDetector.updateAccel(
          event.timestamp, event.values[0], event.values[1], event.values[2]);



    }
  }


  
  @Override
  public void step(long timeNs) {
    numSteps++;

      if(m_activity.mMap != null && (lastTime - timeNs) < 1000000000)
      {

          LatLng pointM = m_activity.calculNewPosition();

          //LatLng pointM = new LatLng(49.400350, (2.800500+ ((float)numSteps/1000000)));

          m_activity.latlong.add(pointM);

        /*
          for (LatLng point : m_activity.latlong) {
              m_activity.options.position(point);
              m_activity.options.title("NouveauMarker");
              m_activity.options.snippet("NouvellePosition");

              m_activity.mMap.addMarker(m_activity.options);
          }

           */


          PolylineOptions rectOptions = new PolylineOptions()
                  .addAll(m_activity.latlong)
                  .color(Color.BLUE);
          Polyline polyline = m_activity.mMap.addPolyline(rectOptions);

          float zoom = 25.0f;
          m_activity.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointM,zoom));

        lastTime = timeNs;
      }

  }

}
