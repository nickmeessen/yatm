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
import nl.enterprisecoding.android.sufficient.models.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import roboguice.activity.RoboActivity;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class TaskManagerTest {

    TaskManager mTaskManager;
    Calendar date;
    long cat1Id, cat2Id;

    @Before
    public void setup() {
        Intent editTaskActivityIntent = new Intent(new TaskActivity(), EditTaskActivity.class);
        editTaskActivityIntent.putExtra(TaskActivity.TASK_ID, 0);
        RoboActivity editTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(editTaskActivityIntent).create().get();

        mTaskManager = new TaskManager(editTaskActivity, (long) 0);

        cat1Id = mTaskManager.createCategory("Category1", 125);
        cat2Id = mTaskManager.createCategory("Category2", 125);

        date = Calendar.getInstance();
        date.set(1993, 01, 20);

    }

    @Test
    public void test_createTask() {
        assertEquals(1, mTaskManager.createTask("Task",(long) 1, date, true));
    }

    @Test
    public void test_deleteTask() {
        mTaskManager.createTask("delete", (long) 1, date, true);
        assertNotNull(mTaskManager.getTaskById((long) 1));
        mTaskManager.deleteTask((long) 1);
        assertNull(mTaskManager.getTaskById((long) 1));
    }

    @Test
    public void test_getAllCategories() {
        mTaskManager.createTask("CreateTask", (long) 1, date, true);
        Map<Long, Category> actual = mTaskManager.getAllCategories();
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(1, actual.get((long) 1).getTasks().size());

    }


    @Test
    public void test_createCategory() {
        mTaskManager.createCategory("Category3", 125);

        assertNotNull(mTaskManager.getCategoryByTitle("Category3"));
        assertTrue(mTaskManager.getCategoryByTitle("Category3") instanceof Category);
        assertTrue(mTaskManager.getCategoryByTitle("Category3").getTitle().equals("Category3"));
    }

    @Test
    public void test_getCategories() {
        List<Category> actual = mTaskManager.getCategories();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    public void test_getVisibleCategories() {

    }

    @Test
    public void test_getCategoryByTitle() {
        assertNotNull(mTaskManager.getCategoryByTitle("Category1"));
        assertTrue(mTaskManager.getCategoryByTitle("Category1") instanceof Category);
        assertTrue(mTaskManager.getCategoryByTitle("Category1").getTitle().equals("Category1"));
    }


//    @Test
//    public void test_deleteCategoryAndMoveTasks() {
//        long category1Id = mTaskManager.createCategory("originCategory", 125);
//        long category2Id = mTaskManager.createCategory("destinationCategory", 125);
//        long task1Id = mTaskManager.createTask("CreateTask1", category1Id, date, true);
//        long task2Id = mTaskManager.createTask("CreateTask2", category1Id, date, true);
//
//
//
//        Map<Long, Category> actual = mTaskManager.getAllCategories();
//        assertEquals(category1Id, mTaskManager.getTaskById(task1Id).getCatId());
//        assertEquals(category1Id, mTaskManager.getTaskById(task2Id).getCatId());
//
//        mTaskManager.deleteCategoryAndMoveTasks(category1Id,category2Id);
//
//        mTaskManager.getCategoryById(category2Id).getTasks();
//        assertNull(mTaskManager.getCategoryById(category1Id));
//        assertNotNull(mTaskManager.getCategoryById(category2Id));
//        assertEquals(category2Id, mTaskManager.getTaskById(task1Id).getCatId());
//        assertEquals(category2Id, mTaskManager.getTaskById(task2Id).getCatId());
//    }

    @Test
    public void test_deleteCategory() {
        mTaskManager.createCategory("DeletingCategory", 125);
        long catId = mTaskManager.getCategoryByTitle("DeletingCategory").getId();
        assertNotNull(mTaskManager.getCategoryById(catId));
        mTaskManager.deleteCategory(catId);
        assertNull(mTaskManager.getCategoryById(catId));
    }

    @Test
    public void test_switchCategoryVisibility() {
        mTaskManager.createCategory("switchingVisabilityCategory", 125);
        Category cat = mTaskManager.getCategoryByTitle("switchingVisabilityCategory");
        assertTrue(cat.isVisible());
        mTaskManager.switchCategoryVisibility(cat);
        assertFalse(cat.isVisible());
    }

    @Test
    public void test_getCategoryById() {
        assertNotNull(mTaskManager.getCategoryById(1));
        assertTrue(mTaskManager.getCategoryById(1) instanceof Category);
        assertEquals((long) 1, mTaskManager.getCategoryById((long) 1).getId());
    }

    @Test
    public void test_getTaskById() {
        long taskId = 1L;
        mTaskManager.createTask("CreateNewTask", taskId, date, true);
        Task actual = mTaskManager.getTaskById(taskId);
        assertNotNull(actual);
        assertEquals(taskId, actual.getId());
    }

    @Test
    public void test_updateTask() {
        String taskNewTitle = "UpdateTask";
        long taskNewCatId = 2L;
        Calendar taskNewDate = Calendar.getInstance();
        taskNewDate.set(1993, 7, 29);
        Boolean taskNewImportant =  false;

        mTaskManager.createTask("CreateNewTask", 1L, date, true);
        Task actual = mTaskManager.getTaskById(1L);
        assertNotSame(taskNewTitle, actual.getTitle());
        assertNotSame(taskNewCatId, actual.getCatId());
        assertNotSame(taskNewDate, actual.getDate());
        assertNotSame(taskNewImportant, actual.isImportant());

        mTaskManager.updateTask(taskNewTitle, taskNewCatId, taskNewDate, taskNewImportant, 1L);
        actual = mTaskManager.getTaskById(1L);
        assertEquals(taskNewTitle, actual.getTitle());
        assertEquals(taskNewCatId, actual.getCatId());
        //assertEquals(taskNewDate, actual.getDate());
        assertEquals(taskNewImportant, actual.isImportant());
    }

    @Test
    public void test_updateCategory() {
        String newTitle = "UpdateCategory";
        int newColour = 100;
        int newVisibility = 0;

        mTaskManager.createCategory("switchingVisabilityCategory", 125);
        Category actual = mTaskManager.getCategoryByTitle("switchingVisabilityCategory");
        assertNotSame(newTitle, actual.getTitle());
        assertNotSame(newColour, actual.getColour());
        assertNotSame(newVisibility, actual.getVisible());

        mTaskManager.updateCategory(newTitle, newColour, newVisibility, actual.getId());
        actual = mTaskManager.getCategoryById(actual.getId());
        assertNotNull(actual);
        assertEquals(newTitle, actual.getTitle());
        assertEquals(newColour, actual.getColour());
        assertEquals(newVisibility, actual.getVisible());
    }

}
