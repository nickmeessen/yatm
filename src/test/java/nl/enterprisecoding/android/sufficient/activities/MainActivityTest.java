package nl.enterprisecoding.android.sufficient.activities;

import android.view.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * MainActivity Test
 *
 * @author Sjors Roelofs
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mMainActivity;

    @Before
    public void setUp() {
        mMainActivity = new MainActivity();
    }

    @Test
    public void test_onKeyUp() {
        assertTrue(mMainActivity.onKeyUp(KeyEvent.KEYCODE_MENU, null));
        assertFalse(mMainActivity.onKeyUp(KeyEvent.KEYCODE_0, null));
    }

}
