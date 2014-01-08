package nl.enterprisecoding.android.sufficient.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    private Category category;

    @Before
    public void setup() {
        category = new Category();
    }

    @Test
    public void Test_setIdGetId() {
        int Id = 1;
        category.setID(Id);

        assertEquals(category.getID(), Id);
    }
}
