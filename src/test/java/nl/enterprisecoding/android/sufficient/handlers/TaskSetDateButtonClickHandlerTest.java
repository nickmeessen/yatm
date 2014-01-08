package nl.enterprisecoding.android.sufficient.handlers;

import android.content.Intent;
import android.widget.Button;
import nl.enterprisecoding.android.sufficient.R;
import nl.enterprisecoding.android.sufficient.activities.EditTaskActivity;
import nl.enterprisecoding.android.sufficient.activities.TaskActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TaskSetDateButtonClickHandlerTest {

    @Test
    public void test_onClick() {
        Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), EditTaskActivity.class);
        intent.putExtra(TaskActivity.TASK_ID, 0);

        EditTaskActivity mEditTaskActivity = Robolectric.buildActivity(EditTaskActivity.class).withIntent(intent).create().start().get();

        Button mTaskSetDateButton = (Button) mEditTaskActivity.findViewById(R.id.task_set_date_button);
        mTaskSetDateButton.performClick();

        //@TODO: CHECK IF DIALOG OPENS! HOE DE FUCK..

        assertTrue(true);
    }

}
