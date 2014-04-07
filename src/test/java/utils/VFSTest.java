package utils;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.VFS;

import java.io.*;
import java.util.HashSet;
import java.util.List;

/**
 * Created by step on 06.04.14.
 */
public class VFSTest {

    private static String dir=System.getProperty("user.dir")+'/';
    private static String YOUR_PATH = "/home/sofia/tp/testings/hw2/server/";
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
        Assert.assertEquals(testRes, YOUR_PATH + "static/img/games.jpg");
        testRes = testObj.getAbsolutePath(YOUR_PATH + "static/img/games.jpg");
        Assert.assertEquals(testRes, YOUR_PATH + "static/img/games.jpg");
    }

    @Test
    public void getRelativePathTest() {
        String testRes = testObj.getRelativePath("static/img/games.jpg");
        Assert.assertEquals(testRes, "static/img/games.jpg");
        testRes = testObj.getRelativePath(YOUR_PATH +"static/img/games.jpg");
        Assert.assertEquals(testRes, "static/img/games.jpg");
    }

    @Test
    public void bfsTest() {
        List<File> testRes = testObj.bfs("static/css/admin.css");
        Assert.assertEquals(testRes.get(0).getAbsolutePath(), YOUR_PATH + "static/css/admin.css");
        testRes = testObj.bfs("static/js");
        HashSet<String> myHashSet = new HashSet<String>();
        for (int i = 0; i< 8; i++) {
            myHashSet.add(testRes.get(i).getAbsolutePath());
        }
        Assert.assertTrue(myHashSet.contains(YOUR_PATH + "static/js/template.js"));
        Assert.assertTrue(myHashSet.contains(YOUR_PATH + "static/js/game.js"));
        Assert.assertTrue(myHashSet.contains(YOUR_PATH + "static/js/rules.js"));
    }

    @Test
    public void writeToFileTest() {
        testObj.writeToFile("static/check", "this is it");
        String str = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader( YOUR_PATH + "static/check"));
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(str, "this is it");
        testObj.writeToFile("static/img/check1/check2/", "This is it");
        testObj.writeToEndOfFile("static/check2", "Error!!!");
        try {
            BufferedReader br = new BufferedReader(new FileReader( YOUR_PATH + "static/check2"));
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(str, "Error!!!");
        testObj.writeToEndOfFile("static/img/check1/check2/", "err");
    }

    @Test
    public void readFileTest() {
        String str = testObj.readFile("static/check");
        Assert.assertEquals(str, "this is it");
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
