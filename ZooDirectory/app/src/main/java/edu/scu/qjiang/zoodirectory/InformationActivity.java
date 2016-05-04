package edu.scu.qjiang.zoodirectory;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Zoo Directory");
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_background));

        Button button = (Button) findViewById(R.id.phone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPhoneNumberUri = "tel:1-888-888-8888";
                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse(myPhoneNumberUri));
                startActivity(dialIntent);
//                if (dialIntent.resolveActivity(getPackageManager()) == null) {
//                    return;
//                }
//                else {
//                    Log.i("HAHA", "It works");
//                    startActivity(dialIntent);
//                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.zoo_info:
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
}
