package com.example.android.actionbarcompat;

import android.app.Application;

import com.droidux.components.DroidUxLib;

public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// initialize the DroidUX library
		DroidUxLib.register(this);

	}
}
