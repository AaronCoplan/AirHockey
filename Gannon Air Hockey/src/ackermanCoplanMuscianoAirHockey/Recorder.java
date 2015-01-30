package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED BUT CURRENTLY NOT IN USE

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Recorder {

	private PrintWriter pw;
	
	public Recorder(String filePath){
		
		try{
			File file = new File(filePath);
			FileWriter fw = new FileWriter(file, false);
			this.pw = new PrintWriter(fw);
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Error with files for Recorder!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
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
