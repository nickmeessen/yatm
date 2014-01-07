package nl.enterprisecoding.android.sufficient.handlers;

import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

public class TaskSetDateButtonClickHandler implements IButtonClickHandler {

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
        mActivity.showTaskSetDateDialog();
    }

}
