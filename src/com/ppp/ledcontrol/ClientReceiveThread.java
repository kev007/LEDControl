package com.ppp.ledcontrol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.widget.ArrayAdapter;
import android.widget.ListView;



public class ClientReceiveThread extends Thread
{
		//Variablen
	    private DatagramSocket socket;
		DatagramPacket packet = null;
		ByteArrayInputStream baos = null;
		ObjectInputStream oos = null;
		byte [] data = null;

		//Konnstruktor
	    public ClientReceiveThread(DatagramSocket s)
	    {
	    		this.socket = s;
	    		data = new byte[4096];
	    }
	    
		public void run() 
	    {
		    	while (true)
		    	{
				    	try 
				    	{
						    	packet = new DatagramPacket(data, data.length);
								socket.receive(packet);								//BLOCKIEREND: Warte auf Paket
						    	
						    	//Container aus Objekt erstellen
						    	baos = new ByteArrayInputStream(data);
								oos = new ObjectInputStream(baos);
								Container c1 = (Container)oos.readObject();
								
								//Container Modus 0 (IP Modus)
						    	if(c1.getModus() == 0)
						    	{
						    			System.out.println("Empfangene IP: " + packet.getAddress());
						    			System.out.println("Empfangener Port: " + packet.getPort());
						    			//Aktulaisieren des Management
						    			MainActivity.serverFound = true;
						    			Management.detectedServer(packet.getAddress(), packet.getPort());	
						    	}
						    	
						    	//Container Modus 2 (Get Modus)
						    	if(c1.getModus() == 2)
						    	{							    		
							    		MainActivity.saveSetting(c1);
						    			System.out.println("Container: " + c1.getName());
						    			
						    			MainActivity.loadDir();
//						    			Profile.loadDir();
						    	}
				    	} 
				    	catch (IOException e)
				    	{
				    		e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
		    	}
	    }
}
