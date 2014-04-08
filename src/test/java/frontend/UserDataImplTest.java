package frontend;

import base.MessageSystem;
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
        int id1 = 1;
        String sessionId1 = "113123";
        UserDataSet userDataSet1 = mock(UserDataSet.class);
        when(userDataSet1.getId()).thenReturn(id1);
        userDataImpl.putLogInUser(sessionId1, userDataSet1);

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

        //when(userDataSet.getNick()).thenReturn(nick);
        //when(userDataSet.getRating()).thenReturn(rating);
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
    public void testPutSessionIdAndChatWS() throws Exception {

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
