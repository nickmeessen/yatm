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
 * <p/>
 * Holds several Tasks, a Title and a Colour to represent the category.
 */
public class Category {

    private List<Task> mTasks;
    private long mId;
    private String mTitle;
    private int mColour;
    private boolean mVisible;

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
    public long getID() {
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
     * Gets visibility of this category.
     *
     * @return boolean based on if this category is visible.
     */
    public boolean isVisible() {
        return mVisible;
    }

    /**
     * Sets the visibility of this category.
     *
     * @param b the visible boolean
     */
    public void setVisible(boolean b) {
        mVisible = b;
    }

    /**
     * Get all mTasks of this category.
     *
     * @return the list of mTasks
     */
    public List<Task> getTasks() {
        return mTasks;
    }
}