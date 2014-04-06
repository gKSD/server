package resource;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 07.04.14.
 */
public class GameSettingsTest {
    GameSettings testObj;
    @BeforeMethod
    public void setUp() throws Exception {

    }
    @Test
    public void gSetTest() {
        testObj = new GameSettings(5, 2);
        int testRes = testObj.getFieldSize();
        Assert.assertEquals(testRes, 5);
        testRes = testObj.getPlayerSize();
        Assert.assertEquals(testRes, 2);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
