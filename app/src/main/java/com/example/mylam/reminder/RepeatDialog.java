package com.example.mylam.reminder;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mylam on 16-11-2017.
 */

public class RepeatDialog extends DialogFragment{

    TextView textView;
    String option_chosen;
    String[] options_display={"Do Not Repeat","Every Day","Every Week","Every Month","Every Year"};
    //EditText edit
    public RepeatDialog(View view){
        textView = (TextView)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        //options_display = getResources().getStringArray(R.id.)
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose an option")
                .setSingleChoiceItems(options_display, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        //textView.setText(which);
                        option_chosen = (String)options_display[which];
                    }
                });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the positive button event back to the host activity
                mListener.onDialogPositiveClick(RepeatDialog.this,option_chosen);
            }
        });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(RepeatDialog.this);
                    }
                });
        return builder.create();

    }
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String option_chosen);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
