/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.inject.Inject;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.handlers.TaskSetDateButtonClickHandler;
import nl.enterprisecoding.android.sufficient.handlers.TaskSetDateDialogButtonClickHandler;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * EditTaskActivity class
 * From here the user can edit and create a task
 *
 * @author Sjors Roelofs & Ferry Wienholts
 */

@ContentView(R.layout.edit_task_item)
public class EditTaskActivity extends MainActivity {

    @InjectView(R.id.task_title)
    private EditText mTaskTitleInput;
    @InjectView(R.id.task_category)
    private Spinner mTaskCategorySpinner;
    @InjectView(R.id.task_set_date_button)
    private Button mTaskSetDateButton;
    @InjectView(R.id.task_important)
    private CheckBox mTaskImportantCheckBox;

    @Inject
    private TaskSetDateButtonClickHandler mTaskSetDateButtonClickHandler;
    @Inject
    private TaskSetDateDialogButtonClickHandler mTaskSetDateDialogButtonClickHandler;

    private Task mSelectedTask;
    private Calendar mDateToday;
    private Calendar mTaskDate;
    private List<Category> mCategoriesArray = new ArrayList<Category>();

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long selectedTaskID = getIntent().getExtras().getLong(TaskActivity.TASK_ID, 0);

        if (selectedTaskID != 0) {
            mActionBar.setTitle(R.string.action_edit);

            mSelectedTask = mTaskManager.getTask(selectedTaskID);
            mActionBar.setBackgroundDrawable(new ColorDrawable(mTaskManager.getCategoryById(mSelectedTask.getCatID()).getColour()));

            mDateToday = Calendar.getInstance();

            mTaskDate = mSelectedTask.getDate();
            Log.d("TEST", mTaskDate.get(Calendar.DAY_OF_MONTH) + "-" + mTaskDate.get(Calendar.MONTH) + "-" + mTaskDate.get(Calendar.YEAR));

            updateDateButtonText();

            mTaskSetDateButtonClickHandler.setActivity(this);
            mTaskSetDateButton.setOnClickListener(mTaskSetDateButtonClickHandler);

            mTaskTitleInput = (EditText) findViewById(R.id.task_title);
            mTaskTitleInput.setHint(mSelectedTask.getTitle());

            initTaskCategorySpinner(mTaskCategorySpinner);
            mTaskCategorySpinner.setSelection(findIndexByCategoryId(mSelectedTask.getCatID()));

            mTaskImportantCheckBox = (CheckBox) findViewById(R.id.task_important);
            mTaskImportantCheckBox.setChecked(mSelectedTask.isImportant());

            Button saveTaskButton = (Button) findViewById(R.id.save_task_button);
            saveTaskButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * Handles the click for the saveTaskButton
                 *
                 * @param v The view in which the click takes place
                 */
                @Override
                public void onClick(View v) {
                    saveTask();
                }
            });
        }
    }

    /**
     * Saves a task.
     */
    private void saveTask() {
        boolean validData = true;
        int selectedCategoryIndex = mTaskCategorySpinner.getSelectedItemPosition();
        long selectedCategoryID = mCategoriesArray.get(selectedCategoryIndex).getId();
        if (mTaskTitleInput.getText().toString().isEmpty()) {
            mTaskTitleInput.setText(mTaskTitleInput.getHint());
        }

        if (validData) {
            mTaskManager.updateTask(mTaskTitleInput.getText().toString(), selectedCategoryID, mTaskDate, mTaskImportantCheckBox.isChecked(), false, mSelectedTask.getID());
            startTaskActivity(selectedCategoryID);
        } else {
            makeToast(getString(R.string.toast_invalid_data));
        }
    }

    /**
     * Initialises the spinner which will display the available categories
     *
     * @param spinner The spinner which displays the categories
     */
    private void initTaskCategorySpinner(Spinner spinner) {

        ArrayAdapter<String> mSpinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                mTaskManager.getCategoriesStringArray()
        );

        mSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSpinnerArrayAdapter);
    }

    /**
     * Find the index position for the category you request by inputting an id
     *
     * @param categoryId The id of the category you are trying to find
     * @return The index position of the category
     */
    private int findIndexByCategoryId(long categoryId) {
        int index = 0;

        int count = 0;
        for (Category cat : mCategoriesArray) {
            if (cat.getId() == categoryId) {
                index = count;
                break;
            }
            count++;
        }

        return index;
    }

    /**
     * Sets the date for the task
     *
     * @param dayOfMonth The day in the wanted month
     * @param month      The wanted month
     * @param year       The wanted year
     */
    private void setTaskDate(int dayOfMonth, int month, int year) {
        mTaskDate.set(year, month, dayOfMonth);
        updateDateButtonText();
    }

    /**
     * Updates the text of the date Button so it matches the selected date
     */
    private void updateDateButtonText() {
        String mMonth = new DateFormatSymbols().getMonths()[mTaskDate.get(Calendar.MONTH)];
        mTaskSetDateButton.setText(mTaskDate.get(Calendar.DAY_OF_MONTH) + " " + mMonth + " " + mTaskDate.get(Calendar.YEAR));
    }

    /**
     * Starts a TaskActivity for a certain category
     *
     * @param catId The id of the category which TaskActivity you are trying to start
     */
    private void startTaskActivity(long catId) {
        Intent intent = new Intent(this, TaskActivity.class);

        intent.putExtra("categoryID", catId);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Shows DatePicker Dialog.
     */
    public void showTaskSetDateDialog() {

        mTaskSetDateDialogButtonClickHandler.setActivity(this);

        DatePickerDialog alert = new DatePickerDialog(this, null, mTaskDate.get(Calendar.YEAR), mTaskDate.get(Calendar.MONTH), mTaskDate.get(Calendar.DAY_OF_MONTH));
        alert.getDatePicker().setMinDate(mDateToday.getTime().getTime());

        alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.action_change_date), mTaskSetDateDialogButtonClickHandler);
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.action_discard), mTaskSetDateDialogButtonClickHandler);

        alert.show();
    }

    /**
     * Sets the Task date according to the date picked with the dialog.
     *
     * @param dialog the dialog providing the date.
     */
    public void setTaskDateFromDialog(DatePickerDialog dialog) {
        setTaskDate(dialog.getDatePicker().getDayOfMonth(), dialog.getDatePicker().getMonth(), dialog.getDatePicker().getYear());
    }
}
