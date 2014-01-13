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

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TaskTest {

    private Task task;

    @Before
    public void setup() {
        task = new Task();
    }

    @Test
    public void test_setIdGetId() {
        int id = Mockito.anyInt();
        task.setId(id);

        assertEquals(id, task.getId());
    }

    @Test
    public void test_setCatIdGetCatId() {
        int catId = Mockito.anyInt();
        task.setCategoryId(catId);

        assertEquals(catId, task.getCatId());
    }

    @Test
    public void test_setTitleGetTitle() {
        String title = Mockito.anyString();
        task.setTitle(title);

        assertEquals(title, task.getTitle());
    }

    @Test
    public void test_setDateGetDate() {
        Calendar date = Calendar.getInstance();
        date.set(1993, Calendar.JANUARY, 20);
        task.setDate(date);

        assertEquals(date, task.getDate());
    }

    @Test
    public void test_setImportantIsImportant() {
        task.setImportant(true);
        assertTrue(task.isImportant());
    }

    @Test
    public void Test_setCompletedIsCompleted() {
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }
}
