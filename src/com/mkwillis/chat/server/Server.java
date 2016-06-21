package com.mkwillis.chat.server;

import java.io.*;
import java.net.*;

import java.util.logging.Logger;

/**
 * Server application entry point
 * 
 * @author Mark Willis
 *
 */
public class Server {
	private static Logger LOGGER = Logger.getLogger(Server.class.getName());
	
	public static void main(String[] args){
		if (args.length < 1){
			System.out.println("Usage");
			System.out.println("\tport");
			System.exit(-1);
		}
					
		try{
			int port = Integer.parseInt(args[0]);
		    new Server(port);
		}
		catch (Throwable thr){ // code brevity only
			LOGGER.severe("Unable to start server: " + thr);
			thr.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Start a ServerSocket on the specified port
	 * @param port
	 * @throws IOException
	 */
	public Server(int port) throws IOException{
		// start the server socket
		ServerSocket serverSocket = new ServerSocket(port);
		
		// no need for elegance
		// allow the server to run indefinitely until the VM is terminated
		while(true){
			Socket clientSocket = serverSocket.accept();
			LOGGER.info("A connection has been received! " + clientSocket.getRemoteSocketAddress());
			new ClientConnection(clientSocket);
		}
	}
}
