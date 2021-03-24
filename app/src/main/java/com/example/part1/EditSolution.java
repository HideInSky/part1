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

public class EditSolution extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView2;

    static int photoNameIndex = 0;
    // request code for getting first photo
    final static int REQUEST_CODE_4 = 4;

    EditText edt_conclusion;
    long ID = 0;
    String date = "NA";
    String title = "New Question";
    String times = "0";
    String img1Path = "NA";
    String img2Path = "NA";
    String conclusion = "Conclusion";
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_solution);

        //toolbar, title is default in xmlfile
        toolbar = findViewById(R.id.toolbar_solution);
        setSupportActionBar(toolbar);
        //toolbar enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default image
        imageView2 = findViewById(R.id.img_2_edit);



        // get corresponding note value from EditNote
        Intent i = getIntent();
        date = i.getStringExtra("date");
        title = i.getStringExtra("title");
        times = i.getStringExtra("times");
        img1Path = i.getStringExtra("img1Path");
        img2Path = i.getStringExtra("img2Path");
        conclusion = i.getStringExtra("conclusion");
        ID = i.getLongExtra("ID", 0);

        // set textview of conclusion
        edt_conclusion = findViewById(R.id.txt_conclusion_edit);
        edt_conclusion.setText(conclusion);

        // set default image
        if (!img2Path.equals("NA")) {
            Bitmap bitmap = BitmapFactory.decodeFile(img2Path);
            imageView2.setImageBitmap(bitmap);
        } else{
            imageView2.setImageResource(R.drawable.avatar_12);
        }



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
        if (requestCode == REQUEST_CODE_4 && resultCode == RESULT_OK){
            if (!img2Path.equals("NA")) {
                Bitmap bitmap = BitmapFactory.decodeFile(img2Path);
                imageView2.setImageDrawable(null);
                imageView2.setImageBitmap(bitmap);
            }
        }

    }

    public static String getPhotoName(){
        photoNameIndex++;
        return "solution"+photoNameIndex;
    }

    public void go_to_main(){
        Intent i = new Intent(EditSolution.this, MainActivity.class);
        startActivity(i);
    }

    private void saveHelper(){
        // set the note
        conclusion = edt_conclusion.getText().toString();
        if (conclusion == null || conclusion =="")
            conclusion = "Conclusion";

        note = new Note(ID, title, times, date, conclusion, img1Path, img2Path);
        NoteDatabase db = new NoteDatabase(this);
        db.editNote(note);

    }

    private void cameraHelper(){
        String filename = getPhotoName();
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
            img2Path = imageFile.getAbsolutePath();
            Uri imageUri = FileProvider.getUriForFile(EditSolution.this,
                    "com.example.part1.fileprovider", imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(i, REQUEST_CODE_4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}