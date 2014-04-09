package frontend;

import base.MessageSystem;
import chat.ChatWSImpl;
import dbService.UserDataSet;
import org.mockito.ArgumentCaptor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resource.TimeSettings;
import utils.TimeHelper;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

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


    @Test
    public void testGetWSBySessionId() throws Exception {
        String sessionId1 = "12313132";
        WebSocketImpl webSocket1 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session1 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint1 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket1.getSession()).thenReturn(session1);
        when(session1.getRemote()).thenReturn(remoteEndpoint1);

        String sessionId2 = "12313132";
        WebSocketImpl webSocket2 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session2 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint2 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket2.getSession()).thenReturn(session2);
        when(session2.getRemote()).thenReturn(remoteEndpoint2);

        int id1 = 1;
        UserDataSet userDataSet1 = new UserDataSet(id1, "Bob", 5, 6, 6);
        userDataSet1.setLastVisit_ForTest(123);
        int id2 = 2;
        UserDataSet userDataSet2 = new UserDataSet(id2, "Tom", 5, 2, 87);
        userDataSet2.setLastVisit_ForTest(345);
        userDataImpl.putLogInUser(sessionId1, userDataSet1);
        userDataImpl.putLogInUser(sessionId2, userDataSet2);

        String sessionId3 = "adsfw4"; // for null return

        userDataImpl.putSessionIdAndWS(sessionId1, webSocket1);
        userDataImpl.putSessionIdAndWS(sessionId2, webSocket2);
        userDataImpl.putSessionIdAndWS(sessionId3, null);

        Assert.assertNull(userDataImpl.getWSBySessionId(sessionId3));
        Assert.assertNotNull(userDataImpl.getWSBySessionId(sessionId1));
        Assert.assertNotNull(userDataImpl.getWSBySessionId(sessionId2));
        //Assert.assertTrue(userDataImpl.getWSBySessionId(sessionId2) == remoteEndpoint2);

    }

    @Test
    public void testGetChatWSBySessionId() throws Exception {
        String sessionId1 = "12313132";
        ChatWSImpl chatWS1 = mock(ChatWSImpl.class);
        org.eclipse.jetty.websocket.api.Session session1 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint1 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(chatWS1.getSession()).thenReturn(session1);
        when(session1.getRemote()).thenReturn(remoteEndpoint1);

        String sessionId2 = "sdfsdf";
        ChatWSImpl chatWS2 = mock(ChatWSImpl.class);
        org.eclipse.jetty.websocket.api.Session session2 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint2 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(chatWS2.getSession()).thenReturn(session2);
        when(session2.getRemote()).thenReturn(remoteEndpoint2);

        String sessionId3 = "adsfw4";

        userDataImpl.putSessionIdAndChatWS(sessionId1, chatWS1);
        userDataImpl.putSessionIdAndChatWS(sessionId2, chatWS2);
        userDataImpl.putSessionIdAndChatWS(sessionId3, null);

        Assert.assertNull(userDataImpl.getChatWSBySessionId(sessionId3));
        Assert.assertNotNull(userDataImpl.getChatWSBySessionId(sessionId1));
        Assert.assertNotNull(userDataImpl.getChatWSBySessionId(sessionId2));
    }

    @Test
    public void testGetOldUserSessionId() throws Exception {
        //test1
        Assert.assertEquals(userDataImpl.getOldUserSessionId_ForTest(13), null);

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

        Assert.assertEquals(userDataImpl.getOldUserSessionId_ForTest(id1), sessionId1);
        Assert.assertEquals(userDataImpl.getOldUserSessionId_ForTest(id2), sessionId2);
        Assert.assertEquals(userDataImpl.getOldUserSessionId_ForTest(id3), sessionId3);
    }

    @Test
    public void testUpdateUserId() throws Exception {

        int id1 = 1;
        String sessionId1 = "113123";
        UserDataSet userDataSet1 = new UserDataSet(id1, "Bob", 5, 6, 6);
        userDataSet1.setLastVisit_ForTest(123);

        int id2 = 2;
        String sessionId2 = "45634563";
        UserDataSet userDataSet2 = new UserDataSet(id2, "Tom", 5, 2, 87);
        userDataSet2.setLastVisit_ForTest(345);

        int id3 = 3;
        String sessionId3 = "sdfgsfg";
        UserDataSet userDataSet3 = new UserDataSet(id3, "Alex", 3, 8, 6);
        userDataSet3.setLastVisit_ForTest(456);

        userDataImpl.putSessionIdAndUserSession(sessionId1, userDataSet1);
        userDataImpl.putSessionIdAndUserSession(sessionId2, userDataSet2);
        userDataImpl.putSessionIdAndUserSession(sessionId3, userDataSet3);

        //test1
        userDataImpl.updateUserId(sessionId1, null);
        Assert.assertEquals(userDataSet1.getPostStatus(), 0);

        //test2
        //logInUsers is empty
        int id4 = 34;
        UserDataSet userDataSet4 = new UserDataSet(id4, "Max", 4, 2, 34);
        userDataSet3.setLastVisit_ForTest(345);
        userDataImpl.updateUserId(sessionId1, userDataSet4);
        Assert.assertEquals(userDataImpl.getUserSessionBySessionId(sessionId1).getNick(), "Max");
        Assert.assertEquals(userDataImpl.getUserSessionBySessionId(sessionId1).getId(), id4);
        Assert.assertEquals(userDataImpl.getUserSessionBySessionId(sessionId1).getRating(), 4);

        //test3
        //logInUsers has some
        String sessionId4 = "123adasd";
        userDataImpl.putLogInUser(sessionId4, userDataSet4);
        userDataImpl.updateUserId(sessionId3, userDataSet4);
        Assert.assertEquals(userDataImpl.getUserSessionBySessionId(sessionId3).getNick(), "Max");
        Assert.assertEquals(userDataImpl.getUserSessionBySessionId(sessionId3).getId(), id4);
        Assert.assertEquals(userDataImpl.getUserSessionBySessionId(sessionId3).getRating(), 4);
    }

    @Test
    public void testKeepAlive() throws Exception {
        String sessionId1 = "12313132";
        WebSocketImpl webSocket1 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session1 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint1 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket1.getSession()).thenReturn(session1);
        when(session1.getRemote()).thenReturn(remoteEndpoint1);

        String sessionId2 = "12313132";
        WebSocketImpl webSocket2 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session2 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint2 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket2.getSession()).thenReturn(session2);
        when(session2.getRemote()).thenReturn(remoteEndpoint2);

        int id1 = 1;
        UserDataSet userDataSet1 = new UserDataSet(id1, "Bob", 5, 6, 6);
        userDataSet1.setLastVisit_ForTest(123);
        int id2 = 2;
        UserDataSet userDataSet2 = new UserDataSet(id2, "Tom", 5, 2, 87);
        userDataSet2.setLastVisit_ForTest(345);
        userDataImpl.putLogInUser(sessionId1, userDataSet1);
        userDataImpl.putLogInUser(sessionId2, userDataSet2);

        String sessionId3 = "adsfw4"; // for null return

        userDataImpl.putSessionIdAndWS(sessionId1, webSocket1);
        userDataImpl.putSessionIdAndWS(sessionId2, webSocket2);
        userDataImpl.putSessionIdAndWS(sessionId3, null);

        //test1
        userDataImpl.keepAlive_For_test(sessionId3);

        //test2
        userDataSet1.setLastVisit_ForTest(123);
        userDataImpl.putLogInUser(sessionId1, userDataSet1);
        long oldVisitTime = userDataSet1.getLastVisit();

        userDataImpl.keepAlive_For_test(sessionId1);

        ArgumentCaptor<String> passCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(remoteEndpoint2, times(1)).sendString(passCodeCaptor.capture());
        Assert.assertTrue(passCodeCaptor.getValue().equals("1"));

        Assert.assertFalse(oldVisitTime == userDataSet1.getLastVisit());
    }

    @Test
     public void testCheckUsers() throws Exception {
        int keepAlive = 0;

        //test1
        userDataImpl.checkUsers_ForTest(keepAlive);

        //test2
        int id1 = 1;
        String sessionId1 = "113123";
        UserDataSet userDataSet1 = new UserDataSet(id1, "Bob", 5, 6, 6);
        userDataSet1.setLastVisit_ForTest(TimeHelper.getCurrentTime() - 100000);


        int id2 = 2;
        String sessionId2 = "45634563";
        UserDataSet userDataSet2 = new UserDataSet(id2, "Tom", 5, 2, 87);
        userDataSet2.setLastVisit_ForTest(TimeHelper.getCurrentTime() - 100);

        int id3 = 3;
        String sessionId3 = "sdfgsfg";
        UserDataSet userDataSet3 = new UserDataSet(id3, "Alex", 3, 8, 6);
        userDataSet3.setLastVisit_ForTest(TimeHelper.getCurrentTime() - 100);

        userDataImpl.putSessionIdAndUserSession(sessionId1, userDataSet1);
        userDataImpl.putSessionIdAndUserSession(sessionId2, userDataSet2);
        userDataImpl.putSessionIdAndUserSession(sessionId3, userDataSet3);

        userDataImpl.putLogInUser(sessionId1, userDataSet1);
        userDataImpl.putLogInUser(sessionId2, userDataSet2);
        userDataImpl.putLogInUser(sessionId3, userDataSet3);


        TimeSettings.setExitTime_ForTest(300);

        userDataImpl.checkUsers_ForTest(keepAlive);

        //Assert.assertTrue(true == false);

        Assert.assertNull(userDataImpl.getUserSessionBySessionId(sessionId1));
        Assert.assertNull(userDataImpl.getLogInUserBySessionId(sessionId1));

        //test3
        keepAlive = 1;

        WebSocketImpl webSocket1 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session1 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint1 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket1.getSession()).thenReturn(session1);
        when(session1.getRemote()).thenReturn(remoteEndpoint1);

        WebSocketImpl webSocket2 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session2 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint2 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket2.getSession()).thenReturn(session2);
        when(session2.getRemote()).thenReturn(remoteEndpoint2);

        WebSocketImpl webSocket3 = mock(WebSocketImpl.class);
        org.eclipse.jetty.websocket.api.Session session3 = mock(org.eclipse.jetty.websocket.api.Session.class);
        org.eclipse.jetty.websocket.api.RemoteEndpoint remoteEndpoint3 = mock(org.eclipse.jetty.websocket.api.RemoteEndpoint.class);
        when(webSocket3.getSession()).thenReturn(session3);
        when(session3.getRemote()).thenReturn(remoteEndpoint3);


        userDataImpl.putSessionIdAndWS(sessionId1, webSocket1);
        userDataImpl.putSessionIdAndWS(sessionId2, webSocket2);
        userDataImpl.putSessionIdAndWS(sessionId3, webSocket3);

        userDataSet3.setLastVisit_ForTest(TimeHelper.getCurrentTime() - 20);

        userDataImpl.checkUsers_ForTest(keepAlive);

        ArgumentCaptor<String> passCodeCaptor = ArgumentCaptor.forClass(String.class);
        verify(remoteEndpoint3, times(1)).sendString(passCodeCaptor.capture());
        Assert.assertTrue(passCodeCaptor.getValue().equals("1"));
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
