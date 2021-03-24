package com.example.part1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<Note> notes;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        toolbar = findViewById(R.id.toolbar_errorbook);
        setSupportActionBar(toolbar);

        //Note database
        NoteDatabase db = new NoteDatabase(this);
        notes = db.getNotes();

        //recycler view
        recyclerView = findViewById(R.id.rcyc_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set recycler view's adapter
        adapter = new Adapter(this, notes);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Toast.makeText(MainActivity.this,
                    "Add is clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, AddNote.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
}