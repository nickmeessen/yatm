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
import android.view.View;
import android.widget.*;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import nl.enterprisecoding.android.sufficient.handlers.TaskSetDateDialogButtonClickHandler;
import nl.enterprisecoding.android.sufficient.models.Category;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.text.DateFormatSymbols;
import java.util.Calendar;


/**
 * EditTaskActivity class
 * From here the user can edit and create a task
 *
 * @author Sjors Roelofs & Ferry Wienholts
 */
@ContentView(R.layout.edit_task)
public class EditTaskActivity extends MainActivity {

    @InjectView(R.id.task_title)
    private EditText mTaskTitleInput;
    @InjectView(R.id.task_category)
    private Spinner mTaskCategorySpinner;
    @InjectView(R.id.task_set_date_button)
    private Button mTaskSetDateButton;
    @InjectView(R.id.task_important)
    private CheckBox mTaskImportantCheckBox;

    private long mSelectedTaskId;
    private Calendar mTaskDate;

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

        mTaskManager = new TaskManager(this, mCurrentCategoryID);

        mSelectedTaskId = getIntent().getExtras().getLong(TaskActivity.TASK_ID, 0);

        mTaskManager = new TaskManager(this, (long) 0);

        mActionBar.setBackgroundDrawable(new ColorDrawable(mTaskManager.getCategoryById(mTaskManager.getTaskById(mSelectedTaskId).getCatId()).getColour()));

        mTaskDate = mTaskManager.getTaskById(mSelectedTaskId).getDate();

        updateDateButtonText();

        mTaskSetDateButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This method will be invoked when a view is clicked.
             * @param view The view that received the click.
             */
            @Override
            public void onClick(View view) {

                DatePickerDialog alert = new DatePickerDialog(view.getContext(), null, mTaskDate.get(Calendar.YEAR), mTaskDate.get(Calendar.MONTH), mTaskDate.get(Calendar.DAY_OF_MONTH));
                alert.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                TaskSetDateDialogButtonClickHandler taskSetDateDialogButtonClickHandler = new TaskSetDateDialogButtonClickHandler(mTaskDate);

                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.action_change_date), taskSetDateDialogButtonClickHandler);
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.action_discard), taskSetDateDialogButtonClickHandler);

                alert.show();
            }
        });

        mTaskTitleInput = (EditText) findViewById(R.id.task_title);

        if (mTaskManager.getTaskById(mSelectedTaskId).getTitle().isEmpty()) {
            mActionBar.setTitle(R.string.action_add);
            mTaskTitleInput.setHint(getString(R.string.empty_task));
        } else {
            mActionBar.setTitle(R.string.action_edit);
            mTaskTitleInput.setHint(mTaskManager.getTaskById(mSelectedTaskId).getTitle());
        }

        initTaskCategorySpinner(mTaskCategorySpinner);

        mTaskCategorySpinner.setSelection(findIndexByCategoryId(mTaskManager.getTaskById(mSelectedTaskId).getCatId()));

        mTaskImportantCheckBox = (CheckBox) findViewById(R.id.task_important);
        mTaskImportantCheckBox.setChecked(mTaskManager.getTaskById(mSelectedTaskId).isImportant());

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

    /**
     * Saves a task.
     */
    private void saveTask() {
        int selectedCategoryIndex = mTaskCategorySpinner.getSelectedItemPosition();
        long selectedCategoryID = mTaskManager.getCategories().get(selectedCategoryIndex).getId();

        if (mTaskTitleInput.getText().toString().isEmpty()) {
            mTaskTitleInput.setText(mTaskTitleInput.getHint());
        }

        mTaskManager.updateTask(mTaskTitleInput.getText().toString(), selectedCategoryID, mTaskDate, mTaskImportantCheckBox.isChecked(), mSelectedTaskId);

        startTaskActivity(selectedCategoryID);
    }

    /**
     * Initialises the spinner which will display the available categories
     *
     * @param spinner The spinner which displays the categories
     */
    private void initTaskCategorySpinner(Spinner spinner) {
        ArrayAdapter<Category> mSpinnerArrayAdapter = new ArrayAdapter<Category>(
                this,
                android.R.layout.simple_spinner_item,
                mTaskManager.getCategories()
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

        for (Category cat : mTaskManager.getCategories()) {
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
    public void setTaskDate(int dayOfMonth, int month, int year) {
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

}
