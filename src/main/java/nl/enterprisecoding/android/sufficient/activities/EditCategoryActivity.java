/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.models.Category;

/**
 * @author Breunie Ploeg
 */
public class EditCategoryActivity extends MainActivity {

    private Activity mActivity = this;
    private int mCategoryColour;
    private EditText mCategoryTitleInput;
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
        setContentView(R.layout.edit_category_list);
        mActionBar.setTitle(R.string.action_edit_category);
        mSelectedCategoryId = getIntent().getExtras().getLong("CategoryID", 0);

        final Category category = mTaskManager.getCategoryById(mSelectedCategoryId);
        getActionBar().setBackgroundDrawable(new ColorDrawable(mTaskManager.getCategoryById(mSelectedCategoryId).getColour()));

        mCategoryTitleInput = (EditText) findViewById(R.id.category_title);
        mCategoryTitleInput.setText(category.getTitle());
        mCategoryTitleInput.requestFocus();
        int textLength = mCategoryTitleInput.getText().length();
        mCategoryTitleInput.setSelection(textLength);
        openKeyboard();
        mCategoryColour = category.getColour();

        Button mEditCategoryButton = (Button) findViewById(R.id.edit_category_button);
        mEditCategoryButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click for the EditCategoryButton
             *
             * @param v The view in which the button is clicked
             */
            @Override
            public void onClick(View v) {

                if (String.valueOf(getCategoryColour()).length() > 2) {
                    mCategoryColour = getCategoryColour();
                }

                mTaskManager.updateCategory(mCategoryTitleInput.getText().toString(), mCategoryColour, 1, mSelectedCategoryId);

                Intent intent = new Intent(mActivity, CategoryActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        });

        final Button colourButton = (Button) findViewById(R.id.category_colour_button);
        final GradientDrawable bgShape = (GradientDrawable) colourButton.getBackground();
        bgShape.setColor(category.getColour());

        colourButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click for the colourButton
             *
             * @param v The view in which the button is clicked
             */
            @Override
            public void onClick(View v) {
                createColourDialog(bgShape);
            }
        });

        mColourDialog = getColourDialog();
        mColourDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /**
             * Handles the dismiss of the colour dialog and changes the actionbar colour
             *
             * @param dialog The DialogInterface that it needs to listen to
             */
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (String.valueOf(getCategoryColour()).length() > 2) {
                    mCategoryColour = getCategoryColour();
                } else {
                    mCategoryColour = category.getColour();
                }
                mActionBar.setBackgroundDrawable(new ColorDrawable(mCategoryColour));
            }
        });
    }
}
