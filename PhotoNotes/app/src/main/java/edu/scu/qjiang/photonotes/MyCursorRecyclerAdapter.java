package edu.scu.qjiang.photonotes;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by clover on 5/12/16.
 */
public class MyCursorRecyclerAdapter extends CursorRecyclerAdapter<MyViewHolder> {
    private int mLayout;
    private int[] mFrom;
    private int[] mTo;
    private String[] mOriginalFrom;

    public MyCursorRecyclerAdapter (int layout, Cursor c, String[] from, int[] to) {
        super(c);
        mLayout = layout;
        mTo = to;
        mOriginalFrom = from;
        findColumns(c, from);
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int ViewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        return new MyViewHolder(v, mTo);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, Cursor cursor) {
        final int count = mTo.length;
        final int[] from = mFrom;

        for (int i = 0; i < count; i++) {
            holder.views[i].setText(cursor.getString(from[i]));
        }
    }

    private void findColumns(Cursor c, String[] from) {
        if (c != null) {
            int i;
            int count = from.length;
            if (mFrom == null || mFrom.length != count) {
                mFrom = new int[count];
            }
            for (i = 0; i < count; i++) {
                mFrom[i] = c.getColumnIndexOrThrow(from[i]);
            }
        } else {
            mFrom = null;
        }
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        findColumns(c, mOriginalFrom);
        return super.swapCursor(c);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView[] views;

    public MyViewHolder (View itemView, int[] to)
    {
        super(itemView);
        views = new TextView[to.length];
        for(int i = 0 ; i < to.length ; i++) {
            views[i] = (TextView) itemView.findViewById(to[i]);
        }
    }
}

