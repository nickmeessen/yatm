package nl.enterprisecoding.android.sufficient.activities;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.content.Intent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import java.util.Calendar;
import static junit.framework.Assert.assertEquals;

/**
 * Test the EditTaskActivity class
 *
 * @author Sjors Roelofs
 */
@RunWith(RobolectricTestRunner.class)
public class EditTaskActivityTest {

    private EditTaskActivity mEditTaskActivity;
    private Intent mEditTaskActivityIntent;
    private Calendar currentDate;

    @Before
    public void setUp() throws Exception {
        mEditTaskActivityIntent = new Intent(new TaskActivity(), EditTaskActivity.class);
        mEditTaskActivityIntent.putExtra(TaskActivity.TASK_ID, 0);
        mEditTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(mEditTaskActivityIntent).create().get();

        currentDate = Calendar.getInstance();
    }

    @Test
    public void test_getTaskDate() throws Exception {
        assertEquals(currentDate.get(Calendar.DAY_OF_MONTH), mEditTaskActivity.getTaskDate().get(Calendar.DAY_OF_MONTH));
        assertEquals(currentDate.get(Calendar.MONTH), mEditTaskActivity.getTaskDate().get(Calendar.MONTH));
        assertEquals(currentDate.get(Calendar.YEAR), mEditTaskActivity.getTaskDate().get(Calendar.YEAR));
    }

    @Test
    public void test_setTaskDate() throws Exception {
        currentDate.add(Calendar.DAY_OF_YEAR, 1);
        mEditTaskActivity.setTaskDate(currentDate.get(Calendar.DAY_OF_MONTH), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.YEAR));

        assertEquals(currentDate.get(Calendar.DAY_OF_MONTH), mEditTaskActivity.getTaskDate().get(Calendar.DAY_OF_MONTH));
        assertEquals(currentDate.get(Calendar.MONTH), mEditTaskActivity.getTaskDate().get(Calendar.MONTH));
        assertEquals(currentDate.get(Calendar.YEAR), mEditTaskActivity.getTaskDate().get(Calendar.YEAR));
    }

}
