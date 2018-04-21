package com.smdproject.smdproject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint("ValidFragment")
public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    EditText txtdate;
    public TimeDialog(View view){
        txtdate=(EditText)view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hrs = 0;
        int min = 0;
        return new TimePickerDialog(getActivity(), this, hrs, min, false);
    }

    public void onTimeSet(TimePicker view, int hrs, int min) {
        String date = hrs+":"+min;
        txtdate.setText(date);
    }
}
