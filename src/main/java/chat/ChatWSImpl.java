package chat;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dbService.UserDataSet;

import frontend.UserDataImpl;

public class ChatWSImpl  extends WebSocketAdapter{
	public ChatWSImpl(){
	}

	public void onWebSocketText(JSONObject json) {
       /* if (isNotConnected()) {
			return; 
		} */
        String message = json.toString();
		String sessionId=null,startServerTime=null;
		String text=null;
		try{
			sessionId=json.get("sessionId").toString();
			startServerTime=json.get("startServerTime").toString();
            text = json.get("text").toString();
		}
		catch (Exception ignor){
		}
		if((sessionId!=null)&&(startServerTime!=null)&&(text!=null)&&(!text.equals(""))&&(UserDataImpl.checkServerTime(startServerTime))){
            addMessageToChat(sessionId, text);
		}
		else if((sessionId!=null)&&(startServerTime!=null)&&(UserDataImpl.checkServerTime(startServerTime))){
            addNewChater(sessionId);
		}
    }

	private void addNewChater(String sessionId){
		UserDataImpl.putSessionIdAndChatWS(sessionId, this);
	}
	
	public void addMessageToChat(String sessionId, String text){
		UserDataSet user = UserDataImpl.getLogInUserBySessionId(sessionId);
		if(user!=null){
            GameChatImpl.sendMessage(sessionId, text);
		}
	}
	
	public static void sendMessage(String sessionId, String message){
		try{
			UserDataImpl.getChatWSBySessionId(sessionId).sendString(message);
		}
		catch(Exception ignor){
		}
	}
}