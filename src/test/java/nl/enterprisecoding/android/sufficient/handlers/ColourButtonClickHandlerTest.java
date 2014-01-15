package nl.enterprisecoding.android.sufficient.handlers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import nl.enterprisecoding.android.sufficient.activities.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Breunie Ploeg on 14-1-14.
 */

@RunWith(RobolectricTestRunner.class)
public class ColourButtonClickHandlerTest {

    private ColourButtonClickHandler mColourButtonClickHandler1;
    private ColourButtonClickHandler mColourButtonClickHandler2;

    private GradientDrawable mBgShape;
    private Dialog mColourDialog;

    @Before
    public void setUp() {

        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();

        mBgShape = new GradientDrawable();
        mColourDialog = new Dialog(activity, 0);

        mColourButtonClickHandler1 = new ColourButtonClickHandler(mBgShape, mColourDialog, 0, 1);
        mColourButtonClickHandler2 = new ColourButtonClickHandler(mBgShape, mColourDialog, 1, 0);

    }

    @Test
    public void test_onClick() {

        Context mockContext = mock(Context.class);
        Resources mockResources = mock(Resources.class);
        View mockView = mock(View.class);

        when(mockView.getContext()).thenReturn(mockContext);
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockResources.getColor(Mockito.anyInt())).thenReturn(Color.BLUE);

        mColourButtonClickHandler1.onClick(mockView);
        mColourButtonClickHandler2.onClick(mockView);

        assertFalse(mColourDialog.isShowing());
    }

}
