package nl.enterprisecoding.android.sufficient.controllers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
    TaskListAdapter mTaskListAdapter1;
    TaskListAdapter mTaskListAdapterSpecific;
    TaskListAdapter mTaskListAdapterEmpty;

    @Before
    public void setUp() {
        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().get();

        List<Category> testingCatsAll = new ArrayList<Category>();

        Category mockCategory1 = new Category();
        mockCategory1.setID((long) 5);

        Category mockCategory2 = new Category();
        mockCategory2.setID((long) 14);

        Category mockEmptyCategory = new Category();
        mockEmptyCategory.setID((long) 15);

        testingCatsAll.add(mockCategory1);
        testingCatsAll.add(mockCategory2);
        testingCatsAll.add(mockEmptyCategory);

        Task beforeTask = new Task();
        Task todayTask = new Task();
        Task tomorrowTask = new Task();
        Task upcomingTask = new Task();
        Task importantTask = new Task();
        Task completedTest = new Task();

        completedTest.setId((long) 11);
        completedTest.setCategoryId((long) 4);
        completedTest.setTitle("abc");

        todayTask.setDate(Calendar.getInstance());

        beforeTask.setId((long) 43);
        tomorrowTask.setId((long) 35);
        importantTask.setId((long) 15);
        upcomingTask.setId((long) 830);
        completedTest.setId((long) 3);

        Calendar before = Calendar.getInstance();
        before.setTimeInMillis(before.getTimeInMillis() - 100000);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);

        Calendar upcoming = Calendar.getInstance();
        upcoming.add(Calendar.DAY_OF_YEAR, 10);

        beforeTask.setDate(before);
        tomorrowTask.setDate(tomorrow);
        upcomingTask.setDate(upcoming);
        importantTask.setDate(tomorrow);

        completedTest.setCompleted(true);
        importantTask.setImportant(true);

        testingCatsAll.get(2).addTask(completedTest);
        testingCatsAll.get(0).addTask(importantTask);
        testingCatsAll.get(2).addTask(upcomingTask);
        testingCatsAll.get(1).addTask(tomorrowTask);
        testingCatsAll.get(0).addTask(todayTask);
        testingCatsAll.get(1).addTask(beforeTask);

        mTaskManager = mock(TaskManager.class);

        when(mTaskManager.getTaskById((long) 11)).thenReturn(todayTask);
        when(mTaskManager.getTaskById((long) 35)).thenReturn(tomorrowTask);
        when(mTaskManager.getTaskById((long) 830)).thenReturn(upcomingTask);
        when(mTaskManager.getTaskById((long) 15)).thenReturn(importantTask);
        when(mTaskManager.getTaskById((long) 3)).thenReturn(completedTest);

        when(mTaskManager.getVisibleCategories()).thenReturn(testingCatsAll);
        when(mTaskManager.getCategoryById((long) 14)).thenReturn(mockCategory1);
        when(mTaskManager.getCategoryById((long) 15)).thenReturn(mockEmptyCategory);

        mTaskListAdapter = new TaskListAdapter(mMainActivity, mTaskManager, (long) 0);
        mTaskListAdapter1 = new TaskListAdapter(mMainActivity, mTaskManager, (long) 5);
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
        assertEquals(3, mTaskListAdapter.getChildrenCount(2));
        assertEquals(1, mTaskListAdapterSpecific.getChildrenCount(2));
        assertEquals(0, mTaskListAdapterSpecific.getChildrenCount(1));
    }

    @Test
    public void test_getGroup() {
        assertEquals("Completed", mTaskListAdapter.getGroup(3));
    }

    @Test
    public void test_getChild() {

        Task task = mTaskListAdapter.getChild(3, 0);

        assertEquals("abc", task.getTitle());
        assertEquals(4, task.getCatId());
        assertEquals(3, task.getId());

        assertNotSame("cba", task.getTitle());
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
        assertEquals(35, mTaskListAdapter.getChild(2, 1).getId());
    }

    @Test
    public void test_onChildClick() {

        long taskId = 35;

        ExpandableListView parent = mock(ExpandableListView.class);
        View view = mock(View.class);

        mTaskListAdapter.onChildClick(parent, view, 2, 1, taskId);

        assertTrue(mTaskManager.getTaskById(taskId).isCompleted());
    }

    @Test
    public void test_isChildSelectable() {
        assertTrue(mTaskListAdapter.isChildSelectable(2, 1));
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

        Category mockCategory = mock(Category.class);

        when(mTaskManager.getCategoryById(0)).thenReturn(mockCategory);
        when(mockCategory.getColour()).thenReturn(Color.BLACK);
        when(convertView.findViewById(R.id.groupTitle)).thenReturn(mock(TextView.class));
        when(convertView.findViewById(R.id.task_category_colour)).thenReturn(mock(Button.class));
        when(convertView.findViewById(R.id.taskText)).thenReturn(mock(TextView.class));
        when(convertView.findViewById(R.id.taskDone)).thenReturn(mock(ImageView.class));

        assertNotNull(mTaskListAdapter.getChildView(2, 0, true, null, null));
        assertNotNull(mTaskListAdapter.getChildView(2, 0, true, convertView, parentView));
    }

}