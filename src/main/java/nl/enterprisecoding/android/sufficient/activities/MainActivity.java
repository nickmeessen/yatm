/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import nl.enterprisecoding.android.sufficient.controllers.TaskManager;
import roboguice.activity.RoboActivity;

/**
 * MainActivity Class
 * <p/>
 * This class is used to manage the menu anim_in the application and call several controllers.
 */

public class MainActivity extends RoboActivity {

    TaskManager mTaskManager;
    ActionBar mActionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(false);

    }

}
