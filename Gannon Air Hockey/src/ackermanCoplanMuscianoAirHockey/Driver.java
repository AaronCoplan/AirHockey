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
		
		/* DONE
		 * Done--Set all catch statements to JOptionPanes
		 * Done--make the goals bigger
		 * Done--teach the computer to account for the puck bouncing off of walls
		 */
		
		/* FOUR PERSON AND MULTIPLAYER IN GENERAL
		 * The entire four player game needs written -- a lot of code can be copied from the two person classes
		 * Also need to catch socket exceptions for when opponent disconnects
		 * We may have to make the server implement runnable cause you cannot close the window when hosting and waiting for a connection (or we could institute a timeout)
		 */
		
		/* FEATURES TO ADD
		 * Need to give people option to pick their color of paddle and goal
		 * still need to do replay of last goal using recorder and playback classes (currently not implemented)
		 */
		
		/* AI ISSUES
		 * bound the computer to the game board
		 */
		
		/* GAMEPLAY ISSUES
		 * need to change JOptionPane that pops up after game ends
		 * bound people to their half
		 * Change the max speed of the puck (make it lower?)
		 */
		
		/* MAC PROBLEMS -- Most likely do not need addressed as our operating system is going to be Windows
		 * Colors do not show up on mac...(try using RGB values for colors)
		 * board is messed up on mac
		 * Scores are messed up on mac...
		 * Robot doesn't work on mac...
		 */
	}
	
	public static void singlePlayer(){
		
		new ComputerGAME();
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

		//figures out which button you have pressed and acts accordingly

		switch(twoPlayerMenu.getButton())
		{
		case 'h': new ServerGAME(twoPlayerMenu.getServer()); //host was selected, it starts the ServerGAME using the server from within the menu class
		break;
		case 'j': new ClientGAME(twoPlayerMenu.getClient()); //join was selected, it starts the ClientGAME code using the already connected client from within the menu class
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
		case 'h': JOptionPane.showMessageDialog(null, "Host");
		break;
		case 'j': JOptionPane.showMessageDialog(null, "Join");
		break;
		default: JOptionPane.showMessageDialog(null, "Error with four-player menu!", "ERROR", JOptionPane.ERROR_MESSAGE);
		break;
		}
		
		System.exit(0);
	}
}
