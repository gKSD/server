package utils;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by step on 06.04.14.
 */
public class CasterTest {
    private Map<String, String> map = new HashMap<String,String>();
    @BeforeMethod
    public void setUp() throws Exception {
        map.put("key1", "yes");
        map.put("key2", "no");
    }

    @Test
    public void castKeysToStringTest() {
        Caster check = new Caster();
        String[] testRes = check.castKeysToStrings(map);
        Assert.assertEquals(testRes[1], "key1");
        Assert.assertEquals(testRes[0], "key2");
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
