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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AddSolution extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView2;
    String img2Path = "NA";
    static int photoNameIndex = 0;
    // request code for getting first photo
    final static int REQUEST_CODE_2 = 2;
    EditText edt_conclusion;
    String date = "NA";
    String title = "NA";
    String times = "NA";
    String img1Path = "NA";
    String conclusion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);

        //toolbar, title is default in xmlfile
        toolbar = findViewById(R.id.toolbar_solution);
        setSupportActionBar(toolbar);
        //toolbar enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default image
        imageView2 = findViewById(R.id.img_2);
        imageView2.setImageResource(R.drawable.avatar_12);

        // set textview of conclusion
        edt_conclusion = findViewById(R.id.txt_conclusion);
        conclusion = edt_conclusion.getText().toString();


        Intent i = getIntent();
        date = i.getStringExtra("date");
        title = i.getStringExtra("title");
        if (title == "")
            title = "New Question";
        img1Path = i.getStringExtra("img1Path");
        times = i.getStringExtra("times");
        Log.d("DATE HERE ", date);
        Log.d("TITLE HERE ", title);
        Log.d("IMG1PATH HERE ", img1Path);
        Log.d("TIMES HERE ", times);





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.camera:
                cameraHelper();
                break;
            case R.id.delete:
                go_to_main();
                break;
            case R.id.save:
                saveHelper();
                go_to_main();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_2 && resultCode == RESULT_OK){
            if (!img2Path.equals("NA")) {
                Bitmap bitmap = BitmapFactory.decodeFile(img2Path);
                imageView2.setImageDrawable(null);
                imageView2.setImageBitmap(bitmap);
            }
        }

    }

    public static String getPhotoName(){
        photoNameIndex++;
        return "solution"+String.valueOf(photoNameIndex);
    }

    public void go_to_main(){
        Intent i = new Intent(AddSolution.this, MainActivity.class);
        startActivity(i);
    }

    private void saveHelper(){
        Note note = new Note(title, times, date, conclusion, img1Path, img2Path);
        note.printNote();
        NoteDatabase db = new NoteDatabase(this);
        long noteID = db.addNote(note);
        Toast.makeText(this, "This note is saved" + noteID, Toast.LENGTH_SHORT).show();
    }

    private void cameraHelper(){
        String filename = getPhotoName();
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
            img2Path = imageFile.getAbsolutePath();
            Uri imageUri = FileProvider.getUriForFile(AddSolution.this,
                    "com.example.part1.fileprovider", imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(i, REQUEST_CODE_2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}