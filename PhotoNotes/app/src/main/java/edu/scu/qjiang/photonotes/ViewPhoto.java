package edu.scu.qjiang.photonotes;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        TextView textView = (TextView) findViewById(R.id.view_caption);
        ImageView imageView = (ImageView) findViewById(R.id.view_fullpath);
//        textView.setText(Uri.parse());
//        imageView.setImageURI(Uri.parse(fileName));
    }
}
