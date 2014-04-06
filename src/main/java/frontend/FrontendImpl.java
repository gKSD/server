package frontend;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import frontend.newOrLoginUser.*;

import utils.CookieDescriptor;
import utils.SHA2;
import utils.SysInfo;
import utils.TemplateHelper;
import utils.TimeHelper;

import base.Address;
import base.Frontend;
import base.MessageSystem;
import dbService.UserDataSet;


public class FrontendImpl extends AbstractHandler implements Frontend{
	private AtomicInteger creatorSessionId=new AtomicInteger();
	final private Address address;
	final private MessageSystem messageSystem;
	enum status {nothing,haveCookie,haveCookieAndPost,waiting,ready}

    final public String ADMIN_URL = "/admin";
    final public String RULES_URL = "/rules";
    final public String WAIT_URL = "/wait";
    final public String GAME_URL = "/game";
    final public String PROFILE_URL = "/profile";
    final public String LOGOUT_URL = "/logout";
    final public String REG_URL = "/reg";
    final public String ROOT_URL = "/";

    final public String INDEX_HTML = "index.html";
    final public String TEMPLATE_HTML = "template.html";
    final public String ADMIN_HTML = "admin.html";
    final public String REG_HTML = "reg.html";
    final public String GAME_HTML = "game.html";
    final public String WAIT_HTML = "wait.html";
    final public String PROFILE_HTML = "profile.html";
    final public String RULES_HTML = "rules.html";
    final public String STATUS_404_HTML = "404.html";

    final public  String PAGE_PARAMETR = "page";

    final public String CONTENT_TYPE= "text/html;charset=utf-8";
    final public String CACHE_CONTROL_HEADER = "Cache-Control";
    final public String STORE_CACHE_REVALIDATE_HEADER = "no-store, no-cache, must-revalidate";

    final public String NICKNAME_FIELD_HTML= "nick";
    final public String REG_NICKNAME_FIELD_HTML= "regNick";
    final public String ID_FIELD_HTML= "id";
    final public String REG_PASSWORD_FIELD_HTML= "regPassword";
    final public String PASSWORD_FIELD_HTML= "password";
    final public String RATING_FIELD_HTML= "rating";

    final public String SESSION_NULL__ID_FIELD_DATA = "0";
    final public String SESSION_NULL__NICKNAME_FIELD_DATA = "Noname";
    final public String SESSION_NULL__RATING_FIELD_DATA ="500";

	public FrontendImpl(MessageSystem msgSystem){
		address=new Address();
		messageSystem=msgSystem;
		messageSystem.addService(this,"Frontend");
	}

	public Address getAddress(){
		return address;
	}

	private void getStatistic(HttpServletResponse response, UserDataSet userSession){

		Map<String,String> data= new HashMap<String,String>();
		String mu=SysInfo.getStat(SysInfo.MEMORY_USAGE_FIELD);
		String tm = SysInfo.getStat(SysInfo.TOTAL_MEMORY_FIELD);
		String time=SysInfo.getStat(SysInfo.TIME_FIELD);
		String ccu = SysInfo.getStat(SysInfo.CCU_FIELD);
		data.put(SysInfo.MEMORY_USAGE_FIELD, mu);
		data.put(SysInfo.TIME_FIELD, time);
		data.put(SysInfo.TOTAL_MEMORY_FIELD, tm);
		data.put(SysInfo.CCU_FIELD, ccu);
		data.put(PAGE_PARAMETR, ADMIN_HTML);
		data.put(ID_FIELD_HTML, String.valueOf(userSession.getId()));
		data.put(NICKNAME_FIELD_HTML, String.valueOf(userSession.getNick()));
		data.put(RATING_FIELD_HTML, String.valueOf(userSession.getRating()));
		try {
			TemplateHelper.renderTemplate(TEMPLATE_HTML, data, response.getWriter());
		} catch (IOException ignor) {
		}
	}

	private status getStatus(HttpServletRequest request,String target,status stat,String sessionId){
		if((stat.equals(status.haveCookie))&&(request.getMethod().equals("POST")))
			stat=status.haveCookieAndPost;
		if((stat.equals(status.haveCookie))&&(UserDataImpl.getUserSessionBySessionId(sessionId).getId()!=0))
			stat=status.ready;
		if (target.equals(WAIT_URL)){
			if((!stat.equals(status.haveCookie)&&(!stat.equals(status.haveCookieAndPost)))
					||(UserDataImpl.getUserSessionBySessionId(sessionId).getPostStatus()==0))
				stat=status.nothing;
			else 
				stat=status.waiting;
		}
		return stat;
	}


	private void prepareResponse(HttpServletResponse response){
		response.setContentType(CONTENT_TYPE);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setHeader(CACHE_CONTROL_HEADER,STORE_CACHE_REVALIDATE_HEADER);
		response.setHeader("Expires", TimeHelper.getGMT());
	}

	private boolean inWeb(String target){
		return ((target.equals(ROOT_URL))||(target.equals(WAIT_URL))||(target.equals(GAME_URL))||(target.equals(PROFILE_URL))
				||(target.equals(ADMIN_URL))||(target.equals(RULES_URL))||(target.equals(LOGOUT_URL))||(target.equals(REG_URL)));
	}

	private boolean isStatic(String target){
		if(target.length()<4)
			return false;
		else if(target.length()==4)
			return target.substring(0, 4).equals("/js/");
		else return (((target.substring(0, 5)).equals("/img/"))||((target.substring(0, 5)).equals("/css/")));
	}

	private boolean newUser(String strSessionId, String strStartServerTime){
		return((strSessionId==null)||(strStartServerTime==null)
				||(!UserDataImpl.checkServerTime(strStartServerTime))
				||(!UserDataImpl.containsSessionId(strSessionId)));
	}

	private void sendPage(String name, UserDataSet userSession, HttpServletResponse response){
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put(PAGE_PARAMETR, name);
			if(userSession!=null){
				data.put(ID_FIELD_HTML, String.valueOf(userSession.getId()));
				data.put(NICKNAME_FIELD_HTML, String.valueOf(userSession.getNick()));
				data.put(RATING_FIELD_HTML, String.valueOf(userSession.getRating()));
			}
			else{
				data.put(ID_FIELD_HTML, SESSION_NULL__ID_FIELD_DATA);
				data.put(NICKNAME_FIELD_HTML, SESSION_NULL__NICKNAME_FIELD_DATA);
				data.put(RATING_FIELD_HTML, SESSION_NULL__RATING_FIELD_DATA);
			}
			TemplateHelper.renderTemplate(TEMPLATE_HTML, data, response.getWriter());
		} 
		catch (IOException ignor) {
		}
	}

	private void onNothingStatus(String target,String strSessionId, UserDataSet userSession, String strStartServerTime,HttpServletResponse response){
		boolean moved=false;
		if (!target.equals(ROOT_URL)){
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.addHeader("Location", ROOT_URL);
			moved=true;
		}
		Cookie cookie1=new Cookie("sessionId", strSessionId);
		Cookie cookie2=new Cookie("startServerTime",strStartServerTime);
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		if (!moved){
			sendPage(INDEX_HTML,userSession,response);
		}
	}

	private void onHaveCookieStatus(String target, UserDataSet userSession, HttpServletResponse response){
		if (target.equals(ROOT_URL)){
			sendPage(INDEX_HTML,userSession,response);
		}
		else if (target.equals(REG_URL)){
			sendPage(REG_HTML,userSession,response);
		}
		else{
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.addHeader("Location", ROOT_URL);
		}
	}

	private void onHaveCookieAndPostStatus(String target, String sessionId,UserDataSet userSession,HttpServletRequest request, HttpServletResponse response){
		String nick=request.getParameter(NICKNAME_FIELD_HTML);
		String password = request.getParameter(PASSWORD_FIELD_HTML);
		if ((nick==null)||(password==null)){
			nick = request.getParameter(REG_NICKNAME_FIELD_HTML);
			password = request.getParameter(REG_PASSWORD_FIELD_HTML);
			if ((nick==null)||(password==null)||(nick.equals(""))||(password.equals(""))||(nick.length()>20)){
				sendPage(target+".html",userSession,response);
			}
			else{
				password=SHA2.getSHA2(password);
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				response.addHeader("Location", WAIT_URL);
				userSession.setPostStatus(1);
				Address to=messageSystem.getAddressByName("DBService");
				Address from=messageSystem.getAddressByName("UserData");
				MsgAddUser msg=new MsgAddUser(from,to,sessionId,nick,password);
				messageSystem.putMsg(to, msg);
			}
		}
		else{
			password=SHA2.getSHA2(password);
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.addHeader("Location", WAIT_URL);
			userSession.setPostStatus(1);
			Address to=messageSystem.getAddressByName("DBService");
			Address from=messageSystem.getAddressByName("UserData");
			MsgGetUser msg=new MsgGetUser(from,to,sessionId,nick,password);
			messageSystem.putMsg(to, msg);
		}
	}

	private void onWaitingStatus(HttpServletResponse response){
		sendPage(WAIT_HTML,null,response);
	}

	private void onReadyStatus(String target, String sessionId, UserDataSet userSession,HttpServletResponse response){
		if(target.equals(ROOT_URL)){
			UserDataImpl.putLogInUser(sessionId, userSession);
			sendPage(INDEX_HTML,userSession,response);
		}
		else if (target.equals(GAME_URL)){
			UserDataImpl.putLogInUser(sessionId, userSession);
			UserDataImpl.playerWantToPlay(sessionId, userSession);
			sendPage(GAME_HTML,userSession,response);
		}
		else if(target.equals(LOGOUT_URL)){
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.addHeader("Location", ROOT_URL);
			String strSessionId = sessionId=SHA2.getSHA2(String.valueOf(creatorSessionId.incrementAndGet()));
			Cookie cookie=new Cookie("sessionId", strSessionId);
			response.addCookie(cookie);
			UserDataImpl.putSessionIdAndUserSession(sessionId, new UserDataSet());
		}
		else if (target.equals(PROFILE_URL)){
			sendPage(PROFILE_HTML,userSession,response);
		}
		else{
			response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			response.addHeader("Location", ROOT_URL);
		}
	}

	public void handle(String target,Request baseRequest,
			HttpServletRequest request, HttpServletResponse response){
		prepareResponse(response);
		status stat=status.nothing;
		CookieDescriptor cookie=new CookieDescriptor(request.getCookies());
		String sessionId=cookie.getCookieByName("sessionId");
		String strStartServerTime=cookie.getCookieByName("startServerTime");
		UserDataSet userSession;
		baseRequest.setHandled(true);
		if(newUser(sessionId, strStartServerTime)){
			userSession=new UserDataSet();
			sessionId=SHA2.getSHA2(String.valueOf(creatorSessionId.incrementAndGet()));
			strStartServerTime=UserDataImpl.getStartServerTime();
			UserDataImpl.putSessionIdAndUserSession(sessionId, userSession);
		}
		else{
			stat=status.haveCookie;
			userSession=UserDataImpl.getUserSessionBySessionId(sessionId);
		}
		if(!inWeb(target)){
			if(!isStatic(target)){
				sendPage(STATUS_404_HTML,userSession,response);
			}
			return;	
		}
		userSession.visit();
		stat=getStatus(request, target, stat, sessionId);
		if (stat!=status.haveCookieAndPost){
			if(target.equals(ADMIN_URL)){
				getStatistic(response,userSession);
				return;
			}
			else if (target.equals(RULES_URL)){
				sendPage(RULES_HTML,userSession, response);
				return;
			}
		}
		switch(stat){
		case nothing:
			onNothingStatus(target, sessionId, userSession,strStartServerTime, response);
			break;
		case haveCookie:
			onHaveCookieStatus(target, userSession,response);
			break;
		case haveCookieAndPost:
			onHaveCookieAndPostStatus(target,sessionId, userSession,request, response);
			break;
		case waiting:
			onWaitingStatus(response);
			break;
		case ready:
			onReadyStatus(target, sessionId, userSession, response);
			break;
		}
	}


    public void getStatisticTest(HttpServletResponse response, UserDataSet userSession){
        getStatistic(response, userSession);
    }
    public void onReadyStatusTest(String target, String sessionId, UserDataSet userSession,HttpServletResponse response){
        onReadyStatus(target, sessionId, userSession, response);
    }
    public void onWaitingStatusTest(HttpServletResponse response){
        onWaitingStatus(response);
    }
    public void onHaveCookieAndPostStatusTest(String target, String sessionId,UserDataSet userSession,HttpServletRequest request, HttpServletResponse response){
        onHaveCookieAndPostStatus(target, sessionId, userSession, request, response);
    }
    public void onHaveCookieStatusTest(String target, UserDataSet userSession, HttpServletResponse response){
        onHaveCookieStatus(target, userSession, response);
    }
    public void sendPageTest(String name, UserDataSet userSession, HttpServletResponse response){
        sendPage(name, userSession, response);
    }
    public boolean newUserTest(String strSessionId, String strStartServerTime){
        return newUser(strSessionId, strStartServerTime);
    }
    public boolean isStaticTest(String target){
        return isStatic(target);
    }
    public boolean inWebTest(String target){
        return inWeb(target);
    }

    public void prepareResponseTest(HttpServletResponse response){
        prepareResponse(response);
    }
    public status getStatusTest(HttpServletRequest request,String target,status stat,String sessionId){
        return getStatus(request, target, stat, sessionId);
    }
}