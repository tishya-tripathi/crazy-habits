package com.example.addnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorEventListener;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import io.realm.Realm;

public class StepsCounter extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    boolean run = true;
    TextView step;
    int prevsteps=0;
    CircularProgressBar turn;

    EditText motivation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_counter);
        turn = findViewById(R.id.circularProgressBar);
        step = findViewById(R.id.stepstaken);
        motivation = findViewById(R.id.doinginfo);
        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"test1",Toast.LENGTH_SHORT);
        run = true;
        Sensor count = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(count==null){
            Toast.makeText(this,"No Sensors on UR phone srry",Toast.LENGTH_SHORT);
        }
        else{
            sensorManager.registerListener((SensorEventListener) this,count,SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if(run){
            step.setText(String.valueOf(sensorEvent.values[0]));
            Toast.makeText(this,"test2",Toast.LENGTH_SHORT);
            turn.setProgressWithAnimation(sensorEvent.values[0]);

            if(sensorEvent.values[0]<1000)
               motivation.setText("WEAK");

            else if(sensorEvent.values[0]>=1000 && sensorEvent.values[0]<2000)
                motivation.setText("OKISH");

            else if(sensorEvent.values[0]>=2000 && sensorEvent.values[0]<4000)
                motivation.setText("AMAZING");

            else if(sensorEvent.values[0]>=4000 && sensorEvent.values[0]<5000)
                motivation.setText("ATHLETE");

            else if(sensorEvent.values[0]>=5000)
                motivation.setText("GOD LEVEL");
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}