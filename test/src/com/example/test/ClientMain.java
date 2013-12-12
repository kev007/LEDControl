package com.example.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain
{
		public static void main(String[] args) 
		{
				//Kostanten
				int PORT = 3963;
				String ADDRESS = "127.0.0.1";
				//String ADDRESS ="255.255.255.255" ;
				InetAddress BROAD_ADDRESS = null;
				
				//InetAddress erstellen
				try 
				{
						BROAD_ADDRESS = InetAddress.getByName(ADDRESS);
				} catch (UnknownHostException e)
				{
						e.printStackTrace();
				}	
				//Fenster und Management erzeugen + übergeben
				Management m = new Management(BROAD_ADDRESS, PORT);
				//new Window(m);
		}
}