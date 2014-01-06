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
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(false);

    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_MENU)) {

            startActivity(new Intent(this, CategoryActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
