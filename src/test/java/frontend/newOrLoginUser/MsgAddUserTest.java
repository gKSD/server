package frontend.newOrLoginUser;

import base.Address;
import base.DataAccessObject;
import base.MessageSystem;
import base.Msg;
import dbService.UserDataSet;
import org.mockito.ArgumentCaptor;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * Created by sofia on 08.04.14.
 */
public class MsgAddUserTest {
    private DataAccessObject dataAccessObject;
    private String login;
    private String password;
    private String sessionId;

    @BeforeMethod
    public void setUp() throws Exception {

        login = new String("Vasiliy");
        password = new String("123456");
        sessionId = new String("14fdwt524rfa");

        dataAccessObject = mock(DataAccessObject.class);
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testExec1() throws Exception {
        Address addressTo = mock(Address.class);
        Address addressFrom = mock(Address.class);
        Address dbAddress = mock(Address.class);
        MsgAddUser msgAddUser = new MsgAddUser(addressFrom , addressTo, sessionId, login, password);

        MessageSystem messageSystem = mock(MessageSystem.class);
        when(dataAccessObject.getMessageSystem()).thenReturn(messageSystem);
        when(dataAccessObject.getAddress()).thenReturn(dbAddress);


        when(dataAccessObject.addUDS(login, password)).thenReturn(true);
        UserDataSet uds0 = new UserDataSet(1, login, 5,6,7);
        when(dataAccessObject.getUDS(login, password)).thenReturn(uds0);
        msgAddUser.exec(dataAccessObject);

        ArgumentCaptor<Address> captorAddress = ArgumentCaptor.forClass(Address.class);
        ArgumentCaptor<Msg> captorMsg = ArgumentCaptor.forClass(Msg.class);
        verify(messageSystem, times(1)).putMsg(captorAddress.capture(), captorMsg.capture());

        Address addressCaptr = captorAddress.getValue();
        Msg message = captorMsg.getValue();
        Assert.assertNotNull(addressCaptr);
        Assert.assertNotNull(message);

        UserDataSet uds = ((MsgUpdateUser)message).getUDS_ForTest();
        Assert.assertEquals(uds.getNick(), login);
    }

    @Test
    public void testExec2() throws Exception {
        Address addressTo = mock(Address.class);
        Address addressFrom = mock(Address.class);
        Address dbAddress = mock(Address.class);
        MsgAddUser msgAddUser = new MsgAddUser(addressFrom , addressTo, sessionId, login, password);

        MessageSystem messageSystem = mock(MessageSystem.class);
        when(dataAccessObject.getMessageSystem()).thenReturn(messageSystem);
        when(dataAccessObject.getAddress()).thenReturn(dbAddress);


        when(dataAccessObject.addUDS(login, password)).thenReturn(false);
        //UserDataSet uds0 = new UserDataSet(1, login, 5,6,7);
        //when(dataAccessObject.getUDS(login, password)).thenReturn(uds0);

        msgAddUser.exec(dataAccessObject);

        ArgumentCaptor<Address> captorAddress = ArgumentCaptor.forClass(Address.class);
        ArgumentCaptor<Msg> captorMsg = ArgumentCaptor.forClass(Msg.class);
        verify(messageSystem, times(1)).putMsg(captorAddress.capture(), captorMsg.capture());

        Address addressCaptr = captorAddress.getValue();
        Msg message = captorMsg.getValue();
        Assert.assertNotNull(addressCaptr);
        Assert.assertNotNull(message);

        Assert.assertNull(((MsgUpdateUser)message).getUDS_ForTest());
    }
}
