package edu.scu.qjiang.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clover on 5/9/16.
 */
public class PhotoNoteDBHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = PhotoNoteDBHelper.class.getName();

    //If change the database schema, must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhotoNote-qjiang.db";
    public static final String NOTES_TABLE_NAME = "notes";
    public static final String NOTES_COLUMN_ID = "_id";
    public static final String NOTES_COLUMN_CAPTION = "caption";
    public static final String NOTES_COLUMN_PICPATH = "fullpath";
    public static final String NOTES_COLUMN_THUBPATH = "thumbpath";

    static private final String SQL_CREATE_TABLE =
            "CREATE TABLE notes (" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
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
        return db.rawQuery("SELECT * FROM notes;", null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, NOTES_TABLE_NAME);
        return numRows;
    }

    public Integer deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("notes", "_id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<NoteInfo> getAllNotes() {
        ArrayList<NoteInfo> arrayList = new ArrayList<NoteInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(" select * from notes", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            NoteInfo noteInfo = new NoteInfo();
            noteInfo.caption = res.getString(res.getColumnIndex(NOTES_COLUMN_CAPTION));
            noteInfo.fullpath = res.getString(res.getColumnIndex(NOTES_COLUMN_PICPATH));
            noteInfo.thumbpath = res.getString(res.getColumnIndex(NOTES_COLUMN_THUBPATH));
            arrayList.add(noteInfo);
            res.moveToNext();
        }
        return arrayList;
    }
}
