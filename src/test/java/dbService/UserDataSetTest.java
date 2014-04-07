package dbService;

import base.MessageSystem;
import frontend.FrontendImpl;
import frontend.UserDataImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import sun.plugin2.util.NativeLibLoader;
import utils.TimeHelper;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;
/**
 * Created by phil on 06.04.14.
 */
public class UserDataSetTest {
    UserDataSet userdata;
//int id, String nick, int rating, int winQuantity, int loseQuantity
    @BeforeMethod
    public void setUp() throws Exception {
        userdata = new UserDataSet(101,"Some_Nick",1,21,13);
    }

    @Test
    public void testMakeLike() throws Exception {
        UserDataSet uds = new UserDataSet(1,"Another_Nick",2,12,31);
        userdata.makeLike(uds);
        Assert.assertEquals(1,userdata.getId());
        Assert.assertEquals("Another_Nick",userdata.getNick());
        Assert.assertEquals(2,userdata.getRating());
        Assert.assertEquals(12,userdata.getWinQuantity());
        Assert.assertEquals(31,userdata.getLoseQuantity());
    }

    @Test
    public void testGetNick() throws Exception {
        Assert.assertEquals("Some_Nick", userdata.getNick());
    }

    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals(101, userdata.getId());
    }

    @Test
    public void testVisit() throws Exception {

    }

    @Test
    public void testGetLastVisit() throws Exception {
        Assert.assertEquals(TimeHelper.getCurrentTime(), userdata.getLastVisit());
    }

    @Test
    public void testSetPostStatus() throws Exception {
        userdata.setPostStatus(15);
        Assert.assertEquals(15, userdata.getPostStatus());
    }

    @Test
    public void testGetPostStatus() throws Exception {
        Assert.assertEquals(0, userdata.getPostStatus());
    }

    @Test
    public void testSetColor() throws Exception {
        userdata.setColor("Black");
        Assert.assertEquals("Black", userdata.getColor());
    }

    @Test
    public void testGetColor() throws Exception {
        Assert.assertEquals(null, userdata.getColor());
    }

    @Test
    public void testGetRating() throws Exception {
        Assert.assertEquals(1, userdata.getRating());
    }

    @Test
    public void testGetWinQuantity() throws Exception {
        Assert.assertEquals(21, userdata.getWinQuantity());
    }

    @Test
    public void testGetLoseQuantity() throws Exception {
        Assert.assertEquals(13, userdata.getLoseQuantity());
    }

    @Test
    public void testLose() throws Exception {
        userdata.lose(5);
        Assert.assertEquals(14,userdata.getLoseQuantity());
        Assert.assertEquals(-4,userdata.getRating());

    }

    @Test
    public void testWin() throws Exception {
        userdata.win(5);
        Assert.assertEquals(22,userdata.getWinQuantity());
        Assert.assertEquals(6,userdata.getRating());
    }
}
