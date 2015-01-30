package ackermanCoplanMuscianoAirHockey;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FourPlayerMenu {

	private Font f;
	private IPConfigurer configurer;
	private JFrame frame;
	private JButton host, join;
	private JLabel peopleConnected;
	private boolean buttonClicked = false;
	private char button = '-';
	private String[] IPs;
	
	public FourPlayerMenu(){
		
		this.f = new Font("High Tower Text", Font.PLAIN, 66);
		startIPConfiguration();
		setUpMenu();
	}
	
	public void setUpMenu(){
	
		AL actionListener = new AL();
		
		frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(3, 0));
		
		host = new JButton("Host Game");
		host.setFont(f);
		host.setBackground(Color.cyan);
		host.addActionListener(actionListener);
		
		join = new JButton("Join Game");
		join.setFont(f);
		join.setBackground(Color.pink);
		join.addActionListener(actionListener);
		
		peopleConnected = new JLabel("Ignore this for now...", JLabel.CENTER);
		peopleConnected.setFont(new Font("High Tower Text", Font.PLAIN, 33));
		peopleConnected.setOpaque(true);
		peopleConnected.setBackground(Color.cyan);
		
		frame.add(host);
		frame.add(join);
		frame.add(peopleConnected);
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
