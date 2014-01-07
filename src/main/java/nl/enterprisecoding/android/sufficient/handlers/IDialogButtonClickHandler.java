package nl.enterprisecoding.android.sufficient.handlers;

import android.content.DialogInterface;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * Created by sjors on 07/01/14.
 * <p/>
 * DialogButton ClickHandler interface.
 */
public interface IDialogButtonClickHandler extends DialogInterface.OnClickListener {

    /**
     * Sets current activity.
     *
     * @param activity the current activity.
     */
    void setActivity(MainActivity activity);

}
