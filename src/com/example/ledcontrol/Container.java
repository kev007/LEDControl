package com.example.ledcontrol;
import java.io.Serializable;

public class Container implements Serializable
{
		private static final long serialVersionUID = -872892999902890930L;
		/*
		 * Container besteht aus dem Modus und 5 Arrays.
		 * Modus: gibt an wofür das Paket genutzt wird
		 * 			Modus 0: Leerer Container zur ermittlung der IP und des Portes
		 * 			Modus 1: FarbContainer zur einfachen Übernahme der Einstellungen
		 * 			Modus 2: einfache Farbübernahme und Speicherung auf Pc
		 * Kettenstatus
		 * 			Status1- Status5: ture = Kette ist an, false= Kette ist aus
		 * Kettenarray: besteht aus 7 Zellen zur Beschreibung der Funktionen
		 * 			Feld 0		: Aktivität der Kette (1 = Ein, 0 = Aus)
		 * 			Feld 1 - 3	: RGB Werte
		 * 			Feld 4		: Intensität (Benutzerung wahrscheinlich erst für richtige LED Ketten)
		 * 			Feld 5		: Zeitkonstante für jeweiligen Übergang 
		 */
		
		//Colorbereich
		private int modus = 0;
		private String name = "";
		
		//Kettenstatus
		boolean status1 = false;
		boolean status2 = false;
		boolean status3 = false;
		boolean status4 = false;
		boolean status5 = false;
		
		//Parameter
		private int[][] kette1;
		private int[][] kette2;
		private int[][] kette3;
		private int[][] kette4;
		private int[][] kette5;
		
		//Konstruktor für Color
		public Container(int modus,String name, int kette1[][], boolean s1, int kette2 [][], 
						boolean s2, int kette3 [][], boolean s3,int kette4 [][], boolean s4, int kette5 [][], boolean s5)
		{
				this.name = name;
				
				this.modus = modus;
				this.kette1 = kette1;
				this.kette2 = kette2;
				this.kette3 = kette3;
				this.kette4 = kette4;
				this.kette5 = kette5;
				
				this.status1 = s1;
				this.status2 = s2;
				this.status3 = s3;
				this.status4 = s4;
				this.status5 = s5;	
		}
		
		//Konstruktor für IP Request
		public Container(int modus)
		{
				this.modus = modus;
		}
		
		//Getter und Setter
		public int getModus()
		{
				return modus;
		}
		public void setModus(int modus)
		{
				this.modus = modus;
		}
		public int[][] getKette1()
		{
				return kette1;
		}
		public void setKette1(int[][] kette1)
		{
				this.kette1 = kette1;
		}
		public int[][] getKette2()
		{
				return kette2;
		}
		public void setKette2(int[][] kette2)
		{
				this.kette2 = kette2;
		}
		public int[][] getKette3()
		{
				return kette3;
		}
		public void setKette3(int[][] kette3)
		{
				this.kette3 = kette3;
		}
		public int[][] getKette4()
		{
				return kette4;
		}
		public void setKette4(int[][] kette4)
		{
				this.kette4 = kette4;
		}
		public int[][] getKette5() 
		{
				return kette5;
		}
		public void setKette5(int[][] kette5) 
		{
				this.kette5 = kette5;
		}
		public boolean isStatus1()
		{
				return status1;
		}
		public void setStatus1(boolean status1)
		{
				this.status1 = status1;
		}
		public boolean isStatus2()
		{
				return status2;
		}
		public void setStatus2(boolean status2)
		{
				this.status2 = status2;
		}
		public boolean isStatus3()
		{
				return status3;
		}
		public void setStatus3(boolean status3)
		{
				this.status3 = status3;
		}
		public boolean isStatus4()
		{
				return status4;
		}
		public void setStatus4(boolean status4)
		{
				this.status4 = status4;
		}
		public boolean isStatus5()
		{
				return status5;
		}
		public void setStatus5(boolean status5)
		{
				this.status5 = status5;
		}
		public String getName()
		{
				return name;
		}
		public void setName(String name)
		{
				this.name = name;
		}
		
		public void setStandard()
		{
				this.modus = 1;
				this.name = "";
				this.status1 = true;
				this.status2 = false;
				this.status3 = false;
				this.status4 = false;
				this.status5 = false;
				
				int [][]temp = {{0,0,0,0,0}};
				kette1 = temp;
		}
		public void coppyContainer(Container c)
		{
				this.kette1 = c.getKette1();
				this.kette2 = c.getKette2();
				this.kette3 = c.getKette3();
				this.kette4 = c.getKette4();
				this.kette5 = c.getKette5();
				
				this.modus = c.getModus();
				this.name = c.getName();
				
				this.status1 = c.isStatus1();
				this.status2 = c.isStatus2();
				this.status3 = c.isStatus3();
				this.status4 = c.isStatus4();
				this.status5 = c.isStatus5();
				
		}
}