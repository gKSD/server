package utils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by step on 07.04.14.
 */
public class SysInfoTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void runTest() {
        SysInfo testObj = new SysInfo();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
