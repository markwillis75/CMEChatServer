package com.mkwillis.chat.server.commands;

import com.mkwillis.chat.server.IClientConnection;

/**
 * Abstract implementaton of ICommand interface to provide common functionality
 * 
 * @author Mark Willis
 *
 */
public abstract class AbstractCommand implements ICommand{
	
	/**
	 * Allows for command redirection
	 */
	private AbstractCommand delegate;
	
	/**
	 * The client which initiated this command
	 */
	private IClientConnection connection;
	
	/**
	 * Constructor
	 * @param cmd        The command to action
	 * @param connection The client which initated the action
	 */
	public AbstractCommand(String cmd, IClientConnection connection){
		this.connection = connection;
		parseCommand(cmd);
	}
	
	/**
	 * Provide subclasses with access to the command delegate
	 * @return
	 */
	protected AbstractCommand getDelegate(){
		return delegate;
	}
	
	/**
	 * Allow subclasses to set the command delegate
	 * @param delegate
	 */
	protected void setDelegate(AbstractCommand delegate){
		this.delegate = delegate;
	}
	
	/**
	 * Allow subclasses to access the IConnection
	 * @return
	 */
	protected IClientConnection getConnection(){
		return connection;
	}

	/**
	 * Default implementation of the parseCommand method.
	 * No implementation by default - allow subclasses to override
	 * if necessary
	 * @param cmd
	 */
	protected void parseCommand(String cmd){
	}
}
