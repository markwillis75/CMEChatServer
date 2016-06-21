package test.com.mkwillis.chat.commands;

import com.mkwillis.chat.server.IClientConnection;
import com.mkwillis.chat.server.IMessage;

public class MockClientConnection implements IClientConnection{
	private boolean sent;
	private String userID;
	
	public MockClientConnection(String userid){
		this.userID = userid;
	}
	
	public void respondToSendWith(boolean sent){
		this.sent = sent;
	}
	
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	public String getUserID(){
		return userID;
	}
	
	public boolean send(IMessage msg){
		return sent;
	}
	
	public void terminate(){
	}
}
