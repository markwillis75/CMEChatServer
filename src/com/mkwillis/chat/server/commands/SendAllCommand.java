package com.mkwillis.chat.server.commands;

import com.mkwillis.chat.server.*;

/**
 * Encapsulation of request to broadcast a message
 * 
 * @author Mark Willis
 *
 */
public class SendAllCommand extends AbstractCommand{
	private IMessage msg;
	
	private static final String expectedSyntax = CommandType.SEND_ALL + " ";
	
	/**
	 * @see AbstractCommand#AbstractCommand(String, IClientConnection)
	 */
	public SendAllCommand(String str, IClientConnection source){
		super(str, source);
	}
	
	/**
	 * @see AbstractCommand#parseCommand(String)
	 */
	@Override
	protected void parseCommand(String str){
		if (str == null || !str.startsWith(expectedSyntax)){
			setDelegate(new InvalidCommand("Invalid syntax: " + str, getConnection()));
		}
		else{
	    	String payload = str.substring(expectedSyntax.length());
		    msg = MessageFactory.getMessage(payload, getConnection().getUserID());
		}
	}
	
	/**
	 * @see ICommand#execute()
	 */
	@Override
	public boolean execute(){
		if (getDelegate() != null){
			getDelegate().execute();
		}
		return ChatServerFactory.getChatServer().send(msg);
	}
}
