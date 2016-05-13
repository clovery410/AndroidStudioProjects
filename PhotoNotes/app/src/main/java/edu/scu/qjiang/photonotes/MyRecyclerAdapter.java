package edu.scu.qjiang.photonotes;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clover on 5/11/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

    private static final String TAG = "MyRecyclerAdapter";
//    private Context mContext;
    private List<NoteInfo> noteList;
//    private ArrayList<String> noteList;
    private Cursor cursor;


    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView vCaption;
//        protected ImageView vFullpath;
        protected ImageView vThumbpath;

        public CustomViewHolder(View v) {
            super(v);
            vCaption = (TextView) v.findViewById(R.id.main_caption);
            vThumbpath = (ImageView) v.findViewById(R.id.mian_thumbnail);
//            vFullpath = (ImageView) v.findViewById(R.id.view_fullpath);
        }

    }

    public MyRecyclerAdapter(Cursor cursor) {
        this.cursor = cursor;
        this.noteList = noteList;
//        this.mContext = context;
    }

    @Override
    public int getItemCount() {
//        return noteList.size();
        return cursor.getCount();
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
//        NoteInfo ni = noteList.get(i);
//        customViewHolder.vCaption.setText(ni.caption);
//        customViewHolder.vThumbpath.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(ni.fullpath), 120, 120));
//        customViewHolder.vThumbpath.setImageBitmap(BitmapFactory.decodeFile(ni.thumbpath));
//        Log.d("qjiang", ni.thumbpath);
        Log.d("lalala", String.valueOf(i));
        cursor.moveToPosition(i);
        customViewHolder.vCaption.setText(cursor.getString(cursor.getColumnIndex("caption")));
        customViewHolder.vThumbpath.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex("fullpath"))));

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.custom_row, viewGroup, false);

        return new CustomViewHolder(itemView);
    }

    public void onItemDismissed(int position) {
        noteList.remove(position);
        notifyItemRemoved(position);
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        NoteInfo temp = noteList.get(fromPosition);
        noteList.set(fromPosition, noteList.get(toPosition));
        noteList.set(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}
