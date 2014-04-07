package accountService;

import base.AccountService;
import base.MessageSystem;
import gameClasses.Stroke;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.mock;

/**
 * Created by step on 07.04.14.
 */
public class AccountServiceImplTest {
    private MessageSystem messageSystem;
    @BeforeMethod
    public void setUp() throws Exception {
        messageSystem = mock(MessageSystem.class);
    }

    @Test
    public void getUserIdTest() {
        AccountServiceImpl testObj = new AccountServiceImpl(messageSystem);
        testObj.nickToId.put("check", 3);
        int testRes = testObj.getUserId("check","123");
        Assert.assertEquals(testRes, 3);
        testRes = testObj.getUserId("","123");
        Assert.assertEquals(testRes, 1);
    }
    @AfterMethod
    public void tearDown() throws Exception {

    }
}
