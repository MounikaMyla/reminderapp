package com.example.mylam.reminder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylam.reminder.data.reminderContract;
import com.example.mylam.reminder.data.reminderContract.reminderEntry;
import com.example.mylam.reminder.data.reminderDbHelper;

public class addtask extends AppCompatActivity implements RepeatDialog.NoticeDialogListener{

    EditText titletext;
    EditText datetext;
    EditText timetext;
    Switch choose;
    TextView tv;
    Button save;
    String dataSent="";

    String selectedOption;
    //int year_x,month_x,day_x;
    //static final int DILOG_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }

        Intent intent=getIntent();
        dataSent = getIntent().getStringExtra("id");

        titletext = (EditText)findViewById(R.id.editText);
        datetext = (EditText)findViewById(R.id.dateText);
        timetext = (EditText)findViewById(R.id.timeText);

        tv = (TextView)findViewById(R.id.repeatText);

        choose = (Switch)findViewById(R.id.switch1);

        save = (Button)findViewById(R.id.button);

        if(dataSent==null){
            setTitle("New Task");
            invalidateOptionsMenu();
            //Toast.makeText(this,"Always this",Toast.LENGTH_SHORT).show();
            save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    insertReminder(v);
                }
            });
        }
        else {
            //Toast.makeText(this,dataSent,Toast.LENGTH_SHORT).show();
            setTitle("Update Task");
            setData(dataSent);

            save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    updateReminder(v,dataSent);
                }
            });

        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if ( dataSent== null) {
            MenuItem menuItem = menu.findItem(R.id.delete);
            menuItem.setVisible(false);
        }
        return true;
    }


    public void setData(String dataSent){
        reminderDbHelper mdbhelper = new reminderDbHelper(this);
        SQLiteDatabase db = mdbhelper.getReadableDatabase();


        String[] projection = {reminderEntry._ID,
                reminderEntry.REMINDER_TITLE,
                reminderEntry.REMINDER_DATE,
                reminderEntry.REMINDER_TIME,
                reminderEntry.REMINDER_REPEAT};
        String selection = reminderEntry._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(dataSent)};
        Cursor cursor = db.query(reminderEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

           /* try {

                if (cursor != null) {
                    //Toast.makeText(this, Integer.toString(cursor.getColumnCount()), Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }*/
        try{
            // Toast.makeText(this,"Index: "+Integer.toString(cursor.getColumnIndex(reminderEntry.REMINDER_TITLE)),
            //Toast.LENGTH_LONG).show();
            cursor.moveToNext();
            //String x= cursor.getString(1);

            String title = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TITLE));
            String date = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_DATE));
            String time = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_TIME));
            String repeat = cursor.getString(cursor.getColumnIndex(reminderEntry.REMINDER_REPEAT));

            int repeatNum = Integer.parseInt(repeat);

            if(repeatNum!=0)
                choose.setChecked(true);
            titletext.setText(title);
            datetext.setText(date);
            timetext.setText(time);
            if(repeatNum==0)
                tv.setText("Does Not Repeat");
            else if (repeatNum==1)
                tv.setText("Repeats every day");
            else if(repeatNum==2)
                tv.setText("Repeats every week");
            else if (repeatNum==3)
                tv.setText("Repeats every month");
            else if(repeatNum==4)
                tv.setText("Repeats every year");
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
        cursor.close();

    }

    public void showTimePickerDialog(View v) {
        /*EditText date = (EditText) findViewById(R.id.dateText);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                DateDialog newFragment = new DateDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                newFragment.show(ft, "timePicker");
            }
        });*/

        TimeDialog newFragment = new TimeDialog(v);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        newFragment.show(ft, "timePicker");

    }

    public void showDatePickerDialog(View v) {

        DateDialog newFragment = new DateDialog(v);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        newFragment.show(ft, "DatePicker");

    }

    public void showOptions(View v){
        Switch repeatSwitch = (Switch)findViewById(R.id.switch1);
        if(repeatSwitch.isChecked()) {
            RepeatDialog newFragment = new RepeatDialog(v);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            newFragment.show(ft, "Repeat option picker");
        }
        else {
            TextView tv = (TextView) findViewById(R.id.repeatText);
            tv.setText("Does Not Repeat");
        }
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String option_chosen) {
        // User touched the dialog's positive button
        TextView tv = (TextView) findViewById(R.id.repeatText);
        if(option_chosen=="Do Not Repeat")
            tv.setText("Does Not Repeat");
        else
            tv.setText("Repeats "+option_chosen.toLowerCase());
        selectedOption = option_chosen;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
       // TextView tv = (TextView) findViewById(R.id.repeatText);
       // tv.setText("Didn't get it");
        choose.setChecked(false);

    }

    public ContentValues extractData(){
        String title = titletext.getText().toString().trim();
        String date = datetext.getText().toString().trim();
        String time = timetext.getText().toString().trim();
        //String repeat = repeattext.getText().toString().trim();
        int option=0;
        if(selectedOption=="Do Not Repeat")
            option=0;
        else if(selectedOption=="Every Day")
            option=1;
        else if(selectedOption=="Every Week")
            option=2;
        else if(selectedOption=="Every Month")
            option=3;
        else if(selectedOption=="Every Year")
            option=4;

        ContentValues values = new ContentValues();

        values.put(reminderContract.reminderEntry.REMINDER_TITLE,title);
        values.put(reminderContract.reminderEntry.REMINDER_DATE,date);
        values.put(reminderContract.reminderEntry.REMINDER_TIME,time);
        values.put(reminderContract.reminderEntry.REMINDER_REPEAT,option);

        return values;

    }

    public void updateReminder(View view,String dataSent){
        ContentValues values = extractData();
        reminderDbHelper dbHelper = new reminderDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String whereClause = reminderEntry._ID + " LIKE ?";
        try {
            String whereArgs[] = {dataSent};
            int num = db.update(reminderEntry.TABLE_NAME, values, whereClause, whereArgs);
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void insertReminder(View view){

        ContentValues values = extractData();
        reminderDbHelper dbHelper = new reminderDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        long newRowId = db.insert(reminderContract.reminderEntry.TABLE_NAME,null,values);

        if(newRowId==-1)
            Toast.makeText(this,"Error while adding reminder",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Added Successfully! " ,Toast.LENGTH_SHORT).show();

        finish();
    }

    public void deleteReminder(){
        reminderDbHelper dbHelper = new reminderDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String whereClause = reminderEntry._ID + " LIKE ?";

            String whereArgs[] = {dataSent};
        db.delete(reminderEntry.TABLE_NAME,whereClause,whereArgs);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.deleteaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.delete:
                deleteReminder();
                return true;
        }
        // User clicked on a menu option in the app bar overflow menu

        return super.onOptionsItemSelected(item);
    }

}
