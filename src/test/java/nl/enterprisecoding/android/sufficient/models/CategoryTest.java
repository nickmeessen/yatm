package nl.enterprisecoding.android.sufficient.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class CategoryTest {

    private Category category;

    @Before
    public void setup() {
        category = new Category();
    }

    @Test
    public void test_setTitleGetTitle() {
        String title = "TestTitle";
        category.setTitle(title);

        assertEquals(category.getTitle(),title);
    }

    @Test
    public void test_setColourGetColour() {
        int Colour = 50;
        category.setColour(Colour);

        assertEquals(category.getColour(),Colour);
    }

    @Test
    public void Test_setIdGetId() {
        int Id = 1;
        category.setID(Id);

        assertEquals(category.getID(), Id);
    }

    @Test
    public void test_addTaskGetTasks() {
        Task task = new Task();
        category.addTask(task);

        assertEquals(category.getTasks().get(0), task);
    }

    @Test
    public void test_setVisibleIsVisible() {
        Boolean visible = true;
        category.setVisible(visible);

        assertEquals(category.isVisible(), visible);
    }
}
