package accountService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import utils.SysInfo;
import utils.TimeHelper;

import base.AccountService;
import base.Address;
import base.MessageSystem;


public class AccountServiceImpl implements AccountService{
	private AtomicInteger creatorId=new AtomicInteger();
	final public MessageSystem messageSystem;
	public Map<String,Integer> nickToId;
	final private Address address;

	public AccountServiceImpl(MessageSystem msgSystem){
		nickToId=new HashMap<String,Integer>();
		address=new Address();
		messageSystem=msgSystem;
		messageSystem.addService(this, "AccountService");
	}

	public Address getAddress() {
		return address;
	}

	public int getUserId(String nick, String password) {
		int id;
		if (nickToId.get(nick)==null)
		{
			id=creatorId.incrementAndGet();
			nickToId.put(nick, id);
		}
		else
			id=nickToId.get(nick);
		return id;
	}

	public MessageSystem getMessageSystem(){
		return messageSystem;
	}

	public void run() {
		while(true){
			messageSystem.execForAbonent(this);
			TimeHelper.sleep(200);
		}
	}
}