package com.mkwillis.chat.server.commands;

import com.mkwillis.chat.server.IClientConnection;
import com.mkwillis.chat.server.MessageFactory;

/**
 * Utility implementation of ICommand which will report the invalid 
 * command back to the client and return false from the execute method
 * 
 * @author Mark Willis
 *
 */
public class InvalidCommand extends AbstractCommand{
	
	private String errMsg;
	
	/**
	 *@see AbstractCommand#AbstractCommand(String, IClientConnection)
	 */
	public InvalidCommand(String cmd, IClientConnection connection){
		super(cmd, connection);
	}
	
	/**
	 * @see AbstractCommand#parseCommand(String)
	 */
	@Override
	protected void parseCommand(String str){
		errMsg = "Invalid command: " + str;
	}
	
	/**
	 * @see ICommand#execute()
	 */
	public boolean execute(){
		getConnection().send(MessageFactory.getMessage(errMsg));
		return false;
	}
}
