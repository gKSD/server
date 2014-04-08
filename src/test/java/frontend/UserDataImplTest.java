package frontend;

import base.MessageSystem;
import chat.ChatWSImpl;
import dbService.UserDataSet;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sofia on 08.04.14.
 */
public class UserDataImplTest {

    //mock
    private MessageSystem messageSystem;

    UserDataImpl userDataImpl;

    @BeforeMethod
    public void setUp() throws Exception {

        messageSystem = mock(MessageSystem.class);
        userDataImpl = new UserDataImpl(messageSystem);

    }

    @Test
    public void testGetSessionIdByUserId() throws Exception {

        //test1
        Assert.assertEquals(userDataImpl.getSessionIdByUserId(13), null);

        //test2
        int id1 = 1;
        String sessionId1 = "113123";
        UserDataSet userDataSet1 = mock(UserDataSet.class);
        when(userDataSet1.getId()).thenReturn(id1);

        int id2 = 2;
        String sessionId2 = "45634563";
        UserDataSet userDataSet2 = mock(UserDataSet.class);
        when(userDataSet2.getId()).thenReturn(id2);

        int id3 = 3;
        String sessionId3 = "sdfgsfg";
        UserDataSet userDataSet3 = mock(UserDataSet.class);
        when(userDataSet3.getId()).thenReturn(id3);

        userDataImpl.putLogInUser(sessionId1, userDataSet1);
        userDataImpl.putLogInUser(sessionId2, userDataSet2);
        userDataImpl.putLogInUser(sessionId3, userDataSet3);

        Assert.assertEquals(userDataImpl.getSessionIdByUserId(id1), sessionId1);
        Assert.assertEquals(userDataImpl.getSessionIdByUserId(id2), sessionId2);
        Assert.assertEquals(userDataImpl.getSessionIdByUserId(id3), sessionId3);

    }

    @Test
    public void testPutSessionIdAndChatWS() throws Exception {

        int id1 = 1;
        String sessionId1 = "113123";
        UserDataSet userDataSet1 = new UserDataSet(id1, "Bob", 5, 6, 6);
        userDataSet1.setLastVisit_ForTest(123);

        int id4 = 4;
        String sessionId4 = "adasdasd";

        int id2 = 2;
        String sessionId2 = "45634563";
        UserDataSet userDataSet2 = new UserDataSet(id2, "Tom", 5, 2, 87);
        userDataSet2.setLastVisit_ForTest(345);

        int id3 = 3;
        String sessionId3 = "sdfgsfg";
        UserDataSet userDataSet3 = new UserDataSet(id3, "Alex", 3, 8, 6);
        userDataSet3.setLastVisit_ForTest(456);

        userDataImpl.putLogInUser(sessionId1, userDataSet1);
        userDataImpl.putLogInUser(sessionId2, userDataSet2);
        userDataImpl.putLogInUser(sessionId3, userDataSet3);


        ChatWSImpl chatWS1 = mock(ChatWSImpl.class);
        String sessionId5= "sdfgsfg";
        ChatWSImpl chatWS2 = mock(ChatWSImpl.class);
        String sessionId6 = "sdfgsfg";

        long oldVisitTime = 0;

        oldVisitTime = userDataSet1.getLastVisit();
        userDataImpl.putSessionIdAndChatWS(sessionId1, chatWS1);
        Assert.assertFalse(oldVisitTime == userDataSet1.getLastVisit());

        oldVisitTime = userDataSet2.getLastVisit();
        userDataImpl.putSessionIdAndChatWS(sessionId2, chatWS1);
        Assert.assertFalse(oldVisitTime == userDataSet2.getLastVisit());

        oldVisitTime = userDataSet3.getLastVisit();
        userDataImpl.putSessionIdAndChatWS(sessionId3, chatWS2);
        Assert.assertFalse(oldVisitTime == userDataSet3.getLastVisit());
    }


    @AfterMethod
    public void tearDown() throws Exception {

    }



    @Test
    public void testCheckServerTime() throws Exception {

    }

    @Test
    public void testGetStartServerTime() throws Exception {

    }

    @Test
    public void testGetUserSessionBySessionId() throws Exception {

    }

    @Test
    public void testContainsSessionId() throws Exception {

    }

    @Test
    public void testCcu() throws Exception {

    }

    @Test
    public void testPutSessionIdAndUserSession() throws Exception {

    }

    @Test
    public void testGetLogInUserBySessionId() throws Exception {

    }

    @Test
    public void testPlayerWantToPlay() throws Exception {

    }

    @Test
    public void testPutLogInUser() throws Exception {

    }

    @Test
    public void testPutSessionIdAndWS() throws Exception {

    }

    @Test
    public void testGetWSBySessionId() throws Exception {

    }

    @Test
    public void testGetChatWSBySessionId() throws Exception {

    }

    @Test
    public void testUpdateUserId() throws Exception {

    }

    @Test
    public void testPartyEnd() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

    }
}
