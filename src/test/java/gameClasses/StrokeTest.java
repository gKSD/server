package gameClasses;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 07.04.14.
 */
public class StrokeTest {
    Stroke testObj;
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void strokeTest() {
        testObj = new Stroke(2, 1, 1, 0, "gay", "b");
        testObj = testObj.getInverse();
        /*int to_x = testObj.getTo_X();
        Assert.assertEquals(to_x, 5);
        int to_y = testObj.getTo_Y();
        Assert.assertEquals(to_y, 6);
        int from_x = testObj.getFrom_X();
        Assert.assertEquals(from_x, 6);
        int from_y = testObj.getFrom_Y();
        String stat = testObj.getStatus();
        String color = testObj.getColor();
        char next = testObj.getNext(); */
        testObj = new Stroke(testObj);
        String testRes = testObj.toString();
        Assert.assertEquals(testRes, "{\"color\":\"w\",\"to_x\":5,\"to_y\":6,\"from_x\":6,\"from_y\":7,\"status\":\"gay\",\"next\":\"0\"}");
        Stroke testObj2 = new Stroke(2, 1, 1, 0, "gay", "w");
        testObj2 = testObj2.getInverse();
        testRes = testObj2.toString();
        Assert.assertEquals(testRes, "{\"color\":\"b\",\"to_x\":5,\"to_y\":6,\"from_x\":6,\"from_y\":7,\"status\":\"gay\",\"next\":\"0\"}");
        testObj.setTo_X(4);
        boolean testBool = testObj.isEmpty();
        Assert.assertEquals(testBool, false);
        testObj.setTo_X(-1);
        testObj.setTo_Y(2);
        testBool = testObj.isEmpty();
        Assert.assertEquals(testBool, false);
        testObj.setTo_Y(-1);
        testObj.setFrom_X(4);
        testBool = testObj.isEmpty();
        Assert.assertEquals(testBool, false);
        testObj.setFrom_X(-1);
        testObj.setFrom_Y(4);
        testBool = testObj.isEmpty();
        Assert.assertEquals(testBool, false);
        testObj.setFrom_Y(-1);
        testBool = testObj.isEmpty();
        Assert.assertEquals(testBool, true);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
