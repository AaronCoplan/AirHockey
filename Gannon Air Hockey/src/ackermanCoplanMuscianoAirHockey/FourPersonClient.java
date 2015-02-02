package ackermanCoplanMuscianoAirHockey; //COMMENTED

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.io.IOException;

import javax.swing.JOptionPane;

public class FourPersonClient {

	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	private String[] IPs;
	private boolean connected = false;
	private String name, name1, name2, name3;
	private final int timeout = 100;
	
	//constructor that takes in list of ips to attempt to connect to
	//also takes in your name so it can be passed to the opponent
	public FourPersonClient(String[] IPs, String name){
		this.IPs = IPs;
		this.name = name;
	}
	
	//method to connect to the hosting player (server)
	public void connect(){
		
		//tries each ip in the array using the for loop using a 2.5 second timeout
		//if an ip works, it breaks the for loop, and sets the connected boolean to true
		for(int x = 0; x < IPs.length; x++){
			try{
				socket = new Socket();
				SocketAddress address = new InetSocketAddress(IPs[x], 63400);
				socket.connect(address, timeout);
				if(socket.isConnected()){
					pw = new PrintWriter(socket.getOutputStream(), true);
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					name1 = br.readLine();
					pw.println(name);
					this.connected = true;
					System.out.println("Successfully connected to: " + IPs[x]);
					break;
				}else{
					System.out.println("Could not connect to: " + IPs[x]);
				}
			}catch(ConnectException e){
				System.out.println("Could not connect to: " + IPs[x]);
			}catch(IOException e){
				System.out.println("Could not connect to: " + IPs[x]);
			}
			
			try{
				name2 = br.readLine();
				name3 = br.readLine();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	//methods for the game class to get the names of both players 
	//names are displayed alongside the scores
	public String getYourName(){return name;}
	public String getOpponent1Name(){return name1;}
	public String getOpponent2Name(){return name2;}
	public String getOpponent3Name(){return name3;}
	
	//method for the menu to determine if the client has succesfully found a host yet
	public boolean isConnected(){return this.connected;}
	
	//method for sending the positions of components to the server in string form
	public void send(String input){pw.println(input);}
	
	//method for reading positions of components from client in string form
	public String readPositions(){
		
		try{
			return br.readLine();
		}catch(IOException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Error with Buffered Reader!", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}
