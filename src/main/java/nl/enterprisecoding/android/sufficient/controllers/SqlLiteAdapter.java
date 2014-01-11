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
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * DatabaseAdapter implementation for SQL Lite.
 */
public class SqlLiteAdapter extends SQLiteOpenHelper implements IDatabaseAdapter {

    private static final String CATEGORIES_TABLE = "categories";
    private static final String TASKS_TABLE = "tasks";
    private static final String DATABASE_NAME = "yatm.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE = "CREATE TABLE ";
    private static final String DESTROY = "DROP TABLE IF EXISTS ";
    private static final Object P_ID_AI = " integer primary key autoincrement, ";
    private static final String INT_NOT_NULL = " integer not null, ";
    private static final String TEXT_NOT_NULL = " text not null, ";

    private static final String CID_COLUMN = "_id";
    private static final String CTITLE_COLUMN = "title";
    private static final String CCOLOUR_COLUMN = "colour";
    private static final String CVISIBILITY = "visible";

    private static final String[] CALL_COLUMNS = {CID_COLUMN, CTITLE_COLUMN, CCOLOUR_COLUMN, CVISIBILITY};
    private static final String CREATE_CATEGORIES_STATEMENT = CREATE
            + CATEGORIES_TABLE + "(" + CID_COLUMN + P_ID_AI
            + CTITLE_COLUMN + TEXT_NOT_NULL
            + CCOLOUR_COLUMN + INT_NOT_NULL
            + CVISIBILITY + INT_NOT_NULL.replace(", ", "")
            + " );";

    private static final String TCOLUMN_ID = "_id";
    private static final String TCOLUMN_CATID = "_catId";
    private static final String TCOLUMN_TASK = "task";
    private static final String TCOLUMN_DATE = "date";
    private static final String TCOLUMN_IMPORTANT = "important";
    private static final String TCOLUMN_COMPLETED = "completed";

    private static final String[] TALL_COLUMNS = {TCOLUMN_ID, TCOLUMN_CATID, TCOLUMN_TASK, TCOLUMN_DATE, TCOLUMN_IMPORTANT, TCOLUMN_COMPLETED};

    private static final String CREATE_TASKS_STATEMENT = CREATE
            + TASKS_TABLE + "(" + TCOLUMN_ID + P_ID_AI
            + TCOLUMN_CATID + INT_NOT_NULL
            + TCOLUMN_TASK + TEXT_NOT_NULL
            + TCOLUMN_DATE + TEXT_NOT_NULL
            + TCOLUMN_IMPORTANT + INT_NOT_NULL
            + TCOLUMN_COMPLETED + INT_NOT_NULL.replace(", ", "")
            + ");";

    private SQLiteDatabase database;

    /**
     * Constructs a new SQL Lite Adapter and opens the database.
     *
     * @param activity the activity called fromm.
     */
    public SqlLiteAdapter(MainActivity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);

        open();

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
        db.execSQL(DESTROY + CATEGORIES_TABLE);
        db.execSQL(DESTROY + TASKS_TABLE);
        onCreate(db);
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
    public Task createTask(String title, long categoryId, Calendar date, boolean important) {
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

        return newTask;
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
     * @param task the task to update.
     */
    public void updateTask(Task task) {

        int isImportant = 0;
        int isCompleted = 0;

        if (task.isImportant()) {
            isImportant = 1;
        }

        if (task.isCompleted()) {
            isCompleted = 1;
        }

        ContentValues values = new ContentValues();

        values.put(TCOLUMN_COMPLETED, isCompleted);
        values.put(TCOLUMN_IMPORTANT, isImportant);
        values.put(TCOLUMN_CATID, task.getCatId());
        values.put(TCOLUMN_TASK, task.getTitle());
        values.put(TCOLUMN_DATE, task.getDate().getTimeInMillis());

        database.update(TASKS_TABLE, values, TCOLUMN_ID + " = " + task.getId(), null);
    }

    /**
     * Deletes a given task
     *
     * @param id the id of the task to be deleted.
     */
    public void deleteTask(long id) {
        database.delete(TASKS_TABLE, TCOLUMN_ID + " = " + id, null);

    }

    /**
     * Retrieve all tasks and put them in the category they belong.
     */
    public List<Task> retrieveAllTasks() {

        List<Task> taskList = new ArrayList<Task>();

        Cursor cursor = database.query(TASKS_TABLE, TALL_COLUMNS, null, null, null, null, TCOLUMN_IMPORTANT + " DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            taskList.add(cursorToTask(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return taskList;
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

        task.setId(cursor.getLong(cursor.getColumnIndex(TCOLUMN_ID)));
        task.setCategoryId(cursor.getLong(cursor.getColumnIndex(TCOLUMN_CATID)));
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
     * Returns category at the placed cursor.
     *
     * @param cursor the SQlite cursor
     * @return the category where the cursor is placed.
     */
    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();

        category.setID(cursor.getLong(0));
        category.setTitle(cursor.getString(1));
        category.setColour(cursor.getInt(2));
        category.setVisible(cursor.getInt(3));

        return category;
    }


    /**
     * Retrieve all categories.
     */
    public List<Category> retrieveAllCategories() {

        List<Category> categoryList = new ArrayList<Category>();

        Cursor cursor = database.query(CATEGORIES_TABLE, CALL_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categoryList.add(cursorToCategory(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return categoryList;
    }

    /**
     * Creates a new Category and adds it to the database.
     *
     * @param title  the title of the category to be created.
     * @param colour the colour for the new category.
     */
    public Category createCategory(String title, int colour) {
        ContentValues values = new ContentValues();
        values.put(CTITLE_COLUMN, title);
        values.put(CCOLOUR_COLUMN, colour);
        values.put(CVISIBILITY, 1);

        long insertId = database.insert(CATEGORIES_TABLE, null, values);
        Cursor cursor = database.query(CATEGORIES_TABLE, CALL_COLUMNS, CID_COLUMN + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Category newCategory = cursorToCategory(cursor);

        cursor.close();

        return newCategory;
    }


    /**
     * Gets a task by it's ID.
     *
     * @param taskId the id of the task to retrieve.
     * @return the task corresponding to the given ID.
     */
    public Category getCategory(long taskId) {

        Cursor cursor = database.query(TASKS_TABLE, TALL_COLUMNS, TCOLUMN_ID + " = " + taskId, null, null, null, null);

        cursor.moveToFirst();
        Category category = cursorToCategory(cursor);
        cursor.close();

        return category;
    }


    /**
     * Updates a category.
     *
     * @param category the category to update.
     */
    public void updateCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(CTITLE_COLUMN, category.getTitle());
        values.put(CCOLOUR_COLUMN, category.getColour());
        values.put(CVISIBILITY, category.getVisible());
        database.update(CATEGORIES_TABLE, values, CID_COLUMN + " = " + category.getId(), null);
    }

    /**
     * Deletes the given category and linked tasks.
     *
     * @param categoryId the category to delete.
     */
    public void deleteCategory(long categoryId) {
        database.delete(TASKS_TABLE, TCOLUMN_CATID + " = " + categoryId, null);
        database.delete(CATEGORIES_TABLE, CID_COLUMN + " = " + categoryId, null);
    }

}
