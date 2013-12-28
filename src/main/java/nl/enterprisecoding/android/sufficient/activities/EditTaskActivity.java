/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * EditTaskActivity class
 * From here the user can edit a task
 *
 * @author Sjors Roelofs & Ferry Wienholts
 */
public class EditTaskActivity extends MainActivity {

    private final Activity mActivity = this;
    private EditText mTaskTitleInput;
    private Spinner mTaskCategorySpinner;
    private Button mTaskSetDateButton;
    private CheckBox mTaskImportantCheckBox;
    private Calendar mDateToday;
    private Calendar mTaskDate;
    private List<Category> mCategoriesArray = new ArrayList<Category>();
    private Task mTask;
    private long mSelectedTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_item);

        mSelectedTask = getIntent().getExtras().getLong(TaskActivity.TASK_ID, 0);

        mActionBar.setTitle(R.string.action_edit);

        mTaskManager = new TaskManager(this, (long) 0);
        mTask = mTaskManager.getTask(mSelectedTask);
        mActionBar.setBackgroundDrawable(new ColorDrawable(mTaskManager.getItemById(mTask.getCatId()).getColour()));

        mDateToday = Calendar.getInstance();
        mTaskDate = Calendar.getInstance();
        mTaskDate.set(mTask.getDate().get(Calendar.YEAR), mTask.getDate().get(Calendar.MONTH), mTask.getDate().get(Calendar.DAY_OF_MONTH));

        mTaskTitleInput = (EditText) findViewById(R.id.task_title);
        mTaskTitleInput.setText(mTask.getTitle());

        mTaskCategorySpinner = (Spinner) findViewById(R.id.task_category);
        initTaskCategorySpinner(mTaskCategorySpinner);

        mTaskCategorySpinner.setSelection(findIndexByCategoryId(mTask.getCatId()));

        mTaskSetDateButton = (Button) findViewById(R.id.task_set_date_button);
        updateDateButtonText();

        mTaskSetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
                final DatePicker datePicker = new DatePicker(mActivity);

                datePicker.setCalendarViewShown(false);

                datePicker.init(mTaskDate.get(Calendar.YEAR), mTaskDate.get(Calendar.MONTH), mTaskDate.get(Calendar.DAY_OF_MONTH), null);
                datePicker.setMinDate(mDateToday.getTime().getTime());

                alert.setView(datePicker);

                alert.setPositiveButton(R.string.action_change_date, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setTaskDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    }
                });

                alert.setNegativeButton(R.string.action_discard, null);
                alert.show();
            }
        });


        mTaskImportantCheckBox = (CheckBox) findViewById(R.id.task_important);
        mTaskImportantCheckBox.setChecked(mTask.isImportant());

        Button mAddTaskButton = (Button) findViewById(R.id.add_task_button);
        mAddTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTaskTitle = mTaskTitleInput.getText().toString();

                boolean mDataIsValidated = true;
                if (mTaskTitle.isEmpty()) {
                    mDataIsValidated = false;
                    mTaskTitleInput.setBackgroundColor(getResources().getColor(R.color.red));
                }

                int mSelectedCategoryIndex = mTaskCategorySpinner.getSelectedItemPosition();
                long mSelectedCategoryId = mCategoriesArray.get(mSelectedCategoryIndex).getID();

                if (mDataIsValidated) {
                    mTaskManager.editTask(mTaskTitle, mSelectedCategoryId, mTaskDate, mTaskImportantCheckBox.isChecked(), mTask.isCompleted(), mSelectedTask);
                    showTaskActivity();
                } else {
                    Toast mInvalidDataToast = Toast.makeText(getApplicationContext(), R.string.toast_invalid_data, Toast.LENGTH_SHORT);
                    mInvalidDataToast.show();
                }
            }
        });

    }

    private void initTaskCategorySpinner(Spinner spinner) {
        mCategoriesArray = mTaskManager.getCategories();
        mCategoriesArray.remove(0);

        ArrayAdapter<String> mSpinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                convertCategoryListToStringArray(mCategoriesArray)
        );

        mSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSpinnerArrayAdapter);
    }

    private String[] convertCategoryListToStringArray(List<Category> categoryList) {
        String[] result = new String[categoryList.size()];

        int count = 0;
        for (Category cat : categoryList) {
            result[count] = cat.getTitle();
            count++;
        }

        return result;
    }

    private int findIndexByCategoryId(long categoryId) {
        int index = 0;

        int count = 0;
        for (Category cat : mCategoriesArray) {
            if (cat.getID() == categoryId) {
                index = count;
                break;
            }
            count++;
        }

        return index;
    }

    private void setTaskDate(int dayOfMonth, int month, int year) {
        mTaskDate.set(year, month, dayOfMonth);
        updateDateButtonText();
    }

    private void updateDateButtonText() {
        mTaskSetDateButton.setText(mTaskDate.get(Calendar.DAY_OF_MONTH) + "/" + (mTaskDate.get(Calendar.MONTH) + 1) + "/" + mTaskDate.get(Calendar.YEAR));
    }

    private void showTaskActivity() {
        Intent intent = new Intent(mActivity, TaskActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
