package com.mkwillis.chat.client;

import java.util.*;

/**
 * Interface to define the functionality required of an object capable
 * of acting a client to a chat server
 * 
 * @author Mark Willis
 */
public interface IClient {
	
	/**
	 * Set the server host
	 * @param host
	 */
	public void setHost(String host);
	
	/**
	 * Set the server port
	 * @param port
	 */
	public void setPort(int port);
	
	/**
	 * Set the username
	 * @param user
	 */
	public void setUser(String user);
	
	/**
	 * Attempt to connect
	 * @return true if successful
	 */
	public boolean connect();
	
	/**
	 * Attempt to disconnect
	 */
	public void disconnect();
	
	/**
	 * Send the message
	 * @param msg
	 */
	public void sendMessage(String msg);
		
	/**
	 * Allow client to be observed so that it can raise notifications
	 * @param obs
	 */
	public void addObserver(Observer obs);
	
	/**
	 * Filter support.  Obtain the list of clients which this client is ignoring
	 * @return
	 */
	public List<String> getIgnoring();
	
	/**
	 * Filter support. Set the list of clients which this client is ignoring
	 * @param ignoring
	 */
	public void setIgnoring(List<String> ignoring);
}
