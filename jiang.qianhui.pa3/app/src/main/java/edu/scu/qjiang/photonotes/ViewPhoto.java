package edu.scu.qjiang.photonotes;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        String caption = (String) getIntent().getExtras().get("captionContent");
        String fullpath = (String) getIntent().getExtras().get("fullImage");

        TextView textView = (TextView) findViewById(R.id.view_caption);
        ImageView imageView = (ImageView) findViewById(R.id.view_fullpath);
        textView.setText(caption);
        imageView.setImageURI(Uri.parse(fullpath));
    }
}
