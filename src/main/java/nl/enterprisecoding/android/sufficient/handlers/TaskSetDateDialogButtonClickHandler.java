/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.handlers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * ClickHandler for the TaskSetDateDialogButton.
 *
 * @author Sjors Roelofs
 */
public class TaskSetDateDialogButtonClickHandler implements Dialog.OnClickListener {

    Calendar mTaskDate;

    /**
     * Construct a new ClickHandler
     * @param taskDate the date for this click handler.
     */
    public TaskSetDateDialogButtonClickHandler(Calendar taskDate) {
        mTaskDate = taskDate;
    }

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param dialog The dialog that received the click.
     * @param which  The button that was clicked
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            DatePicker datePicker = ((DatePickerDialog) dialog).getDatePicker();
            mTaskDate.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
        }
    }
}
