package nl.enterprisecoding.android.sufficient.handlers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

public class TaskSetDateDialogButtonClickHandler implements IDialogButtonClickHandler {

    private EditTaskActivity mActivity;

    public TaskSetDateDialogButtonClickHandler() {
    }

    @Override
    public void setActivity(MainActivity activity) {
        mActivity = (EditTaskActivity) activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            mActivity.setTaskDateFromDialog((DatePickerDialog) dialog);
        }
    }

}
