package com.cmu.smartphone.allavailable.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Fragment to handle the date chosen
 *
 * @author Xi Wang, Dudaxi Huang
 * @version 1.0
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private Button parentDateButton;

    /**
     * Set the parent button
     *
     * @param parentDateButton
     */
    public void setParentDateButton(Button parentDateButton) {
        this.parentDateButton = parentDateButton;
    }

    /**
     * Override the dialog creation
     *
     * @param savedInstanceState
     * @return the created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }

    /**
     * Handle the action to set date
     *
     * @param view
     * @param year
     * @param month
     * @param day
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String displayValue = numTomon(padding_str(month + 1) + "/" + padding_str(day) + "/" + padding_str(year));

        parentDateButton.setText(displayValue);
    }

    /**
     * Padding the zero to the string
     *
     * @param c
     * @return the string with front padding zero
     */
    private static String padding_str(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * Number to Month transfer
     *
     * @param inputDate the input date
     * @return the month string
     */
    private static String numTomon(String inputDate) {
        Date dt = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            dt = format.parse(inputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        CharSequence dateChar = DateFormat.format("  MMM  dd, yyyy ", dt);
        return dateChar.toString();
    }
}