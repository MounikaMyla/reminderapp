package com.example.mylam.reminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.text.*;
import java.util.Date;

import com.example.mylam.reminder.data.ReminderCursorAdapter;
import com.example.mylam.reminder.data.reminderContract;
import com.example.mylam.reminder.data.reminderDbHelper;
import com.example.mylam.reminder.data.reminderContract.reminderEntry;

import java.util.Calendar;

import static android.app.AlarmManager.*;

public class MainActivity extends AppCompatActivity {

    //NotificationCompat.Builder mbuilder;
    Cursor cursor;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addtask.class);
                startActivity(intent);
            }
        });
        displayDatabaseInfo();

        //gettingDB();
        while(cursor.moveToNext()) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(this, notifyService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
            String date = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_DATE));
            String time = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TIME));
            String[] datesplit = date.split("/");
            String[] timesplit = time.split(":");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MINUTE,Integer.parseInt(timesplit[1]));
            calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timesplit[0]));

            calendar.set(Calendar.DATE,Integer.parseInt(datesplit[0]));
            calendar.set(Calendar.MONTH,Integer.parseInt(datesplit[1]));
            calendar.set(Calendar.YEAR,Integer.parseInt(datesplit[2]));
            /*try {
                String dateTime = datesplit[0] + "/" + datesplit[1] + "/" + datesplit[2] + "    " + timesplit[0] + ":" + timesplit[1];
                Toast.makeText(this,dateTime,Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG);
            }*/


            alarmManager.setExact(RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        }


        //notifyme();

           // startService(new Intent(this, notifyMeService.class));
        cursor.close();

    }


    @Override
    protected void onStart() {

        super.onStart();
        displayDatabaseInfo();
    }

    //Getting data from sqlite db in to a cursor
    public  void gettingDB(){
        reminderDbHelper mdbhelper = new reminderDbHelper(this);
        SQLiteDatabase db = mdbhelper.getReadableDatabase();

        String[] projection = {reminderEntry._ID,reminderEntry.REMINDER_TITLE,reminderEntry.REMINDER_DATE,reminderEntry.REMINDER_TIME,
                reminderEntry.REMINDER_REPEAT};

        cursor = db.query(reminderEntry.TABLE_NAME, projection, null, null, null, null, null);

    }
    private void displayDatabaseInfo(){


            gettingDB();
        /*while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TITLE));
        }*/
            ListView listView = (ListView) findViewById(R.id.list);
            ReminderCursorAdapter remindercursor = new ReminderCursorAdapter(this, cursor);
            listView.setAdapter(remindercursor);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,addtask.class);
                    String pos = String.valueOf(id);
                    intent.putExtra("id",pos);
                    //Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });
    }



}
