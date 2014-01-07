/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;

/**
 * MainActivity Class
 * <p/>
 * This class is used to manage the menu anim_in the application and call several controllers.
 *
 * @author Nick Meessen
 */

public class MainActivity extends Activity {

    protected TaskManager mTaskManager;
    protected ActionBar mActionBar;

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
