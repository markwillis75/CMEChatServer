package com.mkwillis.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.*;

import com.mkwillis.chat.server.IMessage;
import com.mkwillis.chat.server.MessageFactory;
import com.mkwillis.chat.server.commands.ICommand;
import com.mkwillis.chat.server.commands.ICommand.CommandType;

public class Client extends Observable implements IClient{
	
	/**
	 * The connection to the server
	 */
	private Socket connection;
	
	private String host,
	               username;
	private int port;
	
	private PrintWriter writer;
	private ObjectInputStream reader;
	
	private List<String> ignoring;
	
	/**
	 * Constructor
	 */
	public Client(){
		ignoring = new ArrayList<>();
	}
	
	/**
	 * @see IClient#setHost(String)
	 */
	public void setHost(String host){
		this.host = host;
	}
	
	/**
	 * @see IClient#setPort(int)
	 */
	public void setPort(int port){
		this.port = port;
	}
	
	/**
	 * @see IClient#setUser(String)
	 */
	public void setUser(String user){
		this.username = user;
	}
		
	/**
	 * Establish a connection to the server
	 */
	public boolean connect(){
		
		boolean returnValue = false;
		
		try
		{		
			connection = new Socket(host, port);
			writer = new PrintWriter(connection.getOutputStream(), true);
			reader = new ObjectInputStream(connection.getInputStream());
			logon();
			returnValue = true;
		}
		catch(IOException e){
			returnValue = false;
		}
			
		Thread readerThread = new Thread(new Runnable(){
			public void run(){
				boolean keepRunning = true;
				try{
					
					while (keepRunning){
						IMessage msg = (IMessage)reader.readObject();
						synchronized(ignoring){
							if (!ignoring.contains(msg.getSender())){
								setChanged();
								notifyObservers(msg);
							}
						}

					}
				}
				catch (Throwable e){// code brevity only
					keepRunning = false;
					setChanged();
					notifyObservers(MessageFactory.getMessage("connection lost"));
				}
			}
		});
		readerThread.start();
							
		return returnValue;
	}
	
	/**
	 * Disconnect from server
	 */
	public void disconnect(){
		sendToServer(ICommand.CommandType.LOGOFF.toString());
	}
	
	/**
	 * Send userid to server
	 */
	private void logon(){
		sendToServer(CommandType.LOGON.toString() + " " + username);
	}
	
	/**
	 * broadcast a message to everyone
	 */
	public void sendMessage(String msg){
		sendToServer(CommandType.SEND_ALL + " " + msg);
	}
	
	/**
	 * @See {@link IClient#setIgnoring(List)}
	 */
	public synchronized void setIgnoring(List<String> ignoring){
		this.ignoring = ignoring;
	}
	
	/**
	 * @see IClient#getIgnoring()
	 */
	public synchronized List<String> getIgnoring(){
		return ignoring;
	}
	
	/**
	 * Transmit the specified message to the server
	 * @param msg
	 */
	private void sendToServer(String msg){
		try
		{
			if (writer != null){
		        writer.println(msg);
			}
		}
		catch(Throwable e){
			e.printStackTrace();
		}
	}
}