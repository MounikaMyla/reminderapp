package com.example.mylam.reminder;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * Created by mylam on 16-11-2017.
 */

public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    EditText texttime;

    public TimeDialog(View view){
        texttime = (EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        String hours= Integer.toString(hourOfDay);
        String min=Integer.toString(minute);
        if(hourOfDay<10)
            hours = "0"+hourOfDay;
        if(minute<10)
            min = "0"+minute;
        String time = hours+":"+min;
        texttime.setText(time);
    }


}

