package gameMechanic.gameCreating;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import base.Address;
import gameMechanic.gameCreating.MsgCreateChat;


/**
 * Created by phil on 06.04.14.
 */


public class MsgCreateChatTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
    @Test
    public void SimpleTest1() {
          MsgCreateChat chat = new MsgCreateChat(new Address(),new Address(),"qq","ss");
          Assert.assertEquals("qq",chat.get_sessionId1());
          Assert.assertEquals("ss", chat.get_sessionId2());
    }
}

