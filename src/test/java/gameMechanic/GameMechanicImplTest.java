package gameMechanic;

import base.GameMechanic;
import base.MessageSystem;
import dbService.DBServiceImpl;
import dbService.UserDataSet;
import junit.framework.Assert;
import messageSystem.MessageSystemImpl;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Caster;

import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

/**
 * Created by phil on 07.04.14.
 */
public class GameMechanicImplTest {
    private Map<String, UserDataSet> wantToPlay = new HashMap<String, UserDataSet>();
    GameMechanicImpl game;
    @BeforeMethod
    public void setUp() throws Exception {
        MessageSystem msg = new MessageSystemImpl();
        game = new GameMechanicImpl(msg);
    }
    @AfterMethod
    public void teardown() throws Exception {

    }

    @Test
    public void testRemoveRepeatUsers() throws Exception {
        UserDataSet user = new UserDataSet(1,"NICK",1,1,1);
        Map<String, UserDataSet> hashmap = new HashMap<String, UserDataSet>();
        hashmap.put("str1", user);
        //1
        game.removeRepeatUsers_pub(hashmap);
        Assert.assertEquals(1, hashmap.size());
        //2
        game.fillWantToPlayFotRunTest();
        game.removeRepeatUsers_pub(hashmap);
        Assert.assertEquals(2, hashmap.size());
    }

    @Test
    public void testRemoveAlreadyInGameUsers() throws Exception {

        Map<String, UserDataSet> hashmap = new HashMap<String, UserDataSet>();
        game.removeAlreadyInGameUsers_pub(hashmap);
        Assert.assertEquals(0,hashmap.size());
        UserDataSet user = new UserDataSet(1,"NICK",1,1,1);
        hashmap.put("1", user);
        hashmap.put("2",new UserDataSet(2,"NICK_1",1,1,1));
        MessageSystem msg = new MessageSystemImpl();
        msg.addService();
       // game.removeAlreadyInGameUsers_pub(hashmap);
      //  Assert.assertEquals(2,hashmap.size());
        game.filluserIdToSessionRunTest();
      //  game.removeAlreadyInGameUsers_pub(hashmap);
        Assert.assertEquals(0, hashmap.size());
    }

    @Test
    public void testCreateGames() throws Exception {

    }

    @Test
    public void testCheckStroke() throws Exception {

    }

    @Test
    public void testRemoveDeadGames_pub() throws Exception {

    }
}
