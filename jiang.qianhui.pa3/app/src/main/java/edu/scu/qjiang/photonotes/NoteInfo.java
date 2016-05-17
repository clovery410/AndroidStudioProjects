package edu.scu.qjiang.photonotes;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by clover on 5/11/16.
 */
public class NoteInfo {
    protected int sort_id;
    protected String caption;
    protected String fullpath;
    protected String thumbpath;

    public NoteInfo (int sort_id, String caption, String fullpath, String thumbpath) {
        this.sort_id = sort_id;
        this.caption = caption;
        this.fullpath = fullpath;
        this.thumbpath = thumbpath;
    }

    public static NoteInfo fromCursor(Cursor cursor) {
        Integer sort_id = cursor.getInt(cursor.getColumnIndex(PhotoNoteDBHelper.NOTES_COLUMN_ORDER));
        String caption = cursor.getString(cursor.getColumnIndex(PhotoNoteDBHelper.NOTES_COLUMN_CAPTION));
        String fullpath = cursor.getString(cursor.getColumnIndex(PhotoNoteDBHelper.NOTES_COLUMN_PICPATH));
        String thumbpath = cursor.getString(cursor.getColumnIndex(PhotoNoteDBHelper.NOTES_COLUMN_THUBPATH));
        NoteInfo noteInfo = new NoteInfo(sort_id, caption, fullpath, thumbpath);
        return noteInfo;
    }

    public Integer getOrder() {
        return sort_id;
    }

    public String getCaption() {
        return caption;
    }

    public String getFullpath() {
        return fullpath;
    }

    public String getThumbpath() {
        return thumbpath;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setFullpath(String fullpath) {
        this.fullpath = fullpath;
    }

    public void setThumbpath(String thumbpath) {
        this.thumbpath = thumbpath;
    }

}

