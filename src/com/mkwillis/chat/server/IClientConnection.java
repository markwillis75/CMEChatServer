package com.mkwillis.chat.server;

/**
 * Simple interface to expose the functionality of classes representing
 * a connection to the server
 * 
 * @author Mark Willis
 *
 */
public interface IClientConnection {
	
	/**
	 * Set the user id for this connection
	 * @param userID
	 */
	public void setUserID(String userID);
	
	/**
	 * Get the user id for this connection
	 * @return
	 */
	public String getUserID();
	
	/**
	 * Transmit the specified message to the client
	 * @param msg
	 * @return
	 */
	public boolean send(IMessage msg);
	
	/**
	 * Terminate the connection to the server
	 * 
	 */
	public void terminate();
}
