package utils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.thread.ThreadTimeoutException;

/**
 * Created by step on 07.04.14.
 */
public class SysInfoTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test(timeOut = 5000)
    public void runTest() {
        SysInfo testObj = new SysInfo();
        try {
            testObj.run();
        } catch (Exception e) {
            System.out.println("Timeout test for infinite loop");
        }

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
