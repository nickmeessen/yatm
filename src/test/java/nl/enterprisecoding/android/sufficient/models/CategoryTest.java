package nl.enterprisecoding.android.sufficient.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class CategoryTest {

    private Category category;

    @Before
    @Test
    public void test_Category() {
        category = new Category();

        assertNotNull(category);
    }

    @Test
    public void test_setTitleGetTitle() {
        String title = "TestTitle";
        category.setTitle(title);

        assertEquals(category.getTitle(), title);
    }

    @Test
    public void test_setColourGetColour() {
        int Colour = 50;
        category.setColour(Colour);

        assertEquals(category.getColour(), Colour);
    }

    @Test
    public void test_setIdGetId() {
        int Id = 1;
        category.setID(Id);

        assertEquals(category.getId(), Id);
    }

    @Test
    public void test_addTaskGetTasks() {
        Task task = new Task();
        category.addTask(task);

        assertEquals(category.getTasks().get(0), task);
    }

    @Test
    public void test_setVisibleIsVisible() {
        category.setVisible(1);
        assertEquals(category.getVisible(), 1);
    }

    @Test
    public void test_toString() {
        String title = "TestTitle1";
        category.setTitle(title);

        assertEquals(category.toString(), title);
    }

}
