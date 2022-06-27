package com.example.addnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;


import java.util.Calendar;
import java.util.Locale;

public class TimePickerBtn extends AppCompatActivity {

    int hour,min;
    Button timeButton;
    Button cancelbtn;
    Button goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker_btn);

        timeButton = findViewById(R.id.timeButton);


        goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cancelbtn = findViewById(R.id.cancelbutton);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });
    }



    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener OnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int mn) {
                hour = hr;
                min = mn;

                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,hr);
                c.set(Calendar.MINUTE,min);
                c.set(Calendar.SECOND,0);
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));

                startAlarm(c);
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,OnTimeSetListener,hour,min,true);
        timePickerDialog.setTitle("Select the time");
        timePickerDialog.show();

    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlertRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}