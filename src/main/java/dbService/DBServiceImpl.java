package dbService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

import utils.TimeHelper;
import base.Address;
import base.DataAccessObject;
import base.MessageSystem;

public class DBServiceImpl implements DataAccessObject{
	private final MessageSystem messageSystem;
	private final Address address;
	private Connection connection;

	public DBServiceImpl(MessageSystem msgSystem){
		address=new Address();
		messageSystem = msgSystem;
		messageSystem.addService(this,"DBService");
	}

	public MessageSystem getMessageSystem(){
		return messageSystem;
	}

	public Address getAddress(){
		return address;
	}

	public UserDataSet getUDS(final String login, String password){
		UserDataSet user = TExecutor.getUDS(connection, login, password,
				new TResultHandler<UserDataSet>(){
			@Override
			public UserDataSet handle(ResultSet result){
				try {
					if(result.first()){
						int id = result.getInt("id");
						int rating = result.getInt("rating");
						int winQuantity = result.getInt("win_quantity");
						int loseQuantity = result.getInt("lose_quantity");
						return new UserDataSet(id,login,rating,winQuantity,loseQuantity);
					}
				} 
				catch (SQLException e) {
					System.err.println("\nError");
					System.err.println("DBServiceImpl, addUDS");
					System.err.println(e.getMessage());
				}
				return null;
			}
		});
		return user;
	}
	
	public boolean addUDS(final String login,String password){
		int rows = TExecutor.findUser(connection, login);
		if(rows==0)
			TExecutor.addUser(connection, login, password);
		return (rows==0);
	}

	public void updateUsers(List<UserDataSet> users){
		ListIterator<UserDataSet> li = users.listIterator();
		while(li.hasNext()){
			UserDataSet user = li.next();
			String login = user.getNick();
			int rating = user.getRating();
			int winQuantity = user.getWinQuantity();
			int loseQuantity = user.getLoseQuantity();
			TExecutor.updateUser(connection, login, rating, winQuantity, loseQuantity);
		}
	}

	public void updateAI(String table, int[] fields, String winner, int whiteQuantity, int blackQuantity){
		TExecutor.findPosition(connection, table, fields, whiteQuantity, blackQuantity);
	}
	
	public void run(){
		try{
			//			Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
			Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
		}
		catch(Exception e){
			System.err.println("\nError");
			System.err.println("DVServiceImpl, run1");
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		//		String url = "jdbc:sqlite:db/game.db";
		String url="jdbc:mysql://localhost:3306/checkers?user=checkers&password=fuckingpassword";
		try{
			connection = DriverManager.getConnection(url);
		}
		catch(Exception e){
			System.err.println("\nError");
			System.err.println("DVServiceImpl, run2");
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		while(true){
			messageSystem.execForAbonent(this);
			TimeHelper.sleep(200);
		}
	}
}
