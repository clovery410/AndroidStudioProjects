package edu.scu.qjiang.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clover on 5/9/16.
 */
public class PhotoNoteDBHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = PhotoNoteDBHelper.class.getName();

    //If change the database schema, must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "PhotoNote-qjiang.db";
    public static final String NOTES_TABLE_NAME = "notes";
    public static final String NOTES_COLUMN_ID = "_id";
    public static final String NOTES_COLUMN_ORDER = "sort_id";
    public static final String NOTES_COLUMN_CAPTION = "caption";
    public static final String NOTES_COLUMN_PICPATH = "fullpath";
    public static final String NOTES_COLUMN_THUBPATH = "thumbpath";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE notes (" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " sort_id INT," +
                    " caption TEXT," +
                    " fullpath TEXT," +
                    " thumbpath TEXT);";

    static private final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS notes";

    Context context;

    public PhotoNoteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    public boolean insertNote (NoteInfo ni) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sort_id", ni.sort_id);
        contentValues.put("caption", ni.caption);
        contentValues.put("fullpath", ni.fullpath);
        contentValues.put("thumbpath", ni.thumbpath);
        db.insert("notes", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM notes WHERE _id = " + id + "", null);
        return res;
    }

    public Cursor fetchAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM notes ORDER BY sort_id DESC;", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTES_TABLE_NAME);
        return numRows;
    }

    public Boolean deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("notes", "_id = ? ", new String[] { String.valueOf(id) });
        if (result > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor selectMaxOrder(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT MAX(sort_id) AS max_sort_id FROM notes;", null);
    }

    public void updateOrderID(Integer newID, int rowid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE " + NOTES_TABLE_NAME + " SET " + NOTES_COLUMN_ORDER + " = " + newID + " WHERE " + NOTES_COLUMN_ID + " = " + rowid;
        db.execSQL(strSQL);
    }

}
