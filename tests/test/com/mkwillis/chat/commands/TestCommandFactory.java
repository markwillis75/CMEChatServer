package test.com.mkwillis.chat.commands;

import org.junit.Assert;
import org.junit.Test;

import com.mkwillis.chat.server.commands.CommandFactory;
import com.mkwillis.chat.server.commands.ICommand.CommandType;


public class TestCommandFactory {
	
	@Test
	public void testValidArgs(){
		
		for (CommandType cc:CommandType.values()){
			Assert.assertNotNull(CommandFactory.getCommand(cc.toString(), new MockClientConnection("1")));
		}
	}
	
	@Test
	public void testInvalidArgs(){
		Assert.assertNotNull(CommandFactory.getCommand(null, new MockClientConnection("1")));
		Assert.assertNotNull(CommandFactory.getCommand("", new MockClientConnection("1")));
		Assert.assertNotNull(CommandFactory.getCommand(" ", new MockClientConnection("1")));
		Assert.assertNotNull(CommandFactory.getCommand("!", new MockClientConnection("1")));
	}
}
