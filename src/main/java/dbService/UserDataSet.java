package dbService;

import utils.TimeHelper;

public class UserDataSet{
	private int id;
	private int rating, winQuantity, loseQuantity;
	private String nick;
	private long lastVisit;
	private int postStatus;
	private String color;

	public UserDataSet(int id, String nick, int rating, int winQuantity, int loseQuantity){
		this.id=id;
		this.rating=rating;
		this.winQuantity=winQuantity;
		this.loseQuantity=loseQuantity;
		this.nick=nick;
		lastVisit=TimeHelper.getCurrentTime();
		postStatus=0;
		color=null;
	}
	
	public UserDataSet(){
		id=0;
		postStatus=0;
		lastVisit=TimeHelper.getCurrentTime();
		nick="";
		color=null;
	}

	public void makeLike(UserDataSet uds){
		this.id=uds.id;
		this.rating=uds.rating;
		this.winQuantity=uds.winQuantity;
		this.loseQuantity=uds.loseQuantity;
		this.nick=uds.nick;
		lastVisit=TimeHelper.getCurrentTime();
		postStatus=uds.postStatus;
		color=uds.color;
	}
	
	public String getNick(){
		return nick;
	}

	public int getId(){
		return id;
	}

	public void visit(){
		lastVisit=TimeHelper.getCurrentTime();
	}
	
	public long getLastVisit(){
		return lastVisit;
	}

	public void setPostStatus(int quantity){
		postStatus=quantity;
	}

	public int getPostStatus(){
		return postStatus;
	}

	public void setColor(String col){
		color=col;
	}
	
	public String getColor(){
		return color;
	}
	
	public int getRating(){
		return rating;
	}
	
	public int getWinQuantity(){
		return winQuantity;
	}
	
	public int getLoseQuantity(){
		return loseQuantity;
	}
	
	public void lose(int diff){
		loseQuantity++;
		rating-=diff;
	}
	
	public void win(int diff){
		winQuantity++;
		rating+=diff;
	}


    public void setLastVisit_ForTest(long time){
        lastVisit = time;
    }
}