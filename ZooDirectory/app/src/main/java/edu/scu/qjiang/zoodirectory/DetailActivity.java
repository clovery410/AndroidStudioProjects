package edu.scu.qjiang.zoodirectory;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Zoo Directory");
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_background));

//        Toast.makeText(getApplicationContext(), "DetailActivity onCreate()" + getIntent().getExtras().get("name") + getIntent().getExtras().get("description"), Toast.LENGTH_SHORT).show();

        String animal_name = (String) getIntent().getExtras().get("name");
        Integer animal_pic = (Integer) getIntent().getExtras().get("big_pic");
        String animal_description = (String) getIntent().getExtras().get("description");

        TextView d_name = (TextView) findViewById(R.id.detail_name);
        ImageView d_img = (ImageView) findViewById(R.id.detail_img);
        TextView d_des = (TextView) findViewById(R.id.detail_description);

        d_name.setText(animal_name);
        d_img.setImageResource(animal_pic);
        d_des.setText(animal_description);

        Button button = (Button) findViewById(R.id.home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                Intent infoIntent = new Intent(DetailActivity.this, InformationActivity.class);
                startActivity(infoIntent);
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
