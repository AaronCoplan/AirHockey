package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.JOptionPane;

public class Driver {

	public static void main(String[] args){
	
		
		MainMenu mainMenu = new MainMenu();
		
		while(!mainMenu.isButtonClicked()){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		
		switch(mainMenu.getButton())
		{
		case '1': singlePlayer();
		break;
		case '2': twoPlayer();
		break;
		case '4': fourPlayer();
		break;
		default: JOptionPane.showMessageDialog(null, "Error with main menu!", "ERROR", JOptionPane.ERROR_MESSAGE);
		break;
		}
		
		/* FOUR PERSON AND MULTIPLAYER IN GENERAL
		 * AARON--The entire four player game needs written -- a lot of code can be copied from the two person classes
		 * AARON--Also need to catch socket exceptions for when opponent disconnects
		 * AARON--We may have to make the server implement runnable cause you cannot close the window when hosting and waiting for a connection (or we could institute a timeout)
		 */
		
		/* FEATURES TO ADD
		 * AARON--add settings menu from main menu instead of having so many JOptionPanes
		 * AARON--If possible, give options to change background image(put in menu)
		 */
	}
	
	public static void singlePlayer(){
		
		String[] colorOptions = {"Red", "Orange", "Green", "Pink", "Purple", "Blue"};
		String colorChoice = (String)JOptionPane.showInputDialog(null, "Choose a color for your paddle and goal:", "Choose a Color", JOptionPane.QUESTION_MESSAGE, null, colorOptions, colorOptions[0]);
		new ComputerGAME(colorChoice);
		System.exit(0);
	}
	
	public static void twoPlayer(){
		
		//sets up the two person multiplayer menu
		TwoPlayerMenu twoPlayerMenu = new TwoPlayerMenu();

		//waits until you have clicked either join or host and been connected
		while(!twoPlayerMenu.isButtonClicked()){
			//thread.sleep saves processor space
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		
		String colorChoice = twoPlayerMenu.getColorChoice();

		//figures out which button you have pressed and acts accordingly

		switch(twoPlayerMenu.getButton())
		{
		case 'h': new ServerGAME(twoPlayerMenu.getServer(), colorChoice); //host was selected, it starts the ServerGAME using the server from within the menu class
		break;
		case 'j': new ClientGAME(twoPlayerMenu.getClient(), colorChoice); //join was selected, it starts the ClientGAME code using the already connected client from within the menu class
		break;
		default: JOptionPane.showMessageDialog(null, "Error with two-player menu!", "ERROR", JOptionPane.ERROR_MESSAGE);
		break;
		}
		System.exit(0);
	}
	
	public static void fourPlayer(){
		
		FourPlayerMenu fourPlayerMenu = new FourPlayerMenu();
		
		while(!fourPlayerMenu.isButtonClicked()){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		
		switch(fourPlayerMenu.getButton())
		{
		case 'h': new FourPersonServerGAME(fourPlayerMenu.getServer());
		break;
		case 'j': new FourPersonClientGAME(fourPlayerMenu.getClient(), fourPlayerMenu.getClient().getPlayerNumber());
		break;
		default: JOptionPane.showMessageDialog(null, "Error with four-player menu!", "ERROR", JOptionPane.ERROR_MESSAGE);
		break;
		}
		
		System.exit(0);
	}
}
