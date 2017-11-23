package com.example.mylam.reminder;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;

import com.example.mylam.reminder.data.reminderDbHelper;
import com.example.mylam.reminder.data.reminderContract.reminderEntry;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class notifyMeService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.mylam.reminder.action.FOO";
    public static final String ACTION_BAZ = "com.example.mylam.reminder.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.mylam.reminder.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.mylam.reminder.extra.PARAM2";

    public notifyMeService() {
        super("notifyMeService");
    }

    Cursor cursor;
    NotificationCompat.Builder mbuilder;

    @Override
    protected void onHandleIntent(Intent intent) {

        notifyme();
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    //Getting data from sqlite db to a cursor
    public  void gettingDB(){
        reminderDbHelper mdbhelper = new reminderDbHelper(this);
        SQLiteDatabase db = mdbhelper.getReadableDatabase();

        String[] projection = {reminderEntry._ID,reminderEntry.REMINDER_TITLE,reminderEntry.REMINDER_DATE,reminderEntry.REMINDER_TIME,
                reminderEntry.REMINDER_REPEAT};

        cursor = db.query(reminderEntry.TABLE_NAME, projection, null, null, null, null, null);

    }

    public void notifyme(){

        mbuilder = new NotificationCompat.Builder(this);
        mbuilder.setAutoCancel(true);

        gettingDB();
        while(cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_DATE));
            String title = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TITLE));
            String time = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TIME));
            String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            //Toast.makeText(this,currentDate,Toast.LENGTH_SHORT).show();

            Date d=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
            String currentTime = sdf.format(d);
            //Toast.makeText(this,currentTime,Toast.LENGTH_SHORT).show();
            if (date.matches(currentDate)&& time.matches(currentTime)) {

                mbuilder.setSmallIcon(R.drawable.ic_launcher_background);
                mbuilder.setTicker("You have a notification");
                mbuilder.setContentTitle(title);
                mbuilder.setContentText("Hello");
                mbuilder.setWhen(System.currentTimeMillis());
                Intent intent = new Intent(this, addtask.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        this, 0, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                mbuilder.setContentIntent(pendingIntent);

                int mNotificationId = 123;

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(mNotificationId, mbuilder.build());
            }
        }
        cursor.close();
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
