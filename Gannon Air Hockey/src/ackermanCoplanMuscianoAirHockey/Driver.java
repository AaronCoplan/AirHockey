package ackermanCoplanMuscianoAirHockey; //COMMENTED

public class Driver {

	public static void main(String[] args){
	
		new ComputerGAME(); //can use this to play the computer as it has not been integrated into the menu yet
		//sets up the menu
		TwoPlayerMenu menu = new TwoPlayerMenu();
		
		//waits until you have clicked either join or host and been connected
		while(!menu.isButtonClicked()){
			//thread.sleep saves processor space
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
		
		//figures out which button you have pressed and acts accordingly
		
		switch(menu.getButton())
		{
		case 'h': new ServerGAME(menu.getServer()); //host was selected, it starts the ServerGAME using the server from within the menu class
		break;
		case 'j': new ClientGAME(menu.getClient()); //join was selected, it starts the ClientGAME code using the already connected client from within the menu class
		break;
		default: System.out.println("ERROR STARTING GAME");
		break;
		}
		
		/*
		 * WE NEED TO GO THROUGH ALL THE CODE AND SET ALL CATCH STATEMENTS TO THROW UP JOPTIONPANES SO ERRORS ARE VISUAL ONCE IT IS A JAR
		 * Also need to catch socket exceptions 
		 */
	}
}
