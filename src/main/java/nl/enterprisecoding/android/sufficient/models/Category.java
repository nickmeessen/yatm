/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Category Class
 * Holds several Tasks, a Title and a Colour to represent the category.
 */
public class Category {

    private List<Task> mTasks;
    private long mId;
    private String mTitle;
    private int mColour;
    private int mVisible;

    /**
     * Constructs a new Category.
     */
    public Category() {
        mTasks = new ArrayList<Task>();
    }

    /**
     * Returns mTitle of this category
     *
     * @return mTitle the mTitle of this category.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets mTitle of this category.
     *
     * @param newTitle the new mTitle of this category.
     */
    public void setTitle(String newTitle) {
        mTitle = newTitle;
    }

    /**
     * Setter for the Colour field.
     *
     * @param newColour the new mColour for this category.
     */
    public void setColour(int newColour) {
        mColour = newColour;
    }

    /**
     * Getter for the Colour field.
     *
     * @return mColour the current mColour of this category.
     */
    public int getColour() {
        return mColour;
    }

    /**
     * Getter for the ID field.
     *
     * @return mId the current ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Setter for the ID field.
     *
     * @param i the new ID to set.
     */
    public void setID(long i) {
        mId = i;
    }

    /**
     * Add a new task
     *
     * @param task the task to be added.
     */
    public void addTask(Task task) {

        mTasks.add(task);
    }

    /**
     * Returns if this Category is visible or not.
     *
     * @return boolean based on if this category is visible or not.
     */
    public boolean isVisible() {
        return mVisible == 1;
    }

    /**
     * Returns visibility of this category.
     */
    public int getVisible() {
        return mVisible;
    }

    /**
     * Sets the visibility of this category.
     *
     * @param i 1 if visible, or 0 if invisible.
     */
    public void setVisible(int i) {
        mVisible = i;
    }

    /**
     * Get all mTasks of this category.
     *
     * @return the list of mTasks
     */
    public List<Task> getTasks() {
        return mTasks;
    }

    /**
     * ToString method to show Category Title.
     *
     * @return a string containing this category's title.
     */
    public String toString() {
        return getTitle();
    }
}