package com.mkwillis.chat.server;

/**
 * Concrete implementation of the IMessage interface
 * 
 * @author Mark Willis
 */
public class Message implements IMessage{
	
	public static final long serialVersionUID = 1l;
	
	private String message,
	               sender;
	
	/**
	 * Constructor
	 * @param msg    Message to send
	 * @param sender Sender of message
	 */
	public Message(String msg, String sender){
		this.message = msg;
		this.sender = sender;
	}
	
	/**
	 * @see IMessage#getMessage()
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * @see IMessage#getSender()
	 */
	public String getSender(){
		return sender;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("sender: ").append(getSender())
		       .append(" msg: ").append(getMessage());
		
		return builder.toString();
	}
}
