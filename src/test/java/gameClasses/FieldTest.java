package gameClasses;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 07.04.14.
 */
public class FieldTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void fieldTest() {
        Field testObj = new Field(Field.checker.nothing);
        boolean testRes = testObj.isEmpty();
        Assert.assertTrue(testRes);
        Field testObj1 = new Field(Field.checker.black);
        testRes = testObj1.isEmpty();
        Assert.assertEquals(testRes, false);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
