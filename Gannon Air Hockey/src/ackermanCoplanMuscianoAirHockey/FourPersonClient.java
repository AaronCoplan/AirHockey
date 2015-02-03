package ackermanCoplanMuscianoAirHockey;

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
	private String yourName, hostName, name2, name3;
	private final int timeout = 100;
	private String playerNumber;

	public FourPersonClient(String[] IPs, String yourName){
		this.IPs = IPs;
		this.yourName = yourName;
	}

	public void connect(){

		for(int x = 0; x < IPs.length; x++){
			try{
				socket = new Socket();
				SocketAddress address = new InetSocketAddress(IPs[x], 63400);
				socket.connect(address, timeout);
				if(socket.isConnected()){
					pw = new PrintWriter(socket.getOutputStream(), true);
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					playerNumber = br.readLine();
					hostName = br.readLine();
					pw.println(yourName);
					this.connected = true;
					System.out.println("Successfully connected to: " + IPs[x]);
					System.out.println("You are player number: " + playerNumber);
					break;
				}else{
					System.out.println("Could not connect to: " + IPs[x]);
				}
			}catch(ConnectException e){
				System.out.println("Could not connect to: " + IPs[x]);
			}catch(IOException e){
				System.out.println("Could not connect to: " + IPs[x]);
			}

		}
	}

	public String getYourName(){return yourName;}
	public String getHostName(){return hostName;}
	public String getOpponent2Name(){return name2;}
	public String getOpponent3Name(){return name3;}

	public boolean isConnected(){return this.connected;}

	//need send and read methods for four person

	public static void main(String[] args){

		String[] ips = {"10.3.12.43"};
		new FourPersonClient(ips, "Emily").connect();
	}
}
