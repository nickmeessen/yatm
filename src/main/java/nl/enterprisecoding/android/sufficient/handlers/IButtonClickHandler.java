package nl.enterprisecoding.android.sufficient.handlers;

import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;

public interface IButtonClickHandler extends View.OnClickListener {

    void setActivity(MainActivity activity);

}
