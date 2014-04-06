package frontend;

import base.MessageSystem;
import dbService.UserDataSet;
import javafx.animation.Animation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        Assert.assertEquals(resStatus, FrontendImpl.status.haveCookieAndPost, "Status is wrong, expected haveCookieAndPost");

        //test2
        sessionId = "123456";
        UserDataImpl userData = mock(UserDataImpl.class);
        UserDataSet userDataSet = mock(UserDataSet.class);
        int id = 123;
        when(userData.getUserSessionBySessionId(sessionId)).thenReturn(userDataSet);
        when(userDataSet.getId()).thenReturn(id);
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, FrontendImpl.status.ready, "Status is wrong, expected ready");


        //test3
        target = frontend.WAIT_URL;
        status = FrontendImpl.status.ready;
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, FrontendImpl.status.nothing, "Status is wrong, expected nothing");

        //test4
        status = FrontendImpl.status.nothing;
        when(userDataSet.getPostStatus()).thenReturn(0);
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, FrontendImpl.status.nothing, "Status is wrong, expected nothing");

        //test5
        when(userDataSet.getPostStatus()).thenReturn(1);
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, FrontendImpl.status.waiting, "Status is wrong, expected waiting");

        //test6
        status = FrontendImpl.status.nothing;
        target = frontend.ADMIN_URL;
        resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, status, "Status is wrong, expected the as status");
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
