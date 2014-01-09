/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

package nl.enterprisecoding.android.sufficient.handlers;

import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * Button ClickHandler interface.
 *
 * @author Sjors Roelofs
 */
public interface IButtonClickHandler extends View.OnClickListener {

    /**
     * Sets current activity.
     *
     * @param activity the current activity.
     */
    void setActivity(MainActivity activity);

}
