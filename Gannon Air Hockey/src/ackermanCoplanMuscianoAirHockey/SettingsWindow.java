package ackermanCoplanMuscianoAirHockey;

import javax.swing.*;

public class SettingsWindow {
	
	private JFrame frame;
	private boolean doneClicked = false;

	public SettingsWindow(){
		
		setUp();
	}
	
	public void setUp(){
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public boolean isDoneClicked(){return doneClicked;}
}
