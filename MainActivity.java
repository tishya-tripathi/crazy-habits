package com.example.addnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

import  com.example.addnotes.Note;
public class MainActivity extends AppCompatActivity {
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton addNoteBtn = findViewById(R.id.addnewbutn);

        //this calls AddnotActivity
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "NOW ADD A HABIT", Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(MainActivity.this,AddNoteActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> noteLists = realm.where(Note.class).findAll();//.findAll().sort("CreatedTime", Sort.DESCENDING);

        if(flag==1){
            noteLists.sort("CreatedTime",Sort.ASCENDING);
//            RealmResults<Note> noteLists =realm.where(Note.class).findAll().sort("CreatedTime",Sort.ASCENDING);
        }

        else{
            noteLists.sort("CreatedTime",Sort.DESCENDING);
//            realm.where(Note.class).findAll().sort("CreatedTime",Sort.DESCENDING);
        }

        RecyclerView recycle = findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this));//creates and sets a lineaer layout for the recycleview
        AdapterNote adapterNote = new AdapterNote(getApplicationContext(),noteLists);
        recycle.setAdapter(adapterNote);//here we set the adapter
        noteLists.addChangeListener(new RealmChangeListener<RealmResults<Note>>(){

            public void onChange(RealmResults<Note> notes){
                adapterNote.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_drop,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.sorting:  Toast.makeText(this, "sorting ORDER pls gibe ",Toast.LENGTH_SHORT).show();
                                return true;

            case R.id.acending:  Toast.makeText(this, "acending",Toast.LENGTH_SHORT).show();
                                 flag=1;
                                 return true;
            case R.id.decending: Toast.makeText(this, "decending",Toast.LENGTH_SHORT).show();
                                 flag = 0;
                                 return true;

            case R.id.move:  Toast.makeText(this, "movement count",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,StepsCounter.class));
                return true;
            case R.id.timepick:  Toast.makeText(this, "sorting acended",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,TimePickerBtn.class));
                return true;
            case R.id.abtus:  Toast.makeText(this, "abt us page",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,AboutUs.class));
                return true;

            default:return super.onOptionsItemSelected(item);
        }

    }
}