package com.mkwillis.chat.client;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class ClientController 
{
	private IClient model;
	private ChatView view;
	
	private JTextField hostField,
	                   portField;
	
	private List<String> ignoring;
	
	public ClientController(IClient model, ChatView view){
		ignoring = new ArrayList<>();
		this.model = model;
		this.view = view;
		view.setServerPanel(getServerPanel());
		view.setDriverPanel(getInputPanel());
	}
	
	private JPanel getServerPanel(){
		JPanel serverPanel = new JPanel();
		
		serverPanel.add(new JLabel("Host:"));
		
		hostField = new JTextField(20);
		hostField.setText("localhost");
		serverPanel.add(hostField);
		
		serverPanel.add(new JLabel("Port: "));
		
		portField = new JTextField(5);
		portField.setText("9000");
		serverPanel.add(portField);
		
		return serverPanel;
	}
	
	private JPanel getInputPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel userLabel = new JLabel("");
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.sendMessage(textField.getText());
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
					    textField.setText("");
					}
				});
				
			}
		});
		
		JButton logonBtn  = new JButton("Logon");
		JButton logoffBtn = new JButton("Logoff");
		
		logonBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.setHost(hostField.getText());
				model.setPort(Integer.parseInt(portField.getText()));
				String user = view.getUsername();
				model.setUser(user);
				if (model.connect()){
					logonBtn.setEnabled(false);
					logoffBtn.setEnabled(true);
					textField.setEditable(true);
					userLabel.setText(user);
				}
			};
		});
		

		logoffBtn.setEnabled(false);
		logoffBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.disconnect();
				logonBtn.setEnabled(true);
				logoffBtn.setEnabled(false);
				textField.setEditable(false);
				userLabel.setText("");
			}
		});
		
		JButton ignoringBtn = new JButton("Ignoring");
		ignoringBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				StringBuilder builder = new StringBuilder();
				for (String ignored: model.getIgnoring()){
					builder.append(ignored).append(",");
				}
				String ignoring = JOptionPane.showInputDialog("Ignoring", builder.toString());
				
				if (ignoring != null){
				    model.setIgnoring(Arrays.asList(ignoring.split(",")));
				}
			}
		});
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(logonBtn);
		btnPanel.add(logoffBtn);
		btnPanel.add(ignoringBtn);
		btnPanel.add(userLabel);
		panel.add(btnPanel, BorderLayout.SOUTH);

		panel.add(textField, BorderLayout.NORTH);
		
		return panel;
	}
}