package dbService;

import base.MessageSystem;
import base.UserData;
import junit.framework.Assert;
import messageSystem.MessageSystemImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Vector;

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
    public void testupdateUsers() throws Exception {
        UserDataSet userdata = new UserDataSet(2,"Login",1,1,1);
        db.addUDS("Third_Login", psw);
        UserDataSet userdata_valid = new UserDataSet(2,"Third_Login",1,1,1);
        List<UserDataSet> users = new Vector<UserDataSet>();
        users.add(userdata);
        db.updateUsers(users);
        //1
        Assert.assertEquals(null,db.getUDS("Login",psw));
        //2
        Assert.assertEquals("Third_Login",db.getUDS("Third_Login",psw).getNick());
        db.delUDS("Third_Login");
        //3
        users.clear();
        db.updateUsers(users);
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
