package com.liuyang.code.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.liuyang.code.R;
import com.liuyang.code.system.SampleContentProvider;

/**
 * @author Liuyang 2016/2/4.
 */
public class ContentProvider extends BaseFragment {

    private int i;

    @Override
    protected int layoutId() {
        return R.layout.content_provider;
    }

    @Override
    protected void init() {
        find(R.id.insert).setOnClickListener(l -> {
            ContentValues value = new ContentValues();
            value.put(SampleContentProvider.COLUMN1, String.valueOf(i++));
            host.getContentResolver().insert(Uri.parse("content://" + SampleContentProvider.AUTHORITY), value);
        });

        find(R.id.read).setOnClickListener(l -> {
            Cursor cursor = host.getContentResolver().query(
                    Uri.parse("content://" + SampleContentProvider.AUTHORITY),
                    new String[]{SampleContentProvider.COLUMN1}, null, null, null);
            StringBuilder strings = new StringBuilder();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    strings.append(cursor.getString(cursor.getColumnIndex(SampleContentProvider.COLUMN1)) + " ");
                    cursor.moveToNext();
                }
            }
            show(strings.toString());
        });

        find(R.id.delete_all).setOnClickListener(l ->
                host.getContentResolver().delete(Uri.parse("content://" + SampleContentProvider.AUTHORITY), null, null));
    }

    @Override
    protected void refresh() {

    }
}
