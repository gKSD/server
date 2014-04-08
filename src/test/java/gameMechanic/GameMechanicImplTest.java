package gameMechanic;

import base.Address;
import base.GameMechanic;
import base.MessageSystem;
import dbService.DBServiceImpl;
import dbService.UserDataSet;
import gameClasses.Stroke;
import junit.framework.Assert;
import messageSystem.MessageSystemImpl;
import org.mockito.ArgumentCaptor;
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
    private GameMechanicImpl game;
    private MessageSystem msg;
    @BeforeMethod
    public void setUp() throws Exception {
        msg = mock(MessageSystem.class);
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
        //1
        Map<String, UserDataSet> hashmap = new HashMap<String, UserDataSet>();
        game.removeAlreadyInGameUsers_pub(hashmap);
        Assert.assertEquals(0,hashmap.size());
        UserDataSet user = new UserDataSet(1,"NICK",1,1,1);
        //2
        hashmap.put("1", user);
        hashmap.put("2",new UserDataSet(2,"NICK_1",1,1,1));
        Assert.assertEquals(2,hashmap.size());
        game.filluserIdToSessionRunTest();
        Address address = mock(Address.class);
        when(msg.getAddressByName("WebSocket")).thenReturn(address);
        //3
        game.removeAlreadyInGameUsers_pub(hashmap);
        Assert.assertEquals(0,hashmap.size());
        game.filluserIdToSessionRunTest();
        //4
       // game.removeAlreadyInGameUsers_pub(hashmap);
       // Assert.assertEquals(0, hashmap.size());
//        Assert.assertEquals(0, hashmap.size());
    }

    @Test
    public void testCreateGames() throws Exception {
        Map<String,UserDataSet> users = new HashMap<String, UserDataSet>();
        Assert.assertEquals(0,game.createGames(users).size());
        users.put("key1", new UserDataSet(1, "User", 1, 1, 1));
        game.createGames(users);
        users.put("key2", new UserDataSet(2, "User2", 2, 2, 2));
        Address address = mock(Address.class);
        when(msg.getAddressByName("GameChat")).thenReturn(address);
        Map<String, String> crgame = game.createGames(users);
        Assert.assertEquals(2,crgame.size());
    }

    @Test
    public void testCheckStroke() throws Exception {
        Stroke stroke = new Stroke(1,4,8,3,"king");
        Map<Integer,Stroke> resp;
        resp = game.checkStroke(1,stroke);
        Assert.assertEquals(0,resp.size());
        game.filluserIdToSessionRunTest();
        resp = game.checkStroke(1,stroke);
       // ArgumentCaptor<Address> captorAddress = ArgumentCaptor.forClass(Address.class);
        GameSession gameSession = mock(GameSession.class);
        when(gameSession.checkStroke(1,stroke.getFrom_X(),stroke.getFrom_Y(),stroke.getTo_X(),stroke.getTo_Y())).thenReturn(true);
        resp = game.checkStroke(1,stroke);
        Assert.assertEquals(1,resp.size());

        stroke.setStatus("lose");
        when(gameSession.getWinnerId()).thenReturn(1);
        resp = game.checkStroke(1,stroke);
        Assert.assertEquals(0,resp.size());


    }

    @Test
    public void testRemoveDeadGames_pub() throws Exception {
        ArgumentCaptor<Object> captor =  ArgumentCaptor.forClass(Object.class);
        Map<Integer,GameSession> userIdToSession = mock(HashMap.class);
       // verify(userIdToSession, times(1)).get(captor);
       when(userIdToSession.get(1)).thenReturn(null);

        game.filluserIdToSessionRunTest();
        game.removeDeadGames_pub();

    }
}
