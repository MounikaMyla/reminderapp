package com.example.mylam.reminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.mylam.reminder.data.reminderContract.reminderEntry;

/**
 * Created by mylam on 16-11-2017.
 */

public class reminderDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="reminderList.db";
    private static final int DATABASE_VERSION = 1;

    //constructor
    public reminderDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){

        String SQL_CREATE_REMINDER_TABLE = "CREATE TABLE " + reminderEntry.TABLE_NAME + " ("
                + reminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + reminderEntry.REMINDER_TITLE + " TEXT NOT NULL, "
                + reminderEntry.REMINDER_DATE + " TEXT NOT NULL, "
                + reminderEntry.REMINDER_TIME + " TEXT NOT NULL, "
                + reminderEntry.REMINDER_REPEAT + " INTEGER NOT NULL Default 0);";

        db.execSQL(SQL_CREATE_REMINDER_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1){}
}




