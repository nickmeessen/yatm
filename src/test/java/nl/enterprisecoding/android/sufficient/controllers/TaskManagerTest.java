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

    @Before
    public void setup() {
        Intent editTaskActivityIntent = new Intent(new TaskActivity(), EditTaskActivity.class);
        editTaskActivityIntent.putExtra(TaskActivity.TASK_ID, 0);
        RoboActivity editTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(editTaskActivityIntent).create().get();

        mTaskManager = new TaskManager(editTaskActivity, (long) 0);

        date = Calendar.getInstance();
        date.set(1993, 01, 20);

    }

    @Test
    public void test_createTask() {
        long catId = mTaskManager.createCategory("setupCategory2", 125);
        assertEquals(1, mTaskManager.createTask("Task",catId, date, true));
    }

    @Test
    public void test_deleteTask() {
        long catId = mTaskManager.createCategory("TestCategory", 125);
        mTaskManager.createTask("delete", catId, date, true);
        assertNotNull(mTaskManager.getTaskById(catId));
        mTaskManager.deleteTask(catId);
        assertNull(mTaskManager.getTaskById(catId));
    }

    @Test
    public void test_getAllCategories() {
        long cat1Id = mTaskManager.createCategory("TestCategory1", 125);
        mTaskManager.createCategory("TestCategory2", 125);
        mTaskManager.createTask("CreateTask", cat1Id, date, true);

        Map<Long, Category> actual = mTaskManager.getAllCategories();

        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(1, actual.get(cat1Id).getTasks().size());
    }

    @Test
    public void test_createCategory() {
        String title = "CreateCategoryTest";
        int color = 125;
        long catId = mTaskManager.createCategory(title, color);

        assertNotNull(mTaskManager.getCategoryById(catId));
        assertTrue(mTaskManager.getCategoryById(catId) instanceof Category);
        assertEquals(mTaskManager.getCategoryById(catId).getTitle(), title);
        assertEquals(mTaskManager.getCategoryById(catId).getColour(), color);
    }

    @Test
    public void test_getCategories() {
        mTaskManager.createCategory("TestCategory1", 125);
        mTaskManager.createCategory("TestCategory2", 125);
        List<Category> actual = mTaskManager.getCategories();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    public void test_getVisibleCategories() {
        long cat1Id = mTaskManager.createCategory("TestCategory1", 125);
        assertEquals(1, mTaskManager.getCategoryById(cat1Id).getVisible());
    }

    @Test
    public void test_getCategoryByTitle() {
        String catTitle = "Shopping";
        mTaskManager.createCategory(catTitle, 125);
        assertNotNull(mTaskManager.getCategoryByTitle(catTitle));
        assertTrue(mTaskManager.getCategoryByTitle(catTitle) instanceof Category);
        assertTrue(mTaskManager.getCategoryByTitle(catTitle).getTitle().equals(catTitle));
    }


    @Test
    public void test_deleteCategoryAndMoveTasks() {
        long category1Id = mTaskManager.createCategory("originCategory", 125);
        long category2Id = mTaskManager.createCategory("destinationCategory", 125);
        long task2Id = mTaskManager.createTask("CreateTask2", category1Id, date, true);
        long task1Id = mTaskManager.createTask("CreateTask1", category1Id, date, true);

        Map<Long, Category> actual = mTaskManager.getAllCategories();
        assertEquals(category1Id, mTaskManager.getTaskById(task1Id).getCatId());
        assertEquals(category1Id, mTaskManager.getTaskById(task2Id).getCatId());

        mTaskManager.deleteCategoryAndMoveTasks(category1Id,category2Id);

        mTaskManager.getCategoryById(category2Id).getTasks();
        assertNull(mTaskManager.getCategoryById(category1Id));
        assertNotNull(mTaskManager.getCategoryById(category2Id));
        assertEquals(category2Id, mTaskManager.getTaskById(task1Id).getCatId());
        assertEquals(category2Id, mTaskManager.getTaskById(task2Id).getCatId());
    }

    @Test
    public void test_deleteCategory() {
        long catId = mTaskManager.createCategory("DeletingCategory", 125);
        assertNotNull(mTaskManager.getCategoryById(catId));
        mTaskManager.deleteCategory(catId);
        assertNull(mTaskManager.getCategoryById(catId));
    }

    @Test
    public void test_switchCategoryVisibility() {
        long catId = mTaskManager.createCategory("switchingVisabilityCategory", 125);
        assertTrue(mTaskManager.getCategoryById(catId).isVisible());
        mTaskManager.switchCategoryVisibility(mTaskManager.getCategoryById(catId));
        assertFalse(mTaskManager.getCategoryById(catId).isVisible());
    }

    @Test
    public void test_getCategoryById() {
        long catId = mTaskManager.createCategory("getCategory", 125);
        assertNotNull(mTaskManager.getCategoryById(catId));
        assertTrue(mTaskManager.getCategoryById(catId) instanceof Category);
        assertEquals(catId, mTaskManager.getCategoryById(catId).getId());
    }

    @Test
    public void test_getTaskById() {
        long catId = mTaskManager.createCategory("Category", 125);
        long taskId = mTaskManager.createTask("CreateNewTask", catId, date, true);
        Task actual = mTaskManager.getTaskById(taskId);
        assertNotNull(actual);
        assertEquals(taskId, actual.getId());
    }

    @Test
    public void test_updateTask() {
        long cat1Id = mTaskManager.createCategory("firstCategory", 125);
        long taskNewCatId = mTaskManager.createCategory("secondCategory", 125);
        String taskNewTitle = "UpdateTask";
        Calendar taskNewDate = Calendar.getInstance();
        taskNewDate.set(1993, 7, 29);
        Boolean taskNewImportant = false;

        long taskId = mTaskManager.createTask("CreateNewTask", cat1Id, date, true);
        Task actual = mTaskManager.getTaskById(taskId);
        assertNotSame(taskNewTitle, actual.getTitle());
        assertNotSame(taskNewCatId, actual.getCatId());
        assertNotSame(taskNewImportant, actual.isImportant());

        mTaskManager.updateTask(taskNewTitle, taskNewCatId, taskNewDate, taskNewImportant, cat1Id);
        actual = mTaskManager.getTaskById(taskId);
        assertEquals(taskNewTitle, actual.getTitle());
        assertEquals(taskNewCatId, actual.getCatId());
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
        assertNotNull(actual);
        assertEquals(newTitle, actual.getTitle());
        assertEquals(newColour, actual.getColour());
        assertEquals(newVisibility, actual.getVisible());
    }

}
