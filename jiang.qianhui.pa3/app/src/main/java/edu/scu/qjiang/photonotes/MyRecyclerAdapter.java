package edu.scu.qjiang.photonotes;

import android.content.Context;
import android.content.Intent;
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
import java.util.Collections;
import java.util.List;

/**
 * Created by clover on 5/11/16.
 */
public class MyRecyclerAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MyRecyclerAdapter";
    private Context mContext;
    private Cursor cursor;
    private final LayoutInflater mLayoutInflater;


    public MyRecyclerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mLayoutInflater = LayoutInflater.from(context);
        this.cursor = cursor;
        this.mContext = context;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        NoteInfo note = NoteInfo.fromCursor(cursor);
        ((CustomViewHolder) holder).vCaption.setText(note.getCaption());
        ((CustomViewHolder) holder).vThumbpath.setImageURI(Uri.parse(note.getThumbpath()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = mLayoutInflater.
                inflate(R.layout.custom_row, viewGroup, false);
        return new CustomViewHolder(itemView, this);
    }

    public void onItemDismissed(int position) {
        cursor.moveToPosition(position);
        deleteItem(cursor.getInt(cursor.getColumnIndex("_id")));
        notifyItemRemoved(position);
        cursor.requery();
        notifyItemRangeChanged(position, cursor.getCount());
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        cursor.moveToPosition(fromPosition);
        int from_sortId = cursor.getInt(cursor.getColumnIndex("sort_id"));
//        cursor.moveToPosition(fromPosition);
        int from_Id = cursor.getInt(cursor.getColumnIndex("_id"));
        cursor.moveToPosition(toPosition);
        int to_sortId = cursor.getInt(cursor.getColumnIndex("sort_id"));
//        cursor.moveToPosition(toPosition);
        int to_Id = cursor.getInt(cursor.getColumnIndex("_id"));
        switchItem(from_sortId, from_Id, to_sortId, to_Id);
        notifyItemMoved(fromPosition, toPosition);
        cursor.requery();
        return true;
    }

    public void deleteItem(int id) {
        PhotoNoteDBHelper dbHelper = new PhotoNoteDBHelper(this.mContext);
        dbHelper.deleteNote(id);
        notifyDataSetChanged();
        dbHelper.close();
    }

    public void switchItem(int from, int from_rowid, int to, int to_rowid) {
        Log.d("drag", String.valueOf(from));
        Log.d("drop", String.valueOf(to));
        PhotoNoteDBHelper dbHelper = new PhotoNoteDBHelper(this.mContext);
        dbHelper.updateOrderID(to, from_rowid);
        dbHelper.updateOrderID(from, to_rowid);
        notifyDataSetChanged();
        dbHelper.close();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vCaption;
        ImageView vThumbpath;
        MyRecyclerAdapter mAdapter;

        CustomViewHolder(View view, MyRecyclerAdapter adapter) {
            super(view);
            mAdapter = adapter;
            vCaption = (TextView) view.findViewById(R.id.main_caption);
            vThumbpath = (ImageView) view.findViewById(R.id.main_thumbnail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
            NoteInfo noteInfo = NoteInfo.fromCursor((Cursor) mAdapter.getItem(getPosition()));
            if (getPosition() < 11) {
                Intent intent = new Intent(mAdapter.mContext, ViewPhoto.class);
                intent.putExtra("captionContent", noteInfo.caption);
                intent.putExtra("fullImage", noteInfo.fullpath);
                mAdapter.mContext.startActivity(intent);
            } else {
//                Intent intent = new Intent(mAdapter.mContext, SelectActivity.class);
//                intent.putExtra("position", getPosition());
//                intent.putExtra("title", item.title);
//                mAdapter.mContext.startActivity(intent);
            }
        }
    }

}
