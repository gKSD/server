package frontend;

import base.MessageSystem;
import dbService.UserDataSet;
import javafx.animation.Animation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    public void testSendPage()
    {
        String name = frontend.PROFILE_HTML;
        String nick = "Nick";
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
        Assert.assertEquals(stringWriter.toString(), "apple");
        //Assert.assertEquals( FrontendImpl.status.ready,  resStatus, "Status is wrong, expected ready");

        /*private void sendPage(String name, UserDataSet userSession, HttpServletResponse response){
        try {
            Map<String, String> data = new HashMap<String, String>();
            data.put(PAGE_PARAMETR, name);
            if(userSession!=null){
                data.put(ID_FIELD_HTML, String.valueOf(userSession.getId()));
                data.put(NICKNAME_FIELD_HTML, String.valueOf(userSession.getNick()));
                data.put(RATING_FIELD_HTML, String.valueOf(userSession.getRating()));
            }
            else{
                data.put(ID_FIELD_HTML, SESSION_NULL__ID_FIELD_DATA);
                data.put(NICKNAME_FIELD_HTML, SESSION_NULL__NICKNAME_FIELD_DATA);
                data.put(RATING_FIELD_HTML, SESSION_NULL__RATING_FIELD_DATA);
            }
            TemplateHelper.renderTemplate(TEMPLATE_HTML, data, response.getWriter());
        }
        catch (IOException ignor) {
        }*/
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
