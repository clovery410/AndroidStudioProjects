package edu.scu.qjiang.photonotes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhoto extends AppCompatActivity {
    private final String LOG_TAG = "AddPhotoLog";
    private final String albumName = "Photo-Notes";
    private String fileName;
    private String thumbName;
    private PhotoNoteDBHelper dbHelper;
    private Cursor cursor;
    private ImageView mPhotoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        dbHelper = new PhotoNoteDBHelper(this);

        Button takePhoto = (Button) findViewById(R.id.photo_button);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(getApplicationContext(), "Cannot take pictures on this device!", Toast.LENGTH_SHORT).show();
                    return;
                }

                fileName = getOutputFileName();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fileName));

                startActivityForResult(intent, 1234);
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thumbName = getOutputThumbnailName();
                Thumbify thumbify = new Thumbify();
                thumbify.generateThumbnail(fileName.substring(7), thumbName.substring(7));

                int numOfRows = dbHelper.numberOfRows();
                int newOrder;
                if (numOfRows == 0) {
                    newOrder = 0;
                }
                else {
                    cursor = dbHelper.selectMaxOrder();
                    int columnIndex = cursor.getColumnIndex("max_sort_id");
                    cursor.moveToFirst();
                    newOrder = cursor.getInt(columnIndex) + 1;
                    cursor.close();
                }

                EditText et = (EditText)findViewById(R.id.note_caption);
                String caption = et.getText().toString();
                NoteInfo ni = new NoteInfo(newOrder, caption, fileName, thumbName);
                Log.d("file", fileName);

                dbHelper.insertNote(ni);
                dbHelper.close();
                Intent intent = new Intent(AddPhoto.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                //finish();
            }
        });


        acquireRunTimePermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234 || resultCode != RESULT_OK) return;

        mPhotoView = (ImageView) findViewById(R.id.note_photo);
        mPhotoView.setImageURI(Uri.parse(fileName));
    }

    private String getOutputFileName() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("qjiang", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/"
                        + albumName
                        + "/JPEG_"
                        + timeStamp
                        + ".jpg";
        Log.i(LOG_TAG, filename);
        return filename;
    }

    private String getOutputThumbnailName() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("qjiang", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String thumbname =
                "file://"
                        + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/"
                        + albumName
                        + "/THUMB_"
                        + timeStamp
                        + ".jpg";
        Log.i(LOG_TAG, thumbname);
        return thumbname;
    }

    private void acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != 111) return;
        if (grantResults.length >= 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Thumbify.cleanImageView(mPhotoView);
    }


}
