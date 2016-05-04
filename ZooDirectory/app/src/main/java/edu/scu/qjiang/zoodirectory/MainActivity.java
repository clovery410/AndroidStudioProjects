package edu.scu.qjiang.zoodirectory;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Zoo Directory");
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_background));
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setHomeButtonEnabled(true);


        //Animal List
        animals = new ArrayList<>();
        animals.add(new Animal("Swamp Monkey", R.drawable.monkey, R.drawable.big_monkey, "Allen’s swamp monkeys “go fishing” by placing leaves or grass on top of the water and grabbing fish that come to hide underneath. As their name implies, swamp monkeys live near water and are good swimmers. They have been known to dive into the water to escape predators. Allen's swamp monkeys have a sturdy build and gray/green skin. The face is reddish with long hair bundles at the cheeks."));
        animals.add(new Animal("Koala", R.drawable.koala, R.drawable.big_koala, "Koalas are often called bears because they look a little like teddy bears, but they are actually marsupials. Like other marsupials, the female has a pouch where she carries her baby, called a joey, for several months after birth. When a joey is strong enough to cling to its mother's back, it leaves the pouch but still sticks its head in to nurse."));
        animals.add(new Animal("Brown Bear", R.drawable.bear, R.drawable.big_bear, "Brown bears are brown, right? Well, maybe! They come in all sizes and shades, from a light cream color to almost black.  Grizzly bears are a type of brown bear, but not all brown bears are grizzlies! Grizzly bears get their name because their fur is brownish next to the skin, but tipped with white or tan."));
        animals.add(new Animal("African Elephant", R.drawable.elephant, R.drawable.big_elephant, "Adult African elephants are huge.  Males can weigh as much as a school bus! Even their teeth are big–an adult's molar is the size of a brick. Their huge teeth help them grind and eat a lot of plant material every day. Our elephants at the Zoo and the Park each eat over 100 pounds of food each day."));
        animals.add(new Animal("African Lion", R.drawable.lion, R.drawable.big_lion, "Lions live in groups called prides. There can be from from 3 to 30 lions in a pride. A pride contains many females and their young, and a few males. The lionesses rule the pride, working together to hunt and raise the cubs. But the adult males play an important role as well. They guard the pride and protect its territory."));
        animals.add(new Animal("Arctic Fox", R.drawable.fox, R.drawable.big_fox, "Small, round body with thick fur and a cute fluffy tail—everything about an Arctic fox helps it survive its cold, harsh habitat. Even the bottom of its paws have hair to give the fox good traction as it races across icy ground. But for an Arctic fox the tail, also called a \"brush,\" is extra useful, since the fox curls it around the face like a scarf when the frigid winds blow."));
        animals.add(new Animal("Giraffe", R.drawable.giraffe, R.drawable.big_giraffe, "Giraffes are the tallest land animals. A giraffe could look into a second-story window without even having to stand on its tiptoes! A giraffe's 6-foot-long neck weighs about 600 pounds. The legs of a giraffe are also 6 feet long. The back legs look shorter than the front legs, but they are about the same length."));
        animals.add(new Animal("Giant Panda", R.drawable.panda, R.drawable.big_panda, "Giant pandas are only about the size of a stick of butter at birth, and they're hairless and helpless. The panda mother gives great care to her tiny cub, usually cradling it in one paw and holding it close to her chest. For several days after birth, the mother does not leave the den, not even to eat or drink!"));
        animals.add(new Animal("Coyote", R.drawable.coyote, R.drawable.big_coyote, "Coyotes are excellent hunters. They have terrific vision, a keen sense of smell, and good hearing. They live in family groups, and sometimes the pack cooperates to hunt down a large animal like a deer.  At other times, coyotes hunt alone or in pairs."));
        animals.add(new Animal("Chinese Alligator", R.drawable.alligator, R.drawable.big_alligator, "From nose to tail, belly to back, a Chinese alligator is protected by hard scales. Even the eyelids have bony plates under the skin! When the cold months come, they hunker down in their burrows—or in caves—and stay there throughout the winter."));

        ListView lv = (ListView) findViewById(R.id.animal_list);
        lv.setAdapter(new ListAdapter(this, R.layout.custom_row, animals));
        lv.setOnItemClickListener(this);


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
                Intent infoIntent = new Intent(MainActivity.this, InformationActivity.class);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        //Log.d("HHA", String.format("position: %d", position));
        if (position == animals.size() - 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Scary Animal")
                    .setMessage("This animal is scary, do you want to proceed?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //dialog.dismiss();
                            Animal animal = animals.get(position);
                            Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
                            myIntent.putExtra("name", animal.getName());
                            myIntent.putExtra("big_pic", animal.getBigImageId());
                            myIntent.putExtra("description", animal.getDescription());
                            if (myIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(myIntent);
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setCancelable(false)
                    ;
            builder.create().show();

        }

        else {
            //Toast.makeText(getApplicationContext(), "Animal clicked : " + animal.getName(), Toast.LENGTH_SHORT).show();
            Animal animal = animals.get(position);
            Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
            myIntent.putExtra("name", animal.getName());
            myIntent.putExtra("big_pic", animal.getBigImageId());
            myIntent.putExtra("description", animal.getDescription());
            if (myIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(myIntent);
            }
        }
    }

}
