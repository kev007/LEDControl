package com.ppp.ledcontrol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.ppp.ledcontrol.Container;


public class Management
{
		//Konstanten
		private int [] portVector = {2797,8167,4603,11173,21121};	//Portvektor für Portauswahl
		private int finalPort=0;
		int quellport = 0;
		InetAddress address = null;
		InetAddress broad = null;
		DatagramSocket socket = null;
		ClientTransmitThread cTT =  null;
		ClientReceiveThread cRT =  null;
		
		//Konstruktor
		public Management(InetAddress broad_address, int quellport)
		{
				this.quellport = quellport;
				this.broad = broad_address;
				try 
				{
					 	socket = new DatagramSocket(quellport);
				}
				catch (SocketException e)
				{
						e.printStackTrace();
				}
		}
		//Containererzeugung Modus 0
		public Container createZeroContainer()
		{
				Container co_broad = new Container(0);
				return co_broad;
		}
		//Senden eines Containers
		public void sendPackage(Container co)
		{
				//Notwendig für die Rückgabe
				try 
				{
						//Falls vorhanden IP Adresse ansonst Broadcast
						InetAddress inet_address = null;
						if(address != null)
						{
								inet_address = address;
						}
						else
						{
								inet_address = broad;
						}
						
						//Intialisieren der Outputstreams
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(co);
						oos.flush();
						
						//Umwandlung byte[]
						byte[] buffer = baos.toByteArray();
						
						//Übergabe an ClienttrasmitThread
						if (finalPort == 0)
						{
							for(int port : portVector)
							{
									cTT= new ClientTransmitThread(socket,inet_address,port,buffer);
									cTT.start();
									//Warte auf Packet (ClienteceiveThread)
									if(cRT == null)
									{
											cRT = new ClientReceiveThread(socket,this);
											cRT.start();
									}
							}
						} else {
							cTT= new ClientTransmitThread(socket,inet_address,finalPort,buffer);
							cTT.start();
							//Warte auf Packet (ClienteceiveThread)
							if(cRT == null)
							{
									cRT = new ClientReceiveThread(socket,this);
									cRT.start();
							}
						}

				}
				catch (IOException e2)
				{
						e2.printStackTrace();
				}
		}
		//Getter & Setter
		public void detectedServer(InetAddress address, int port)
		{
				this.address = address;
				this.finalPort = port;
		}
		public void changePort(int port)
		{
				this.quellport = port;
				if(socket != null)
				{
						if(cRT != null)cRT.stop();
						socket.close();
				}
				try {
					socket = new DatagramSocket(quellport);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
}
