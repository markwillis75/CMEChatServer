package test.com.mkwillis.chat.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mkwillis.chat.server.ChatServerFactory;
import com.mkwillis.chat.server.IChatServer;
import com.mkwillis.chat.server.MessageFactory;

import test.com.mkwillis.chat.commands.MockClientConnection;

public class TestChatServer {

	@Test
	public void testFullServer(){
		IChatServer server = ChatServerFactory.getChatServer();
		
		List<MockClientConnection> clients = new ArrayList<>();
		
		MockClientConnection mock;
		for (int i = 0; i < 10; i ++){
			mock = new MockClientConnection(String.valueOf(i));
			mock.respondToSendWith(true);
			clients.add(mock);
			
			Assert.assertTrue(server.logon(mock));
		}
		
		MockClientConnection refused = new MockClientConnection("100");
		refused.respondToSendWith(true);
		
		// no room at the inn
		Assert.assertFalse(server.logon(refused));
		
		// make room for one more
		mock = clients.remove(0);
		Assert.assertTrue(server.logoff(mock));
		
		// refused will now be accepted
		Assert.assertTrue(server.logon(refused));
		clients.add(refused);
		
		// house keeping clear for the next test
		for (MockClientConnection client:clients){
			Assert.assertTrue(server.logoff(client));
		}

	}
	
	@Test
	public void testDoubleLogon(){
		IChatServer server = ChatServerFactory.getChatServer();
		
		MockClientConnection mock = new MockClientConnection("100");
		mock.respondToSendWith(true);
		
		// second attempt should be refused
		Assert.assertTrue(server.logon(mock));
		Assert.assertFalse(server.logon(mock));
		
		// log the connection off then log back on again
		Assert.assertTrue(server.logoff(mock));
		Assert.assertTrue(server.logon(mock));
		
		// house-keeping
		server.logoff(mock);
	}
	
	@Test
	public void testInvalidLogoff(){
		MockClientConnection mock = new MockClientConnection("99");
		mock.respondToSendWith(true);
		
		IChatServer server = ChatServerFactory.getChatServer();
		Assert.assertFalse(server.logoff(mock));
	}
	
	@Test
	public void testDisconnectedClient(){
		MockClientConnection mock = new MockClientConnection("99");
		mock.respondToSendWith(false);
		
		IChatServer server = ChatServerFactory.getChatServer();
		Assert.assertFalse(server.logon(mock));
		Assert.assertFalse(server.send(MessageFactory.getMessage("Hello world")));
	}
	
	public static void main(String[] args){
		org.junit.runner.JUnitCore.main("test.com.mkwillis.chat.server.TestChatServer");
	}
}
