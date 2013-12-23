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
import com.droidux.components.demo.abs.internal.ActivityHelper;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.widget.appbar.StandardAppBar;

public class StandardAppBarTest extends BaseTestActivity {
	private StandardAppBar mAppBarLogo;
	private StandardAppBar mAppBarText;
	private Handler mHandler = new Handler();

	@Override
	protected int getLayoutId() {
		return R.layout.activity_standardappbartest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_appbar_standard_desc;
    }

    @Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// logo title
		mAppBarLogo = (StandardAppBar)findViewById(R.id.test_appbar_logo);
		mAppBarLogo.beginLayout()
			.setTitleAction(new ActionItem().setIcon(R.drawable.ic_launcher).setTitle(ActivityHelper.getActivityTitle(this)).setOnActionListener(mActionListenerLogo))
			.setProgressAction(null)
			.addAction(new ActionItem().setTitle("Camera").setIcon(R.drawable.ic_appbar_camera).setOnActionListener(mActionListenerLogo))
			.addAction(new ActionItem().setTitle("Search").setIcon(R.drawable.ic_appbar_search).setOnActionListener(mActionListenerLogo))
			.endLayout();

		// text title
		mAppBarText = (StandardAppBar)findViewById(R.id.test_appbar_text);
		mAppBarText.beginLayout()
			.setTitleAction(new ActionItem().setTitle(ActivityHelper.getActivityTitle(this)).setOnActionListener(mActionListenerText))
			.setProgressAction(new ActionItem().setTitle("Refresh").setIcon(R.drawable.ic_action_refresh_default).setOnActionListener(mActionListenerText))
			.addAction(new ActionItem().setTitle("Home").setIcon(R.drawable.ic_appbar_home_default).setOnActionListener(mActionListenerText), true)
			.addAction(new ActionItem().setTitle("Camera").setIcon(R.drawable.ic_action_camera_default).setOnActionListener(mActionListenerText))
			.addAction(new ActionItem().setTitle("Search").setIcon(R.drawable.ic_action_search_default).setOnActionListener(mActionListenerText))
			.endLayout();

	}

	private OnActionListener mActionListenerLogo = new OnActionListener() {

		@Override
		public void onAction(View v, ActionItem action) {
			mAppBarLogo.showProgress(true);
			String title = action.getTitle();
			if(TextUtils.isEmpty(title)) {
				title = "Logo title";
			}
			Toast.makeText(StandardAppBarTest.this, String.format("%s action is clicked.", title), Toast.LENGTH_SHORT).show();
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mAppBarLogo.showProgress(false);
				}

			}, 3000);
		}
	};
	private OnActionListener mActionListenerText = new OnActionListener() {

		@Override
		public void onAction(View v, ActionItem action) {
			mAppBarText.showProgress(true);
			String title = action.getTitle();
			if(TextUtils.isEmpty(title)) {
				title = "Logo title";
			}
			Toast.makeText(StandardAppBarTest.this, String.format("%s action is clicked.", title), Toast.LENGTH_SHORT).show();
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					mAppBarText.showProgress(false);
				}

			}, 3000);
		}
	};
}
