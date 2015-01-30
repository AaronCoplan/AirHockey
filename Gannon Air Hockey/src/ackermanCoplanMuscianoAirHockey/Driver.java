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
		
		/*
		 * Things to do:
		 * 
		 * Done--Set all catch statements to JOptionPanes
		 * Done--make the goals bigger
		 * 
		 * Colors do not show up on mac...(try using RGB values for colors)
		 * Scores are messed up on mac...
		 * Robot doesn't work on mac...
		 * Also need to catch socket exceptions for when opponent disconnects
		 * need to change game over JOptionPane
		 * bound people to their half
		 * Change the max speed of the puck (make it lower?)
		 * bound the computer to the game board
		 * teach the computer to account for the puck bouncing off of walls
		 * still need to do replay of last goal using recorder and playback classes (currently not implemented)
		 * we may have to make the server implement runnable cause you cannot close the window when hosting and waiting for a connection (or we could institute a timeout)
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
		System.exit(0);
	}
}
