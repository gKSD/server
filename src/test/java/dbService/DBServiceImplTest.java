package dbService;

import base.MessageSystem;
import junit.framework.Assert;
import messageSystem.MessageSystemImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by phil on 06.04.14.
 */
public class DBServiceImplTest {
    DBServiceImpl db;
    @BeforeMethod
    public void setUp() throws Exception {
        db = new DBServiceImpl(new MessageSystemImpl());
    }

    @Test
    public void testGetMessageSystem() throws Exception {
        db.run();
        Assert.assertEquals(1,db.getUDS("phil","123456").getId());
    }

    @Test
    public void testGetAddress() throws Exception {

    }

    @Test
    public void testGetUDS() throws Exception {

    }

    @Test
    public void testAddUDS() throws Exception {

    }

    @Test
    public void testUpdateUsers() throws Exception {

    }

    @Test
    public void testUpdateAI() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

    }
}
