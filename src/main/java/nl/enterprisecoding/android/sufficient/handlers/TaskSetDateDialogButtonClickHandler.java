package nl.enterprisecoding.android.sufficient.handlers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * ClickHandler for the TaskSetDateDialogButton.
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
            mActivity.setTaskDateFromDialog((DatePickerDialog) dialog);
        }
    }

}
