package nl.enterprisecoding.android.sufficient.activities;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import nl.enterprisecoding.android.sufficient.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

@RunWith(RobolectricTestRunner.class)
public class CategoryActivityTest {

    private CategoryActivity mCategoryActivity1;
    private CategoryActivity mCategoryActivity2;

    @Before
    public void setUp() {

        Intent intent = new Intent(new CategoryActivity(), CategoryActivity.class);
        intent.putExtra(MainActivity.CATEGORY_ID, 3);

        mCategoryActivity1 = Robolectric.buildActivity(CategoryActivity.class).withIntent(intent).create().get();
        mCategoryActivity2 = Robolectric.buildActivity(CategoryActivity.class).create().get();
    }

    @Test

    public void test_onKeyUp() {
        assertTrue(mCategoryActivity1.onKeyUp(KeyEvent.KEYCODE_MENU, null));
        assertFalse(mCategoryActivity2.onKeyUp(KeyEvent.KEYCODE_0, null));
    }


    @Test
    public void test_onBackPressed1() {
        mCategoryActivity1.onBackPressed();
        assertTrue(mCategoryActivity1.isFinishing());
    }

    @Test
    public void test_onBackPressed2() {
        mCategoryActivity2.onBackPressed();
        assertTrue(mCategoryActivity2.isFinishing());
    }


    @Test
    public void test_onCreateContextMenu1() {

        ContextMenu menu = mock(ContextMenu.class);
        View view = mock(View.class);
        ContextMenu.ContextMenuInfo menuInfo = mock(ContextMenu.ContextMenuInfo.class);

        when(view.getId()).thenReturn(R.id.cat_list);

        mCategoryActivity1.onCreateContextMenu(menu, view, menuInfo);

    }

    @Test
    public void test_onCreateContextMenu2() {

        ContextMenu menu = mock(ContextMenu.class);
        View view = mock(View.class);
        ContextMenu.ContextMenuInfo menuInfo = mock(ContextMenu.ContextMenuInfo.class);

        when(view.getId()).thenReturn(R.id.catText);

        mCategoryActivity1.onCreateContextMenu(menu, view, menuInfo);

    }



    @Test
    public void test_onContextItemSelected1() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getTitle()).thenReturn(mCategoryActivity1.getString(R.string.action_edit));

        assertTrue(mCategoryActivity1.onContextItemSelected(menuItem));
        assertTrue(mCategoryActivity2.onContextItemSelected(menuItem));

    }

    @Test
    public void test_onContextItemSelected2() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getTitle()).thenReturn(mCategoryActivity1.getString(R.string.toast_no_category));

        assertTrue(mCategoryActivity1.onContextItemSelected(menuItem));
        assertTrue(mCategoryActivity2.onContextItemSelected(menuItem));

    }
}
