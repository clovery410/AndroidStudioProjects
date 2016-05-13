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
import java.util.List;

/**
 * Created by clover on 5/11/16.
 */
public class MyRecyclerAdapter extends BaseAbstractRecycleCursorAdapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MyRecyclerAdapter";
//    private Context mContext;
    private List<NoteInfo> noteList;
//    private ArrayList<String> noteList;
    private Cursor cursor;
    private final LayoutInflater mLayoutInflater;


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

    public MyRecyclerAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
//        NoteInfo ni = noteList.get(i);
//        customViewHolder.vCaption.setText(ni.caption);
//        customViewHolder.vThumbpath.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(ni.fullpath), 120, 120));
//        customViewHolder.vThumbpath.setImageBitmap(BitmapFactory.decodeFile(ni.thumbpath));
//        Log.d("qjiang", ni.thumbpath);
//        Log.d("lalala", String.valueOf(i));
//        cursor.moveToPosition(i);
//        customViewHolder.vCaption.setText(cursor.getString(cursor.getColumnIndex("caption")));
//        customViewHolder.vThumbpath.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex("fullpath"))));
        NoteInfo note = NoteInfo.fromCursor(cursor);
        ((NormalTextViewHolder) holer).vCaption.setText(note.getCaption());
        ((NormalTextViewHolder) holder).vThumbname.

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = mLayoutInflater.
                inflate(R.layout.custom_row, viewGroup, false);

        return new NormalTextViewHolder(itemView, this);
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        protected TextView vCaption;
        protected ImageView vThumbpath;
        MyRecyclerAdapter mAdapter;

        NormalTextViewHolder(View view, MyRecyclerAdapter adapter) {
            super(view);
            mAdapter = adapter;
            vCaption = (TextView) view.findViewById(R.id.main_caption);
            vThumbpath = (ImageView) view.findViewById(R.id.mian_thumbnail);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.cv_item)
        void onItemClick() {
            Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
            DemoItem item = DemoItem.fromCursor((Cursor) mAdapter.getItem(getPosition()));
            if (getPosition() < 11) {
                Intent intent = new Intent(mAdapter.mContext, DetailActivity.class);
                intent.putExtra("position", getPosition());
                intent.putExtra("title", item.title);
                mAdapter.mContext.startActivity(intent);
            } else {
                Intent intent = new Intent(mAdapter.mContext, SelectActivity.class);
                intent.putExtra("position", getPosition());
                intent.putExtra("title", item.title);
                mAdapter.mContext.startActivity(intent);
            }
        }
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
