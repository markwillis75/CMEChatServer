package com.mkwillis.chat.server.commands;

import com.mkwillis.chat.server.*;

/**
 * Encapsulation of a request to logon
 * 
 * @author Mark Willis
 *
 */
public class LogonCommand extends AbstractCommand{
	
	/**
	 * @see AbstractCommand#AbstractCommand(String, IClientConnection)
	 * @param str
	 * @param connection
	 */
	public LogonCommand(String str, IClientConnection connection){
		super(str, connection);
	}
	
	/**
	 * @see AbstractCommand#parseCommand(String)
	 */
	@Override
	public void parseCommand(String str){
		String[] split = str.split(" ");
		if (split == null || split.length < 2){
			setDelegate(new InvalidCommand(str, getConnection()));
		}
		else{
			getConnection().setUserID(split[1]);
		}
	}
	
	/**
	 * @see ICommand#execute()
	 */
	@Override
	public boolean execute(){
		return ChatServerFactory.getChatServer().logon(getConnection());
	}
}
