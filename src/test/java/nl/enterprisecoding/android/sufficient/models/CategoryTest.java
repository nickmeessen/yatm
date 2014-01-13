package nl.enterprisecoding.android.sufficient.models;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

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
        String title = Mockito.anyString();
        category.setTitle(title);

        assertEquals(category.getTitle(), title);
    }

    @Test
    public void test_setColourGetColour() {
        int colour = Mockito.anyInt();
        category.setColour(colour);

        assertEquals(category.getColour(), colour);
    }

    @Test
    public void test_setIdGetId() {
        int id = Mockito.anyInt();
        category.setID(id);

        assertEquals(category.getId(), id);
    }

    @Test
    public void test_addTaskGetTasks() {
        Task task = new Task();
        category.addTask(task);

        assertEquals(category.getTasks().get(0), task);
    }

    @Test
    public void test_setVisible() {
        category.setVisible(1);
        assertEquals(category.getVisible(), 1);
    }

    @Test
    public void test_isVisible() {
        category.setVisible(0);
        assertEquals(category.isVisible(), false);

        category.setVisible(1);
        assertEquals(category.isVisible(), true);
    }

    @Test
    public void test_toString() {
        String title = Mockito.anyString();
        category.setTitle(title);

        assertEquals(category.toString(), title);
    }

}
