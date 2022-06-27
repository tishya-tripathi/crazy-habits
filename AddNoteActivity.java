package com.example.addnotes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity{// implements View.onClick(View v){


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        EditText titleinput = findViewById(R.id.titleinput);
        EditText descinput = findViewById(R.id.descinput);
        MaterialButton save = findViewById(R.id.savebtn);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleinput.getText().toString();
                String desc = descinput.getText().toString();
                long time = System.currentTimeMillis();

                realm.beginTransaction();//We begin the act of adding to realm database
                Note note = realm.createObject(Note.class);//we create an object note to add
                note.setTitle(title);//from here we add our user inputted title desc and time
                note.setDescription(desc);
                note.setCreatedTime(time);
                realm.commitTransaction();//here we commit the actions to database

                Toast.makeText(getApplicationContext(),"Habit Saved",Toast.LENGTH_SHORT);
                finish();//this will close the activity of addnote
            }
        });
    }
}