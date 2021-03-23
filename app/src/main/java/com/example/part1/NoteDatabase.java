package com.example.part1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
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
