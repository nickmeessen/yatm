/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.handlers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import com.google.inject.Inject;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

import java.util.Calendar;

/**
 * ClickHandler for the TaskSetDateButton.
 *
 * @author Sjors Roelofs
 */
public class TaskSetDateButtonClickHandler implements IButtonClickHandler {

    @Inject
    private TaskSetDateDialogButtonClickHandler mTaskSetDateDialogButtonClickHandler;

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
     * This method will be invoked when a view is clicked.
     *
     * @param view The view that received the click.
     */
    @Override
    public void onClick(View view) {
        mTaskSetDateDialogButtonClickHandler.setActivity(mActivity);

        DatePickerDialog alert = new DatePickerDialog(mActivity, null, mActivity.getTaskDate().get(Calendar.YEAR), mActivity.getTaskDate().get(Calendar.MONTH), mActivity.getTaskDate().get(Calendar.DAY_OF_MONTH));
        alert.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        alert.setButton(DialogInterface.BUTTON_POSITIVE, mActivity.getString(R.string.action_change_date), mTaskSetDateDialogButtonClickHandler);
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, mActivity.getString(R.string.action_discard), mTaskSetDateDialogButtonClickHandler);

        alert.show();
    }

}
