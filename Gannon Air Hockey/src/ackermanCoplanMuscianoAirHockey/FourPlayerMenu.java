package ackermanCoplanMuscianoAirHockey;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FourPlayerMenu {

	private Font f1, f2;
	private IPConfigurer configurer;
	private JFrame frame;
	private JButton host, join;
	private JLabel peopleConnected, peopleConnected2;
	private boolean buttonClicked = false;
	private char button = '-';
	private String[] IPs;
	private FourPersonServer server;
	private FourPersonClient client;
	
	public FourPlayerMenu(){
		
		this.f1 = new Font("High Tower Text", Font.PLAIN, 66);
		this.f2 = new Font("High Tower Text", Font.PLAIN, 33);
		startIPConfiguration();
		setUpMenu();
	}
	
	public void setUpMenu(){
	
		AL actionListener = new AL();
		
		frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(4, 0));
		
		host = new JButton("Host Game");
		host.setFont(f1);
		host.setBackground(Color.cyan);
		host.addActionListener(actionListener);
		
		join = new JButton("Join Game");
		join.setFont(f1);
		join.setBackground(Color.pink);
		join.addActionListener(actionListener);
		
		peopleConnected = new JLabel("Ignore this for now...", JLabel.CENTER);
		peopleConnected.setFont(f2);
		peopleConnected.setOpaque(true);
		peopleConnected.setBackground(Color.cyan);
		
		peopleConnected2 = new JLabel("Also ignore this for now...", JLabel.CENTER);
		peopleConnected2.setFont(f2);
		peopleConnected2.setOpaque(true);
		peopleConnected2.setBackground(Color.pink);
		
		frame.add(host);
		frame.add(join);
		frame.add(peopleConnected);
		frame.add(peopleConnected2);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		this.IPs = configurer.getIPs();
	}
	
	public boolean isButtonClicked(){return buttonClicked;}
	public char getButton(){return button;}
	
	public void startIPConfiguration(){
		//instantiates the pinger then generates the necessary ips
		this.configurer = new IPConfigurer();
		configurer.configure();
	}
	
	private class AL implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(e.getSource().equals(host)){
				
				String yourName = JOptionPane.showInputDialog(null, "Enter your name:", "Name", JOptionPane.QUESTION_MESSAGE);
				server = new FourPersonServer(yourName);
				server.startServer();
				
				host.setText("Hosting...");
				join.setText("Waiting for opponent...");
				peopleConnected.setText("Waiting for opponent...");
				peopleConnected2.setText("Waiting for opponent...");
				host.setFont(f2);
				join.setFont(f2);
				host.setEnabled(false);
				join.setEnabled(false);
				frame.update(frame.getGraphics());
				
				server.connect1();
				join.setText(server.getOpponent1Name() + " connected!");
				frame.update(frame.getGraphics());
				
				server.connect2();
				peopleConnected.setText(server.getOpponent2Name() + " connected!");
				frame.update(frame.getGraphics());
				
				server.connect3();
				peopleConnected2.setText(server.getOpponent3Name() + " connected!");
				frame.update(frame.getGraphics());
				
				//wait 5 seconds so you can see all opponent names
				try{
					Thread.sleep(5000);
				}catch(InterruptedException interrupted){}
				
				frame.setVisible(false);
				buttonClicked = true;
				button = 'h';
			}else if(e.getSource().equals(join)){
				
				frame.setVisible(false);
				buttonClicked = true;
				button = 'j';
			}
		}
	}
}
