/*
 * Copyright (C) 2011-2012 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by
 * international copyright laws and treaty provisions.
 * Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement
 * accompanying this product.
 */
package com.droidux.components.demo.abs;

import android.app.Application;
import com.droidux.components.DroidUxLib;

public class DroidUXApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// initialize the DroidUX library
		DroidUxLib.register("enter-your-api-key-here", this);
	}
}
