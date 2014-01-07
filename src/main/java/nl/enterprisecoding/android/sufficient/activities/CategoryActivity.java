/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

    private Activity mActivity = this;
    private Dialog mColorDialog;
    private int mChosenColour = 0;
    private int mCategoryColour;
    private String[] mSpringerArray;
    private Spinner catInput;
    private long mSelectedCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category_list);

        mActionBar.hide();

        final String standardText = getResources().getString(R.string.new_cat);

        mTaskManager = new TaskManager(this, (long) 0);

        final Button colorButton = (Button) findViewById(R.id.catColorButton);
        final GradientDrawable bgShape = (GradientDrawable) colorButton.getBackground();
        final int[] randomColor = generateRandomColor();
        bgShape.setColor(randomColor[0]);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColorDialog = new Dialog(CategoryActivity.this);
                mColorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mColorDialog.setContentView(R.layout.color_dialog);

                createColorButton(bgShape, R.id.color_purple_button, R.color.purple);
                createColorButton(bgShape, R.id.color_blue_button, R.color.blue);
                createColorButton(bgShape, R.id.color_green_button, R.color.green);
                createColorButton(bgShape, R.id.color_orange_button, R.color.orange);
                createColorButton(bgShape, R.id.color_red_button, R.color.red);
                createRandomColorButton(bgShape, R.id.color_random0_button);
                createRandomColorButton(bgShape, R.id.color_random1_button);
                createRandomColorButton(bgShape, R.id.color_random2_button);
                createRandomColorButton(bgShape, R.id.color_random3_button);
                createRandomColorButton(bgShape, R.id.color_random4_button);

                mColorDialog.show();
            }
        });

        final EditText editText = (EditText) findViewById(R.id.newCategory);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String categoryName = editText.getText().toString();
                    if (mCategoryColour == 0) {
                        mChosenColour = randomColor[0];
                        int r = Color.red(mChosenColour);
                        int g = Color.green(mChosenColour);
                        int b = Color.blue(mChosenColour);
                        mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, r, g, b));
                    }

                    // @TODO (Breunie) Params aren't checked.
                    if (categoryName.equals(standardText)) {
                        Toast.makeText(mActivity.getApplicationContext(), getString(R.string.ChooseDiffName), Toast.LENGTH_SHORT).show();
                    } else {
                        mTaskManager.checkExistingCategory(categoryName, mCategoryColour);
                        editText.setText("");
                    }
                }
                return false;
            }
        });

        final View addButton = findViewById(R.id.catAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = editText.getText().toString();
                if (mCategoryColour == 0) {
                    mChosenColour = randomColor[0];
                    int r = Color.red(mChosenColour);
                    int g = Color.green(mChosenColour);
                    int b = Color.blue(mChosenColour);
                    mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, r, g, b));
                }

                // @TODO (Breunie) Params aren't checked.
                if (categoryName.equals(standardText)) {
                    Toast.makeText(mActivity.getApplicationContext(), getString(R.string.ChooseDiffName), Toast.LENGTH_SHORT).show();
                } else {
                    mTaskManager.checkExistingCategory(categoryName, mCategoryColour);
                    editText.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    mTaskManager.notifyDataSetChanged();
                    mActivity.startActivity(getIntent());
                }
            }
        });

    }

    /**
     * Create a button with a predefined color.
     *
     * @param bgShape  the shape that shows the color on screen.
     * @param buttonId the id of the button to create.
     * @param color    the color of the button to create.
     */
    private void createColorButton(final GradientDrawable bgShape, int buttonId, final int color) {
        final EditText editText = (EditText) findViewById(R.id.newCategory);
        final Button colorButton = (Button) mColorDialog.findViewById(buttonId);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgShape.setColor(getResources().getColor(color));
                mChosenColour = getResources().getColor(color);
                int r = Color.red(mChosenColour);
                int g = Color.green(mChosenColour);
                int b = Color.blue(mChosenColour);
                mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, r, g, b));
                mColorDialog.dismiss();
                editText.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
    }

    /**
     * Creates a button with a random color.
     *
     * @param bgShape  the shape that shows the color on screen.
     * @param buttonId the id of the button to create.
     * @todo (Breunie) merge with above method to avoid duplicate code.
     */
    private void createRandomColorButton(final GradientDrawable bgShape, int buttonId) {
        final EditText editText = (EditText) findViewById(R.id.newCategory);
        final Button colorButton = (Button) mColorDialog.findViewById(buttonId);
        final int[] color = generateRandomColor();
        colorButton.setBackgroundColor(color[0]);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgShape.setColor(color[0]);
                mCategoryColour = Color.parseColor(String.format(COLOUR_FORMAT, color[1], color[2], color[3]));
                mColorDialog.dismiss();
                editText.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
    }

    int[] generateRandomColor() {
        int r = (int) (255 * Math.random());
        int g = (int) (255 * Math.random());
        int b = (int) (255 * Math.random());
        int maxColorValue = 100;

        if (r > maxColorValue || g > maxColorValue || b > maxColorValue) {
            generateRandomColor();
        }

        int color = Color.rgb(r, g, b);

        return new int[]{color, r, g, b};
    }

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
                public void onClick(DialogInterface dialog, int whichButton) {
                    Category originCategory = mTaskManager.getCategoryById(mSelectedCategoryId);

                    String destinationCategory = mSpringerArray[catInput.getSelectedItemPosition()];
                    if (destinationCategory.equals(getResources().getString(R.string.action_delete_all_tasks))) {
                        mTaskManager.deleteCategory(originCategory);
                    } else {
                        mTaskManager.deleteCategoryAndMoveTasks(originCategory, mTaskManager.getCategory(destinationCategory));
                    }

                    Intent intent = new Intent(mActivity, CategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            });

            alert.setNegativeButton(getResources().getString(R.string.action_discard), null);
            alert.show();
        }

        return true;
    }

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

    private String[] convertCategoryListToStringArray(List<Category> categoryList) {
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
