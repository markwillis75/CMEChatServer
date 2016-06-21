package com.mkwillis.chat.server;

import java.io.Serializable;

/**
 * Interface to abstract a message
 * 
 * @author Mark Willis
 *
 */
public interface IMessage extends Serializable{
		
	/**
	 * ID of user sending the message
	 * @return
	 */
	public String getSender();
	
	/**
	 * Message payload
	 * @return
	 */
	public String getMessage();
}
