package com.example.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class MainActivity extends Activity implements OnClickListener {

	private Management m;
	private SeekBar seekBarR, seekBarG, seekBarB, seekBarL;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		RadioButton radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
		seekBarR = (SeekBar) findViewById(R.id.seekBarR);
		seekBarG = (SeekBar) findViewById(R.id.seekBarG);
		seekBarB = (SeekBar) findViewById(R.id.seekBarB);
		seekBarL = (SeekBar) findViewById(R.id.seekBarL);
		Button update = (Button) findViewById(R.id.update);
		Button broadcast = (Button) findViewById(R.id.broadcast);
		
		/*
		radioButton1.setOnClickListener(this);
		radioButton2.setOnClickListener(this);
		radioButton3.setOnClickListener(this);
		radioButton4.setOnClickListener(this);
		radioButton5.setOnClickListener(this);
		seekBarR.setOnClickListener(this);
		seekBarG.setOnClickListener(this);
		seekBarB.setOnClickListener(this);
		seekBarL.setOnClickListener(this);
		*/
		update.setOnClickListener(this);
		broadcast.setOnClickListener(this);
		
		radioButton1.setChecked(true);
		radioButton2.setChecked(false);
		radioButton3.setChecked(false);
		radioButton4.setChecked(false);
		radioButton5.setChecked(false);
		seekBarR.setProgress(128);
		seekBarG.setProgress(128);
		seekBarB.setProgress(128);
		seekBarL.setProgress(100);
		
		createManagement();
        sendBroadcast();
        m.sendPackage(m.createZeroContainer());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		/*
			case R.id.radioButton1:
				
			break;
			case R.id.radioButton2:
				
			break;
			case R.id.radioButton3:
				
			break;
			case R.id.radioButton4:
				
			break;
			case R.id.radioButton5:
				
			break;
			case R.id.seekBarR:
				
			break;
			case R.id.seekBarG:
				
			break;
			case R.id.seekBarB:
				
			break;
			case R.id.seekBarL:
				
			break;
		*/
			case R.id.broadcast:
				m.sendPackage(m.createZeroContainer());
			break;
			case R.id.update:
				int [][] kette1 = new int[1][5];
				kette1[0][0] = (int) seekBarR.getProgress();
				kette1[0][1] = (int) seekBarG.getProgress();
				kette1[0][2] = (int) seekBarB.getProgress();
				kette1[0][3] = (int) seekBarL.getProgress();
				kette1[0][4] = 0;
				/*
				kette1[1][0] = seekBarR.getProgress();
				kette1[1][1] = seekBarG.getProgress();
				kette1[1][2] = seekBarB.getProgress();
				kette1[1][3] = seekBarL.getProgress();
				kette1[1][4] = 1000;
				*/
				System.out.println("R: " + seekBarR.getProgress());
				System.out.println("G: " + seekBarG.getProgress());
				System.out.println("B: " + seekBarB.getProgress());
				System.out.println("L: " + seekBarL.getProgress());
				m.sendPackage(new Container(1, kette1,true, null,false, null,false, null,false, null,false));
			break;
			
		}
	}

    private void sendBroadcast() {
    	m.sendPackage(m.createZeroContainer());
    	Log.d("management", "send broadcast");
	}

	private void createManagement() {
    	//Kostanten
		int PORT = 4501;
		//String ADDRESS = "127.0.0.1";
		String ADDRESS ="255.255.255.255" ;
		//String ADDRESS ="192.168.1.100" ;
		//String ADDRESS ="10.0.2.2" ;
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
		m = new Management(BROAD_ADDRESS, PORT);
		Log.d("management", "create management");
	}
}
