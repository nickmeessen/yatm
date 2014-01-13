package nl.enterprisecoding.android.sufficient.controllers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    TaskListAdapter mTaskListAdapterEmpty;

    @Before
    public void setUp() {
        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        mTaskManager = mock(TaskManager.class);

        when(mTaskManager.getTaskById(20)).thenReturn(mock(Task.class));

        List<Category> testingCatsAll = new ArrayList<Category>();

        Category mockCategory = mock(Category.class);
        mockCategory.setID(14);

        testingCatsAll.add(mock(Category.class));
        testingCatsAll.add(mock(Category.class));
        testingCatsAll.add(mock(Category.class));

        Task completedTest1 = new Task();
        Task completedTest2 = new Task();
        Task completedTest3 = new Task();
        Task completedTest4 = new Task();
        Task todayTask = new Task();

        completedTest1.setCompleted(true);
        completedTest2.setCompleted(true);
        completedTest3.setCompleted(true);
        completedTest4.setCompleted(true);

        todayTask.setDate(Calendar.getInstance());

        testingCatsAll.get(2).addTask(completedTest1);
        testingCatsAll.get(1).addTask(completedTest2);
        testingCatsAll.get(2).addTask(completedTest3);
        testingCatsAll.get(1).addTask(completedTest4);
        testingCatsAll.get(0).addTask(todayTask);

        mockCategory.addTask(completedTest1);
        mockCategory.addTask(completedTest2);
        mockCategory.addTask(completedTest3);
        mockCategory.addTask(completedTest4);
        mockCategory.addTask(todayTask);

        when(mTaskManager.getVisibleCategories()).thenReturn(testingCatsAll);
        when(mTaskManager.getCategoryById(14)).thenReturn(mockCategory);
        when(mTaskManager.getCategoryById(15)).thenReturn(mock(Category.class));

        mTaskListAdapter = new TaskListAdapter(mMainActivity, mTaskManager, (long) 0);
        mTaskListAdapterSpecific = new TaskListAdapter(mMainActivity, mTaskManager, (long) 14);
        mTaskListAdapterEmpty = new TaskListAdapter(mMainActivity, mTaskManager, (long) 15);
    }

    @Test
    public void test_getGroupCount() {
        assertEquals(4, mTaskListAdapter.getGroupCount());
        assertNotSame(3, mTaskListAdapter.getGroupCount());
    }

    @Test(expected = AssertionError.class)
    public void test_getGroupCount_fail() {
        assertEquals(2, mTaskListAdapter.getGroupCount());
        assertNotSame(4, mTaskListAdapter.getGroupCount());
    }

    @Test
    public void test_getChildrenCount() {
        assertEquals(3, mTaskListAdapter.getChildrenCount(3));
        assertEquals(1, mTaskListAdapterSpecific.getChildrenCount(0));
    }

    @Test
    public void test_getGroup() {
        assertEquals("Completed", mTaskListAdapter.getGroup(3));
    }

    @Test
    public void test_getChild() {

//        Task task = mTaskListAdapter.getChild(0, 0);
//
//        assertEquals("abc", task.getTitle());
//        assertEquals(4, task.getCatId());
//        assertEquals(11, task.getId());
//
//        assertNotSame("cba", task.getTitle());
    }

    @Test
    public void test_getGroupId() {
        assertNotNull(mTaskListAdapter.getGroupId(0));
        assertNotNull(mTaskListAdapter.getGroupId(1));
        assertNotNull(mTaskListAdapter.getGroupId(2));
        assertNotNull(mTaskListAdapter.getGroupId(3));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_getGroupIdOutOfBounds() {
        assertNotNull(mTaskListAdapter.getGroupId(3134013));
    }

    @Test
    public void test_getChildId() {
//        mTaskListAdapter.getChild(0, 0).getId();
//        assertEquals(400, mTaskListAdapter.getChild(0, 0).getId());
    }

    @Test
    public void test_onChildClick() {

        long taskId = 20;
        boolean initValue = mTaskManager.getTaskById(taskId).isCompleted();

        ExpandableListView parent = mock(ExpandableListView.class);
        View view = mock(View.class);

//        mTaskListAdapter.onChildClick(parent, view, 0, 0, taskId);

        mTaskManager.getTaskById(taskId);

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

    @Test
    public void test_getGroupView() {

        ViewGroup parentView = mock(ViewGroup.class);
        View convertView = mock(View.class);

        when(convertView.findViewById(R.id.groupTitle)).thenReturn(mock(TextView.class));

        assertNotNull(mTaskListAdapter.getGroupView(0, true, null, null));
        assertNotNull(mTaskListAdapter.getGroupView(0, true, convertView, parentView));

    }

    @Test
    public void test_getChildView() {
        ViewGroup parentView = mock(ViewGroup.class);
        View convertView = mock(View.class);

        when(convertView.findViewById(R.id.groupTitle)).thenReturn(mock(TextView.class));

//        assertNotNull(mTaskListAdapter.getChildView(0, 0, true, null, null));
//        assertNotNull(mTaskListAdapter.getChildView(0, 1, true, convertView, parentView));
    }
}