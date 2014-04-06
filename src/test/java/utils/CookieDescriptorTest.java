package utils;

import junit.framework.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import utils.CookieDescriptor;
/**
 * Created by step on 06.04.14.
 */
public class CookieDescriptorTest {

    private Map<String,String> nameToCookie= new HashMap<String,String>();
    private Cookie[] coo;

    @BeforeMethod
    public void setUp() throws Exception {
        coo = new Cookie[3];
        coo[0] = new Cookie("username", "John");
        coo[1] = new Cookie("result", "1000 points");
        coo[2] = new Cookie("playtime", "40 minutes");
    }

    @Test
    public void ThisTest() {
        CookieDescriptor checkObj = new CookieDescriptor(coo);
        String testRes = checkObj.getCookieByName("username");
        Assert.assertEquals(testRes, "John");
        testRes = checkObj.getCookieByName("result");
        Assert.assertEquals(testRes, "1000 points");
        testRes = checkObj.getCookieByName("playtime");
        Assert.assertEquals(testRes, "40 minutes");
        testRes = checkObj.getCookieByName("sdfsd");
        Assert.assertEquals(testRes, null);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
