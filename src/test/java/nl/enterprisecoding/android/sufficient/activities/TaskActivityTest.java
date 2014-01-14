package nl.enterprisecoding.android.sufficient.activities;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.app.ActionBar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import nl.enterprisecoding.android.sufficient.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import javax.naming.Context;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TaskActivityTest {

    private TaskActivity mTaskActivity;

    @Before
    public void setUp() {
        mTaskActivity = Robolectric.buildActivity(TaskActivity.class).create().get();
    }

    @Test
    public void test_onBackPressed() {
        mTaskActivity.onBackPressed();
        ShadowActivity shadowActivity = Robolectric.shadowOf(mTaskActivity);

        assertTrue(shadowActivity.isFinishing());
    }
}
