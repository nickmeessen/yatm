/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import nl.enterprisecoding.android.sufficient.models.Task;

/**
 * TaskActivity class
 *
 * From here a user could execute various actions on tasks.
 */
public class TaskActivity extends MainActivity {

    private Long mCategoryID;
    private long mSelectedTaskId;
    public static final String TASK_ID = "taskID";
    public static final String CATEGORY_ID = "categoryID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        final ExpandableListView mTaskListView = (ExpandableListView) findViewById(R.id.taskList);
        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedTaskId = ((Task) parent.getItemAtPosition(position)).getId();
                return false;
            }
        });

        registerForContextMenu(mTaskListView);

        mActionBar.setTitle(R.string.title_tasks);

        mCategoryID = getIntent().getLongExtra(CATEGORY_ID, 0);

        mTaskManager = new TaskManager(this, mCategoryID);

        if (mCategoryID != 0) {
            mActionBar.setTitle(mTaskManager.getItemById(mCategoryID).getTitle());
            mActionBar.setBackgroundDrawable(new ColorDrawable(mTaskManager.getItemById(mCategoryID).getColour()));
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId() == R.id.taskList) {
            String[] menuItems = getResources().getStringArray(R.array.task_context_menu);

            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(getResources().getString(R.string.action_edit))) {
            if (mSelectedTaskId == 0) {
                Toast.makeText(getApplicationContext(), R.string.toast_error, Toast.LENGTH_SHORT).show();
            } else {
                Intent mEditTaskActivity = new Intent(this, EditTaskActivity.class);
                mEditTaskActivity.putExtra(TASK_ID, mSelectedTaskId);
                startActivity(mEditTaskActivity);
            }
        } else if (item.getTitle().equals(getResources().getString(R.string.action_delete))) {
            mTaskManager.deleteTask(mTaskManager.getTask(mSelectedTaskId));
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startAddTaskActivity();
                return true;
            case R.id.action_categories:
                startActivity(new Intent(this, CategoryActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void startAddTaskActivity() {
        Intent mAddTaskActivity = new Intent(this, AddTaskActivity.class);
        mAddTaskActivity.putExtra("categoryID", mCategoryID);
        startActivity(mAddTaskActivity);
    }

    @Override
    public void onBackPressed() {
        if (mCategoryID != 0) {

            Intent intent = new Intent(this, TaskActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        } else {
            super.onBackPressed();
        }
    }

}
