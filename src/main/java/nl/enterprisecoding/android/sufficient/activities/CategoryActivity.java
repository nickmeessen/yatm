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
 * From here a user could execute various actions on categories.
 *
 * @author Breunie Ploeg
 */
public class CategoryActivity extends MainActivity implements View.OnKeyListener, View.OnClickListener {

    private Spinner catInput;
    private long mSelectedCategoryId;
    private int mCategoryColour;
    private int[] mRandomColour;
    private GradientDrawable mBgShape;

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

        mTaskManager = new TaskManager(this, mCurrentCategoryID);

        mActionBar.hide();
        final Button colourButton = (Button) findViewById(R.id.category_colour_button);
        mBgShape = (GradientDrawable) colourButton.getBackground();
        generateColourShape();

        colourButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click for the colourButton
             * @param v The view in which the button is clicked
             */
            public void onClick(View v) {
                createColourDialog(mBgShape);
            }
        });

        final EditText editText = (EditText) findViewById(R.id.newCategory);
        editText.setOnKeyListener(this);

        final View addButton = findViewById(R.id.catAddButton);
        addButton.setOnClickListener(this);

        View allCategoriesView = findViewById(R.id.all_cats);
        allCategoriesView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newCategory:
                createColourDialog(mBgShape);
                break;
            case R.id.catAddButton:
                addCategory();
                break;
            case R.id.all_cats:
                startTaskActivity();
                break;
            default:
                new Intent(getApplicationContext(), TaskActivity.class);
                break;
        }
    }

    /**
     * Handles the enter button for adding a category
     *
     * @param v       The view in which the action is performed
     * @param keyCode The keycode for the key that is pressed
     * @param event   The event that contains the action
     * @return Always returns false
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            addCategory();
        }
        return false;
    }

    /**
     * Adds a new category and checks for cases in which it isn't allowed to add a category
     */
    private void addCategory() {
        final EditText editText = (EditText) findViewById(R.id.newCategory);
        String categoryName = editText.getText().toString();
        mCategoryColour = getCategoryColour();

        if (mCategoryColour == 0) {
            mCategoryColour = mRandomColour[0];
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        mTaskManager.createCategory(categoryName, mCategoryColour);
        editText.setText("");
        makeToast(getString(R.string.category_added));
        generateColourShape();

    }

    /**
     * Sets a new colour for the shape which represents the category colour
     */
    private void generateColourShape() {
        mRandomColour = generateRandomColour();
        mBgShape.setColor(mRandomColour[0]);
        setCategoryColour(mRandomColour[0]);
    }

    /**
     * Creates a button with a certain colour within the colour choosing dialog.
     *
     * @param bgShape      The shape that is clicked
     * @param buttonId     The id of the button that shows the colour
     * @param inputColour  The colour the button will have, 0 is a random colour
     * @param colourDialog The Dialog that will display the colours
     */
    @Override
    protected void createColourButton(final GradientDrawable bgShape, int buttonId, final int inputColour, final Dialog colourDialog) {
        super.createColourButton(bgShape, buttonId, inputColour, colourDialog);
        final EditText editText = (EditText) findViewById(R.id.newCategory);
        editText.requestFocus();
        openKeyboard();
    }

    /**
     * Called when a context menu for the {@code view} is about to be shown.
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.cat_list) {
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
        if (item.getTitle().equals(getString(R.string.action_edit_category))) {
            Intent intent = new Intent(this, EditCategoryActivity.class);
            intent.putExtra("CategoryID", mSelectedCategoryId);
            startActivity(intent);
        } else if (item.getTitle().equals(getString(R.string.action_delete_category))) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            catInput = new Spinner(this);
            initTaskCategorySpinner(catInput);
            alert.setMessage(getString(R.string.delete_category_text));
            alert.setView(catInput);
            alert.setPositiveButton(getString(R.string.action_confirm), new DialogInterface.OnClickListener() {
                /**
                 * Handles the click for the positive button of the context menu
                 *
                 * @param dialog The dialog in which the click takes place
                 * @param whichButton The button which is clicked
                 */
                public void onClick(DialogInterface dialog, int whichButton) {
                    long destinationCategoryId = ((Category) catInput.getSelectedItem()).getId();
                    if (destinationCategoryId == 0) {
                        mTaskManager.deleteCategory(mSelectedCategoryId);
                    } else {
                        mTaskManager.deleteCategoryAndMoveTasks(mSelectedCategoryId, destinationCategoryId);
                    }
                }
            });
            alert.setNegativeButton(getString(R.string.action_discard), null);
            alert.show();
        } else {
            return false;
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
        List<Category> spinnerArray = mTaskManager.getCategories();

        spinnerArray.remove(mTaskManager.getCategoryById(mSelectedCategoryId));

        Category dummyCat = new Category();
        dummyCat.setID(0);
        dummyCat.setTitle(getString(R.string.action_delete_all_tasks));

        spinnerArray.add(dummyCat);

        ArrayAdapter<Category> mSpinnerArrayAdapter = new ArrayAdapter<Category>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerArray
        );

        mSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mSpinnerArrayAdapter);
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mTaskManager.getCategoryById(mSelectedCategoryId) == null) {
            Intent allIntent = new Intent(this, TaskActivity.class);
            allIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(allIntent);
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Starts the TaskActivity.
     */
    private void startTaskActivity() {
        Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
