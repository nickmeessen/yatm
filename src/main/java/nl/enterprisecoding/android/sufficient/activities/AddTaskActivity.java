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
import android.view.WindowManager;
import android.widget.*;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import nl.enterprisecoding.android.sufficient.models.Category;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * AddTaskActivity class
 * From here the user can add a task
 *
 * @author Sjors Roelofs
 */
public class AddTaskActivity extends MainActivity {

    private Activity mActivity = this;
    private EditText mTaskTitleInput;
    private Spinner mTaskCategorySpinner;
    private Button mTaskSetDateButton;
    private CheckBox mTaskImportantCheckBox;
    private Calendar mDateToday;
    private Calendar mTaskDate;
    private List<Category> mCategoriesArray = new ArrayList<Category>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_item);

        long mCurrentCategory = getIntent().getExtras().getLong(TaskActivity.CATEGORY_ID, 0);
        mTaskManager = new TaskManager(this, mCurrentCategory);

        mActionBar.setTitle(R.string.action_add);

        mTaskManager = new TaskManager(this, (long) 0);
        mActionBar.setBackgroundDrawable(new ColorDrawable(mTaskManager.getItemById(mCurrentCategory).getColour()));

        mDateToday = Calendar.getInstance();
        mTaskDate = Calendar.getInstance();

        mTaskTitleInput = (EditText) findViewById(R.id.task_title);
        mTaskTitleInput.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mTaskCategorySpinner = (Spinner) findViewById(R.id.task_category);
        initTaskCategorySpinner(mTaskCategorySpinner);

        if (mCurrentCategory != 0) {
            mTaskCategorySpinner.setSelection(findIndexByCategoryId(mCurrentCategory));
        }

        mTaskSetDateButton = (Button) findViewById(R.id.task_set_date_button);
        updateDateButtonText();

        mTaskSetDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
                final DatePicker datePicker = new DatePicker(mActivity);

                datePicker.setCalendarViewShown(false);
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

                if (mSelectedCategoryIndex < 0) {
                    Toast.makeText(getApplicationContext(), R.string.toast_no_category, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mActivity, CategoryActivity.class);
                    mActivity.startActivity(intent);
                } else {
                    long mSelectedCategoryId = mCategoriesArray.get(mSelectedCategoryIndex).getID();

                    if (mDataIsValidated) {
                        mTaskManager.createTask(mTaskTitle, mSelectedCategoryId, mTaskDate, mTaskImportantCheckBox.isChecked());
                        showTaskActivity(mSelectedCategoryId);
                    } else {
                        Toast mInvalidDataToast = Toast.makeText(getApplicationContext(), R.string.toast_invalid_data, Toast.LENGTH_SHORT);
                        mInvalidDataToast.show();
                    }
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

    private void showTaskActivity(long catId) {
        Intent intent = new Intent(mActivity, TaskActivity.class);

        intent.putExtra("categoryID", catId);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
