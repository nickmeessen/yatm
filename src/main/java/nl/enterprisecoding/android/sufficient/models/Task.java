/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.models;

import java.util.Calendar;

/**
 * Task Class
 * Represents a Task Object.
 */
public class Task {

    private long mId;
    private long mCatId;
    private String mTitle;
    private boolean mImportant = false;
    private boolean mCompleted = false;
    private Calendar mDate;

    /**
     * Setter for ID field.
     *
     * @param id new ID for this Task.
     */
    public void setId(long id) {
        mId = id;
    }

    /**
     * Getter for the ID field.
     *
     * @return the id of this task.
     */
    public long getId() {
        return mId;
    }


    /**
     * Setter for CatId field.
     *
     * @param id new CatId for this Task.
     */
    public void setCatId(long id) {
        mCatId = id;
    }

    /**
     * Getter for the CatId field.
     *
     * @return the CatId of this task.
     */
    public long getCatId() {
        return mCatId;
    }


    /**
     * Getter for the Title field.
     *
     * @return the title of this Task.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Setter for the Title field.
     *
     * @param title the new title for this task.
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Getter for Date field.
     *
     * @return the date of this task.
     */
    public Calendar getDate() {
        return mDate;
    }

    /**
     * Setter for the Date field.
     *
     * @param date the new date for this task.
     */
    public void setDate(Calendar date) {
        mDate = date;
    }

    /**
     * Returns whether this task is marked as important or not.
     *
     * @return boolean task is important true/false
     */
    public boolean isImportant() {
        return mImportant;
    }

    /**
     * Setter for the Important field.
     *
     * @param important boolean value true/false for importance of this task.
     */
    public void setImportant(boolean important) {
        mImportant = important;
    }

    /**
     * Returns whether this task is completed or not.
     *
     * @return boolean task is completed or not
     */
    public boolean isCompleted() {
        return mCompleted;
    }

    /**
     * Setter for the Completed field.
     *
     * @param completed boolean value true/false for completion of this task.
     */
    public void setCompleted(boolean completed) {
        this.mCompleted = completed;
    }

}