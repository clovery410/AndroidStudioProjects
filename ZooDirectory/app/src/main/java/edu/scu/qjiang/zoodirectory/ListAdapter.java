package edu.scu.qjiang.zoodirectory;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by clover on 4/30/16.
 */
public class ListAdapter extends ArrayAdapter<Animal> {

    private final List<Animal> animals;

    private Context mContext;

    public ListAdapter(Context context, int resource, List<Animal> animals) {
        super(context, resource, animals);
        this.mContext = context;
        this.animals = animals;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        if (convertView == null) {
            row = inflater.inflate(R.layout.custom_row, parent, false);
        }

        TextView textView = (TextView) row.findViewById(R.id.animal_name);
        textView.setText(animals.get(position).getName());

        ImageView imageView = (ImageView) row.findViewById(R.id.animal_thumbnail);
        imageView.setImageResource(animals.get(position).getImageId());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return row;

//        ScrapViewHolder holder;
//        View row = convertView;
//
//        if (row == null) {
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            row = inflater.inflate(R.layout.custom_row, parent, false);
//
//            holder = new ScrapViewHolder();
//            holder.animal_name = (TextView) row.findViewById(R.id.animal_name);
//            holder.animal_thumbnail = (ImageView) row.findViewById(R.id.animal_thumbnail);
//            holder.favorite = (CheckBox) row.findViewById(R.id.favorite);
//            row.setTag(holder);
//        }
//        else {
//            holder = (ScrapViewHolder) row.getTag();
//        }
//
//        holder.animal_name.setText(animals.get(position).getName());
//        holder.animal_thumbnail.setImageResource(animals.get(position).getImageId());
//        holder.animal_thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        return row;
    }

//    public class ScrapViewHolder {
//        TextView animal_name;
//        ImageView animal_thumbnail;
//        CheckBox favorite;
//
//    }
}
