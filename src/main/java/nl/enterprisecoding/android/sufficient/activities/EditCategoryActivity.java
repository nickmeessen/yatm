/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import nl.enterprisecoding.android.sufficient.models.Category;

/**
 * @author Breunie Ploeg
 */
public class EditCategoryActivity extends MainActivity {

    private Activity mActivity = this;
    private Dialog mColourDialog;
    private int mCategoryColour;
    private EditText mCategoryTitleInput;
    private long mSelectedCategory;

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
        setContentView(R.layout.edit_category_list);
        mActionBar.setTitle(R.string.action_edit_category);
        mSelectedCategory = getIntent().getExtras().getLong("CategoryID", 0);

        mTaskManager = new TaskManager(this, (long) 0);
        Category mCategory = mTaskManager.getCategoryById(mSelectedCategory);
        getActionBar().setBackgroundDrawable(new ColorDrawable(mTaskManager.getCategoryById(mSelectedCategory).getColour()));

        mCategoryTitleInput = (EditText) findViewById(R.id.category_title);
        mCategoryTitleInput.setText(mCategory.getTitle());
        mCategoryColour = mCategory.getColour();

        Button mEditCategoryButton = (Button) findViewById(R.id.edit_category_button);
        mEditCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTaskManager.editCategory(mCategoryTitleInput.getText().toString(), mCategoryColour, mSelectedCategory);

                Intent intent = new Intent(mActivity, CategoryActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        });

        final Button colourButton = (Button) findViewById(R.id.category_colour_button);
        final GradientDrawable bgShape = (GradientDrawable) colourButton.getBackground();
        bgShape.setColor(mCategory.getColour());

        colourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mColourDialog = new Dialog(EditCategoryActivity.this);
                mColourDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mColourDialog.setContentView(R.layout.colour_dialog);
                createColourButton(bgShape, R.id.colour_blue_button, R.color.blue);
                createColourButton(bgShape, R.id.colour_purple_button, R.color.purple);
                createColourButton(bgShape, R.id.colour_green_button, R.color.green);
                createColourButton(bgShape, R.id.colour_orange_button, R.color.orange);
                createColourButton(bgShape, R.id.color_red_button, R.color.red);
                createColourButton(bgShape, R.id.colour_random0_button, 0);
                createColourButton(bgShape, R.id.colour_random1_button, 0);
                createColourButton(bgShape, R.id.colour_random2_button, 0);
                createColourButton(bgShape, R.id.colour_random3_button, 0);
                createColourButton(bgShape, R.id.colour_random4_button, 0);
                mColourDialog.show();
            }
        });
    }

    /**
     * Create a button with a predefined inputColour.
     *
     * @param bgShape  the shape.
     * @param buttonId the button ID.
     * @param inputColour   the inputColour, can be 0 then a random inputColour will be generated.
     */
    private void createColourButton(final GradientDrawable bgShape, int buttonId, final int inputColour) {
        final Button colourButton = (Button) mColourDialog.findViewById(buttonId);
        final int[] randColour = generateRandomColour();

        if (inputColour != 0) {
            colourButton.setBackgroundColor(getResources().getColor(inputColour));
        } else {
            colourButton.setBackgroundColor(randColour[0]);
        }

        colourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputColour != 0) {
                    int chosenColour = getResources().getColor(inputColour);
                    int r = Color.red(chosenColour);
                    int g = Color.green(chosenColour);
                    int b = Color.blue(chosenColour);
                    mCategoryColour = Color.parseColor(String.format(CategoryActivity.COLOUR_FORMAT, r, g, b));
                    bgShape.setColor(getResources().getColor(inputColour));
                } else {
                    mCategoryColour = Color.parseColor(String.format(CategoryActivity.COLOUR_FORMAT, randColour[1], randColour[2], randColour[3]));
                    bgShape.setColor(randColour[0]);
                }
                mColourDialog.dismiss();
            }
        });
    }
}
