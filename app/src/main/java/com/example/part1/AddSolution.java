package com.example.part1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class AddSolution extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);

        //toolbar
        toolbar = findViewById(R.id.toolbar_solution);
        setSupportActionBar(toolbar);
        //toolbar enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default image
        imageView = findViewById(R.id.default_image2);
        imageView.setImageResource(R.drawable.avatar_12);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        if (item.getItemId()==R.id.camera)
            Toast.makeText(AddSolution.this,
                    "Camera is clicked", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mn_camera, menu);
        return true;
    }
}