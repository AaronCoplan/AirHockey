package ackermanCoplanMuscianoAirHockey; //COMMENTED

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPConfigurer {

	private String[] IPs; //array of IPs that will be retrieved by the menu class and passed to the client to attempt to connect to
	private final int numberOfComputers = 254;//this is the number of cpu's on network it will generate IPs for
	
	//configure method generates array of IPs
	public void configure(){
		boolean hasManualIp = false;

		if(Driver.opponentIp != null){
			hasManualIp = true;
		}
		
		try{
			InetAddress localHost = InetAddress.getLocalHost(); //gets your computer's current IP (ex. /192.168.1.10)
			String thisComputerIP = localHost.toString(); //converts it to a string
			thisComputerIP = thisComputerIP.substring(thisComputerIP.indexOf("/") + 1); //substrings it to get rid of the slash (ex. 192.168.1.10) 
			String baseIP = thisComputerIP.substring(0, thisComputerIP.lastIndexOf(".")+1); //substrings it after the last period (ex. 192.168.1.)

			if(!hasManualIp) {
				IPs = new String[numberOfComputers]; //instantiates the empty array
			}else{
				IPs = new String[numberOfComputers + 1];
				IPs[numberOfComputers] = Driver.opponentIp;
			}
			
			//for loop to fill array with increasing IPs (ex. 192.168.1.1, 192.168.1.2, 192.168.1.3...all the way to the numberOfComputers variable)
			for(int x = 0; x < numberOfComputers; x++){
				
				IPs[x] = baseIP + (x+1);
			}

		}catch(UnknownHostException e){ //catches UnknownHostException if it can't figure out your computer's IP
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "Error getting host!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String[] getIPs(){return IPs;} //accessor method to get the string[] of IPs once they are finished configuring
}


