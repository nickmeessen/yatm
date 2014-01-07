/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import nl.enterprisecoding.android.sufficient.models.Category;

import java.util.List;

/**
 * CategoryActivity class
 * <p/>
 * From here a user could execute various actions on categories.
 *
 * @author Breunie Ploeg
 */
public class CategoryActivity extends MainActivity {

    public static final String COLOUR_FORMAT = "#%02x%02x%02x";

    private Dialog mColourDialog;
    private int mChosenColour = 0;
    private int mCategoryColour;
    private String[] mSpringerArray;
    private Spinner catInput;
    private long mSelectedCategoryId;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        mActionBar.hide();

        mTaskManager = new TaskManager(this, (long) 0);

        final Button colourButton = (Button) findViewById(R.id.category_colour_button);
        final GradientDrawable bgShape = (GradientDrawable) colourButton.getBackground();
        final int[] randomColour = generateRandomColour();
        bgShape.setColor(randomColour[0]);

        colourButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Handles the click for the colorButton
             *
             * @param v The view in which the click should perform it's action
             */
            @Override
            public void onClick(View v) {
                mColourDialog = new Dialog(CategoryActivity.this);
                mColourDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mColourDialog.setContentView(R.layout.colour_dialog);
                addColourButtons(bgShape);
                mColourDialog.show();
            }
        });

        final EditText editText = (EditText) findViewById(R.id.newCategory);
        editText.setOnKeyListener(new View.OnKeyListener() {
            /**
             * Handles the enter button for adding a category
             *
             * @param v The view in which the action is performed
             * @param keyCode The keycode for the key that is pressed
             * @param event The event that contains the action
             * @return Always returns false
             */
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    addCategory(randomColour);
                }
                return false;
            }
        });

        final View addButton = findViewById(R.id.catAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click for the addButton
             *
             * @param v The view in which the click takes place
             */
            @Override
            public void onClick(View v) {
                addCategory(randomColour);
            }
        });

    }

    /**
     * Adds the colour buttons to the given shape.
     *
     * @param bgShape the shape to use.
     */
    private void addColourButtons(GradientDrawable bgShape) {
        createColourButton(bgShape, R.id.colour_purple_button, R.color.purple);
        createColourButton(bgShape, R.id.colour_blue_button, R.color.blue);
        createColourButton(bgShape, R.id.colour_green_button, R.color.green);
        createColourButton(bgShape, R.id.colour_orange_button, R.color.orange);
        createColourButton(bgShape, R.id.color_red_button, R.color.red);
        createColourButton(bgShape, R.id.colour_random0_button, 0);
        createColourButton(bgShape, R.id.colour_random1_button, 0);
        createColourButton(bgShape, R.id.colour_random2_button, 0);
        createColourButton(bgShape, R.id.colour_random3_button, 0);
        createColourButton(bgShape, R.id.colour_random4_button, 0);
    }

    /**
     * Adds a new category and checks for cases in which it isn't allowed to add a category
     *
     * @param colour The colour that the category will use
     */
    private void addCategory(int[] colour) {
        final EditText editText = (EditText) findViewById(R.id.newCategory);
        int[] randomColour = colour;
        String categoryName = editText.getText().toString();
        if (mCategoryColour == 0) {
            mChosenColour = randomColour[0];
            mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, randomColour[1], randomColour[2], randomColour[3]));
        }

        if (categoryName.trim().isEmpty()) {
            makeToast(getResources().getString(R.string.category_name_empty_error), false);
        } else if (mTaskManager.getCategoryByTitle(categoryName) != null) {
            makeToast(getResources().getString(R.string.toast_category_exists), false);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            mTaskManager.createCategory(categoryName, mCategoryColour);
            editText.setText("");
            makeToast(getResources().getString(R.string.category_added), false);
        }
    }

    /**
     * Creates a button with a certain colour within the colour choosing dialog.
     *
     * @param bgShape     The shape that is clicked
     * @param buttonId    The id of the button that shows the colour
     * @param inputColour The colour the button will have, 0 is a random colour
     */
    private void createColourButton(final GradientDrawable bgShape, int buttonId, final int inputColour) {
        final EditText editText = (EditText) findViewById(R.id.newCategory);
        final Button colourButton = (Button) mColourDialog.findViewById(buttonId);
        final int[] randColour = generateRandomColour();

        if (inputColour != 0) {
            colourButton.setBackgroundColor(getResources().getColor(inputColour));
        } else {
            colourButton.setBackgroundColor(randColour[0]);
        }

        colourButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click for the colourButton
             *
             * @param v The view in which the click takes place
             */
            @Override
            public void onClick(View v) {
                if (inputColour != 0) {
                    mChosenColour = getResources().getColor(inputColour);
                    int r = Color.red(mChosenColour);
                    int g = Color.green(mChosenColour);
                    int b = Color.blue(mChosenColour);
                    mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, r, g, b));
                    bgShape.setColor(getResources().getColor(inputColour));
                } else {
                    mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, randColour[1], randColour[2], randColour[3]));
                    bgShape.setColor(randColour[0]);
                }
                mColourDialog.dismiss();
                editText.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        if (v.getId() == R.id.cat_list && info.position != 0) {
            String[] menuItems = getResources().getStringArray(R.array.category_context_menu);

            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    /**
     * This hook is called whenever an item in a context menu is selected.
     *
     * @param item The context menu item that was selected.
     * @return boolean Return false to allow normal context menu processing to
     * proceed, true to consume it here.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        mSelectedCategoryId = info.id;
        if (item.getTitle().equals(getResources().getString(R.string.action_edit_category))) {
            Intent mEditCategoryActivity = new Intent(this, EditCategoryActivity.class);
            mEditCategoryActivity.putExtra("CategoryID", mSelectedCategoryId);
            startActivity(mEditCategoryActivity);
        } else if (item.getTitle().equals(getResources().getString(R.string.action_delete_category))) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            catInput = new Spinner(this);
            initTaskCategorySpinner(catInput);
            alert.setMessage(getResources().getString(R.string.delete_category_text));
            alert.setView(catInput);
            alert.setPositiveButton(getResources().getString(R.string.action_confirm), new DialogInterface.OnClickListener() {
                /**
                 * Handles the click for the positive button of the context menu
                 *
                 * @param dialog The dialog in which the click takes place
                 * @param whichButton The button which is clicked
                 */
                public void onClick(DialogInterface dialog, int whichButton) {
                    Category originCategory = mTaskManager.getCategoryById(mSelectedCategoryId);
                    String destinationCategory = mSpringerArray[catInput.getSelectedItemPosition()];
                    if (destinationCategory.equals(getResources().getString(R.string.action_delete_all_tasks))) {
                        mTaskManager.deleteCategory(originCategory);
                    } else {
                        mTaskManager.deleteCategoryAndMoveTasks(originCategory, mTaskManager.getCategoryByTitle(destinationCategory));
                    }
                }
            });
            alert.setNegativeButton(getResources().getString(R.string.action_discard), null);
            alert.show();
        }

        return true;
    }

    /**
     * Called when a key was released and not handled by any of the views
     * inside of the activity.
     *
     * @return Return <code>true</code> to prevent this event from being propagated
     * further, or <code>false</code> to indicate that you have not handled
     * this event and it should continue to be propagated.
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {

            startActivity(new Intent(this, TaskActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     * Initialises the spinner which will display the available categories
     *
     * @param spinner The spinner which displays the categories
     */
    private void initTaskCategorySpinner(Spinner spinner) {
        mSpringerArray = convertCategoryListToStringArray(mTaskManager.getCategories());
        ArrayAdapter<String> mSpinnerArrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                mSpringerArray
        );

        mSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSpinnerArrayAdapter);
    }

    /**
     * Converts a list of categories to a string array.
     *
     * @param categoryList the list to convert
     * @return an array of strings containing the category titles.
     */
    protected String[] convertCategoryListToStringArray(List<Category> categoryList) {
        String[] result = new String[categoryList.size()];

        int count = 0;
        for (Category cat : categoryList) {
            result[count] = cat.getTitle();
            count++;
        }

        result[0] = getResources().getString(R.string.action_delete_all_tasks);

        return result;
    }
}
