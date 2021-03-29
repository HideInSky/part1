package com.example.part1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.Application;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;

public class EditNote extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView1;
    static int photoNameIndex = 0;


    // request code for getting first photo
    final static int REQUEST_CODE_3 = 3;

    EditText edt_title, edt_times;
    TextView txt_date;
    Note note;
    String img1Path = "NA";
    String img2Path = "NA";
    String conclusion = "Conclusion";
    String title = "New Question";
    String times = "0";
    String date = "NA";
    long ID = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //toolbar, title is default in xmlfile
        toolbar = findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);

        //toolbar enable back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set default image
        imageView1 = findViewById(R.id.img_1_edit);

        // set textview of title
        edt_title = findViewById(R.id.questionTitle_edit);
        edt_times = findViewById(R.id.times_edit);
        txt_date = findViewById(R.id.date_edit);

        // get ID from mainactivity, success
        Intent from_adapter = getIntent();
        ID = from_adapter.getLongExtra("ID", -1);

        // get corresponding data through ID
        NoteDatabase db = new NoteDatabase(this);
        note = db.getNote(ID);
        title = note.getQuestionTitle();
        times = note.getDoneTimes();
        date = note.getDateOfCreate();
        img1Path = note.getImg1Path();
        img2Path = note.getImg2Path();
        conclusion = note.getQuestionConclusion();

        // set textview's text
        edt_title.setText(title);
        edt_times.setText(times);
        txt_date.setText(date);

        // set default image
        if (!img1Path.equals("NA")) {
            Bitmap bitmap = BitmapFactory.decodeFile(img1Path);
            imageView1.setImageBitmap(bitmap);
        } else{
            imageView1.setImageResource(R.drawable.avatar_11);
        }

        //button solution set on click listener
        Button solution = findViewById(R.id.btn_solution_edit);
        solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditNote.this, EditSolution.class);
                title = edt_title.getText().toString();
                times = edt_times.getText().toString();


                if (title == null || title == "")
                    title = "New Question";
                if (times == null || times == "")
                    times = "0";
                Log.d("EditNote conlusion", conclusion);

                i.putExtra("date", date);
                i.putExtra("title", title);
                i.putExtra("times", times);
                i.putExtra("img1Path", img1Path);
                i.putExtra("img2Path", img2Path);
                i.putExtra("conclusion", conclusion);
                i.putExtra("ID", ID);
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
        return "question_edit"+photoNameIndex;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_3 && resultCode == RESULT_OK){
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
            Uri imageUri = FileProvider.getUriForFile(EditNote.this,
                    "com.example.part1.fileprovider", imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(i, REQUEST_CODE_3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}