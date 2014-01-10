/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.handlers;

import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * ClickHandler for the TaskSetDateButton.
 *
 * @author Sjors Roelofs
 */
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
