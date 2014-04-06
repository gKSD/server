package resource;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 06.04.14.
 */
public class RatingTest {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void getDiffTest() {
        Rating.avgDiff = 10;
        Rating.maxDiff = 2;
        Rating.minDiff = 30;
        Rating.decreaseThreshold = 0;
        int testRes = Rating.getDiff(2,3);
        Assert.assertEquals(testRes, 10);
        Rating.decreaseThreshold = 1;
        testRes = Rating.getDiff(5, 20);
        Assert.assertEquals(testRes, 2);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
