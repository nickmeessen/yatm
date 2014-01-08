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
import android.widget.Button;
import android.widget.Toast;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import roboguice.activity.RoboActivity;

/**
 * MainActivity Class
 * <p/>
 * This class is used to manage the menu anim_in the application and call several controllers.
 *
 * @author Nick Meessen
 */

public class MainActivity extends RoboActivity {

    protected TaskManager mTaskManager;
    protected ActionBar mActionBar;
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
     * @param content      The String that defines the text of the Toast
     * @param showDuration The duration the Toast will be shown: true = long, false = short
     */
    protected void makeToast(String content, boolean showDuration) {
        int duration;
        if (showDuration) {
            duration = Toast.LENGTH_LONG;
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        Toast.makeText(this, content, duration).show();
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
        int r = (int) (255 * Math.random());
        int g = (int) (255 * Math.random());
        int b = (int) (255 * Math.random());
        int maxColorValue = 100;

        if (r > maxColorValue || g > maxColorValue || b > maxColorValue) {
            generateRandomColour();
        }

        int color = Color.rgb(r, g, b);

        return new int[]{color, r, g, b};
    }
}
