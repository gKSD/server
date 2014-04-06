package gameMechanic.gameCreating;

import base.GameMechanic;
import base.MessageSystem;
import gameClasses.Snapshot;
import gameClasses.Stroke;
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
    }
}
