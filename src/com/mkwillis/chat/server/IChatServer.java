package com.mkwillis.chat.server;

/**
 * Interface defining the functionality of a chat server
 * 
 * @author Mark Willis
 *
 */
public interface IChatServer {
	
	/**
	 * Request to initiate a new session
	 * @param session 
	 * @return true if new session permitted
	 */
	public boolean logon(IClientConnection session);
	
	/**
	 * Request to logoff
	 * @param session to terminate
	 * @return true if session terminated
	 */
	public boolean logoff(IClientConnection session);
	
	/**
	 * Request to transmit the specified message
	 * @param msg
	 * @return
	 */
	public boolean send(IMessage msg);
}
