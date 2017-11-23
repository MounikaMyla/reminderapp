package com.example.mylam.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


/**
 * Created by mylam on 16-11-2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText textdate;

    public DateDialog(View view){
        textdate = (EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
            month = month+1;
            String setday = Integer.toString(day);
            String setMonth = Integer.toString(month);
            if(day<10)
                setday="0"+setday;
            if(month<10)
                setMonth="0"+setMonth;
            String date = setday+"/"+setMonth+"/"+year;
            textdate.setText(date);
    }
}

