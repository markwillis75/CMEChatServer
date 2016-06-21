package com.mkwillis.chat.server.commands;

import java.util.*;

import com.mkwillis.chat.server.IClientConnection;

/**
 * Factory to encapsulate the creation of ICommand instances
 * 
 * @author Mark Willis
 *
 */
public final class CommandFactory {
		
	/**
	 * Return an ICommand instance for the given request
	 * @param is 
	 * @return
	 */
	public static ICommand getCommand(String str, IClientConnection source){
		
		if (str == null || "".equals(str.trim()))
			return new InvalidCommand("Null command", source);
		
		Scanner scanner = new Scanner(str);
		String cmd = scanner.next();
		scanner.close();
				
		if (ICommand.CommandType.LOGON.toString().equals(cmd)){
			return new LogonCommand(str, source);
		}
		else if(ICommand.CommandType.LOGOFF.toString().equals(cmd)){
			return new LogoffCommand(str, source);
		}
		else if(ICommand.CommandType.SEND_ALL.toString().equals(cmd)){
			return new SendAllCommand(str, source);
		}
		
		return new InvalidCommand(str, source);
	}
}