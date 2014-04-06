package gameMechanic.gameCreating;

import base.Address;
import base.GameChat;
import messageSystem.MsgToGameChat;

public class MsgCreateChat extends MsgToGameChat{
	final private String sessionId1,sessionId2;
	
	public MsgCreateChat(Address from, Address to, String sessionId1, String sessionId2){
		super(from,to);
		this.sessionId1=sessionId1;
		this.sessionId2=sessionId2;
	}
	
	public void exec(GameChat gameChat){
		gameChat.createChat(sessionId1, sessionId2);
	}

    String get_sessionId1() {
        return sessionId1;
    }

    String get_sessionId2() {
        return sessionId2;
    }
}
