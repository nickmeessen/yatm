package nl.enterprisecoding.android.sufficient.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
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
    Category mockCategory2;

    @Before
    public void setUp() {
        mMainActivity = mock(MainActivity.class);
        mTaskManager = mock(TaskManager.class);

        Map<Long, Category> testingCatsAll = new HashMap<Long, Category>();

        mockCategory = new Category();
        mockCategory.setID((long) 12);
        mockCategory.setVisible(0);

        mockCategory2 = new Category();
        mockCategory2.setID((long) 88);
        mockCategory2.setVisible(1);

        testingCatsAll.put((long) 0, mock(Category.class));
        testingCatsAll.put((long) 1, mock(Category.class));
        testingCatsAll.put((long) 2, mock(Category.class));
        testingCatsAll.put((long) 3, mockCategory);
        testingCatsAll.put((long) 4, mockCategory2);

        when(mTaskManager.getAllCategories()).thenReturn(testingCatsAll);

        mCatListAdapter = new CategoryListAdapter(mTaskManager);

    }

    @Test
    public void test_getCount() {
        assertEquals(5, mCatListAdapter.getCount());
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

    @Test
    public void test_getView() {
        LayoutInflater layoutInflater = mock(LayoutInflater.class);
        ViewGroup parent = mock(ViewGroup.class);
        View view = mock(View.class);

        when(parent.getContext()).thenReturn(mMainActivity);
        when(mMainActivity.getSystemService(anyString())).thenReturn(layoutInflater);
        when(layoutInflater.inflate(R.layout.category_item, null)).thenReturn(view);
        when(view.findViewById(R.id.catText)).thenReturn(mock(TextView.class));
        when(view.findViewById(R.id.catChangeVisibilityButton)).thenReturn(mock(ImageView.class));

        assertNotNull(mCatListAdapter.getView(3, view, parent));
        assertNotNull(mCatListAdapter.getView(3, null, parent));

        assertNotNull(mCatListAdapter.getView(4, view, parent));
        assertNotNull(mCatListAdapter.getView(4, null, parent));
    }
}
