package com.droidux.tutorials;

import com.droidux.components.DroidUxLib;

import android.app.Application;

public class DuxTutorialApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		DroidUxLib.register("enter-your-api-key-here", this);
	}
}
