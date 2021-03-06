package com.example.part1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    private static final String KEY_IMAGE1_PATH = "img1path";
    private static final String KEY_IMAGE2_PATH = "img2path";

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
                KEY_CONCLUSION+ " TEXT,"+
                KEY_IMAGE1_PATH + " TEXT,"+
                KEY_IMAGE2_PATH + " TEXT"+ ")";
        db.execSQL(query);

    }
    public Note getNote(long id){
        // select * from database where ID is 1
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_TITLE, KEY_TIMES,KEY_DATE, KEY_CONCLUSION, KEY_IMAGE1_PATH, KEY_IMAGE2_PATH}, KEY_ID+"=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor!= null)
            cursor.moveToFirst();
        return new Note(cursor.getLong(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5),
                cursor.getString(6));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public List<Note> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> allNotes = new ArrayList<>();

        // select * from database name

        String query = "SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Note note = new Note();
                note.setID(cursor.getLong(0));
                note.setQuestionTitle(cursor.getString(1));
                note.setDoneTimes(cursor.getString(2));
                note.setDateOfCreate(cursor.getString(3));
                note.setQuestionConclusion(cursor.getString(4));
                note.setImg1Path(cursor.getString(5));
                note.setImg2Path(cursor.getString(6));
                allNotes.add(note);
            } while(cursor.moveToNext());

        }
        return allNotes;
    }

    public boolean editNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getQuestionTitle());
        c.put(KEY_TIMES, note.getDoneTimes());
        c.put(KEY_DATE, note.getDateOfCreate());
        c.put(KEY_CONCLUSION, note.getQuestionConclusion());
        c.put(KEY_IMAGE1_PATH, note.getImg1Path());
        c.put(KEY_IMAGE2_PATH, note.getImg2Path());
        db.update(DB_TABLE, c, KEY_ID+"=?",
                new String[]{String.valueOf(note.getID())});
        return true;
    }


    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getQuestionTitle());
        c.put(KEY_TIMES, note.getDoneTimes());
        c.put(KEY_DATE, note.getDateOfCreate());
        c.put(KEY_CONCLUSION, note.getQuestionConclusion());
        c.put(KEY_IMAGE1_PATH, note.getImg1Path());
        c.put(KEY_IMAGE2_PATH, note.getImg2Path());


        long ID = db.insert(DB_TABLE, null, c);

        //Log
        Log.d("inserted", " ID -> " + ID);
        return ID;

    }
    /**
     * Helper function that parses a given table into a string
     * and returns it for easy printing. The string consists of
     * the table name and then each row is iterated through with
     * column_name: value pairs printed out.
     *
     * how to use:
     * NoteDatabase db = new NoteDatabase(this);
     * db.printNotes();
     *
     * @return the table tableName as a string
     */
    public void printNotes() {
        SQLiteDatabase db = getWritableDatabase();
        String tableName = DB_TABLE;
        Log.d("PRINT", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        Log.d("DATABASE", tableString);
    }
}
