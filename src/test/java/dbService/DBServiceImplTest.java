package dbService;

import base.MessageSystem;
import base.UserData;
import junit.framework.Assert;
import messageSystem.MessageSystemImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by phil on 06.04.14.
 */
public class DBServiceImplTest {
    DBServiceImpl db;
    public String login;
    public String psw;
    @BeforeMethod
    public void setUp() throws Exception {
        db = new DBServiceImpl(new MessageSystemImpl());
        db.Connect();
        login = new String("Some_Login");
        psw = new String("Some_psw");
        db.addUDS(login,psw);
    }
    @AfterMethod
    public void teardown() throws Exception {
        db.delUDS(login);
    }


    @Test
    public void testGetUDS() throws Exception {
        //1
        UserDataSet userdata = db.getUDS(login, psw);
        Assert.assertEquals("Some_Login", userdata.getNick());
        //2
        userdata = db.getUDS("Another_User","Another_psw");
        Assert.assertEquals(null, userdata);
    }

    @Test
    public void testAddUDS() throws Exception {
        //1
        boolean bool = db.addUDS("Second_Login","Some_psw");
        Assert.assertEquals(true,bool);
        //2
        bool = db.addUDS("Second_Login","Some_psw");
        Assert.assertEquals(false,bool);
        db.delUDS("Second_Login");
    }


}
