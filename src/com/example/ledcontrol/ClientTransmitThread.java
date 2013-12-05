package com.example.ledcontrol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientTransmitThread extends Thread
{	
		//Konstanten
	    private DatagramSocket s = null;
	    InetAddress address = null;
	    int port = 0;
	    byte[] buffer = null;

	    //Konstructor
	    public ClientTransmitThread(DatagramSocket socket,InetAddress address,int port, byte[] data)
	    {
	    		this.s = socket;
	    		this.address = address;
	    		this.port = port;
	    		this.buffer = data;
	    }
		
		public void run() 
	    {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
				try
				{
						//Senden des Packets
						s.send(packet);				//BLOCKIEREND
						System.out.println("Paket gesendet an " + address +  " : " +  port );
				} catch (IOException e)
				{
						e.printStackTrace();
				}
	    }
}
