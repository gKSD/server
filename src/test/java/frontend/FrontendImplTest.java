package frontend;

import base.MessageSystem;
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
        FrontendImpl.status resStatus = frontend.getStatusTest(httpServletRequest, target, status, sessionId);
        Assert.assertEquals(resStatus, FrontendImpl.status.haveCookieAndPost, "Status is wrong, expected haveCookieAndPost");

        /*private status getStatus(HttpServletRequest request,String target,status stat,String sessionId){
            if((stat.equals(status.haveCookie))&&(request.getMethod().equals("POST")))
                stat=status.haveCookieAndPost;
            if((stat.equals(status.haveCookie))&&(UserDataImpl.getUserSessionBySessionId(sessionId).getId()!=0))
                stat=status.ready;
            if (target.equals(WAIT_URL)){
                if((!stat.equals(status.haveCookie)&&(!stat.equals(status.haveCookieAndPost)))
                        ||(UserDataImpl.getUserSessionBySessionId(sessionId).getPostStatus()==0))
                    stat=status.nothing;
                else
                    stat=status.waiting;
            }
            return stat;
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
