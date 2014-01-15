package nl.enterprisecoding.android.sufficient.activities;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.view.KeyEvent;
import android.widget.Toast;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowHandler;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;

/**
 * MainActivity Test
 *
 * @author Sjors Roelofs
 */
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mMainActivity;
    private MainTest mMainTest;

    @Before
    public void setUp() {
        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        mMainTest = new MainTest();
    }

    @Test
    public void test_onKeyUp() {
        assertTrue(mMainActivity.onKeyUp(KeyEvent.KEYCODE_MENU, null));
        assertFalse(mMainActivity.onKeyUp(KeyEvent.KEYCODE_0, null));
        assertFalse(mMainActivity.onKeyUp(KeyEvent.KEYCODE_ALT_LEFT, null));
    }

    @Test
    public void test_generateRandomColor() {
        assertNotSame(mMainActivity.generateRandomColour(), mMainActivity.generateRandomColour());
        assertNotSame(mMainActivity.generateRandomColour(), mMainActivity.generateRandomColour());
        assertNotSame(mMainActivity.generateRandomColour(), mMainActivity.generateRandomColour());
        assertNotSame(mMainActivity.generateRandomColour(), mMainActivity.generateRandomColour());
        assertNotSame(mMainActivity.generateRandomColour(), mMainActivity.generateRandomColour());

        int[] randomColours = mMainActivity.generateRandomColour();
        assertNotNull(randomColours);

        for (int colour : randomColours) {
            assertTrue("expected to be less than", colour <= 255);
        }
    }

    @Test
    public void test_makeToast() {
        String input = "Test toast";
        mMainTest.makeToast(input);

        ShadowHandler.idleMainLooper();
        String output = ShadowToast.getTextOfLatestToast();
        int expectedLength = Toast.LENGTH_SHORT;
        int actualLength = ShadowToast.getLatestToast().getDuration();

        assertEquals(expectedLength, actualLength);
        assertEquals(input, output);
    }

//    @Test
//    public void test_createColourDialog() {
//        GradientDrawable bgShape = new GradientDrawable();
//        Dialog colourDialog = mMainTest.createColourDialog(bgShape);
//
//        assertNotNull(colourDialog);
//    }

    class MainTest extends MainActivity {

        @Override
        public void makeToast(String content) {
            super.makeToast(content);
        }

        @Override
        public Dialog createColourDialog(GradientDrawable bgShape) {
            return super.createColourDialog(bgShape);
        }

    }

}
