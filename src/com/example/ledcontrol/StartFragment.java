package com.example.ledcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StartFragment extends Fragment implements android.view.View.OnClickListener {
	
	private Button button1;
	
	public static Fragment newInstance(Context context) {
        StartFragment f = new StartFragment();
        
        
        return f;
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.start_fragment, container, false);
		
		//button1 = (Button) findViewById(R.id.button1);
		//button1.setOnClickListener(this);
	}

	public void onClick(View v) {
		Log.d(getTag(), "Button test complete");
		
	}
}
