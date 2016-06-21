package com.mkwillis.chat.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.mkwillis.chat.server.IMessage;

public class ClientApp{

	public static void main(String[] args){
		new ClientApp();
	}
	
	public ClientApp(){
		
		IClient model = new Client();	
		ChatView view = new ChatView(model);
		ClientController controller = new ClientController(model, view);

		JFrame frame = new JFrame("Chat client");
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				model.disconnect();
				System.exit(0);
			}
		});
		
		frame.getContentPane().setBackground(Color.red);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(view, BorderLayout.CENTER);
		
		frame.setSize(400,  400);
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				frame.setVisible(true);
			}
		});
	}
}
