package nl.enterprisecoding.android.sufficient.controllers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.content.Intent;
import android.graphics.Color;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.TaskActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.activity.RoboActivity;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.*;

/**
 * @author Sjors Roelofs
 */
@RunWith(RobolectricTestRunner.class)
public class SqlLiteAdapterTest {

    private SqlLiteAdapter mAdapter;

    @Test
    @Before
    public void test_SqlLiteAdapter() {
        Intent editTaskActivityIntent = new Intent(new TaskActivity(), EditTaskActivity.class);
        editTaskActivityIntent.putExtra(TaskActivity.TASK_ID, 0);
        RoboActivity editTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(editTaskActivityIntent).create().get();

        mAdapter = new SqlLiteAdapter(editTaskActivity);
    }

//    @Test
//    public void test_onCreate() {
//        //@TODO: below
//        assertEquals(true, false);
//    }
//
//    @Test
//    public void test_onUpgrade() {
//        //@TODO: below
//        assertEquals(true, false);
//    }

    @Test
    public void test_createTask() {
        Task newTask = mAdapter.createTask("Test task", 0, Calendar.getInstance(), false);
        assertNotNull(newTask);

        if(newTask != null) {
            mAdapter.deleteTask(newTask.getId());
        }
    }

    @Test
    public void test_getTask() {
        Task newTask = mAdapter.createTask("Test task 2", 0, Calendar.getInstance(), false);
        assertNotNull(mAdapter.getTask(newTask.getId()));

        if(newTask != null) {
            mAdapter.deleteTask(newTask.getId());
        }
    }

    @Test
    public void test_updateTask() {
        String newTaskName = "Test task 2";
        Task newTask = mAdapter.createTask(newTaskName, 0, Calendar.getInstance(), false);
        assertEquals(newTaskName, newTask.getTitle());

        String editedTaskName = "Test task 2 edited";
        newTask.setTitle(editedTaskName);
        newTask.setImportant(true);
        Calendar newDate = Calendar.getInstance();
        newDate.add(Calendar.DAY_OF_MONTH, 2);
        newTask.setDate(newDate);
        newTask.setCompleted(true);

        mAdapter.updateTask(newTask);

        assertEquals(editedTaskName, mAdapter.getTask(newTask.getId()).getTitle());
        assertTrue(mAdapter.getTask(newTask.getId()).isImportant());
        assertEquals(newDate.get(Calendar.DAY_OF_MONTH), mAdapter.getTask(newTask.getId()).getDate().get(Calendar.DAY_OF_MONTH));
        assertEquals(newDate.get(Calendar.MONTH), mAdapter.getTask(newTask.getId()).getDate().get(Calendar.MONTH));
        assertEquals(newDate.get(Calendar.YEAR), mAdapter.getTask(newTask.getId()).getDate().get(Calendar.YEAR));
        assertTrue(mAdapter.getTask(newTask.getId()).isImportant());

        if(newTask != null) {
            mAdapter.deleteTask(newTask.getId());
        }
    }

    @Test
    public void test_deleteTask() {
        Task newTask = mAdapter.createTask("Test task 3", 0, Calendar.getInstance(), false);
        long taskId = newTask.getId();

        assertNotNull(mAdapter.getTask(taskId));
        mAdapter.deleteTask(taskId);

        assertNull(mAdapter.getTask(taskId));
    }

    @Test
    public void test_retrieveAllTasks() {
        mAdapter.createTask("Test task 1.1", 0, Calendar.getInstance(), false);
        mAdapter.createTask("Test task 1.2", 0, Calendar.getInstance(), false);

        List<Task> task = mAdapter.retrieveAllTasks();

        assertTrue("expected to be greater than", task.size() > 1);
    }

    @Test
    public void test_createCategory() {
        Category newCat = mAdapter.createCategory("Test category", Color.BLUE);
        assertNotNull(newCat);

        if(newCat != null) {
            mAdapter.deleteCategory(newCat.getId());
        }
    }

//    @Test
//    public void test_getCategory() {
//        Category newCat = mAdapter.createCategory("Test category 2", Color.GREEN);
//        assertNotNull(mAdapter.getCategory(newCat.getId()));
//
//        if(newCat != null) {
//            mAdapter.deleteCategory(newCat.getId());
//        }
//    }
//
//    @Test
//    public void test_updateCategory() {
//        String newCatName = "Test category 3";
//        Category newCat = mAdapter.createCategory(newCatName, Color.CYAN);
//        assertEquals(newCatName, newCat.getTitle());
//
//        String editedCatName = "Test category 3 edited";
//        newCat.setTitle(editedCatName);
//        int newColour = Color.MAGENTA;
//        newCat.setColour(newColour);
//        newCat.setVisible(0);
//
//        mAdapter.updateCategory(newCat);
//
//        assertEquals(editedCatName, mAdapter.getCategory(newCat.getId()).getTitle());
//        assertEquals(newColour, mAdapter.getCategory(newCat.getId()).getColour());
//        assertTrue(mAdapter.getCategory(newCat.getId()).isVisible());
//
//        if(newCat != null) {
//            mAdapter.deleteCategory(newCat.getId());
//        }
//    }
//
//    @Test
//    public void test_deleteCategory() {
//        //@TODO: below
//        assertEquals(true, false);
//    }
//
//    @Test
//    public void test_retrieveAllCategories() {
//        //@TODO: below
//        assertEquals(true, false);
//    }

}
