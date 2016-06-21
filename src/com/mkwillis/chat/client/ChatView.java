package com.mkwillis.chat.client;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.mkwillis.chat.server.IMessage;

public class ChatView extends JPanel{
	
	private IClient model;
	
	private JTextArea textArea;
	
	public ChatView(IClient model){
		this.model = model;
		
		setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);
		
		model.addObserver(new ModelObserver());
	}
	
	public void setServerPanel(JPanel panel){
		add(panel, BorderLayout.NORTH);
	}
	
	public void setDriverPanel(JPanel panel){
		add(panel, BorderLayout.SOUTH);
	}
	
	public String getUsername(){
		return JOptionPane.showInputDialog(
				"Enter username");
	}
	private class ModelObserver implements Observer{
		private final String stringFormat = "%s : %s";
		
		public void update(Observable o, Object arg){
			if (arg != null){
				IMessage msg = (IMessage)arg;
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						textArea.append(String.format(stringFormat, msg.getSender(), msg.getMessage()));
						textArea.append("\n");
					}
				});
			}
		}
	}
}
