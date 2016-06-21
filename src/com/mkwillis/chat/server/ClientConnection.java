package com.mkwillis.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

import com.mkwillis.chat.server.commands.CommandFactory;
import com.mkwillis.chat.server.commands.ICommand;

/**
 * Server side encapsulation of the client connection to the server
 * 
 * @author Mark Willis
 *
 */
public class ClientConnection implements IClientConnection{
	
	private Logger LOGGER = Logger.getLogger(ClientConnection.class.getName());
	
	private Socket socket;
	
	private ObjectOutputStream writer;
	private BufferedReader reader;
	
	private Thread thread;
	
	private String userID;
	
	private boolean keepRunning = true;
	
	/**
	 * Constructor 
	 * @param ss  The client socket
	 */
	public ClientConnection (Socket ss){
		this.socket = ss;
		try
		{
		    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    writer = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		// read the input stream on a thread 
		thread = new Thread(new Runnable(){
			public void run(){
				while (keepRunning){
					try{
					    String str = reader.readLine();
					    
					    ICommand cmd = CommandFactory.getCommand(str, ClientConnection.this);
					    cmd.execute();
					}
					catch (IOException e){
						keepRunning = false;
						LOGGER.info("client disconnected: " + getUserID());
						ChatServerFactory.getChatServer().logoff(ClientConnection.this);
					}
				}	
			}
		});
		
		thread.start();
	}
	
	/**
	 * @see IClientConnection#setUserID(String)
	 */
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	/**
	 * @see IClientConnection#getUserID()
	 */
	public String getUserID(){
		return userID;
	}
	
	/**
	 * @see IClientConnection#send(IMessage)
	 */
	public boolean send(IMessage msg){
		boolean returnValue = true;
		if (msg != null){
			try{
			    writer.writeObject(msg);
			    return true;
			}
			catch (IOException e){
				returnValue = false;
			}
		}
		
		return returnValue;
	}

	/**
	 * @see IClientConnection#terminate()
	 */
	public void terminate(){
		send(MessageFactory.getMessage("server terminating socket"));
		keepRunning = false;
		try{
			reader.close();
		}
		catch(IOException e){
		}
		
		try{
			writer.close();
		}
		catch(IOException e){
		}
		
		try{
			socket.close();
		}
		catch(IOException e){
		}
	}
}
