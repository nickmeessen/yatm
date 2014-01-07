package nl.enterprisecoding.android.sufficient.handlers;

import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * Created by sjors on 07/01/14.
 * <p/>
 * Button ClickHandler interface.
 */
public interface IButtonClickHandler extends View.OnClickListener {

    /**
     * Sets current activity.
     *
     * @param activity the current activity.
     */
    void setActivity(MainActivity activity);

}
