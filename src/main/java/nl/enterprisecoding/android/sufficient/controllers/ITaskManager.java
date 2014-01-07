package nl.enterprisecoding.android.sufficient.controllers;

import java.util.Calendar;

/**
 * TaskManager Interface Class
 */
public interface ITaskManager {

    /**
     * Creates a new Category and adds it to the database.
     *
     * @param title  the title of the category to be created.
     * @param colour the colour for the new category.
     */
    void createCategory(String title, int colour);

    /**
     * Creates a new Task and adds it to the database.
     *
     * @param title      task title
     * @param categoryId categoryID of task
     * @param date       date of task
     * @param important  task important?
     * @return task the created task
     */
    long createTask(String title, long categoryId, Calendar date, boolean important);

}
