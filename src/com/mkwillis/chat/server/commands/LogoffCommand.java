package com.mkwillis.chat.server.commands;

import com.mkwillis.chat.server.*;

/**
 * Encapsulation of a request to logoff
 * 
 * @author Mark Willis
 *
 */
public class LogoffCommand extends AbstractCommand{
	
	/**
	 * @see AbstractCommand#AbstractCommand(String, IClientConnection)
	 */
	public LogoffCommand(String str, IClientConnection connection){
		super(str, connection);
	}
	
	/**
	 * @see ICommand#execute()
	 */
	@Override
	public boolean execute(){
		return ChatServerFactory.getChatServer().logoff(getConnection());
	}
}
