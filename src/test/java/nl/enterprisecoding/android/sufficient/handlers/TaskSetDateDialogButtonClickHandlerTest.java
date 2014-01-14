package nl.enterprisecoding.android.sufficient.handlers;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskSetDateDialogButtonClickHandlerTest {

    private Calendar mTaskDate;
    private TaskSetDateDialogButtonClickHandler mClickHandler;

    @Before
    public void setUp() {
        mTaskDate = Calendar.getInstance();
        mTaskDate.set(2014, Calendar.JANUARY, 1, 0, 0, 0);
        mClickHandler = new TaskSetDateDialogButtonClickHandler(mTaskDate);
    }

    @Test
    public void test_onClickPositive() {

        Calendar testDate = Calendar.getInstance();
        DatePickerDialog dialog = mock(DatePickerDialog.class);
        DatePicker datePicker = mock(DatePicker.class);

        testDate.set(2015, Calendar.FEBRUARY, 2, 0, 0, 0);

        when(dialog.getDatePicker()).thenReturn(datePicker);
        when(datePicker.getYear()).thenReturn(2015);
        when(datePicker.getMonth()).thenReturn(Calendar.FEBRUARY);
        when(datePicker.getDayOfMonth()).thenReturn(2);

        mClickHandler.onClick(dialog, DialogInterface.BUTTON_POSITIVE);

        assertEquals(testDate, mTaskDate);

    }

    @Test
    public void test_onClickNegative() {

        Calendar testDate = Calendar.getInstance();
        DatePickerDialog dialog = mock(DatePickerDialog.class);
        DatePicker datePicker = mock(DatePicker.class);

        testDate.set(2014, Calendar.JANUARY, 1, 0, 0, 0);

        when(dialog.getDatePicker()).thenReturn(datePicker);
        when(datePicker.getYear()).thenReturn(2015);
        when(datePicker.getMonth()).thenReturn(Calendar.FEBRUARY);
        when(datePicker.getDayOfMonth()).thenReturn(2);

        mClickHandler.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);

        assertEquals(testDate, mTaskDate);
    }
}