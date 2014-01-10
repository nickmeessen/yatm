/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.controllers;

import android.widget.ExpandableListView;
import android.widget.ListView;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.*;

/**
 * TaskManager Class
 * <p/>
 * Manages several Task Models.
 */
public class TaskManager implements ITaskManager {

    private IDatabaseAdapter mDatabaseAdapter;
    private TaskListAdapter mTaskListAdapter;
    private CategoryListAdapter mCategoryListAdapter;

    private Map<Long, Category> mCategoryList;

    /**
     * Constructs a new TaskManager
     *
     * @param activity   the activity called from.
     * @param categoryID the current CategoryID.
     */
    public TaskManager(MainActivity activity, Long categoryID) {

        mDatabaseAdapter = new SqlLiteAdapter(activity);

        mCategoryList = new TreeMap<Long, Category>();

        for (Category category : mDatabaseAdapter.retrieveAllCategories()) {
            mCategoryList.put(category.getId(), category);
        }

        for (Task task : mDatabaseAdapter.retrieveAllTasks()) {
            mCategoryList.get(task.getCatId()).addTask(task);
        }

        mCategoryListAdapter = new CategoryListAdapter(activity, this);

        ListView categoryView = (ListView) activity.findViewById(R.id.cat_list);
        ExpandableListView tasklistView = (ExpandableListView) activity.findViewById(R.id.taskList);

        if (categoryView != null) {
            categoryView.setAdapter(mCategoryListAdapter);
            categoryView.setOnItemClickListener(mCategoryListAdapter);

            activity.registerForContextMenu(categoryView);
        }

        mDatabaseAdapter.retrieveAllTasks();

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

        Task newTask = mDatabaseAdapter.createTask(title, categoryId, date, important);

        mCategoryList.get(categoryId).addTask(newTask);
        mTaskListAdapter.notifyDataSetChanged();

        return newTask.getId();
    }

    /**
     * Deletes a given task
     *
     * @param taskId the id of the task to be deleted.
     */
    public void deleteTask(long taskId) {
        mDatabaseAdapter.deleteTask(taskId);
        mTaskListAdapter.notifyDataSetChanged();
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
     * Creates a new Category and adds it to the database.
     *
     * @param title  the title of the category to be created.
     * @param colour the colour for the new category.
     */
    public void createCategory(String title, int colour) {

        Category newCategory = mDatabaseAdapter.createCategory(title, colour);

        mCategoryList.put(newCategory.getId(), newCategory);

        mCategoryListAdapter.notifyDataSetChanged();
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
     *
     * @return an array of strings containing the category titles.
     */
    public String[] getCategoriesStringArray() {
        String[] result = new String[mCategoryList.size()];

        int count = 0;
        for (Category cat : mCategoryList.values()) {
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
     * @param originId      the origin category
     * @param destinationId the destination category
     */
    public void deleteCategoryAndMoveTasks(long originId, long destinationId) {
        moveTasks(originId, destinationId);
        deleteCategory(originId);
    }

    /**
     * Deletes the given category and linked tasks.
     *
     * @param categoryId the category to delete.
     */
    public void deleteCategory(long categoryId) {
        mDatabaseAdapter.deleteCategory(categoryId);
        mCategoryList.remove(categoryId);
        mCategoryListAdapter.notifyDataSetChanged();
    }

    /**
     * Moves tasks from one category to another category.
     *
     * @param originId      the origin category
     * @param destinationId the destination category
     */
    private void moveTasks(long originId, long destinationId) {
        List<Task> tasks = getCategoryById(originId).getTasks();
        for (Task task : tasks) {
            task.setCategoryId(destinationId);
            mDatabaseAdapter.updateTask(task);
        }
    }

    /**
     * Switches visibility of the given category.
     *
     * @param category the category to update.
     * @return the new visibility setting.
     */
    public boolean switchCategoryVisibility(Category category) {
        int newVisibility = 1;
        if (category.isVisible()) {
            newVisibility = 0;
        }

        category.setVisible(newVisibility);

        mDatabaseAdapter.updateCategory(category);

        return category.isVisible();
    }

    /**
     * Gets a Category by it's ID.
     *
     * @param id the id of the category to get.
     * @return the category coressponding to the ID.
     */
    public Category getCategoryById(long id) {
        return mCategoryList.get(id);
    }

    /**
     * Gets a task by it's ID.
     *
     * @param id the id of the task to get.
     * @return task with the id given.
     */
    public Task getTaskById(long id) {
        return mDatabaseAdapter.getTask(id);
    }

    /**
     * Updates a task with the given parameters.
     *
     * @param title the new title for this task
     * @param categoryId the new categoryId for this task
     * @param important whether the task is important or not
     * @param taskId the id of the task to be updated.
     */
    public void updateTask(String title, long categoryId, boolean important, long taskId) {

        Task task = mDatabaseAdapter.getTask(taskId);

        task.setTitle(title);
        task.setCategoryId(categoryId);
        task.setImportant(important);

        mDatabaseAdapter.updateTask(task);
    }

    /**
     * Updates a category with the given parameters.
     *
     * @param title the new title for this category
     * @param colour the new colour for this category
     * @param visibility whether this category is visible or not
     * @param categoryId the id of the category to be updated.
     */
    public void updateCategory(String title, int colour, int visibility, long categoryId) {

        Category category = mDatabaseAdapter.getCategory(categoryId);

        category.setTitle(title);
        category.setColour(colour);
        category.setVisible(visibility);

        mDatabaseAdapter.updateCategory(category);

    }
}
