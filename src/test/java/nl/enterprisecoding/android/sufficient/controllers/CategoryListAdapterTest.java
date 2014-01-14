package nl.enterprisecoding.android.sufficient.controllers;

import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Ferry Wienholts
 * Date: 13-1-14
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */

@RunWith(RobolectricTestRunner.class)
public class CategoryListAdapterTest {

    CategoryListAdapter mCatListAdapter;
    TaskManager mTaskManager;
    MainActivity mMainActivity;
    Category mockCategory;

    @Before
    public void setUp() {

        mMainActivity = mock(MainActivity.class);
        mTaskManager = mock(TaskManager.class);
        mCatListAdapter = new CategoryListAdapter(mTaskManager);

        Map<Long, Category> testingCatsAll = new HashMap<Long, Category>();

        mockCategory = new Category();
        mockCategory.setID((long) 12);

        testingCatsAll.put((long) 0, mock(Category.class));
        testingCatsAll.put((long) 1, mock(Category.class));
        testingCatsAll.put((long) 2, mock(Category.class));
        testingCatsAll.put((long) 3, mockCategory);

        when(mTaskManager.getAllCategories()).thenReturn(testingCatsAll);
    }

    @Test
    public void test_getCount() {
        assertEquals(4, mCatListAdapter.getCount());
        assertNotSame(8, mCatListAdapter.getCount());
    }

    @Test
    public void test_getItem() {
        assertEquals(mockCategory, mCatListAdapter.getItem(3));
        assertNotSame(mockCategory, mCatListAdapter.getItem(2));
    }

    @Test
    public void test_getItemId() {
        assertEquals(12, mCatListAdapter.getItemId(3));
    }
}
