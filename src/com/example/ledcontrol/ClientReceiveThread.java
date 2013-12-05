package com.example.ledcontrol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;



public class ClientReceiveThread extends Thread
{
		//Variablen
	    private DatagramSocket socket;
		DatagramPacket packet = null;
		ByteArrayInputStream baos = null;
		ObjectInputStream oos = null;
		Management m = null;
		byte [] data = null;

		//Konnstruktor
	    public ClientReceiveThread(DatagramSocket s, Management m)
	    {
	    		this.socket = s;
	    		this.m = m;
	    		data = new byte[512];
	    }
	    
		public void run() 
	    {
		    	while (true)
		    	{
				    	try 
				    	{
						    	//Packet empfangen für Größe
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
						    			m.detectedServer(packet.getAddress(), packet.getPort());	
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
