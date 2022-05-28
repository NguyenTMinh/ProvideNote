package com.example.sqlitetest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitetest.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "note";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method will be automatically called if there is no database with this name existed
    /* From docs: Called when the database is created for the first time. This is where the creation of tables and the initial
     population of the tables should happen.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE " + TABLE_NOTE + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR (225) NOT NULL, " +
                "content VARCHAR (225) NOT NULL" + ")";
        db.execSQL(queryCreateTable);
    }

    /*This method will be called if there is a database with DATABASE_NAME existed but the its version is
    * lower than the DATABASE_VERSION pass in, */
    /* From docs: Called when the database needs to be upgraded. The implementation should use this method
     to drop tables, add tables, or do anything else it needs to upgrade to the new schema version. */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        onCreate(db);
    }

    // Insert new row of table
    // params: bindArgs-Object: only byte[], String, Long and Double are supported in bindArgs.
    public void insertNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        String[] noteObject = {null, note.getTitle(), note.getContent()};
        db.execSQL("INSERT INTO note VALUES (?, ?, ?)", noteObject);
    }

    // Delete a note from table
    public void deleteNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        String queryDelete = "DELETE FROM note WHERE ID = ?";
        db.execSQL(queryDelete, new String[]{String.valueOf(note.getID())});
    }

    // Update a note
    public void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        String queryUpdate = "UPDATE note SET title= ?, content= ? WHERE id= ?";
        String[] noteObject = {note.getTitle(), note.getContent(), String.valueOf(note.getID())};
        db.execSQL(queryUpdate, noteObject);
    }

    public List<Note> getNotes() {
        List<Note> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM note", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int noteID = cursor.getInt(0);
            String noteTitle = cursor.getString(1);
            String noteContent = cursor.getString(2);

            list.add(new Note(noteID, noteTitle, noteContent));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
