/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
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
import com.droidux.widget.appbar.EnhancedColorAppBar;
import com.droidux.widget.color.ColorPickerPanel;

public class EnhancedColorAppBarTest extends BaseTestActivity {

	private EnhancedColorAppBar mTestAppBar;
	private Handler mHandler = new Handler();
	private ColorPickerPanel mPicker;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_enhancedcolorappbartest;
	}
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTestAppBar = (EnhancedColorAppBar)findViewById(R.id.test_appbar_color);
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
				mTestAppBar.setTintColor(color);
			}
		});

		picker.setColor(0xFF57FFBB);
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_appbar_enhanced_desc;
    }

    @Override
	protected void onResume() {
		super.onResume();
		mTestAppBar.setTintColor(mPicker.getColor());
	}
	private OnActionListener mActionListener = new OnActionListener() {

		@Override
		public void onAction(View v, ActionItem action) {
			mTestAppBar.showProgress(true);
			String title = action.getTitle();
			if(TextUtils.isEmpty(title)) {
				title = "[No Title]";
			}
			Toast.makeText(EnhancedColorAppBarTest.this, String.format("%s action is clicked.", title), Toast.LENGTH_SHORT).show();
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mTestAppBar.showProgress(false);
				}

			}, 3000);
		}
	};
}
