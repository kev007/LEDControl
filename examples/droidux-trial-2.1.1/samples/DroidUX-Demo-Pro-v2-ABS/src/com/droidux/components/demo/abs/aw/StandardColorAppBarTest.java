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
package com.droidux.components.demo.abs.aw;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorChangedListener;
import com.droidux.widget.appbar.StandardColorAppBar;
import com.droidux.widget.color.ColorPickerPanel;

public class StandardColorAppBarTest extends BaseTestActivity {
	private StandardColorAppBar mTestAppBar;
	private Handler mHandler = new Handler();
	private ColorPickerPanel mPicker;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_standardcolorappbartest;
	}
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTestAppBar = (StandardColorAppBar)findViewById(R.id.test_appbar_color);
		mTestAppBar.beginLayout()
			.setTitleAction(new ActionItem().setIcon(R.drawable.ic_launcher).setTitle("Title").setOnActionListener(mActionListener))
			.setProgressAction(new ActionItem().setTitle("Refresh").setIcon(R.drawable.ic_appbar_refresh).setOnActionListener(mActionListener))
			.addAction(new ActionItem().setTitle("Home").setIcon(R.drawable.ic_appbar_home).setOnActionListener(mActionListener), true)
			.addAction(new ActionItem().setTitle("Camera").setIcon(R.drawable.ic_appbar_camera).setOnActionListener(mActionListener))
			.addAction(new ActionItem().setTitle("Search").setIcon(R.drawable.ic_appbar_search).setOnActionListener(mActionListener))
			.endLayout();

		final ColorPickerPanel picker = mPicker = (ColorPickerPanel) findViewById(R.id.picker);
		picker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(View view, int color) {
				mTestAppBar.setBackgroundColor(color);
			}
		});

		picker.setColor(0xFF8E2800);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mTestAppBar.setBackgroundColor(mPicker.getColor());
	}
	private OnActionListener mActionListener = new OnActionListener() {

		@Override
		public void onAction(View v, ActionItem action) {
			mTestAppBar.showProgress(true);
			String title = action.getTitle();
			if(TextUtils.isEmpty(title)) {
				title = "[No Title]";
			}
			Toast.makeText(StandardColorAppBarTest.this, String.format("%s action is clicked.", title), Toast.LENGTH_SHORT).show();
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mTestAppBar.showProgress(false);
				}

			}, 3000);
		}
	};
}
