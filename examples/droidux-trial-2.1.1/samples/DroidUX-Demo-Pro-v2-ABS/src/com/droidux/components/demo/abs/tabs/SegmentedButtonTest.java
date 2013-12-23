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
package com.droidux.components.demo.abs.tabs;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.widget.tabs.SegmentedButtonGroup;

public class SegmentedButtonTest extends BaseTestActivity implements OnActionListener {

	@Override
	protected int getLayoutId() {
		return R.layout.activity_segmentedbuttonttest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_tabs_segment_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionItem chartAction = new ActionItem("Chart").setIcon(R.drawable.ic_qact_chart).setOnActionListener(this);
		ActionItem contactAction = new ActionItem("Contact").setIcon(R.drawable.ic_qact_contact).setOnActionListener(this);
		ActionItem scheduleAction = new ActionItem("Schedule").setIcon(R.drawable.ic_qact_calendar).setOnActionListener(this);
		// text only
		SegmentedButtonGroup sb = (SegmentedButtonGroup) findViewById(R.id.segmentedButtons1);
		sb.beginLayout()
			.addAction(chartAction)
			.addAction(contactAction)
			.addAction(scheduleAction)
		.endLayout();
//		sb.check(chartAction);

		// images only
		sb = (SegmentedButtonGroup) findViewById(R.id.segmentedButtons2);
		sb.beginLayout()
			.addAction(chartAction)
			.addAction(contactAction)
			.addAction(scheduleAction)
		.endLayout();

		// auto
		sb = (SegmentedButtonGroup) findViewById(R.id.segmentedButtons3);
		// setup the layout
		sb.beginLayout()
			.addAction(chartAction)
			.addAction(new ActionItem("Contact").setOnActionListener(this))
			.addAction(new ActionItem().setIcon(R.drawable.ic_qact_calendar).setOnActionListener(this))
		.endLayout();

	}

	@Override
	public void onAction(View v, ActionItem action) {
		String title = action.getTitle();
		if(TextUtils.isEmpty(title)) {
			title="NoText";
		}
		Toast.makeText(this, String.format("[%s] is selected.", title), Toast.LENGTH_SHORT).show();
	}
}
