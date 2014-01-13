package nl.enterprisecoding.android.sufficient.controllers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * TaskListAdapterTest
 *
 * @author Nick Meessen
 */

@RunWith(RobolectricTestRunner.class)
public class TaskListAdapterTest {

    MainActivity mMainActivity;
    TaskManager mTaskManager;
    TaskListAdapter mTaskListAdapter;
    TaskListAdapter mTaskListAdapterSpecific;

    @Before
    public void setUp() {
        mMainActivity = mock(MainActivity.class);
        mTaskManager = mock(TaskManager.class);

        when(mTaskManager.getTaskById(20)).thenReturn(mock(Task.class));

        List<Category> testingCatsAll = new ArrayList<Category>();

        Category mockCategory = mock(Category.class);
        mockCategory.setID(14);

        testingCatsAll.add(mock(Category.class));
        testingCatsAll.add(mock(Category.class));
        testingCatsAll.add(mock(Category.class));

        when(mTaskManager.getVisibleCategories()).thenReturn(testingCatsAll);
        when(mTaskManager.getCategoryById(14)).thenReturn(mockCategory);

        mTaskListAdapter = new TaskListAdapter(mMainActivity, mTaskManager, (long) 0);
        mTaskListAdapterSpecific = new TaskListAdapter(mMainActivity, mTaskManager, (long) 14);
    }

    @Test
    public void test_getGroupCount() {
        assertEquals(4, mTaskListAdapter.getGroupCount());
        assertNotSame(3, mTaskListAdapter.getGroupCount());
    }

    @Test(expected=AssertionError.class)
    public void test_getGroupCount_fail() {
        assertEquals(2, mTaskListAdapter.getGroupCount());
        assertNotSame(4, mTaskListAdapter.getGroupCount());

    }

    @Test
    public void test_notifyDataSetChanged() {
    }

    @Test
    public void test_getChildrenCount() {
//        assertEquals(3, mTaskListAdapter.getChildrenCount(4));
//        assertNotSame(36, mTaskListAdapter.getChildrenCount(4));
//
//        assertEquals(7, mTaskListAdapterSpecific.getChildrenCount(4));
//        assertNotSame(16, mTaskListAdapterSpecific.getChildrenCount(4));

    }

    @Test
    public void test_getGroup() {
//        assertEquals("Completed", mTaskListAdapter.getGroup(4));

    }

    @Test
    public void test_getChild() {
        // int groupPosition, int childPosition) {
    }

    @Test
    public void test_getGroupId() {
//       assertNotNull(mTaskListAdapter.getGroupId(1));
    }

    @Test
    public void test_getChildId() {
        // int groupPosition, int childPosition) {
    }


    @Test
    public void test_getGroupView() {
        // int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    }

    @Test
    public void test_getChildView() {
        // int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    }

    @Test
    public void test_onChildClick() {

        long taskId = 20;
        boolean initValue = mTaskManager.getTaskById(taskId).isCompleted();

        ExpandableListView parent = mock(ExpandableListView.class);
        View view = mock(View.class);

//        mTaskListAdapter.onChildClick(parent, view, 0, 0, taskId);

        mTaskManager.getTaskById(taskId);

//        assertEquals(!initValue, mTaskManager.getTaskById(taskId).isCompleted());
                assertEquals(initValue, mTaskManager.getTaskById(taskId).isCompleted());
    }


    @Test
    public void test_isChildSelectable() {
        assertTrue(mTaskListAdapter.isChildSelectable(0, 0));
    }

    @Test
    public void test_hasStableIds() {
        assertTrue(mTaskListAdapter.hasStableIds());
    }
}