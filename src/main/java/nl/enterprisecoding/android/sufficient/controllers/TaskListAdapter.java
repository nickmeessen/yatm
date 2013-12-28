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
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.models.Category;
import nl.enterprisecoding.android.sufficient.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * TaskListAdapter
 * <p/>
 * An adapter class for managing the custom list views.
 */
public class TaskListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    private Activity mActivity;
    private List<Task> mData;

    private List<String> mGroupTitles;
    private SparseArray<List<Task>> mGroups;
    private List<Task> mTodayList;
    private List<Task> mTomorrowList;
    private List<Task> mUpcomingList;
    private long mCategoryId;

    private TaskManager mTaskManager;

    //  @TODO #TEST-282 & #TEST-268 (DONE) // Kwaliteit Eis #16 & #2; TE Lange methode.
    public TaskListAdapter(Activity activity, TaskManager taskManager, Long categoryId) {

        mActivity = activity;

        mCategoryId = categoryId;
        mTaskManager = taskManager;

        mGroupTitles = new ArrayList<String>();
        mGroups = new SparseArray<List<Task>>();

        mTomorrowList = new ArrayList<Task>();
        mUpcomingList = new ArrayList<Task>();

        mGroupTitles.add(0, activity.getString(R.string.today));
        mGroupTitles.add(1, activity.getString(R.string.tomorrow));
        mGroupTitles.add(2, activity.getString(R.string.upcoming));

        mGroups.put(0, mTodayList = new ArrayList<Task>());
        mGroups.put(1, mTomorrowList = new ArrayList<Task>());
        mGroups.put(2, mUpcomingList = new ArrayList<Task>());
        mData = new ArrayList<Task>();

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

        //@TODO #TEST-290 (DONE) // Kwaliteit Eis #37 ; Geen loop in een loop.
        if (mCategoryId == 0) {

            for (Category cat : mTaskManager.getVisibleCategories()) {

                ArrayList<Task> restList = new ArrayList<Task>();

                List<Task> important = new ArrayList<Task>();
                List<Task> normal = new ArrayList<Task>();
                List<Task> done = new ArrayList<Task>();


                for (Task task : cat.getTasks()) {

                    if (task.isImportant() && !task.isCompleted()) {
                        important.add(task);
                    }
                    if (!task.isImportant() && !task.isCompleted()) {
                        normal.add(task);
                    }
                    if (task.isCompleted()) {
                        done.add(task);
                    }
                }

                for (Task task : important) {

                    if (task.getDate().compareTo(today) == 0) {
                        mTodayList.add(task);
                    } else if (task.getDate().compareTo(tomorrow) == 0) {
                        mTomorrowList.add(task);
                    } else if (task.getDate().after(tomorrow)) {
                        mUpcomingList.add(task);
                    } else {
                        restList.add(task);
                    }

                }

                for (Task task : normal) {

                    if (task.getDate().compareTo(today) == 0) {
                        mTodayList.add(task);
                    } else if (task.getDate().compareTo(tomorrow) == 0) {
                        mTomorrowList.add(task);
                    } else if (task.getDate().after(tomorrow)) {
                        mUpcomingList.add(task);
                    } else {
                        restList.add(task);
                    }
                }

                for (Task task : done) {

                    if (task.getDate().compareTo(today) == 0) {
                        mTodayList.add(task);
                    } else if (task.getDate().compareTo(tomorrow) == 0) {
                        mTomorrowList.add(task);
                    } else if (task.getDate().after(tomorrow)) {
                        mUpcomingList.add(task);
                    } else {
                        restList.add(task);
                    }
                }

                for (Task task : restList) {
                    task.setImportant(true);
                    mTodayList.add(task);
                }
            }

        } else {

            mData = mTaskManager.getItemById(mCategoryId).getTasks();

            List<Task> important = new ArrayList<Task>();
            List<Task> normal = new ArrayList<Task>();
            List<Task> done = new ArrayList<Task>();

            ArrayList<Task> restList = new ArrayList<Task>();

            for (Task task : mData) {

                if (task.isImportant() && !task.isCompleted()) {
                    important.add(task);
                }
                if (!task.isImportant() && !task.isCompleted()) {
                    normal.add(task);
                }
                if (task.isCompleted()) {
                    done.add(task);
                }
            }

            for (Task task : important) {

                if (task.getDate().compareTo(today) == 0) {
                    mTodayList.add(task);
                } else if (task.getDate().compareTo(tomorrow) == 0) {
                    mTomorrowList.add(task);
                } else if (task.getDate().after(tomorrow)) {
                    mUpcomingList.add(task);
                } else {
                    restList.add(task);
                }

            }

            for (Task task : normal) {

                if (task.getDate().compareTo(today) == 0) {
                    mTodayList.add(task);
                } else if (task.getDate().compareTo(tomorrow) == 0) {
                    mTomorrowList.add(task);
                } else if (task.getDate().after(tomorrow)) {
                    mUpcomingList.add(task);
                } else {
                    restList.add(task);
                }
            }

            for (Task task : done) {

                if (task.getDate().compareTo(today) == 0) {
                    mTodayList.add(task);
                } else if (task.getDate().compareTo(tomorrow) == 0) {
                    mTomorrowList.add(task);
                } else if (task.getDate().after(tomorrow)) {
                    mUpcomingList.add(task);
                } else {
                    restList.add(task);
                }
            }

            for (Task task : restList) {
                task.setImportant(true);
                mTodayList.add(task);
            }

        }
    }

    private void sortTasks() {
        mGroupTitles = new ArrayList<String>();
        mGroups = new SparseArray<List<Task>>();

        mTomorrowList = new ArrayList<Task>();
        mUpcomingList = new ArrayList<Task>();

        mGroupTitles.add(0, "Today");
        mGroupTitles.add(1, "Tomorrow");
        mGroupTitles.add(2, "Upcoming");

        mGroups.put(0, mTodayList = new ArrayList<Task>());
        mGroups.put(1, mTomorrowList = new ArrayList<Task>());
        mGroups.put(2, mUpcomingList = new ArrayList<Task>());
        mData = new ArrayList<Task>();


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

        //@TODO #TEST-290 (DONE) // Kwaliteit Eis #37 ; Geen loop in een loop.
        if (mCategoryId == 0) {

            for (Category cat : mTaskManager.getVisibleCategories()) {

                ArrayList<Task> restList = new ArrayList<Task>();

                List<Task> important = new ArrayList<Task>();
                List<Task> normal = new ArrayList<Task>();
                List<Task> done = new ArrayList<Task>();


                for (Task task : cat.getTasks()) {

                    if (task.isImportant() && !task.isCompleted()) {
                        important.add(task);
                    }
                    if (!task.isImportant() && !task.isCompleted()) {
                        normal.add(task);
                    }
                    if (task.isCompleted()) {
                        done.add(task);
                    }
                }

                for (Task task : important) {

                    if (task.getDate().compareTo(today) == 0) {
                        mTodayList.add(task);
                    } else if (task.getDate().compareTo(tomorrow) == 0) {
                        mTomorrowList.add(task);
                    } else if (task.getDate().after(tomorrow)) {
                        mUpcomingList.add(task);
                    } else {
                        restList.add(task);
                    }

                }

                for (Task task : normal) {

                    if (task.getDate().compareTo(today) == 0) {
                        mTodayList.add(task);
                    } else if (task.getDate().compareTo(tomorrow) == 0) {
                        mTomorrowList.add(task);
                    } else if (task.getDate().after(tomorrow)) {
                        mUpcomingList.add(task);
                    } else {
                        restList.add(task);
                    }


                }

                for (Task task : done) {


                    if (task.getDate().compareTo(today) == 0) {
                        mTodayList.add(task);
                    } else if (task.getDate().compareTo(tomorrow) == 0) {
                        mTomorrowList.add(task);
                    } else if (task.getDate().after(tomorrow)) {
                        mUpcomingList.add(task);
                    } else {
                        restList.add(task);
                    }
                }

                for (Task task : restList) {
                    task.setImportant(true);
                    mTodayList.add(task);
                }
            }

        } else {

            mData = mTaskManager.getItemById(mCategoryId).getTasks();

            List<Task> important = new ArrayList<Task>();
            List<Task> normal = new ArrayList<Task>();
            List<Task> done = new ArrayList<Task>();

            ArrayList<Task> restList = new ArrayList<Task>();

            for (Task task : mData) {

                if (task.isImportant() && !task.isCompleted()) {
                    important.add(task);
                }
                if (!task.isImportant() && !task.isCompleted()) {
                    normal.add(task);
                }
                if (task.isCompleted()) {
                    done.add(task);
                }
            }

            for (Task task : important) {

                if (task.getDate().compareTo(today) == 0) {
                    mTodayList.add(task);
                } else if (task.getDate().compareTo(tomorrow) == 0) {
                    mTomorrowList.add(task);
                } else if (task.getDate().after(tomorrow)) {
                    mUpcomingList.add(task);
                } else {
                    restList.add(task);
                }

            }

            for (Task task : normal) {

                if (task.getDate().compareTo(today) == 0) {
                    mTodayList.add(task);
                } else if (task.getDate().compareTo(tomorrow) == 0) {
                    mTomorrowList.add(task);
                } else if (task.getDate().after(tomorrow)) {
                    mUpcomingList.add(task);
                } else {
                    restList.add(task);
                }

            }

            for (Task task : done) {

                if (task.getDate().compareTo(today) == 0) {
                    mTodayList.add(task);
                } else if (task.getDate().compareTo(tomorrow) == 0) {
                    mTomorrowList.add(task);
                } else if (task.getDate().after(tomorrow)) {
                    mUpcomingList.add(task);
                } else {
                    restList.add(task);
                }

            }

            for (Task task : restList) {
                task.setImportant(true);
                mTodayList.add(task);
            }

        }
    }

    public void addItem(Task newTask) {

        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        if (newTask.getDate().equals(today)) {
            mTodayList.add(newTask);
        } else if (newTask.getDate().equals(tomorrow)) {
            mTomorrowList.add(newTask);
        } else {
            mUpcomingList.add(newTask);
        }

        notifyDataSetChanged();
    }

    public void deleteItem(Task taskToDelete) {

        Task taskToRemove = null;

        // @todo zorgen dat hier task fatsoenlijk verwijderd wordt.
        for (Task task : mTodayList) {

            if (task.getId() == taskToDelete.getId()) {
                taskToRemove = task;
                break;
            }

        }

        for (Task task : mTomorrowList) {

            if (task.getId() == taskToDelete.getId()) {
                taskToRemove = task;
                break;
            }

        }

        for (Task task : mUpcomingList) {

            if (task.getId() == taskToDelete.getId()) {
                taskToRemove = task;
                break;
            }

        }


        mData.remove(taskToRemove);
        mTodayList.remove(taskToRemove);
        mTomorrowList.remove(taskToRemove);
        mUpcomingList.remove(taskToRemove);


        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        sortTasks();
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
        return mGroups.get(groupPosition).get(childPosition);
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
        return mGroups.get(groupPosition).get(childPosition).getId();
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
            if (getChildrenCount(groupPosition) == 0) {
                view = layoutInflater.inflate(R.layout.group_item_e, null);
            } else {
                view = layoutInflater.inflate(R.layout.group_item, null);
            }
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

//        * @TODO #TEST-270 (DONE) // Kwaliteit Eis #4 ; In een methode/functie mogen maximaal 10 vergelijkingen staan.

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.task_item, null);
        }

        if (childPosition % 2 == 0) {
            view.setBackgroundResource(R.color.list_bg1);
        } else {
            view.setBackgroundResource(R.color.list_bg2);
        }

        //@TODO #TEST-285 (DONE) // Kwaliteit Eis #19 ; Een regel code mag niet meer dan 100 tekens bevatten.
        ((TextView) view.findViewById(R.id.taskText)).setText(getChild(groupPosition, childPosition).getTitle());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(getChild(groupPosition, childPosition).getDate().getTime());

        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        String due = "";

        if (getChild(groupPosition, childPosition).getDate().compareTo(today) < 0) {
            due = "(DUE) ";
        }

        ((TextView) view.findViewById(R.id.taskText)).setText(due + getChild(groupPosition, childPosition).getTitle() + " (" + currentDate + ")");

        TextView taskTitle = (TextView) view.findViewById(R.id.taskText);
        ImageView taskDone = (ImageView) view.findViewById(R.id.taskDone);

        if (getChild(groupPosition, childPosition).isCompleted()) {
            taskDone.setImageResource(R.drawable.done);
            taskTitle.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            taskTitle.setPaintFlags(taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            taskDone.setImageResource(R.drawable.undone);
            taskTitle.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            taskTitle.setPaintFlags(taskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        Button catColor = (Button) view.findViewById(R.id.taskCatColor);
        catColor.setBackgroundColor(mTaskManager.getItemById(getChild(groupPosition, childPosition).getCatId()).getColour());

        if (getChild(groupPosition, childPosition).isImportant()) {
            if (getChild(groupPosition, childPosition).isCompleted()) {
                taskTitle.setTypeface(taskTitle.getTypeface(), Typeface.BOLD_ITALIC);
            }
        }
        if (getChild(groupPosition, childPosition).isImportant()) {
            if (!getChild(groupPosition, childPosition).isCompleted()) {
                taskTitle.setTypeface(taskTitle.getTypeface(), Typeface.BOLD);
            }
        }
        if (!getChild(groupPosition, childPosition).isImportant()) {
            if (!getChild(groupPosition, childPosition).isCompleted()) {
                taskTitle.setTypeface(taskTitle.getTypeface(), Typeface.NORMAL);
            }
        }
        if (!getChild(groupPosition, childPosition).isImportant()) {
            if (getChild(groupPosition, childPosition).isCompleted()) {
                taskTitle.setTypeface(taskTitle.getTypeface(), Typeface.ITALIC);
            }
        }

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

        mTaskManager.editTask(task.getTitle(), task.getCatId(), task.getDate(), task.isImportant(), task.isCompleted(), task.getId());

        notifyDataSetChanged();

        return false;
    }

}