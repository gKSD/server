package dbService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by phil on 07.04.14.
 */
public class TExecutorTest  {

    private Connection connect;
    private String Login = "Some_Login";
    private String Psw = "Psw";

    @Test
    public void testFindUser() throws Exception {

        Integer cout = TExecutor.findUser(connect, Login);
        Assert.assertEquals(1, cout.intValue());
        cout = TExecutor.findUser(connect, null);
        Assert.assertEquals(0, cout.intValue());
    }


    @BeforeMethod
    public void setUp() throws Exception {
        Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
        DriverManager.registerDriver(driver);
        String url="jdbc:mysql://localhost:3306/checkers?user=checkers&password=fuckingpassword";
        connect = DriverManager.getConnection(url);
        TExecutor.addUser (connect, Login, Psw);
    }
    @AfterMethod
    public void teardown() throws Exception {
        TExecutor.delUser(connect, Login);
        connect.close();
    }
}