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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {

	private Management m;
	private SeekBar seekBarR, seekBarG, seekBarB, seekBarL, seekBarT;
	private int keyframeCount = 0; 
	private int [][] tempKette = new int[255][5];
	private TextView TextTime;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RadioButton radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		RadioButton radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		RadioButton radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
		seekBarT = (SeekBar) findViewById(R.id.seekBarT);
		seekBarR = (SeekBar) findViewById(R.id.seekBarR);
		seekBarG = (SeekBar) findViewById(R.id.seekBarG);
		seekBarB = (SeekBar) findViewById(R.id.seekBarB);
		seekBarL = (SeekBar) findViewById(R.id.seekBarL);
		TextTime = (TextView) findViewById(R.id.TextTime);
		Button update = (Button) findViewById(R.id.update);
		Button broadcast = (Button) findViewById(R.id.broadcast);
		Button add = (Button) findViewById(R.id.add);
		
		/*
		radioButton1.setOnClickListener(this);
		radioButton2.setOnClickListener(this);
		radioButton3.setOnClickListener(this);
		radioButton4.setOnClickListener(this);
		radioButton5.setOnClickListener(this);*/
		seekBarT.setOnSeekBarChangeListener(this);
		seekBarR.setOnClickListener(this);
		seekBarG.setOnClickListener(this);
		seekBarB.setOnClickListener(this);
		seekBarL.setOnClickListener(this);
		
		update.setOnClickListener(this);
		broadcast.setOnClickListener(this);
		add.setOnClickListener(this);
		
		radioButton1.setChecked(true);
		radioButton2.setChecked(false);
		radioButton3.setChecked(false);
		radioButton4.setChecked(false);
		radioButton5.setChecked(false);

		seekBarT.setProgress(3000);
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
			*/
		
			case R.id.broadcast:
				m.sendPackage(m.createZeroContainer());
			break;
			case R.id.add:
				keyframeCount++;
				tempKette[0][4] = 0;
				tempKette[keyframeCount][4] = ((int) seekBarT.getProgress() + tempKette[keyframeCount-1][4]);
				tempKette[keyframeCount][0] = (int) seekBarR.getProgress();
				tempKette[keyframeCount][1] = (int) seekBarG.getProgress();
				tempKette[keyframeCount][2] = (int) seekBarB.getProgress();
				tempKette[keyframeCount][3] = (int) seekBarL.getProgress();
				
				System.out.println("T: " + tempKette[keyframeCount][4]);
				System.out.println("R: " + tempKette[keyframeCount][0]);
				System.out.println("G: " + tempKette[keyframeCount][1]);
				System.out.println("B: " + tempKette[keyframeCount][2]);
				System.out.println("L: " + tempKette[keyframeCount][3]);
			break;
			case R.id.update:
				int [][] kette1 = new int[keyframeCount][5];
				for (int i = 0; i<keyframeCount; i++){
					kette1[i][0] = tempKette[i+1][0];
					kette1[i][1] = tempKette[i+1][1];
					kette1[i][2] = tempKette[i+1][2];
					kette1[i][3] = tempKette[i+1][3];
					kette1[i][4] = tempKette[i+1][4];
				}
				
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		TextTime.setText(Integer.toString(seekBar.getProgress()) + " ms");
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
