package nl.enterprisecoding.android.sufficient.activities;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import nl.enterprisecoding.android.sufficient.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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

        when(menuItem.getTitle()).thenReturn(mCategoryActivity1.getString(R.string.action_edit_category));
        AdapterView.AdapterContextMenuInfo menuInfo = mock(AdapterView.AdapterContextMenuInfo.class);
        when(menuItem.getMenuInfo()).thenReturn(menuInfo);
        menuInfo.id = 0;

        assertTrue(mCategoryActivity1.onContextItemSelected(menuItem));
        assertTrue(mCategoryActivity2.onContextItemSelected(menuItem));

    }

    @Test
    public void test_onContextItemSelected2() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getTitle()).thenReturn(mCategoryActivity1.getString(R.string.toast_no_category));
        AdapterView.AdapterContextMenuInfo menuInfo = mock(AdapterView.AdapterContextMenuInfo.class);
        when(menuItem.getMenuInfo()).thenReturn(menuInfo);
        menuInfo.id = 0;

        assertTrue(mCategoryActivity1.onContextItemSelected(menuItem));
        assertTrue(mCategoryActivity2.onContextItemSelected(menuItem));

    }

    @Test
    public void test_onContextItemSelected3() {

        MenuItem menuItem = mock(MenuItem.class);

        when(menuItem.getTitle()).thenReturn(mCategoryActivity1.getString(R.string.action_delete_category));
        AdapterView.AdapterContextMenuInfo menuInfo = mock(AdapterView.AdapterContextMenuInfo.class);
        when(menuItem.getMenuInfo()).thenReturn(menuInfo);
        menuInfo.id = 0;

        assertTrue(mCategoryActivity1.onContextItemSelected(menuItem));
        assertTrue(mCategoryActivity2.onContextItemSelected(menuItem));

    }


    @Test
    public void test_onKey() {

        int keyCode = KeyEvent.KEYCODE_ENTER;
        KeyEvent keyEvent = mock(KeyEvent.class);
        when(keyEvent.getAction()).thenReturn(KeyEvent.ACTION_DOWN);

        mCategoryActivity1.onKey(mock(View.class), keyCode, keyEvent);

        keyCode = KeyEvent.KEYCODE_ENTER;
        keyEvent = mock(KeyEvent.class);
        when(keyEvent.getAction()).thenReturn(KeyEvent.ACTION_DOWN);

        mCategoryActivity1.onKey(mock(View.class), keyCode, keyEvent);

        keyCode = KeyEvent.KEYCODE_SHIFT_RIGHT;
        keyEvent = mock(KeyEvent.class);
        when(keyEvent.getAction()).thenReturn(KeyEvent.ACTION_UP);

        mCategoryActivity1.onKey(mock(View.class), keyCode, keyEvent);

        keyCode = KeyEvent.KEYCODE_SHIFT_RIGHT;
        keyEvent = mock(KeyEvent.class);
        when(keyEvent.getAction()).thenReturn(KeyEvent.ACTION_UP);

        mCategoryActivity1.onKey(mock(View.class), keyCode, keyEvent);
    }

    @Test
    public void test_OnClick() {

        View view = mock(View.class);

        when(view.getId()).thenReturn(R.id.newCategory);
        mCategoryActivity1.onClick(view);

        when(view.getId()).thenReturn(R.id.catAddButton);
        mCategoryActivity1.onClick(view);

        when(view.getId()).thenReturn(R.id.all_cats);
        mCategoryActivity1.onClick(view);

        when(view.getId()).thenReturn(R.id.catChangeVisibilityButton);
        mCategoryActivity1.onClick(view);
    }
}
