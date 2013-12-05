package com.example.android.actionbarcompat;

import com.droidux.components.DroidUxLib;

import android.app.Application;

public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// initialize the DroidUX library
		DroidUxLib.register(this);

	}
}
