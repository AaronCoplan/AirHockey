package ackermanCoplanMuscianoAirHockey; //COMMENTED

import java.net.*;
import java.io.*;

public class Server {

	private ServerSocket ss;
	private Socket clientSocket;
	private PrintWriter pw;
	private BufferedReader br;
	private String yourName, opponentName;
	
	//constructor that takes in your name so it can be passed to the opponent
	public Server(String yourName){
		this.yourName = yourName;
	}
	
	//method to accept a connection from a client
	public void connect(){
		
		try{
			//opens the server, accepts a client, opens PrintWriter and BufferedReader
			//then sends your name and receives opponent name
			ss = new ServerSocket(63400);
			clientSocket = ss.accept();
			pw = new PrintWriter(clientSocket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw.println(yourName);
			opponentName = br.readLine();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("ERROR WITH SERVER CONNECTION");
		}
	}
	
	//methods for the game class to get the names of both players 
	//names are displayed alongside the scores
	public String getYourName(){return yourName;}
	public String getOpponentName(){return opponentName;}
	
	//method for sending positions of components to client in string form
	public void send(String input){pw.println(input);}
	
	//method for reading positions of components from client in string form
	public String readPositions(){
		
		try{
			return br.readLine();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Error with Buffered Reader.");
			return null;
		}
	}
	
}
