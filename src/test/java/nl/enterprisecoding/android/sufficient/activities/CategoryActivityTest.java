package nl.enterprisecoding.android.sufficient.activities;

import android.view.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.*;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

@RunWith(RobolectricTestRunner.class)
public class CategoryActivityTest {

    private CategoryActivity mCategoryActivity;

    @Before
    public void setUp() {
        mCategoryActivity = Robolectric.buildActivity(CategoryActivity.class).create().get();
    }

    @Test
    public void test_onKeyUp() {
        assertTrue(mCategoryActivity.onKeyUp(KeyEvent.KEYCODE_MENU, null));
        assertFalse(mCategoryActivity.onKeyUp(KeyEvent.KEYCODE_0, null));
    }

    @Test
    public void test_onBackPressed() {
        mCategoryActivity.onBackPressed();
        ShadowActivity shadowActivity = Robolectric.shadowOf(mCategoryActivity);

        assertTrue(shadowActivity.isFinishing());
    }
}
