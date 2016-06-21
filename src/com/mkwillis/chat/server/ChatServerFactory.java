package com.mkwillis.chat.server;

/**
 * Simple factory to allow clients access to the chat server implementations
 * through an interface
 * 
 * @author Mark Willis
 *
 */
public class ChatServerFactory {

	/**
	 * Return an IChatServer
	 * @return
	 */
	public static IChatServer getChatServer(){
		return ChatServer.getInstance();
	}
}
