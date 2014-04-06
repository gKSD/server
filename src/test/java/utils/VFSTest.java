package utils;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.VFS;
/**
 * Created by step on 06.04.14.
 */
public class VFSTest {

    private static String dir=System.getProperty("user.dir")+'/';
    VFS testObj = new VFS();

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void fileExisting() {
        boolean testRes = testObj.isExist("/static/img/games.jpg");
        Assert.assertEquals(testRes, true);
        testRes = testObj.isExist("/static/img/games123.jpg");
        Assert.assertEquals(false, testRes);
    }

    @Test
    public void isFileTest() {
        boolean testRes = testObj.isFile("/static/img/games.jpg");
        Assert.assertEquals(testRes, true);
        testRes = testObj.isFile("/static/img/");
        Assert.assertEquals(testRes, false);
    }

    @Test
    public void getAbsolutePathTest() {
        String testRes = testObj.getAbsolutePath("static/img/games.jpg");
        System.out.println(testRes);
        Assert.assertEquals(testRes, "/home/step/Technopark_3_sem/Tests_QA/server/static/img/games.jpg");
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
