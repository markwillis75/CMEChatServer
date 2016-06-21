package com.mkwillis.chat.server.commands;

/**
 * Command interface to be used in Command pattern
 * 
 * @author Mark Willis
 *
 */
public interface ICommand {
	public enum CommandType{
		LOGON,
		LOGOFF,
		SEND_ALL;
	}
	
	/**
	 * Execute this command
	 * @return true if command succeeded
	 */
	public boolean execute();
}
