package utils;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.VFS;

import java.io.File;
import java.util.List;

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

    @Test
    public void getRelativePathTest() {
        String testRes = testObj.getRelativePath("static/img/games.jpg");
        Assert.assertEquals(testRes, "static/img/games.jpg");
        testRes = testObj.getRelativePath("/home/step/Technopark_3_sem/Tests_QA/server/static/img/games.jpg");
        Assert.assertEquals(testRes, "static/img/games.jpg");
    }

    @Test
    public void bfsTest() {
        List<File> testRes = testObj.bfs("static/css/admin.css");
        Assert.assertEquals(testRes.get(0).getAbsolutePath(), "/home/step/Technopark_3_sem/Tests_QA/server/static/css/admin.css");
        testRes = testObj.bfs("static/img");
        for (int i = 0; i< 3; i++) {
            switch (i) {
                case 0:
                   Assert.assertEquals(testRes.get(0).getAbsolutePath(), "/home/step/Technopark_3_sem/Tests_QA/server/static/img/games.jpg");
                    break;
                case 1:
                    Assert.assertEquals(testRes.get(1).getAbsolutePath(), "/home/step/Technopark_3_sem/Tests_QA/server/static/img/games3.jpg");
                    break;
                case 2:
                    Assert.assertEquals(testRes.get(4).getAbsolutePath(), "/home/step/Technopark_3_sem/Tests_QA/server/static/img/slide-01.jpg");
                    break;
            }
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
