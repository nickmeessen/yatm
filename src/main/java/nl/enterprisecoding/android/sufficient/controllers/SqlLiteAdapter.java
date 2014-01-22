/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * DatabaseAdapter implementation for SQL Lite.
 */
public class SqlLiteAdapter extends SQLiteOpenHelper implements IDatabaseAdapter {

    private static final String sCategoriesTable = "categories";
    private static final String sTasksTable = "tasks";
    private static final String sDatabaseName = "yatm.db";
    private static final int sDatabaseVersion = 1;

    private static final String sCreateTable = "CREATE TABLE ";
    private static final String sDestroyTable = "DROP TABLE IF EXISTS ";
    private static final Object sPrimaryKey = " integer primary key autoincrement, ";
    private static final String sIntNotNull = " integer not null, ";
    private static final String sTextNotNull = " text not null, ";

    private static final String sCidColumn = "_id";
    private static final String sTitleColumn = "title";
    private static final String sColourColumn = "colour";
    private static final String sVisible = "visible";

    private static final String[] sCategoryColumns = {sCidColumn, sTitleColumn, sColourColumn, sVisible};
    private static final String sCreateCategoriesStatement = sCreateTable
            + sCategoriesTable + "(" + sCidColumn + sPrimaryKey
            + sTitleColumn + sTextNotNull
            + sColourColumn + sIntNotNull
            + sVisible + sIntNotNull.replace(", ", "")
            + " );";

    private static final String sTaskIdColumn = "_id";
    private static final String sTaskCatIdColumn = "_catId";
    private static final String sTaskColumn = "task";
    private static final String sTaskDateColumn = "date";
    private static final String sTaskImportantColumn = "important";
    private static final String sTaskCompletedColumn = "completed";

    private static final String[] sTaskAllColumns = {sTaskIdColumn, sTaskCatIdColumn, sTaskColumn, sTaskDateColumn, sTaskImportantColumn, sTaskCompletedColumn};

    private static final String sCreateTasksStatement = sCreateTable
            + sTasksTable + "(" + sTaskIdColumn + sPrimaryKey
            + sTaskCatIdColumn + sIntNotNull
            + sTaskColumn + sTextNotNull
            + sTaskDateColumn + sTextNotNull
            + sTaskImportantColumn + sIntNotNull
            + sTaskCompletedColumn + sIntNotNull.replace(", ", "")
            + ");";

    private SQLiteDatabase mDatabase;

    /**
     * Constructs a new SQL Lite Adapter and opens the database.
     *
     * @param activity the activity called fromm.
     */
    public SqlLiteAdapter(Activity activity) {
        super(activity, sDatabaseName, null, sDatabaseVersion);
        open();
    }

    /**
     * Opens a SQLite database.
     */
    private void open() {
        mDatabase = getWritableDatabase();
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sCreateTasksStatement);
        db.execSQL(sCreateCategoriesStatement);
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
        db.execSQL(sDestroyTable + sCategoriesTable);
        db.execSQL(sDestroyTable + sTasksTable);
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

        values.put(sTaskCompletedColumn, 0);
        values.put(sTaskImportantColumn, important ? 1 : 0);
        values.put(sTaskCatIdColumn, categoryId);
        values.put(sTaskColumn, title);
        values.put(sTaskDateColumn, date.getTimeInMillis());

        long insertId = mDatabase.insert(sTasksTable, null, values);
        Cursor cursor = mDatabase.query(sTasksTable, sTaskAllColumns, sTaskIdColumn + " = " + insertId, null, null, null, null);
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
        Cursor cursor = mDatabase.query(sTasksTable, sTaskAllColumns, sTaskIdColumn + " = " + taskId, null, null, null, null);

        Task task = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            task = cursorToTask(cursor);
        }

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

        values.put(sTaskCompletedColumn, isCompleted);
        values.put(sTaskImportantColumn, isImportant);
        values.put(sTaskCatIdColumn, task.getCatId());
        values.put(sTaskColumn, task.getTitle());
        values.put(sTaskDateColumn, task.getDate().getTimeInMillis());

        mDatabase.update(sTasksTable, values, sTaskIdColumn + " = " + task.getId(), null);
    }

    /**
     * Deletes a given task
     *
     * @param id the id of the task to be deleted.
     */
    public void deleteTask(long id) {
        mDatabase.delete(sTasksTable, sTaskIdColumn + " = " + id, null);
    }

    /**
     * Retrieve all tasks and put them in the category they belong.
     */
    public List<Task> retrieveAllTasks() {

        List<Task> taskList = new ArrayList<Task>();

        Cursor cursor = mDatabase.query(sTasksTable, sTaskAllColumns, null, null, null, null, sTaskImportantColumn + " DESC");

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
        taskDate.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(sTaskDateColumn)));

        taskDate.set(Calendar.HOUR_OF_DAY, 0);
        taskDate.set(Calendar.MINUTE, 0);
        taskDate.set(Calendar.SECOND, 0);
        taskDate.set(Calendar.MILLISECOND, 0);

        task.setId(cursor.getLong(cursor.getColumnIndex(sTaskIdColumn)));
        task.setCategoryId(cursor.getLong(cursor.getColumnIndex(sTaskCatIdColumn)));
        task.setTitle(cursor.getString(cursor.getColumnIndex(sTaskColumn)));
        task.setDate(taskDate);

        if (cursor.getInt(cursor.getColumnIndex(sTaskImportantColumn)) == 1) {
            task.setImportant(true);
        } else {
            task.setImportant(false);
        }

        if (cursor.getInt(cursor.getColumnIndex(sTaskCompletedColumn)) == 1) {
            task.setCompleted(true);
        } else {
            task.setCompleted(false);
        }

        return task;
    }


    /**
     * Returns category at the placed cursor.
     *
     * @param cursor the SQLite cursor
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

        Cursor cursor = mDatabase.query(sCategoriesTable, sCategoryColumns, null, null, null, null, null);

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
        values.put(sTitleColumn, title);
        values.put(sColourColumn, colour);
        values.put(sVisible, 1);

        long insertId = mDatabase.insert(sCategoriesTable, null, values);
        Cursor cursor = mDatabase.query(sCategoriesTable, sCategoryColumns, sCidColumn + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        Category newCategory = cursorToCategory(cursor);

        cursor.close();

        return newCategory;
    }


    /**
     * Gets a category by it's ID.
     *
     * @param catId the id of the task to retrieve.
     * @return the category corresponding to the given ID.
     */
    public Category getCategory(long catId) {
        Cursor cursor = mDatabase.query(sCategoriesTable, sCategoryColumns, sCidColumn + " = " + catId, null, null, null, null);

        Category category = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            category = cursorToCategory(cursor);
        }

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
        values.put(sTitleColumn, category.getTitle());
        values.put(sColourColumn, category.getColour());
        values.put(sVisible, category.getVisible());
        mDatabase.update(sCategoriesTable, values, sCidColumn + " = " + category.getId(), null);
    }

    /**
     * Deletes the given category and linked tasks.
     *
     * @param categoryId the category to delete.
     */
    public void deleteCategory(long categoryId) {
        mDatabase.delete(sTasksTable, sTaskCatIdColumn + " = " + categoryId, null);
        mDatabase.delete(sCategoriesTable, sCidColumn + " = " + categoryId, null);
    }

}
