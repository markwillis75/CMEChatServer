package com.mkwillis.chat.server;

/**
 * Simple factory to encapsulate creation of IMessage instances
 * 
 * @author Mark Willis
 *
 */
public class MessageFactory {

	/**
	 * Get message with specified payload
	 * @param msg
	 * @return
	 */
	public static IMessage getMessage(String msg){
		return new Message(msg, "SERVER");
	}
	
	/**
	 * Get message with specified payload from specified sender
	 * @param msg
	 * @param sender
	 * @return
	 */
	public static IMessage getMessage(String msg, String sender){
		return new Message(msg, sender);
	}
}
