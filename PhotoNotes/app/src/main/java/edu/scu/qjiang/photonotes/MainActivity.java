package edu.scu.qjiang.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "MainActivityLog";
    private List<NoteInfo> noteList;
    MyRecyclerAdapter mra;
    PhotoNoteDBHelper dbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new PhotoNoteDBHelper(this);
        cursor = dbHelper.fetchAll();
//        cursor.moveToFirst();
//        noteList = cursor.getClass(cursor.getColumnIndexOrThrow())

        RecyclerView recList = (RecyclerView) findViewById(R.id.noteList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

//        noteList = new ArrayList<NoteInfo>();
        noteList = dbHelper.getAllNotes();
//        mra = new MyRecyclerAdapter(noteList);
        mra = new MyRecyclerAdapter(cursor);
        recList.setAdapter(mra);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPhotoIntent = new Intent(MainActivity.this, AddPhoto.class);
                startActivity(addPhotoIntent);
            }
        });

        TouchHelperCallback callback = new TouchHelperCallback(mra);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recList);

    }

    public void onItemClick(int position) {
        cursor.moveToPosition(position);
        Intent viewIntent = new Intent(MainActivity.this, ViewPhoto.class);
        viewIntent.putExtra("imagePath", Uri.parse(cursor.getString(cursor.getColumnIndex("fullpath"))));
        viewIntent.putExtra("captionContent", cursor.getString(cursor.getColumnIndex("caption")));
        startActivity(viewIntent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.add_photo:
                Intent addPhotoIntent = new Intent(MainActivity.this, AddPhoto.class);
                startActivity(addPhotoIntent);
                break;

            case R.id.uninstall:
                Intent deleteIntent = new Intent(Intent.ACTION_DELETE);
                deleteIntent.setData(Uri.parse("package:" + this.getPackageName()));
                startActivity(deleteIntent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdir()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }



}
