package com.example.part1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView1;
    String firstPhotoPath = "Null";
    static int photoNameIndex = 0;
    // request code for getting first photo
    final static int REQUEST_CODE_1 = 1;

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
        imageView1 = findViewById(R.id.img_1);
        imageView1.setImageResource(R.drawable.avatar_11);

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
                String filename = getPhotoName();
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
                    firstPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(AddNote.this,
                            "com.example.part1.fileprovider", imageFile);
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(i, REQUEST_CODE_1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public static String getPhotoName(){
        photoNameIndex++;
        return "question"+String.valueOf(photoNameIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_1 && resultCode == RESULT_OK){
            if (!firstPhotoPath.equals("Null")) {
                Bitmap bitmap = BitmapFactory.decodeFile(firstPhotoPath);
                imageView1.setImageDrawable(null);
                imageView1.setImageBitmap(bitmap);
            }
        }

    }
}