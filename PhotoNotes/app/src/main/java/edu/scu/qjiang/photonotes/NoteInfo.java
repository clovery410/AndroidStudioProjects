package edu.scu.qjiang.photonotes;

import android.provider.BaseColumns;

/**
 * Created by clover on 5/11/16.
 */
public class NoteInfo {
    protected String caption;
    protected String fullpath;
    protected String thumbpath;

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

