package ackermanCoplanMuscianoAirHockey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class FourPersonServer {

	private ServerSocket ss;
	private Socket clientSocket1, clientSocket2, clientSocket3;
	private PrintWriter pw1, pw2, pw3;
	private BufferedReader br1, br2, br3;
	private String hostName, name1, name2, name3;
	private final int TIMEOUT = 120 * 1000;
	
	
	public FourPersonServer(String hostName){
		this.hostName = hostName;
	}
	
	public void startServer(){
		try{
			ss = new ServerSocket(63400);
			ss.setSoTimeout(TIMEOUT);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void connect1(){
		try{
			clientSocket1 = ss.accept();
			br1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
			pw1 = new PrintWriter(clientSocket1.getOutputStream(), true);
			pw1.println(1);
			pw1.println(hostName);
			name1 = br1.readLine();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void connect2(){
		try{
			clientSocket2 = ss.accept();
			br2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
			pw2 = new PrintWriter(clientSocket2.getOutputStream(), true);
			pw2.println(2);
			pw2.println(hostName);
			name2 = br2.readLine();
			pw2.println(name1);
			pw1.println(name2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void connect3(){
		try{
			clientSocket3 = ss.accept();
			br3 = new BufferedReader(new InputStreamReader(clientSocket3.getInputStream()));
			pw3 = new PrintWriter(clientSocket3.getOutputStream(), true);
			pw3.println(3);
			pw3.println(hostName);
			name3 = br3.readLine();
			pw1.println(name3);
			pw2.println(name3);
			pw3.println(name2);
			pw3.println(name3);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getHostName(){return hostName;}
	public String getOpponent1Name(){return name1;}
	public String getOpponent2Name(){return name2;}
	public String getOpponent3Name(){return name3;}
	
	public void flush(){
		pw1.flush();
		pw2.flush();
		pw3.flush();
	}
	
	public void sendLocations(int hostPaddleX, int hostPaddleY, int opponent1PaddleX, int opponent1PaddleY, int opponent2PaddleX, int opponent2PaddleY, int opponent3PaddleX, int opponent3PaddleY, int puckX, int puckY){
		
		pw1.println(hostPaddleX + " " + hostPaddleY + " " + opponent1PaddleX + " " + opponent1PaddleY + " " + opponent2PaddleX + " " + opponent2PaddleY + " " + opponent3PaddleX + " " + opponent3PaddleY + " " + puckX + " " + puckY);
	}
	
	public String readOpponent1Locations(){
		try{
			return br1.readLine();
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			JOptionPane.showMessageDialog(null, "Error with Buffered Reader 1!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public String readOpponent2Locations(){
		try{
			return br2.readLine();
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			JOptionPane.showMessageDialog(null, "Error with Buffered Reader 2!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public String readOpponent3Locations(){
		try{
			return br3.readLine();
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			JOptionPane.showMessageDialog(null, "Error with Buffered Reader 3!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}
