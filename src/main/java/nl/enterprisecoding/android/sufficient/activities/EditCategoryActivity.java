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
    private Dialog mColorDialog;
    private int mCategoryColour;
    private EditText mCategoryTitleInput;
    private long mSelectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_category_list);
        mActionBar.setTitle(R.string.action_edit_category);
        mSelectedCategory = getIntent().getExtras().getLong("CategoryID", 0);

        Category mCategory = mTaskManager.getCategoryById(mSelectedCategory);
        mTaskManager = new TaskManager(this, (long) 0);
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

        final Button colorButton = (Button) findViewById(R.id.catColorButton);
        final GradientDrawable bgShape = (GradientDrawable) colorButton.getBackground();
        bgShape.setColor(mCategory.getColour());

        colorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mColorDialog = new Dialog(EditCategoryActivity.this);
                mColorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mColorDialog.setContentView(R.layout.colour_dialog);

                createColorButton(bgShape, R.id.color_blue_button, R.color.blue);
                createColorButton(bgShape, R.id.color_purple_button, R.color.purple);
                createColorButton(bgShape, R.id.color_green_button, R.color.green);
                createColorButton(bgShape, R.id.color_orange_button, R.color.orange);
                createColorButton(bgShape, R.id.color_red_button, R.color.red);
                createColorButton(bgShape, R.id.color_random0_button, 0);
                createColorButton(bgShape, R.id.color_random1_button, 0);
                createColorButton(bgShape, R.id.color_random2_button, 0);
                createColorButton(bgShape, R.id.color_random3_button, 0);
                createColorButton(bgShape, R.id.color_random4_button, 0);

                mColorDialog.show();
            }
        });
    }

    /**
     * Create a button with a predefined color.
     *
     * @param bgShape  the shape.
     * @param buttonId the button ID.
     * @param color    the colour, can be 0 then a random colour will be generated.
     */
    private void createColorButton(final GradientDrawable bgShape, int buttonId, int color) {

        if (color == 0) {
            final int r = (int) (255 * Math.random());
            final int g = (int) (255 * Math.random());
            final int b = (int) (255 * Math.random());

            color = Color.rgb(r, g, b);
        }

        final Button colorButton = (Button) mColorDialog.findViewById(buttonId);
        final int finalColor = color;

        colorButton.setBackgroundColor(finalColor);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bgShape.setColor(getResources().getColor(finalColor));
                int chosenColour = getResources().getColor(finalColor);
                int r = Color.red(chosenColour);
                int g = Color.green(chosenColour);
                int b = Color.blue(chosenColour);
                mCategoryColour = Color.parseColor(String.format(CategoryActivity.COLOUR_FORMAT, r, g, b));
                mColorDialog.dismiss();
            }
        });
    }
}
