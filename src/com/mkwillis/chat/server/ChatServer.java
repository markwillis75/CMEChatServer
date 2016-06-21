package com.mkwillis.chat.server;

import java.util.*;
import java.util.logging.Logger;

/**
 * Manages core functionality for tracking IClientConnection instances,
 * including logon, logoff and message routing
 * 
 * Implemented as a singleton to ensure we only have one instance
 * 
 * @author Mark Willis
 */
public final class ChatServer implements IChatServer{

	private static IChatServer instance;
	
	private Map<String, IClientConnection> connections;
	private int maxSessions = 10; // TODO dynamically
	
	private Logger LOGGER = Logger.getLogger(ChatServer.class.getName());
	
	/**
	 * Private contstructor to prevent external instantiation
	 */
	private ChatServer(){
		connections = new HashMap<>(maxSessions);
	}
	
	/**
	 * Provide access to the singleton instance
	 * @return
	 */
	public static synchronized IChatServer getInstance(){
		if (instance == null){
			instance = new ChatServer();
		}
		
		return instance;
	}
	
	/**
	 * Add the specified session to the current sessions
	 * @param connection
	 * @return
	 */
	@Override
	public boolean logon(IClientConnection connection) {
		
		boolean returnVal = false;
		
		String userid = null;
		if (connection != null){
			userid = connection.getUserID();
			
			if (userid != null && !"".equals(userid)){
				// delay synchronizing until necessary to avoid locking the map
				synchronized(connections){
					if (connections.size() < maxSessions){
						if (!connections.containsKey(userid)){
							if (connection.send(MessageFactory.getMessage("Current Users"))){
								Iterator<String> welcome
								    = connections.keySet().iterator();
								while(welcome.hasNext()){
									connection.send(MessageFactory.getMessage(connections.get(welcome.next()).getUserID()));
								}
								connections.put(userid, connection);
								returnVal = true;
								send(MessageFactory.getMessage("New client: " + connection.getUserID()));
							}
						}
						else{
							connection.send(MessageFactory.getMessage("user is already logged on"));
						}
					}
					else{
						connection.send(MessageFactory.getMessage("Server is full"));
						connection.terminate();
					}
				}
			}
		}
		
		return returnVal;
	}

	/**
	 * Remove the specified connection from the current sessions
	 * @param connection
	 * @return
	 */
	@Override
	public boolean logoff(IClientConnection connection) {
		boolean returnValue = false;
		
		IClientConnection removed = null;
		if (connection != null){
			synchronized(connections){
				removed = connections.remove(connection.getUserID());
			}
			
			if (removed != null){
				returnValue = true;
				removed.terminate();
				LOGGER.info("Client disconnected: " + connection.getUserID());
			}
		}
		return returnValue;
	}

	/**
	 * Allow
	 */
	@Override
	public boolean send(IMessage msg) {
		boolean returnValue = false;
		
		if (msg != null){
			synchronized(connections){
				Iterator<String> iterator = 
						connections.keySet().iterator();
				
				while (iterator.hasNext()){
					IClientConnection connection = connections.get(iterator.next());
					if (connection == null){
						iterator.remove();
					}
					else{
					    if (!send(msg, connection)){
						    iterator.remove();
					    }
					}
				}
			}
		}
		
		return returnValue;
	}
	
	private boolean send(IMessage msg, IClientConnection session){
		return session.send(msg);
	}
}
