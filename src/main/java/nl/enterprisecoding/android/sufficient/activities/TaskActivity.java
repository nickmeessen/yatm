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
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.Calendar;
import java.util.List;

/**
 * TaskActivity class
 * <p/>
 * From here a user could execute various actions on tasks.
 *
 * @author Nick Meessen
 */
public class TaskActivity extends MainActivity {

    private Long mCategoryID;
    private long mSelectedTaskId;
    public static final String TASK_ID = "taskID";
    public static final String CATEGORY_ID = "categoryID";

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        final ExpandableListView taskListView = (ExpandableListView) findViewById(R.id.taskList);
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedTaskId = ((Task) parent.getItemAtPosition(position)).getId();
                return false;
            }
        });

        registerForContextMenu(taskListView);

        mActionBar.setTitle(R.string.title_tasks);

        mCategoryID = getIntent().getLongExtra(CATEGORY_ID, 0);

        mTaskManager = new TaskManager(this, mCategoryID);

        if (mCategoryID != 0) {
            mActionBar.setTitle(mTaskManager.getCategoryById(mCategoryID).getTitle());
            mActionBar.setBackgroundDrawable(new ColorDrawable(mTaskManager.getCategoryById(mCategoryID).getColour()));
        }


    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId() == R.id.taskList) {
            String[] menuItems = getResources().getStringArray(R.array.task_context_menu);

            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    /**
     * This hook is called whenever an item in a context menu is selected.
     *
     * @param item The context menu item that was selected.
     * @return boolean Return false to allow normal context menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(getResources().getString(R.string.action_edit))) {
            if (mSelectedTaskId == 0) {
                Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT).show();
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

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startEditTaskActivity();
                return true;
            case R.id.action_categories:
                startCategoryActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Starts the "Edit Task" activity.
     */
    private void startEditTaskActivity() {

        List<Category> catList = mTaskManager.getCategories();

        if (mTaskManager.getCategories().size() == 1) {
            Toast.makeText(this, R.string.toast_no_category, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {

            long newTaskID = mTaskManager.createTask("New Task", catList.get(0).getID(), Calendar.getInstance(), false);

            Intent intent = new Intent(this, EditTaskActivity.class);
            intent.putExtra(TASK_ID, newTaskID);
            startActivity(intent);
        }
    }

    /**
     * Starts the "Categories" activity.
     */
    private void startCategoryActivity() {
        startActivity(new Intent(this, CategoryActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.
     */
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
