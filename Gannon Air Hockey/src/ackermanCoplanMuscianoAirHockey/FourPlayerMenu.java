package ackermanCoplanMuscianoAirHockey;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class FourPlayerMenu {

	private Font f;
	private IPConfigurer configurer;
	private JFrame frame;
	private JButton host, join;
	private JLabel peopleConnected;
	
	public FourPlayerMenu(){
		
		this.f = new Font("High Tower Text", Font.PLAIN, 66);
		startIPConfiguration();
		setUpMenu();
	}
	
	public void setUpMenu(){
		
		frame = new JFrame("Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(3, 0));
		
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void startIPConfiguration(){
		//instantiates the pinger then generates the necessary ips
		this.configurer = new IPConfigurer();
		configurer.configure();
	}
}
