package resource;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 06.04.14.
 */
public class ResourceFactoryTest {
    ResourceFactory testObj;
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void constructorTest() {
        testObj.instanse();
        testObj.instanse();
    }

    @Test
    public void getResourceTest() {
        testObj.instanse();
    }
    @AfterMethod
    public void tearDown() throws Exception {

    }
}
