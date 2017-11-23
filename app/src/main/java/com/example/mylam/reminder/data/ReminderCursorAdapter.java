package com.example.mylam.reminder.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.mylam.reminder.R;

/**
 * Created by mylam on 21-11-2017.
 */

public class ReminderCursorAdapter extends CursorAdapter {
    public ReminderCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView)view.findViewById(R.id.reminderName);

        String ReminderName = cursor.getString(cursor.getColumnIndex(reminderContract.reminderEntry.REMINDER_TITLE));

        name.setText(ReminderName);
    }
}
