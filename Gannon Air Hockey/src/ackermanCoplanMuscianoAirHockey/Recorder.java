package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.io.*;

public class Recorder {

	private PrintWriter pw;
	
	public Recorder(String filePath) throws Exception{
		
		File file = new File(filePath);
		FileWriter fw = new FileWriter(file, false);
		this.pw = new PrintWriter(fw);
	}
	
	public void record(int puckX, int puckY, int userPaddleX, int userPaddleY, int opponentPaddleX, int opponentPaddleY){
		
		pw.println(puckX);
		pw.println(puckY);
		pw.println(userPaddleX);
		pw.println(userPaddleY);
		pw.println(opponentPaddleX);
		pw.println(opponentPaddleY);
	}
}
