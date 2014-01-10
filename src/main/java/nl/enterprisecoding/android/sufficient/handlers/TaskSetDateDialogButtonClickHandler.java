/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.handlers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * ClickHandler for the TaskSetDateDialogButton.
 *
 * @author Sjors Roelofs
 */
public class TaskSetDateDialogButtonClickHandler implements IDialogButtonClickHandler {

    private EditTaskActivity mActivity;

    /**
     * Sets current activity.
     *
     * @param activity the current activity.
     */
    @Override
    public void setActivity(MainActivity activity) {
        mActivity = (EditTaskActivity) activity;
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
            DatePickerDialog datePickerDialog = (DatePickerDialog)dialog;
            mActivity.setTaskDate(datePickerDialog.getDatePicker().getDayOfMonth(), datePickerDialog.getDatePicker().getMonth(), datePickerDialog.getDatePicker().getYear());
        }
    }

}
