/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * TaskListAdapter
 * <p/>
 * An adapter class for managing the custom list views.
 *
 * @author Nick Meessen
 */
public class TaskListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    private Activity mActivity;

    private List<String> mGroupTitles;
    private Map<Integer, List<Long>> mGroups;
    private List<Long> mTodayList, mTomorrowList, mUpcomingList, mCompletedList;
    private long mCategoryId;

    private TaskManager mTaskManager;

    /**
     * Constructs the adapter.
     *
     * @param activity    the calling activity.
     * @param taskManager a taskmanager.
     * @param categoryId  the current categoryid.
     */
    public TaskListAdapter(Activity activity, TaskManager taskManager, Long categoryId) {

        mActivity = activity;

        mCategoryId = categoryId;
        mTaskManager = taskManager;

        mGroupTitles = new ArrayList<String>();
        mGroups = new TreeMap<Integer, List<Long>>();

        mGroupTitles.add(0, mActivity.getString(R.string.today));
        mGroupTitles.add(1, mActivity.getString(R.string.tomorrow));
        mGroupTitles.add(2, mActivity.getString(R.string.upcoming));
        mGroupTitles.add(3, mActivity.getString(R.string.completed));

        updateList();

    }

    /**
     * Updates the list.
     */
    private void updateList() {

        mTodayList = new ArrayList<Long>();
        mTomorrowList = new ArrayList<Long>();
        mUpcomingList = new ArrayList<Long>();
        mCompletedList = new ArrayList<Long>();

        mGroups.put(0, mTodayList);
        mGroups.put(1, mTomorrowList);
        mGroups.put(2, mUpcomingList);
        mGroups.put(3, mCompletedList);

        if (mCategoryId == 0) {
            for (Category cat : mTaskManager.getVisibleCategories()) {
                fillList(cat.getTasks());
            }
        } else {
            fillList(mTaskManager.getCategoryById(mCategoryId).getTasks());
        }
    }

    /**
     * Fill the list with a list of Tasks, split the tasks into several sublists.
     *
     * @param tasks the list of tasks to split.
     */
    private void fillList(List<Task> tasks) {
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);

        for (Task task : tasks) {
            if (task.isCompleted()) {
                mCompletedList.add(task.getId());
            } else if (task.getDate().compareTo(today) == 0 || task.getDate().before(today)) {
                mTodayList.add(task.getId());
            } else if (task.getDate().compareTo(tomorrow) == 0) {
                mTomorrowList.add(task.getId());
            } else if (task.getDate().after(tomorrow)) {
                mUpcomingList.add(task.getId());
            }
        }
    }

    /**
     * Intercepts notifyDataSetChanged() to re-sort the list.
     */
    @Override
    public void notifyDataSetChanged() {
        updateList();
        super.notifyDataSetChanged();
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public String getGroup(int groupPosition) {
        return mGroupTitles.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Task getChild(int groupPosition, int childPosition) {
        return mTaskManager.getTaskById(mGroups.get(groupPosition).get(childPosition));
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return getGroup(groupPosition).hashCode();
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     * @see android.widget.Adapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View view = convertView;

        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
                view = layoutInflater.inflate(R.layout.group_item, null);
        }

        ((TextView) view.findViewById(R.id.groupTitle)).setText(getGroup(groupPosition));

        view.setId((int) getGroupId(groupPosition));

        return view;

    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View view = convertView;

        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.task_item, null);
        }

        if (childPosition % 2 == 0) {
            view.setBackgroundResource(R.color.list_bg1);
        } else {
            view.setBackgroundResource(R.color.list_bg2);
        }

        String title = getChild(groupPosition, childPosition).getTitle();

        ((TextView) view.findViewById(R.id.taskText)).setText(title);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = formatter.format(getChild(groupPosition, childPosition).getDate().getTimeInMillis());

        ((TextView) view.findViewById(R.id.taskText)).setText(getChild(groupPosition, childPosition).getTitle() + " (" + currentDate + ")");

        TextView taskTitle = (TextView) view.findViewById(R.id.taskText);
        ImageView taskDone = (ImageView) view.findViewById(R.id.taskDone);

        if (getChild(groupPosition, childPosition).isCompleted()) {
            taskDone.setImageResource(R.drawable.done);

            if (getChild(groupPosition, childPosition).isImportant()) {
                taskTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
            } else {
                taskTitle.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            }
            taskTitle.setPaintFlags(taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            taskDone.setImageResource(R.drawable.undone);
            if (getChild(groupPosition, childPosition).isImportant()) {
                taskTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            } else {
                taskTitle.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            }
            taskTitle.setPaintFlags(taskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        Button catColour = (Button) view.findViewById(R.id.task_category_colour);
        catColour.setBackgroundColor(mTaskManager.getCategoryById(getChild(groupPosition, childPosition).getCatId()).getColour());

        return view;

    }

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * Callback method to be invoked when a child in this expandable list has
     * been clicked.
     *
     * @param parent        The ExpandableListView where the click happened
     * @param v             The view within the expandable list/ListView that was clicked
     * @param groupPosition The group position that contains the child that
     *                      was clicked
     * @param childPosition The child position within the group
     * @param id            The row id of the child that was clicked
     * @return True if the click was handled
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Task task = getChild(groupPosition, childPosition);

        if (task.isCompleted()) {
            task.setCompleted(false);
        } else {
            task.setCompleted(true);
        }

        mTaskManager.updateTask(task.getTitle(), task.getCatId(), task.getDate(), task.isImportant(), id);

        notifyDataSetChanged();

        return false;
    }

}