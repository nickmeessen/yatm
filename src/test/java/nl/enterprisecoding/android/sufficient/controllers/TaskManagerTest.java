package nl.enterprisecoding.android.sufficient.controllers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.content.Intent;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.TaskActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.activity.RoboActivity;

import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class TaskManagerTest {

    TaskManager mTaskManager;
    Calendar date;

    @Before
    public void setup() {
        Intent editTaskActivityIntent = new Intent(new TaskActivity(), EditTaskActivity.class);
        editTaskActivityIntent.putExtra(TaskActivity.TASK_ID, 0);
        RoboActivity editTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(editTaskActivityIntent).create().get();

        mTaskManager = new TaskManager(editTaskActivity, (long) 0);

        mTaskManager.createCategory("Category1", 125);
        mTaskManager.createCategory("Category2", 125);

        date = Calendar.getInstance();
        date.set(1993, 01, 20);

    }

    ;

    @Test
    public void test_createTask() {
        assertEquals(1, mTaskManager.createTask("Task", 1, date, true));
    }

    /*@Test
    public void test_deleteTask() {

    }

    @Test
    public void test_getAllCategories() {
    }

    @Test
    public void test_createCategory() {
    }

    @Test
    public void test_getCategories() {
    }

    @Test
    public void test_getVisibleCategories() {
    }*/

    @Test
    public void test_getCategoryByTitle() {
        assertNotNull(mTaskManager.getCategoryByTitle("Category1"));
        assertTrue(mTaskManager.getCategoryByTitle("Category1") instanceof Category);
        assertTrue(mTaskManager.getCategoryByTitle("Category1").getTitle().equals("Category1"));
    }

    /*
    @Test
    public void test_deleteCategoryAndMoveTasks() {
    }

    @Test
    public void test_deleteCategory() {
    }

    @Test
    public void test_switchCategoryVisibility() {
    }

    @Test
    public void test_getCategoryById() {
    }

    @Test
    public void test_getTaskById() {
    }

    @Test
    public void test_updateTask() {
    }

    @Test
    public void test_updateCategory() {
    }*/
}
