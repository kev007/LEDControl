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
		private static int [] portVector = {2797,8167,4603,11173,21121};	//Portvektor für Portauswahl
		private static int finalPort=0;
		static int quellport = 0;
		static InetAddress address = null;
		static InetAddress broad = null;
		static DatagramSocket socket = null;
		static ClientTransmitThread cTT =  null;
		static ClientReceiveThread cRT =  null;
		static int PORT = 4501;
		static String ADDRESS ="255.255.255.255" ;
		
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
		public static Container createZeroContainer()
		{
				Container co_broad = new Container(0);
				return co_broad;
		}
		//Senden eines Containers
		public static void sendPackage(Container co)
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
											cRT = new ClientReceiveThread(socket);
											cRT.start();
									}
							}
						} else {
							cTT= new ClientTransmitThread(socket,inet_address,finalPort,buffer);
							cTT.start();
							//Warte auf Packet (ClienteceiveThread)
							if(cRT == null)
							{
									cRT = new ClientReceiveThread(socket);
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
		public static void detectedServer(InetAddress address2, int port)
		{
				address = address2;
				finalPort = port;
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
