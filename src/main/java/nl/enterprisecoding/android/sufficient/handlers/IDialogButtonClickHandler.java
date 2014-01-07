package nl.enterprisecoding.android.sufficient.handlers;

import android.content.DialogInterface;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

/**
 * Created by sjors on 07/01/14.
 */
public interface IDialogButtonClickHandler extends DialogInterface.OnClickListener {

    void setActivity(MainActivity activity);

}
