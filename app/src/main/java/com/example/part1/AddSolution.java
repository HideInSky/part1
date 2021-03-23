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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class AddSolution extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView2;
    String secondPhotoPath = "Null";
    static int photoNameIndex = 0;
    // request code for getting first photo
    final static int REQUEST_CODE_2 = 2;

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
        imageView2 = findViewById(R.id.img_2);
        imageView2.setImageResource(R.drawable.avatar_12);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        if (item.getItemId()==R.id.camera){
            String filename = getPhotoName();
            File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
                secondPhotoPath = imageFile.getAbsolutePath();
                Uri imageUri = FileProvider.getUriForFile(AddSolution.this,
                        "com.example.part1.fileprovider", imageFile);
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(i, REQUEST_CODE_2);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mn_camera, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_2 && resultCode == RESULT_OK){
            if (!secondPhotoPath.equals("Null")) {
                Bitmap bitmap = BitmapFactory.decodeFile(secondPhotoPath);
                imageView2.setImageDrawable(null);
                imageView2.setImageBitmap(bitmap);
            }
        }

    }

    public static String getPhotoName(){
        photoNameIndex++;
        return "solution"+String.valueOf(photoNameIndex);
    }
}