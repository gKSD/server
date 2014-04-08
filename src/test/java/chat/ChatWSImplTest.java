package chat;

import base.Abonent;
import base.Address;
import base.MessageSystem;
import base.Msg;
import dbService.UserDataSet;
import frontend.UserDataImpl;
import messageSystem.MessageSystemImpl;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by step on 07.04.14.
 */
public class ChatWSImplTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void chatTest() {
        JSONObject testJson = new JSONObject();
        testJson.put("sessionId","12345");
        testJson.put("startServerTime","14:09:12");
        testJson.put("text","yes");
        MessageSystem msg;
        msg = new MessageSystemImpl();
        UserDataImpl objForTest = new UserDataImpl(msg);
        UserDataSet dataSetForTest = new UserDataSet(1,"John",30,15,85);
        UserDataSet dataSetForTest1 = new UserDataSet(2,"Douglas",17,60,40);
        objForTest.putLogInUser("12345",dataSetForTest);
        objForTest.putLogInUser("123456",dataSetForTest1);
        GameChatImpl gmObj = new GameChatImpl(msg);
        gmObj.createChat("12345", "123456");
        objForTest.startServerTime = "14:09:12";
        ChatWSImpl testObj = new ChatWSImpl();
        testObj.onWebSocketText(testJson.toString());
        String testRes = gmObj.sessionIdToChat.get("12345").get(0).getMessage();
        Assert.assertEquals(testRes, "yes");

        JSONObject testJson1 = new JSONObject();
        testJson1.put("sessionId","123456");
        testJson1.put("startServerTime","14:09:12");
        testJson1.put("text","");
        testObj.onWebSocketText(testJson1.toString());
        boolean testResBool = false;
        if (objForTest.sessionIdToChatWS.get("123456").toString()!=null) {
            testResBool = true;
        }
        Assert.assertTrue(testResBool);

        //проверка, что нормально компилиться при некорректных вызовах
        JSONObject testJson2 = new JSONObject();
        testJson2.put("sessionId","1234567");
        testJson2.put("startServerTime","14:19:12");
        testJson2.put("text","check");
        testObj.onWebSocketText(testJson2.toString());

        JSONObject testJson3 = new JSONObject();
        testJson3.put("sessionId","1234567");
        testJson3.put("startServerTime","14:19:12");
        testObj.onWebSocketText(testJson3.toString());

        JSONObject testJson4 = new JSONObject();
        testJson4.put("sessionId","1234567");
        testJson4.put("text","");
        testObj.onWebSocketText(testJson4.toString());

        JSONObject testJson5 = new JSONObject();
        testJson4.put("text","");
        testObj.onWebSocketText(testJson5.toString());

        //для addMessageChat
        JSONObject testJson6 = new JSONObject();
        testJson6.put("sessionId","0");
        testJson6.put("startServerTime","14:09:12");
        testJson6.put("text","check");
        testObj.onWebSocketText(testJson2.toString());
    }

    //просто для покрытия нулевого ифа
    @Test
    public void sendMessTest() {
        MessageSystem msg;
        msg = new MessageSystemImpl();
        GameChatImpl testObj = new GameChatImpl(msg);
        UserDataImpl objForTest = new UserDataImpl(msg);
        UserDataSet dataSetForTest = new UserDataSet(100,"Empty",0,0,0);
        objForTest.putLogInUser("999",dataSetForTest);
        testObj.sendMessage("999", "No text");

        ChatWSImpl one = new ChatWSImpl();
        one.addMessageToChat("000","dd");
     }
    @AfterMethod
    public void tearDown() throws Exception {

    }
}
