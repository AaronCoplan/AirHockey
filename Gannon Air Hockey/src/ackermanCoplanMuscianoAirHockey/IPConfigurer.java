package ackermanCoplanMuscianoAirHockey; //NEEDS COMMENTED

import java.net.*;

public class IPConfigurer {

	private String[] IPs;
	private boolean done = false;
	private final int numberOfComputers = 100;//this is the number of cpu's on network it will generate IPs for
	
	public void configure(){
		
		try{
			InetAddress localHost = InetAddress.getLocalHost();
			String thisComputerIP = localHost.toString();
			thisComputerIP = thisComputerIP.substring(thisComputerIP.indexOf("/") + 1);
			String baseIP = thisComputerIP.substring(0, thisComputerIP.lastIndexOf(".")+1);
			
			IPs = new String[numberOfComputers];
			for(int x = 0; x < numberOfComputers; x++){
				
				IPs[x] = baseIP + (x+1);
			}
			
		}catch(UnknownHostException e){
			e.printStackTrace();
			System.out.println("ERROR GETTING HOST");
		}
		this.done = true;
	}
	
	public boolean isDone(){return this.done;}
	public String[] getIPs(){return IPs;}
}


