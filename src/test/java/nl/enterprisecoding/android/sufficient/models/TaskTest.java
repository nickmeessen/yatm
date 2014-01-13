package nl.enterprisecoding.android.sufficient.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class TaskTest {

    private Task task;

    @Before
    public void setup() {
        task = new Task();
    }

    @Test
    public void Test_setIdGetId() {
        int id = Mockito.anyInt();
        task.setId(id);

        assertEquals(task.getId(), id);
    }

    @Test
    public void Test_setCatIdGetCatId() {
        int catId = Mockito.anyInt();
        task.setCategoryId(catId);

        assertEquals(task.getCatId(), catId);
    }

    @Test
    public void Test_setTitleGetTitle() {
        String title = Mockito.anyString();
        task.setTitle(title);

        assertEquals(task.getTitle(), title);
    }

    @Test
    public void Test_setDateGetDate() {
        Calendar date = Calendar.getInstance();
        date.set(1993, Calendar.JANUARY, 20);
        task.setDate(date);

        assertEquals(task.getDate(), date);
    }

    @Test
    public void Test_setImportantIsImportant() {
        task.setImportant(true);
        assertTrue(task.isImportant());
    }

    @Test
    public void Test_setCompletedIsCompleted() {
        task.setImportant(true);
        assertTrue(task.isImportant());
    }
}
