package nl.enterprisecoding.android.sufficient.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import nl.enterprisecoding.android.sufficient.R;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Copyright (c) 2013. "EnterpriseCoding"
 * ("EnterpriseCoding" constitutes; Nick Meessen, Jasper Burgers, Ferry Wienholts, Breunie Ploeg & Sjors Roelofs).
 *
 * This content is released under the MIT License. A copy of this license should be included with the project otherwise can be found at http://opensource.org/licenses/MIT
 */


public class EditTaskActivityTest {

    private EditTaskActivity mEditTaskActivity;

    @Before
    public void setUp() {

        Intent intent = new Intent(new EditTaskActivity(), EditTaskActivity.class);
        intent.putExtra(TaskActivity.TASK_ID, 0);

        mEditTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(intent).create().get();
    }

    @Test
    public void test_OnClick() {

        View view = mock(View.class);

        when(view.getId()).thenReturn(R.id.save_task_button);
        mEditTaskActivity.onClick(view);

        when(view.getId()).thenReturn(R.id.catAddButton);
        mEditTaskActivity.onClick(view);

    }
}
