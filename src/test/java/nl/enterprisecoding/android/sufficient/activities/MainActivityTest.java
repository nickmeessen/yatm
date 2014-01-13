package nl.enterprisecoding.android.sufficient.activities;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.graphics.Color;
import android.view.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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

    @Test
    public void test_setGetFinalColour() {
        mMainActivity.setFinalColour(Color.BLUE);
        assertEquals(Color.BLUE, mMainActivity.getFinalColour());
    }

    @Test
    public void test_generateRandomColor() {
        int[] randomColours = mMainActivity.generateRandomColour();

        assertNotSame(mMainActivity.generateRandomColour(), mMainActivity.generateRandomColour());
        assertNotNull(randomColours);

        for(int colour : randomColours) {
            assertTrue("expected to be less than", colour <= 255);
        }
    }

}
