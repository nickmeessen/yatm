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
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class CategoryTest {

    private Category category;

    @Before
    @Test
    public void test_Category() {
        category = new Category();
        assertNotNull(category);
    }

    @Test
    public void test_setTitleGetTitle() {
        String title = "testTitle";
        category.setTitle(title);

        assertEquals("testTitle", category.getTitle());
    }

    @Test
    public void test_setColourGetColour() {
        int colour = Color.RED;
        category.setColour(colour);

        assertEquals(Color.RED, category.getColour());
    }

    @Test
    public void test_setIdGetId() {
        int id = 5;
        category.setID(id);

        assertEquals(id, category.getId());
    }

    @Test
    public void test_addTaskGetTasks() {
        Task task = new Task();
        category.addTask(task);

        assertEquals(task, category.getTasks().get(0));
    }

    @Test
    public void test_setVisible() {
        category.setVisible(1);
        assertEquals(1, category.getVisible());
    }

    @Test
    public void test_isVisible() {
        category.setVisible(0);
        assertEquals(false, category.isVisible());

        category.setVisible(1);
        assertEquals(true, category.isVisible());
    }

    @Test
    public void test_toString() {
        String title = Mockito.anyString();
        category.setTitle(title);

        assertEquals(title, category.toString());
    }

    public void removeAllTasks() {
        for(Task t : category.getTasks()) {
            category.removeTask(t);
        }
    }

    @Test
    public void test_removeTask() {
        removeAllTasks();

        Task task = new Task();
        category.addTask(task);
        category.removeTask(task);

        assertEquals(0, category.getTasks().size());
    }

}
