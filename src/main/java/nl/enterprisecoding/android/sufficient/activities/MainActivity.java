/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import roboguice.activity.RoboActivity;

/**
 * MainActivity Class
 * This class is used to manage the menu anim_in the application and call several controllers.
 *
 * @author Nick Meessen
 */
public class MainActivity extends RoboActivity {
    private static final String CATEGORY_ID = "categoryID";
    protected TaskManager mTaskManager;
    protected ActionBar mActionBar;
    protected long mCurrentCategoryID;
    protected int mFinalColour;

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

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(false);

        mCurrentCategoryID = getIntent().getLongExtra(CATEGORY_ID, 0);
    }

    /**
     * Called when a key was released and not handled by any of the views
     * inside of the activity.
     *
     * @return Return <code>true</code> to prevent this event from being propagated
     * further, or <code>false</code> to indicate that you have not handled
     * this event and it should continue to be propagated.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            startActivity(new Intent(this, CategoryActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     * Shows a Toast
     *
     * @param content The String that defines the text of the Toast
     */
    protected void makeToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates a button with a certain colour within the colour choosing dialog.
     *
     * @param bgShape      The shape that is clicked
     * @param buttonId     The id of the button that shows the colour
     * @param inputColour  The colour the button will have, 0 is a random colour
     * @param colourDialog The Dialog that will display the colours
     */
    protected void createColourButton(final GradientDrawable bgShape, int buttonId, final int inputColour, final Dialog colourDialog) {
        final Button colourButton = (Button) colourDialog.findViewById(buttonId);
        final int[] randColour = generateRandomColour();

        if (inputColour != 0) {
            colourButton.setBackgroundColor(getResources().getColor(inputColour));
        } else {
            colourButton.setBackgroundColor(randColour[0]);
        }

        colourButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click for the colourButton
             * @param v The view in which the click takes place
             */
            @Override
            public void onClick(View v) {

                int chosenColour;
                if (inputColour != 0) {
                    chosenColour = getResources().getColor(inputColour);
                    bgShape.setColor(getResources().getColor(inputColour));
                } else {
                    chosenColour = randColour[0];
                    bgShape.setColor(randColour[0]);
                }
                colourDialog.dismiss();
                mFinalColour = chosenColour;
            }
        });
    }

    protected int getCategoryColour() {
        return mFinalColour;
    }

    /**
     * Generates a random colour
     *
     * @return returns the random colour
     */
    protected int[] generateRandomColour() {
        int receiveMaxValue = (int) (2 * Math.random());
        int rValue = 255;
        int gValue = 255;
        int bValue = 255;

        if (receiveMaxValue == 0) {
            rValue = 100;
        } else if (receiveMaxValue == 1) {
            gValue = 100;
        } else {
            bValue = 100;
        }

        int r = (int) (rValue * Math.random());
        int g = (int) (gValue * Math.random());
        int b = (int) (bValue * Math.random());

        int color = Color.rgb(r, g, b);

        return new int[]{color, r, g, b};
    }

    /**
     * Creates a dialog which displays available colours
     *
     * @param bgShape The shape that will display the chosen colour
     */
    protected Dialog createColourDialog(GradientDrawable bgShape) {
        int bgColour = getResources().getColor(R.color.action_bg);
        Dialog colourDialog = new Dialog(this);

        colourDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        colourDialog.setContentView(R.layout.colour_dialog);
        colourDialog.findViewById(R.id.colour_dialog).setBackgroundColor(bgColour);

        createColourButton(bgShape, R.id.colour_purple_button, R.color.purple, colourDialog);
        createColourButton(bgShape, R.id.colour_blue_button, R.color.blue, colourDialog);
        createColourButton(bgShape, R.id.colour_green_button, R.color.green, colourDialog);
        createColourButton(bgShape, R.id.colour_orange_button, R.color.orange, colourDialog);
        createColourButton(bgShape, R.id.color_red_button, R.color.red, colourDialog);
        createColourButton(bgShape, R.id.colour_random0_button, 0, colourDialog);
        createColourButton(bgShape, R.id.colour_random1_button, 0, colourDialog);
        createColourButton(bgShape, R.id.colour_random2_button, 0, colourDialog);
        createColourButton(bgShape, R.id.colour_random3_button, 0, colourDialog);
        createColourButton(bgShape, R.id.colour_random4_button, 0, colourDialog);

        return colourDialog;
    }

    /**
     * Activates the android keyboard
     */
    protected void openKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
