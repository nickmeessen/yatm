/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.TaskActivity;
import nl.enterprisecoding.android.sufficient.models.Category;

import java.util.ArrayList;

/**
 * CategoryListAdapter
 * <p/>
 * An adapter class for managing the custom list views.
 */
class CategoryListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private TaskManager mTaskManager;

    public CategoryListAdapter(Activity activity, TaskManager taskManager) {
        mActivity = activity;
        mTaskManager = taskManager;
    }

    /**
     * Returns the number of categories this adapter.
     *
     * @return the size of this adapter.
     */
    public int getCount() {
        return new ArrayList<Category>(mTaskManager.retrieveAllCategories().values()).size();
    }

    /**
     * Returns the item at the given position.
     *
     * @param position the position of the item.
     * @return the category object position at the given position.
     */
    public Category getItem(int position) {
        return new ArrayList<Category>(mTaskManager.retrieveAllCategories().values()).get(position);
    }

    /**
     * Retrieves the ID of the item at a given position.
     *
     * @param position the the position of the item.
     * @return the ID of the item at the given position.
     */
    public long getItemId(int position) {
        return getItem(position).getID();
    }

    public Category getItemById(long id) {
        return mTaskManager.retrieveAllCategories().get(id);
    }

    /**
     * Generates the custom listItemView for a category item.
     *
     * @param position    the position of the item.
     * @param convertView the view to be created/converted
     * @param parent      the parent view
     * @return the generated view.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final Category mSelectedCategory = getItem(position);

        LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.category_item, null);
        }

        TextView catTitle = (TextView) view.findViewById(R.id.catText);
        catTitle.setText(getItem(position).getTitle());

        view.setBackgroundColor(getItem(position).getColour());
        final ImageView catVisibility = (ImageView) view.findViewById(R.id.catChangeVisibilityButton);

        if (mSelectedCategory.getID() != 0) {
            updateCategoryVisibilityButton(catVisibility, mSelectedCategory.isVisible());
            catVisibility.setVisibility(View.VISIBLE);
        } else {
            catVisibility.setVisibility(View.GONE);
        }

        catVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategoryVisibilityButton(catVisibility, mTaskManager.switchCategoryVisibility(mSelectedCategory));
            }
        });

        return view;
    }

    private void updateCategoryVisibilityButton(ImageView button, boolean visible) {
        if (visible) {
            button.setImageResource(R.drawable.visible);
        } else {
            button.setImageResource(R.drawable.invisible);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(mTaskManager.getCategoryById(id).getColour()));

        Intent intent = new Intent(mActivity, TaskActivity.class);

        intent.putExtra("categoryID", id);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
