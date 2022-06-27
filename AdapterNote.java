package com.example.addnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;


public class AdapterNote extends RecyclerView.Adapter<AdapterNote.ViewHolder>{
    
    Context context;
    RealmResults<Note> notesList;

    public AdapterNote(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//here we are inflating the class and not attaching it to the root
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//to keep the note binded
        Note note = notesList.get(position);
        holder.titleout.setText(note.getTitle());
        holder.descout.setText(note.getDescription());

        String formattime = DateFormat.getDateTimeInstance().format(note.CreatedTime);
        holder.timeout.setText(formattime);//since time is in millisecond we need ot format it

        //to delete on long press wwe use this onlong click listener
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("DELETE")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                        }
                        return true;
                    }
                });

                menu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        
        TextView titleout;
        TextView descout;
        TextView timeout;
        
        ViewHolder(@NonNull View itemView){
            super(itemView);
            titleout = itemView.findViewById(R.id.titleout);
            descout = itemView.findViewById(R.id.descout);
            timeout = itemView.findViewById(R.id.timecout);
        }
    }
}
