/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.controllers;

import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.util.Calendar;
import java.util.List;

/**
 * Interface for the DatabaseAdapter
 */
public interface IDatabaseAdapter {


    /**
     * Creates a new Task and adds it to the database.
     *
     * @param title      task title
     * @param categoryId categoryID of task
     * @param date       date of task
     * @param important  task important?
     * @return task the created task's ID.
     */
    Task createTask(String title, long categoryId, Calendar date, boolean important);


    /**
     * Gets a task by it's ID.
     *
     * @param taskId the id of the task to retrieve.
     * @return the task corresponding to the given ID.
     */
    Task getTask(long taskId);

    /**
     * Updates a task.
     *
     * @param task the task to update.
     */
    void updateTask(Task task);

    /**
     * Deletes a given task
     *
     * @param id the id of task to be deleted.
     */
    void deleteTask(long id);

    /**
     * Retrieve all tasks and put them in the category they belong.
     *
     * @return a list of all tasks.
     */
    List<Task> retrieveAllTasks();

    /**
     * Creates a new Category and adds it to the database.
     *
     * @param title  the title of the category to be created.
     * @param colour the colour for the new category.
     */
    Category createCategory(String title, int colour);

    /**
     * Updates a category.
     *
     * @param category the category to update.
     */
    void updateCategory(Category category);

    /**
     * Deletes the given category and linked tasks.
     *
     * @param id the id of the category to delete.
     */
    void deleteCategory(long id);

    /**
     * Retrieve all categories.
     *
     * @return a list of all categories.
     */
    List<Category> retrieveAllCategories();

}
