package com.example.part1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //toolbar
        toolbar = findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);
        //toolbar enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default image
        imageView = findViewById(R.id.default_image1);
        imageView.setImageResource(R.drawable.avatar_11);

        //button solution set on click listener
        Button solution = findViewById(R.id.btn_solution);
        solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNote.this,
                        "Solution is clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddNote.this, AddSolution.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera:
                Toast.makeText(AddNote.this,
                        "Camera is clicked", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                this.finish();
                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mn_camera, menu);
        return true;
    }
}