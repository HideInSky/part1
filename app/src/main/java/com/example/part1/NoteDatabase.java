package com.example.part1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteDatabase extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "NoteDB";
    private static final String DB_TABLE = "NoteTable";

    //colunms name for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TIMES = "times";
    private static final String KEY_DATE = "date";
    private static final String KEY_CONCLUSION = "conclusion";

    NoteDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table, specify table name, etc.
        // (id INT PRIMARY KEY, title TEXT,
        // content TEXT, date TEXT, time TEXT)
        String query = "CREATE TABLE "+ DB_TABLE +
                " (" + KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT,"+
                KEY_TIMES+ " TEXT,"+
                KEY_DATE + " TEXT," +
                KEY_CONCLUSION+ " TEXT"+")";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }
}
