package nl.enterprisecoding.android.sufficient.handlers;

import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

public class TaskSetDateButtonClickHandler implements IButtonClickHandler {

    private EditTaskActivity mActivity;

    public TaskSetDateButtonClickHandler() {}

    @Override
    public void setActivity(MainActivity activity) {
        mActivity = (EditTaskActivity)activity;
    }

    @Override
    public void onClick(View v) {
        mActivity.showTaskSetDateDialog();
    }

}
