package nl.enterprisecoding.android.sufficient.handlers;

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.CategoryActivity;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Breunie Ploeg on 14-1-14.
 */

@RunWith(RobolectricTestRunner.class)
public class ColourButtonClickHandlerTest {

    private ColourButtonClickHandler mColourButtonClickHandler;
    private MainActivity mMainActivity;

    private int mInputColour = 0;
    private int mRandColour = 1;
    private GradientDrawable mBgShape = new GradientDrawable();
    private Dialog mColourDialog;

    @Before
    public void setUp() {
        mColourButtonClickHandler = new ColourButtonClickHandler();
        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        mColourDialog = new Dialog(mMainActivity.getApplicationContext(), 0);
    }

    @Test
    public void test_setData() {
        mColourButtonClickHandler.setData(mMainActivity, mInputColour, mBgShape, mColourDialog, mRandColour);

        assertEquals(mInputColour, mColourButtonClickHandler.getInputColour());
        assertEquals(mBgShape, mColourButtonClickHandler.getBgShape());
        assertEquals(mColourDialog, mColourButtonClickHandler.getColourDialog());
        assertEquals(mRandColour, mColourButtonClickHandler.getRandColour());
    }

    @Test
    public void test_setActivity() {
        mColourButtonClickHandler.setActivity(mMainActivity);

        assertEquals(mMainActivity, mColourButtonClickHandler.getActivity());
    }

}
