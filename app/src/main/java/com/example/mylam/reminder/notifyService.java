package com.example.mylam.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.mylam.reminder.data.reminderDbHelper;
import com.example.mylam.reminder.data.reminderContract.reminderEntry;

public class notifyService extends Service {
    public notifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //onCreate();
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onCreate() {
        //super.onCreate();

        int notificationID = 123;
        NotificationManager mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        /*Notification notification = new Notification(R.drawable.ic_launcher_foreground,
                                    "Notify Alarm strart",
                                    System.currentTimeMillis());*/
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this);
        mbuilder.setSmallIcon(R.drawable.ic_launcher_background);
        mbuilder.setTicker("You have a notification");

        Cursor cursor;
        reminderDbHelper mdbhelper = new reminderDbHelper(this);
        SQLiteDatabase db = mdbhelper.getReadableDatabase();

        String[] projection = {reminderEntry._ID,
                reminderEntry.REMINDER_TITLE,
                reminderEntry.REMINDER_DATE,
                reminderEntry.REMINDER_TIME,
                reminderEntry.REMINDER_REPEAT};

        cursor = db.query(reminderEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);



        String title = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TITLE));

        mbuilder.setContentTitle(title);
        mbuilder.setContentText("Hello");

        Intent myIntent = new Intent(this, addtask.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, myIntent, 0);
        //notification.setLatestEventInfo(this, "Notify label", "Notify text", contentIntent);
        mNM.notify(notificationID, mbuilder.build());
        cursor.close();
    }
}
