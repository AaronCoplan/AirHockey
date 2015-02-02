package ackermanCoplanMuscianoAirHockey;

import java.util.Date;

public class FourPersonServerTesting {

	public static void main(String[] args){
		
		FourPersonServer fps = new FourPersonServer("Aaron");
		fps.startServer();
		System.out.println("Server started at: " + new Date());
		fps.connect1();
		System.out.println(fps.getOpponent1Name() + " connected at: " + new Date());
		fps.connect2();
		System.out.println(fps.getOpponent2Name() + " connected at: " + new Date());
		fps.connect3();
		System.out.println(fps.getOpponent3Name() + " connected at: " + new Date());
		System.out.println("\n\nAll People Playing:\n" + fps.getUserName() + "\n" + fps.getOpponent1Name() + "\n" + fps.getOpponent2Name() + "\n" + fps.getOpponent3Name());
		
	}
}
