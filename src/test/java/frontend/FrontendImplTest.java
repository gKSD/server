package frontend;

import base.Address;
import base.MessageSystem;
import base.Msg;
import dbService.UserDataSet;
import org.mockito.ArgumentCaptor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sun.print.resources.serviceui_sv;
import utils.TemplateHelper;
import utils.TimeHelper;

import static org.mockito.Mockito.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sofia on 06.04.14.
 */

public class FrontendImplTest {

    private FrontendImpl frontend;

    //@Mock
    private HttpServletRequest httpServletRequest;

    //@Mock
    private HttpServletResponse httpServletResponse;

    private String target;
    private String sessionId;
    private MessageSystem messageSystem;

    @BeforeMethod
    public void setUp() throws Exception {

        target = new String();
        sessionId = new String();

        messageSystem = mock(MessageSystem.class);
        frontend = new FrontendImpl(messageSystem);

        httpServletRequest = mock(HttpServletRequest.class);
        httpServletResponse = mock(HttpServletResponse.class);

        //when(httpServletRequest.getCookies()).thenReturn(new Cookie[0]);
        //when(httpServletRequest.getMethod()).thenReturn("POST");

        Class<?> secretClass = frontend.getClass();
        Method methods[] = secretClass.getDeclaredMethods();
        //System.out.println("Access all the methods");
        for (Method method : methods) {
            //System.out.println("Method Name: " + method.getName());
            //System.out.println("Return type: " + method.getReturnType());
            method.setAccessible(true);
        }


        Field fields[] = secretClass.getDeclaredFields();
        //System.out.println("Access all the fields");
        for (Field field : fields) {
            //System.out.println("Field Name: " + field.getName());
            field.setAccessible(true);
            //System.out.println(field.get(frontend) + "\n");
        }

    }

    @Test
    public void testGetStatistic() throws Exception {
        //хз пока что
    }

    @Test
    public void testGetStatus() throws Exception {
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[0]);
        when(httpServletRequest.getMethod()).thenReturn("POST");

        //enum status {nothing,haveCookie,haveCookieAndPost,waiting,ready}
        FrontendImpl.status status = FrontendImpl.status.haveCookie;

        //test1
        FrontendImpl.status resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(FrontendImpl.status.haveCookieAndPost, resStatus, "Status is wrong, expected haveCookieAndPost");

        ///test2
        when(httpServletRequest.getMethod()).thenReturn("GET");
        sessionId = "123456";
        status = FrontendImpl.status.haveCookie;
        UserDataImpl userData = new UserDataImpl(messageSystem);
        UserDataSet userDataSet = new UserDataSet(1, "lalal", 5, 6, 6);
        userData.putSessionIdAndUserSession(sessionId, userDataSet);
        int id = 123;
        //when(userDataSet0.getId()).thenReturn(id);
        //when(bar.getFoo()).thenReturn(fooBar)
        //doReturn(fooBar).when(bar).getFoo()
        //when(userData.getUserSessionBySessionId(sessionId)).thenReturn(userDataSet0);
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals( FrontendImpl.status.ready,  resStatus, "Status is wrong, expected ready");



        UserDataSet userDataSet0 = mock(UserDataSet.class);

        //test3
        target = frontend.WAIT_URL;
        status = FrontendImpl.status.ready;
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals( FrontendImpl.status.nothing, resStatus, "Status is wrong, expected nothing");

        //test4
        status = FrontendImpl.status.nothing;
        when(userDataSet0.getPostStatus()).thenReturn(0);
        userDataSet.setPostStatus(0);
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals( FrontendImpl.status.nothing, resStatus, "Status is wrong, expected nothing");

        //test5
        status = FrontendImpl.status.haveCookieAndPost;
        when(userDataSet0.getPostStatus()).thenReturn(1);
        userDataSet.setPostStatus(1);
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, FrontendImpl.status.waiting,"Status is wrong, expected waiting");

        //test6
        status = FrontendImpl.status.nothing;
        target = frontend.ADMIN_URL;
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(status, resStatus, "Status is wrong, expected the as status");

    }

    @Test
    public void testInWeb(){

        boolean res;

        //test1
        target = frontend.ROOT_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test2
        target = frontend.WAIT_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test3
        target = frontend.GAME_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test4
        target = frontend.PROFILE_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test5
        target = frontend.ADMIN_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test6
        target = frontend.RULES_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test7
        target = frontend.LOGOUT_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test8
        target = frontend.REG_URL;
        res = frontend.inWebTest(target);
        Assert.assertEquals(true, res);

        //test9
        target = "asdasd";
        res = frontend.inWebTest(target);
        Assert.assertEquals(false, res);
    }

    @Test
    public void testIsStatic(){
        boolean res;

        //test1
        target = "/js";
        res = frontend.isStaticTest(target);
        Assert.assertEquals(false, res);

        //test2
        target = "/js/";
        res = frontend.isStaticTest(target);
        Assert.assertEquals(true, res);

        //test3
        target = "/img/";
        res = frontend.isStaticTest(target);
        Assert.assertEquals(true, res);

        //test4
        target = "/img/";
        res = frontend.isStaticTest(target);
        Assert.assertEquals(true, res);

        //test5
        target = "/lalal/";
        res = frontend.isStaticTest(target);
        Assert.assertEquals(false, res);

    }

    @Test
    public  void  testNewUser()
    {
        boolean res;
        String startServerTime = new String();

        //test1
        res = frontend.newUserTest(null, startServerTime);
            Assert.assertEquals(true, res);

        //test2
        sessionId = "aa";
        res = frontend.newUserTest(sessionId, null);
        Assert.assertEquals(true, res);

        //test3
        UserDataImpl userData = new UserDataImpl(messageSystem);
        startServerTime = "123";
        sessionId = "bb";
        //UserDataImpl userData = mock(UserDataImpl.class);
        //when(userData.checkServerTime(startServerTime)).thenReturn(false);
        res = frontend.newUserTest(sessionId, startServerTime);
        Assert.assertEquals(true, res);

        //test4
        startServerTime = "aa";
        sessionId = "bb";
        //when(userData.checkServerTime(startServerTime)).thenReturn(true);
        //when(userData.containsSessionId(sessionId)).thenReturn(false);
        res = frontend.newUserTest(sessionId, startServerTime);
        Assert.assertEquals(true, res);
    }

    @Test
    public void testSendPage_UserSessionExists()
    {
        TemplateHelper.init();

        String name = frontend.PROFILE_HTML;
        String nick = "Bob";
        int id = 123;
        int rating = 55;
        StringWriter stringWriter = new StringWriter();

        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(id);
        when(userDataSet.getNick()).thenReturn(nick);
        when(userDataSet.getRating()).thenReturn(rating);

        try
        {
            when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));
        }
        catch (Exception e)
        {

        }
        frontend.sendPageTest(name, userDataSet, httpServletResponse);

        System.out.println(stringWriter.toString());
        Assert.assertTrue(stringWriter.toString().contains("<title>Шашечки</title>"));
        Assert.assertTrue(stringWriter.toString().contains("Bob"));
        Assert.assertTrue(stringWriter.toString().contains("Rating: 55"));
    }
    @Test
    public void testSendPage_UserSessionIsNull()
    {
        TemplateHelper.init();

        String name = frontend.PROFILE_HTML;
        StringWriter stringWriter = new StringWriter();

        try
        {
            when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));
        }
        catch (Exception e)
        {

        }
        frontend.sendPageTest(name, null, httpServletResponse);

        System.out.println(stringWriter.toString());
        Assert.assertTrue(stringWriter.toString().contains("<title>Шашечки</title>"));
        Assert.assertTrue(stringWriter.toString().contains(frontend.SESSION_NULL__NICKNAME_FIELD_DATA));
        Assert.assertTrue(stringWriter.toString().contains(frontend.SESSION_NULL__RATING_FIELD_DATA));
    }


    @Test
    public void testOnNothingStatus_TargetIsRootUrl() throws Exception {

        String startServerTime = new String();
        int id= 123, rating = 55;
        String nick = "Bob";

        target = frontend.REG_URL;
        sessionId = "123356";
        startServerTime = "34567";

        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(id);
        when(userDataSet.getNick()).thenReturn(nick);
        when(userDataSet.getRating()).thenReturn(rating);


        frontend.onNothingStatusTest(target, sessionId, userDataSet, startServerTime, httpServletResponse);

        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> header1CodeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> header2CodeCaptor = ArgumentCaptor.forClass(String.class);

        verify(httpServletResponse, times(1)).setStatus(statusCodeCaptor.capture());
        Integer res = statusCodeCaptor.getValue();
        Assert.assertTrue(res == HttpServletResponse.SC_MOVED_TEMPORARILY);

        verify(httpServletResponse, times(1)).addHeader(header1CodeCaptor.capture(), header2CodeCaptor.capture());
        String header1 = header1CodeCaptor.getValue();
        String header2 = header2CodeCaptor.getValue();

        Assert.assertEquals("Location", header1);
        Assert.assertEquals(frontend.ROOT_URL,header2);


        ArgumentCaptor<Cookie> cookieArgumentCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(httpServletResponse, times(2)).addCookie(cookieArgumentCaptor.capture());
        Assert.assertEquals(startServerTime,cookieArgumentCaptor.getValue().getValue());
    }
    @Test
    public void testOnNothingStatus_TargetIsNotRootUrl() throws Exception {

        TemplateHelper.init();

        String startServerTime = new String();
        int id= 123, rating = 55;
        String nick = "Bob";

        target = frontend.ROOT_URL;
        sessionId = "123356";
        startServerTime = "34567";
        StringWriter stringWriter = new StringWriter();

        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(id);
        when(userDataSet.getNick()).thenReturn(nick);
        when(userDataSet.getRating()).thenReturn(rating);


        try
        {
            when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));
        }
        catch (Exception e)
        {

        }

        frontend.onNothingStatusTest(target, sessionId, userDataSet, startServerTime, httpServletResponse);

        ArgumentCaptor<Cookie> cookieArgumentCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(httpServletResponse, times(2)).addCookie(cookieArgumentCaptor.capture());
        Assert.assertEquals(startServerTime,cookieArgumentCaptor.getValue().getValue());

        System.out.println(stringWriter.toString());
        Assert.assertTrue(stringWriter.toString().contains("<title>Шашечки</title>"));
        Assert.assertTrue(stringWriter.toString().contains(nick));
    }


    @Test
    public void testOnHaveCookieStatus_ROOT_URL() throws  Exception{

        TemplateHelper.init();

        int id= 123, rating = 55;
        String nick = "Bob";

        target = frontend.ROOT_URL;
        StringWriter stringWriter = new StringWriter();

        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(id);
        when(userDataSet.getNick()).thenReturn(nick);
        when(userDataSet.getRating()).thenReturn(rating);

        try
        {
            when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));
        }
        catch (Exception e)
        {

        }

        frontend.onHaveCookieStatusTest(target, userDataSet, httpServletResponse);
        System.out.println(stringWriter.toString());
        Assert.assertTrue(stringWriter.toString().contains("<title>Шашечки</title>"));
        Assert.assertTrue(stringWriter.toString().contains(nick));

        Assert.assertTrue(stringWriter.toString().contains("id=\"carousel\""));
        Assert.assertTrue(stringWriter.toString().contains("<div class=\"carousel-caption\">"));
    }

    @Test
    public void testOnHaveCookieStatus_REG_URL() throws  Exception{


        TemplateHelper.init();

        int id= 123, rating = 55;
        String nick = "Bob";

        target = frontend.REG_URL;
        StringWriter stringWriter = new StringWriter();

        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(id);
        when(userDataSet.getNick()).thenReturn(nick);
        when(userDataSet.getRating()).thenReturn(rating);

        try
        {
            when(httpServletResponse.getWriter()).thenReturn(new PrintWriter(stringWriter));
        }
        catch (Exception e)
        {

        }

        frontend.onHaveCookieStatusTest(target, userDataSet, httpServletResponse);
        Assert.assertTrue(stringWriter.toString().contains("<title>Шашечки</title>"));
        Assert.assertTrue(stringWriter.toString().contains(frontend.REG_NICKNAME_FIELD_HTML));
        Assert.assertTrue(stringWriter.toString().contains(frontend.REG_PASSWORD_FIELD_HTML));

        /*private void onHaveCookieStatus(String target, UserDataSet userSession, HttpServletResponse response){
            if (target.equals(ROOT_URL)){
                sendPage(INDEX_HTML,userSession,response);
            }
            else if (target.equals(REG_URL)){
                sendPage(REG_HTML,userSession,response);
            }
            else{
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.addHeader("Location", ROOT_URL);
            }
        }*/
    }
    @Test
    public void testOnHaveCookieStatus_DEFAULT() throws  Exception{

        int id= 123, rating = 55;
        String nick = "Bob";

        target = frontend.PROFILE_URL;
        StringWriter stringWriter = new StringWriter();

        UserDataSet userDataSet = mock(UserDataSet.class);
        when(userDataSet.getId()).thenReturn(id);
        when(userDataSet.getNick()).thenReturn(nick);
        when(userDataSet.getRating()).thenReturn(rating);

        frontend.onHaveCookieStatusTest(target, userDataSet, httpServletResponse);

        ArgumentCaptor<Integer> statusCodeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> header1CodeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> header2CodeCaptor = ArgumentCaptor.forClass(String.class);

        verify(httpServletResponse, times(1)).setStatus(statusCodeCaptor.capture());
        Integer res = statusCodeCaptor.getValue();
        Assert.assertTrue(res == HttpServletResponse.SC_MOVED_TEMPORARILY);

        verify(httpServletResponse, times(1)).addHeader(header1CodeCaptor.capture(), header2CodeCaptor.capture());
        String header1 = header1CodeCaptor.getValue();
        String header2 = header2CodeCaptor.getValue();

        Assert.assertEquals("Location", header1);
        Assert.assertEquals(frontend.ROOT_URL,header2);

    }

    @Test
    public void testGetAddress() throws Exception {

    }

    @Test
    public void testHandle() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
}
