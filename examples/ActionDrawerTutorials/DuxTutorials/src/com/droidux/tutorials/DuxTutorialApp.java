package com.droidux.tutorials;

import android.app.Application;

import com.droidux.components.DroidUxLib;

public class DuxTutorialApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		DroidUxLib.register("enter-your-api-key-here", this);
	}
}
