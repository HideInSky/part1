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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView1;
    static int photoNameIndex = 0;
    // request code for getting first photo
    final static int REQUEST_CODE_1 = 1;
    Calendar calendar;
    EditText edt_title, edt_times;

    String date = "NA";
    String title = "New Question";
    String times = "0";
    String img1Path = "NA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //toolbar, title is default in xmlfile
        toolbar = findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);

        //toolbar enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default image
        imageView1 = findViewById(R.id.img_1);
        imageView1.setImageResource(R.drawable.avatar_11);

        // set textview of title
        edt_title = findViewById(R.id.questionTitle);
        edt_times = findViewById(R.id.times);


        // calendar to get date
        calendar = Calendar.getInstance();
        // c.get(Calendar.YEAR)
        date = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/"
                + calendar.get(Calendar.DAY_OF_MONTH);



        //button solution set on click listener
        Button solution = findViewById(R.id.btn_solution);
        solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNote.this,
                        "Solution is clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddNote.this, AddSolution.class);
                title = edt_title.getText().toString();
                times = edt_times.getText().toString();

                if (title == null || title == "")
                    title = "New Question";
                if (times == null || times == "")
                    times = "0";

                i.putExtra("date", date);
                i.putExtra("title", title);
                i.putExtra("img1Path", img1Path);
                i.putExtra("times", times);

                startActivity(i);
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.camera:
                cameraHelper();
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
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    public static String getPhotoName(){
        photoNameIndex++;
        return "question"+ photoNameIndex;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_1 && resultCode == RESULT_OK){
            if (!img1Path.equals("NA")) {
                Bitmap bitmap = BitmapFactory.decodeFile(img1Path);
                imageView1.setImageDrawable(null);
                imageView1.setImageBitmap(bitmap);
            }
        }

    }



    private void cameraHelper(){
        String filename = getPhotoName();
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
            img1Path = imageFile.getAbsolutePath();
            Uri imageUri = FileProvider.getUriForFile(AddNote.this,
                    "com.example.part1.fileprovider", imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(i, REQUEST_CODE_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}