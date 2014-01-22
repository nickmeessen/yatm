package nl.enterprisecoding.android.sufficient.activities;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import nl.enterprisecoding.android.sufficient.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class TaskActivityTest {

    private TaskActivity mTaskActivity1;
    private TaskActivity mTaskActivity2;

    @Before
    public void setUp() {

        Intent intent = new Intent(new TaskActivity(), TaskActivity.class);
        intent.putExtra(MainActivity.CATEGORY_ID, 3);

        mTaskActivity1 = Robolectric.buildActivity(TaskActivity.class).withIntent(intent).create().get();
        mTaskActivity2 = Robolectric.buildActivity(TaskActivity.class).create().get();
    }

    @Test
    public void test_onBackPressed1() {
        mTaskActivity1.onBackPressed();
        assertTrue(mTaskActivity1.isFinishing());
    }

    @Test
    public void test_onBackPressed2() {
        mTaskActivity2.onBackPressed();
        assertTrue(mTaskActivity2.isFinishing());
    }

    @Test
    public void test_onCreateContextMenu1() {

        ContextMenu menu = mock(ContextMenu.class);
        View view = mock(View.class);
        ContextMenu.ContextMenuInfo menuInfo = mock(ContextMenu.ContextMenuInfo.class);

        when(view.getId()).thenReturn(R.id.taskList);

        mTaskActivity1.onCreateContextMenu(menu, view, menuInfo);

    }

    @Test
    public void test_onCreateContextMenu2() {

        ContextMenu menu = mock(ContextMenu.class);
        View view = mock(View.class);
        ContextMenu.ContextMenuInfo menuInfo = mock(ContextMenu.ContextMenuInfo.class);

        when(view.getId()).thenReturn(R.id.catText);

        mTaskActivity1.onCreateContextMenu(menu, view, menuInfo);

    }


    @Test
    public void test_onContextItemSelected1() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getTitle()).thenReturn(mTaskActivity1.getString(R.string.action_edit));

        assertTrue(mTaskActivity1.onContextItemSelected(menuItem));
        assertTrue(mTaskActivity2.onContextItemSelected(menuItem));

    }

    @Test
    public void test_onContextItemSelected2() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getTitle()).thenReturn(mTaskActivity1.getString(R.string.toast_no_category));

        assertTrue(mTaskActivity1.onContextItemSelected(menuItem));
        assertTrue(mTaskActivity2.onContextItemSelected(menuItem));

    }

    @Test
    public void test_onOptionsItemSelected1() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getItemId()).thenReturn(R.id.action_add);

        assertTrue(mTaskActivity1.onOptionsItemSelected(menuItem));

        ShadowActivity shadowActivity = Robolectric.shadowOf(mTaskActivity1);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(startedIntent);
        assertEquals(CategoryActivity.class.getName(), shadowIntent.getComponent().getClassName());
    }

    @Test
    public void test_onOptionsItemSelected2() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getItemId()).thenReturn(R.id.action_categories);

        assertTrue(mTaskActivity1.onOptionsItemSelected(menuItem));

        ShadowActivity shadowActivity = Robolectric.shadowOf(mTaskActivity1);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Robolectric.shadowOf(startedIntent);
        assertEquals(CategoryActivity.class.getName(), shadowIntent.getComponent().getClassName());
    }

    @Test
    public void test_onOptionsItemSelected3() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getItemId()).thenReturn(1);

        assertFalse(mTaskActivity1.onOptionsItemSelected(menuItem));
    }
}

