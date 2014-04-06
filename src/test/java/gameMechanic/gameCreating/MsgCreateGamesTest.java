package gameMechanic.gameCreating;

import base.GameMechanic;
import base.MessageSystem;
import gameClasses.Snapshot;
import gameClasses.Stroke;
import junit.framework.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import dbService.UserDataSet;
import base.Address;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phil on 06.04.14.
 */

public class MsgCreateGamesTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet_users() throws Exception {
    UserDataSet data = new UserDataSet(1, "Nick", 1, 1, 0);
        Map<String, UserDataSet> hashmap = new HashMap<String, UserDataSet>();
        hashmap.put("User", data);
        MsgCreateGames msg = new MsgCreateGames(new Address(),new Address(), hashmap);
        Assert.assertEquals(1, msg.get_users().get("User").getId());
        Assert.assertEquals("Nick",msg.get_users().get("User").getNick());
        Assert.assertEquals(1,msg.get_users().get("User").getRating());
        Assert.assertEquals(1,msg.get_users().get("User").getWinQuantity());
        Assert.assertEquals(0,msg.get_users().get("User").getLoseQuantity());
    }
}

