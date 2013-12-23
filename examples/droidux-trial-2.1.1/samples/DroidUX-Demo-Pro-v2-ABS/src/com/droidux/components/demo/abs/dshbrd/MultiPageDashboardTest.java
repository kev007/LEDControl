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
package com.droidux.components.demo.abs.dshbrd;

import android.view.View;
import android.widget.Toast;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.widget.dashboard.MultiPageDashboard;

public class MultiPageDashboardTest extends BaseTestActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_multipagedashboardtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_dshbrd_multipage_desc;
    }

    @Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		OnActionListener l = new OnActionListener() {
			@Override
			public void onAction(View v, ActionItem action) {
				Toast.makeText(MultiPageDashboardTest.this, String.format("Action [%s] is clicked.", action.getTitle()), Toast.LENGTH_SHORT).show();
			}
		};

		MultiPageDashboard dbrd = (MultiPageDashboard)findViewById(R.id.dashboard);
		dbrd.beginLayout()
			.addAction(new ActionItem().setTitle("Behance").setIcon(R.drawable.ic_social_behance).setOnActionListener(l))
			.addAction(new ActionItem().setTitle("Digg").setIcon(R.drawable.ic_social_digg).setOnActionListener(l))
			.addAction(new ActionItem().setTitle("Facebook").setIcon(R.drawable.ic_social_facebook).setOnActionListener(l))
			.addAction(new ActionItem().setTitle("LinkedIn").setIcon(R.drawable.ic_social_linkedin).setOnActionListener(l))
			.addAction(new ActionItem().setTitle("RSS").setIcon(R.drawable.ic_social_rss).setOnActionListener(l))
			.addAction(new ActionItem().setTitle("StumbleUpon").setIcon(R.drawable.ic_social_stumbleupon).setOnActionListener(l))
			.addAction(new ActionItem("Twitter").setIcon(R.drawable.ic_social_twitter).setOnActionListener(l))
			.addAction(new ActionItem("Vimeo").setIcon(R.drawable.ic_social_vimeo).setOnActionListener(l))
			.addAction(new ActionItem("YouTube").setIcon(R.drawable.ic_social_youtube).setOnActionListener(l))
			.endLayout();
	}

}
