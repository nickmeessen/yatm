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
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TaskTest {

    private Task mTask;

    @Before
    public void setup() {
        mTask = new Task();
    }

    @Test
    public void test_setIdGetId() {
        int id = 3;
        mTask.setId(id);

        assertEquals(id, mTask.getId());
    }

    @Test
    public void test_setCatIdGetCatId() {
        int catId = 6;
        mTask.setCategoryId(catId);

        assertEquals(catId, mTask.getCatId());
    }

    @Test
    public void test_setTitleGetTitle() {
        String title = "Yolo";
        mTask.setTitle(title);

        assertEquals(title, mTask.getTitle());
    }

    @Test
    public void test_setDateGetDate() {
        Calendar date = Calendar.getInstance();
        date.set(1993, Calendar.JANUARY, 20);
        mTask.setDate(date);

        assertEquals(date, mTask.getDate());
    }

    @Test
    public void test_setImportantIsImportant() {
        mTask.setImportant(true);
        assertTrue(mTask.isImportant());
    }

    @Test
    public void test_setCompletedIsCompleted() {
        mTask.setCompleted(true);
        assertTrue(mTask.isCompleted());
    }

    @Test
    public void test_toString() {
        String title = "test";
        mTask.setTitle(title);

        assertEquals(title, mTask.toString());
    }
}
