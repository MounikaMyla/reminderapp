package com.example.mylam.reminder.data;

import android.provider.BaseColumns;

/**
 * Created by mylam on 16-11-2017.
 */

public class reminderContract {

    public static abstract class reminderEntry implements BaseColumns{

        public static final String TABLE_NAME = "reminders";

        public static final String _ID = BaseColumns._ID;
        public static final String REMINDER_TITLE = "title";
        public static final String REMINDER_DATE="date";
        public static final String REMINDER_TIME= "time";
        public static final String REMINDER_REPEAT = "option";

        public static final int DONOT_REPEAT=0;
        public static final int EVERY_DAY=1;
        public static final int EVERY_WEEK=2;
        public static final int EVERY_MONTH=3;
        public static final int EVERY_YEAR=4;
    }
}

