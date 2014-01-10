/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.controllers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.widget.ExpandableListView;
import android.widget.ListView;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.helpers.SQLHelper;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.*;

/**
 * TaskManager Class
 * <p/>
 * Manages several Task Models.
 */
public class TaskManager extends SQLiteOpenHelper implements ITaskManager {

    private SQLiteDatabase database;

    private static final String CATEGORIES_TABLE = "categories";
    private static final String TASKS_TABLE = "tasks";
    private static final String DATABASE_NAME = "yatm.db";
    private static final int DATABASE_VERSION = 1;

    private TaskListAdapter mTaskListAdapter;
    private CategoryListAdapter mCategoryListAdapter;

    private static final String TCOLUMN_ID = "_id";
    private static final String TCOLUMN_CATID = "_catId";
    private static final String TCOLUMN_TASK = "task";
    private static final String TCOLUMN_DATE = "date";
    private static final String TCOLUMN_IMPORTANT = "important";
    private static final String TCOLUMN_COMPLETED = "completed";

    private static final String[] TALL_COLUMNS = {TCOLUMN_ID, TCOLUMN_CATID, TCOLUMN_TASK, TCOLUMN_DATE, TCOLUMN_IMPORTANT, TCOLUMN_COMPLETED};

    private static final String CREATE_TASKS_STATEMENT = SQLHelper.CREATE
            + TASKS_TABLE + "(" + TCOLUMN_ID + SQLHelper.P_ID_AI
            + TCOLUMN_CATID + SQLHelper.INT_NOT_NULL
            + TCOLUMN_TASK + SQLHelper.TEXT_NOT_NULL
            + TCOLUMN_DATE + SQLHelper.TEXT_NOT_NULL
            + TCOLUMN_IMPORTANT + SQLHelper.INT_NOT_NULL
            + TCOLUMN_COMPLETED + SQLHelper.INT_NOT_NULL.replace(", ", "")
            + ");";


    private Map<Long, Category> mCategoryList;

    private static final String CID_COLUMN = "_id";
    private static final String CTITLE_COLUMN = "title";
    private static final String CCOLOUR_COLUMN = "colour";
    private static final String CVISIBILITY = "visible";

    private static final String[] CALL_COLUMNS = {CID_COLUMN, CTITLE_COLUMN, CCOLOUR_COLUMN, CVISIBILITY};
    private static final String CREATE_CATEGORIES_STATEMENT = SQLHelper.CREATE
            + CATEGORIES_TABLE + "(" + CID_COLUMN + SQLHelper.P_ID_AI
            + CTITLE_COLUMN + SQLHelper.TEXT_NOT_NULL
            + CCOLOUR_COLUMN + SQLHelper.INT_NOT_NULL
            + CVISIBILITY + SQLHelper.INT_NOT_NULL.replace(", ", "")
            + " );";

    private MainActivity mActivity;

    /**
     * Constructs a new TaskManager
     *
     * @param activity   the activity called from.
     * @param categoryID the current CategoryID.
     */
    public TaskManager(MainActivity activity, Long categoryID) {

        super(activity, DATABASE_NAME, null, DATABASE_VERSION);

        mActivity = activity;

        open();

        mCategoryList = new TreeMap<Long, Category>();

        retrieveAllCategories();

        mCategoryListAdapter = new CategoryListAdapter(activity, this);

        ListView categoryView = (ListView) activity.findViewById(R.id.cat_list);
        ExpandableListView tasklistView = (ExpandableListView) activity.findViewById(R.id.taskList);

        if (categoryView != null) {
            categoryView.setAdapter(mCategoryListAdapter);
            categoryView.setOnItemClickListener(mCategoryListAdapter);

            activity.registerForContextMenu(categoryView);
        }

        retrieveAllTasks();

        mTaskListAdapter = new TaskListAdapter(activity, this, categoryID);

        if (tasklistView != null) {
            tasklistView.setAdapter(mTaskListAdapter);
            tasklistView.setOnChildClickListener(mTaskListAdapter);
            tasklistView.expandGroup(0, true);
            tasklistView.expandGroup(1, true);
            tasklistView.expandGroup(2, true);
            tasklistView.expandGroup(3, true);
        }
    }

    /**
     * Creates a new Task and adds it to the database.
     *
     * @param title      task title
     * @param categoryId categoryID of task
     * @param date       date of task
     * @param important  task important?
     * @return task the created task's ID.
     */
    public long createTask(String title, long categoryId, Calendar date, boolean important) {
        ContentValues values = new ContentValues();

        values.put(TCOLUMN_COMPLETED, 0);
        values.put(TCOLUMN_IMPORTANT, important ? 1 : 0);
        values.put(TCOLUMN_CATID, categoryId);
        values.put(TCOLUMN_TASK, title);
        values.put(TCOLUMN_DATE, date.getTimeInMillis());

        long insertId = database.insert(TASKS_TABLE, null, values);
        Cursor cursor = database.query(TASKS_TABLE, TALL_COLUMNS, TCOLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();

        mCategoryList.get(categoryId).addTask(newTask);
        mTaskListAdapter.notifyDataSetChanged();

        return newTask.getID();
    }

    /**
     * Deletes a given task
     *
     * @param task the task to be deleted.
     */
    public void deleteTask(Task task) {
        long id = task.getID();
        database.delete(TASKS_TABLE, TCOLUMN_ID + " = " + id, null);
        mTaskListAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieve all tasks and put them in the category they belong.
     */
    private void retrieveAllTasks() {

        Cursor cursor = database.query(TASKS_TABLE, TALL_COLUMNS, null, null, null, null, TCOLUMN_IMPORTANT + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);

            mCategoryList.get(task.getCatID()).addTask(task);

            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * Returns task at the placed cursor.
     *
     * @param cursor the SQlite cursor
     * @return the task where the cursor is placed.
     */
    private Task cursorToTask(Cursor cursor) {

        Task task = new Task();

        Calendar taskDate = Calendar.getInstance();
        taskDate.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(TCOLUMN_DATE)));

        task.setID(cursor.getLong(cursor.getColumnIndex(TCOLUMN_ID)));
        task.setCatId(cursor.getLong(cursor.getColumnIndex(TCOLUMN_CATID)));
        task.setTitle(cursor.getString(cursor.getColumnIndex(TCOLUMN_TASK)));
        task.setDate(taskDate);

        if (cursor.getInt(cursor.getColumnIndex(TCOLUMN_IMPORTANT)) == 1) {
            task.setImportant(true);
        } else {
            task.setImportant(false);
        }

        if (cursor.getInt(cursor.getColumnIndex(TCOLUMN_COMPLETED)) == 1) {
            task.setCompleted(true);
        } else {
            task.setCompleted(false);
        }

        return task;
    }

    /**
     * Gets a task by it's ID.
     *
     * @param taskId the id of the task to retrieve.
     * @return the task corresponding to the given ID.
     */
    public Task getTask(long taskId) {

        Cursor cursor = database.query(TASKS_TABLE, TALL_COLUMNS, TCOLUMN_ID + " = " + taskId, null, null, null, null);

        cursor.moveToFirst();
        Task task = cursorToTask(cursor);
        cursor.close();

        return task;
    }

    /**
     * Updates a task.
     *
     * @param title      the new title of the task to update.
     * @param categoryId the new categoryID of the task to update.
     * @param date       the new date of the task to update.
     * @param important  wether the task is marked as important or not
     * @param completed  wether the task is marked as completed or not.
     * @param taskID     the id of the task to update
     */
    public void editTask(String title, long categoryId, Calendar date, boolean important, boolean completed, long taskID) {
        int mIsImportant, taskIsCompleted;
        if (important) {
            mIsImportant = 1;
        } else {
            mIsImportant = 0;
        }

        if (completed) {
            taskIsCompleted = 1;
        } else {
            taskIsCompleted = 0;
        }

        ContentValues values = new ContentValues();

        values.put(TCOLUMN_COMPLETED, taskIsCompleted);
        values.put(TCOLUMN_IMPORTANT, mIsImportant);
        values.put(TCOLUMN_CATID, categoryId);
        values.put(TCOLUMN_TASK, title);
        values.put(TCOLUMN_DATE, date.getTimeInMillis());

        database.update(TASKS_TABLE, values, TCOLUMN_ID + " = " + taskID, null);
    }

    /**
     * Opens a SQLite database.
     */
    private void open() {
        database = getWritableDatabase();
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASKS_STATEMENT);
        db.execSQL(CREATE_CATEGORIES_STATEMENT);

        ContentValues values = new ContentValues();
        values.put(CID_COLUMN, 0);
        values.put(CTITLE_COLUMN, mActivity.getString(R.string.all_categories));
        values.put(CCOLOUR_COLUMN, Color.BLACK);
        values.put(CVISIBILITY, 1);

        db.insert(CATEGORIES_TABLE, null, values);
    }

    /**
     * Called when database is upgraded
     *
     * @param db         the database to upgrade
     * @param oldVersion the old version number
     * @param newVersion the new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLHelper.DESTROY + CATEGORIES_TABLE);
        db.execSQL(SQLHelper.DESTROY + TASKS_TABLE);
        onCreate(db);
    }

    /**
     * Retrieve all categories.
     */
    private void retrieveAllCategories() {

        Cursor cursor = database.query(CATEGORIES_TABLE, CALL_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category cat = cursorToCategory(cursor);
            mCategoryList.put(cat.getID(), cat);
            cursor.moveToNext();
        }
        cursor.close();

    }

    /**
     * Returns all categories.
     *
     * @return a map containing all categories, mapped by CategoryID.
     */
    public Map<Long, Category> getAllCategories() {
        return mCategoryList;
    }

    /**
     * Returns category at the placed cursor.
     *
     * @param cursor the SQlite cursor
     * @return the category where the cursor is placed.
     */
    private Category cursorToCategory(Cursor cursor) {
        Category cat = new Category();

        boolean mCatVisibility = true;
        if (cursor.getLong(3) == 0) {
            mCatVisibility = false;
        }

        cat.setID(cursor.getLong(0));
        cat.setTitle(cursor.getString(1));
        cat.setColour(cursor.getInt(2));
        cat.setVisible(mCatVisibility);

        return cat;
    }

    /**
     * Creates a new Category and adds it to the database.
     *
     * @param title  the title of the category to be created.
     * @param colour the colour for the new category.
     */
    public void createCategory(String title, int colour) {
        ContentValues values = new ContentValues();
        values.put(CTITLE_COLUMN, title);
        values.put(CCOLOUR_COLUMN, colour);
        values.put(CVISIBILITY, 1);

        long insertId = database.insert(CATEGORIES_TABLE, null, values);
        Cursor cursor = database.query(CATEGORIES_TABLE, CALL_COLUMNS, CID_COLUMN + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Category newCategory = cursorToCategory(cursor);

        mCategoryList.put(newCategory.getID(), newCategory);

        cursor.close();

        mCategoryListAdapter.notifyDataSetChanged();
    }

    /**
     * Updates a category.
     *
     * @param title      the new category title
     * @param colour     the new category colour.
     * @param categoryID the ID of the category to update
     */
    public void editCategory(String title, int colour, Long categoryID) {
        ContentValues values = new ContentValues();
        values.put(CTITLE_COLUMN, title);
        values.put(CCOLOUR_COLUMN, colour);
        database.update(CATEGORIES_TABLE, values, CID_COLUMN + " = " + categoryID, null);
    }


    /**
     * Get all categories.
     *
     * @return a list of all categories.
     */
    public List<Category> getCategories() {
        return new ArrayList<Category>(mCategoryList.values());
    }

    /**
     * Retrieves a list of categories in the form of a string array.
     * @return an array of strings containing the category titles.
     */
    public String[] getCategoriesStringArray() {
        String[] result = new String[categoryList.size()];

        int count = 0;
        for (Category cat : categoryList) {
            result[count] = cat.getTitle();
            count++;
        }

        return result;
    }

    /**
     * Gets all visible categories.
     *
     * @return a list of all categories that are currently visible.
     */
    public List<Category> getVisibleCategories() {
        ArrayList<Category> mVisibleCategories = new ArrayList<Category>();
        for (Category cat : mCategoryList.values()) {
            if (cat.isVisible()) {
                mVisibleCategories.add(cat);
            }
        }
        return mVisibleCategories;
    }

    /**
     * Retrieves a Category by it's Title.
     *
     * @param categoryTitle the category title to search for
     * @return the found category, or null when not found.
     */
    public Category getCategoryByTitle(String categoryTitle) {
        for (Category c : mCategoryList.values()) {
            if (c.getTitle().equals(categoryTitle)) {
                return c;
            }
        }

        return null;
    }

    /**
     * Delete given category and move tasks to given destination category.
     *
     * @param origin      the origin category
     * @param destination the destination category
     */
    public void deleteCategoryAndMoveTasks(Category origin, Category destination) {
        moveTasks(origin, destination);
        deleteCategory(origin);
    }

    /**
     * Deletes the given category and linked tasks.
     *
     * @param category the category to delete.
     */
    public void deleteCategory(Category category) {
        long id = category.getID();

        database.delete(TASKS_TABLE, TCOLUMN_CATID + " = " + id, null);
        database.delete(CATEGORIES_TABLE, CID_COLUMN + " = " + id, null);

        mCategoryList.remove(category.getID());

        mCategoryListAdapter.notifyDataSetChanged();
    }

    /**
     * Moves tasks from one category to another category.
     *
     * @param origin      the origin category
     * @param destination the destination category
     */
    private void moveTasks(Category origin, Category destination) {
        List<Task> tasks = origin.getTasks();
        for (Task t : tasks) {
            long id = t.getID();
            ContentValues values = new ContentValues();
            values.put(TCOLUMN_CATID, destination.getID());
            database.update(TASKS_TABLE, values, TCOLUMN_ID + " = " + id, null);
        }
    }

    /**
     * Switches visibility of the given category.
     *
     * @param category the category to update.
     * @return the new visibility setting.
     */
    public boolean switchCategoryVisibility(Category category) {
        boolean mNewVisibility = true;
        if (category.isVisible()) {
            mNewVisibility = false;
        }

        ContentValues values = new ContentValues();
        values.put(CVISIBILITY, mNewVisibility);
        database.update(CATEGORIES_TABLE, values, CID_COLUMN + " = " + category.getID(), null);

        category.setVisible(mNewVisibility);

        return mNewVisibility;
    }

    /**
     * Gets a Category by it's ID.
     *
     * @param id the id of the category to get.
     * @return the category coressponding to the ID.
     */
    public Category getCategoryById(long id) {
        return mCategoryListAdapter.getItemById(id);
    }
}
