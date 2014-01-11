package nl.enterprisecoding.android.sufficient.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class TaskTest {

    private Task task;

    @Before
    public void setup() {
        task = new Task();
    }

    @Test
    public void Test_setIdGetId() {
        int Id = 1;
        task.setId(Id);

        assertEquals(task.getId(), Id);
    }

    @Test
    public void Test_setCatIdGetCatId() {
        int CatId = 3;
        task.setCategoryId(CatId);

        assertEquals(task.getCatId(), CatId);
    }

    @Test
    public void Test_setTitleGetTitle() {
        String Title = "Test";
        task.setTitle(Title);

        assertEquals(task.getTitle(), "Test");
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
        Boolean important = true;
        task.setImportant(important);

        assertEquals(task.isImportant(), important);
    }

    @Test
    public void Test_setCompletedIsCompleted() {
        Boolean completed = true;
        task.setImportant(completed);

        assertEquals(task.isImportant(), completed);
    }
}
