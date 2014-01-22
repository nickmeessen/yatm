package nl.enterprisecoding.android.sufficient.models;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.graphics.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class CategoryTest {

    private Category mCategory;

    @Before
    @Test
    public void test_Category() {
        mCategory = new Category();
        assertNotNull(mCategory);
    }

    @Test
    public void test_setTitleGetTitle() {
        String title = "testTitle";
        mCategory.setTitle(title);

        assertEquals("testTitle", mCategory.getTitle());
    }

    @Test
    public void test_setColourGetColour() {
        int colour = Color.RED;
        mCategory.setColour(colour);

        assertEquals(Color.RED, mCategory.getColour());
    }

    @Test
    public void test_setIdGetId() {
        int id = 5;
        mCategory.setID(id);

        assertEquals(id, mCategory.getId());
    }

    @Test
    public void test_addTaskGetTasks() {
        Task task = new Task();
        mCategory.addTask(task);

        assertEquals(task, mCategory.getTasks().get(0));
    }

    @Test
    public void test_setVisible() {
        mCategory.setVisible(1);
        assertEquals(1, mCategory.getVisible());
    }

    @Test
    public void test_isVisible() {
        mCategory.setVisible(0);
        assertEquals(false, mCategory.isVisible());

        mCategory.setVisible(1);
        assertEquals(true, mCategory.isVisible());
    }

    @Test
    public void test_toString() {
        String title = "CategoryTitle";
        mCategory.setTitle(title);

        assertEquals(title, mCategory.toString());
    }

    public void removeAllTasks() {
        for (Task t : mCategory.getTasks()) {
            mCategory.removeTask(t);
        }
    }

    @Test
    public void test_removeTask() {
        removeAllTasks();

        Task task = new Task();
        mCategory.addTask(task);
        mCategory.removeTask(task);

        assertEquals(0, mCategory.getTasks().size());
    }

}
