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
package com.droidux.components.demo.abs.misc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.SlidingTab;

public class SlidingTabTest extends BaseTestActivity implements SlidingTab.OnTriggerListener{
	private TextView mText1;
	private TextView mText2;
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_slidingtabtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_misc_slidingtab_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mText1 = (TextView) findViewById(R.id.text1);
		mText2 = (TextView) findViewById(R.id.text2);
		SlidingTab st = (SlidingTab) findViewById(R.id.slidingTab);
		st.setLeftTabResources(R.drawable.ic_jog_dial_answer,
			R.drawable.jog_tab_target_green,
			R.drawable.jog_tab_bar_left_answer, 
			R.drawable.jog_tab_left_answer);
		st.setRightTabResources(R.drawable.ic_jog_dial_decline, 
			R.drawable.jog_tab_target_red,
			R.drawable.jog_tab_bar_right_decline, 
			R.drawable.jog_tab_right_decline);
		st.setLeftHintText(R.string.text_slide_to_answer_hint);
		st.setRightHintText(R.string.text_slide_to_decline_hint);
		st.setOnTriggerListener(this);
	}

	@Override
	public void onTrigger(View v, int whichHandle) {
		String hintText;
		int hintColor;
		switch(whichHandle) {
			case SlidingTab.OnTriggerListener.LEFT_HANDLE:
				hintText = "Answered.";
				hintColor = Color.GREEN;
				break;
			case SlidingTab.OnTriggerListener.RIGHT_HANDLE:
				hintText = "Declined.";
				hintColor = Color.RED;
				break;
			default:
				hintText="default";
				hintColor=Color.WHITE;
				break;
		}
		mText1.setText(hintText);
		mText1.setTextColor(hintColor);
		
	}

	@Override
	public void onGrabbedStateChange(View v, int grabbedState) {
		String hintText;
		int hintColor;
		switch(grabbedState) {
			case SlidingTab.OnTriggerListener.NO_HANDLE:
				hintText=null;
				hintColor=0;
				break;
			case SlidingTab.OnTriggerListener.LEFT_HANDLE:
				hintText = "Drag right to answer";
				hintColor = Color.GREEN;
				break;
			case SlidingTab.OnTriggerListener.RIGHT_HANDLE:
				hintText = "Drag left to decline";
				hintColor = Color.RED;
				break;
			default:
				hintText=null;
				hintColor=0;
				break;
		}
		mText2.setText(hintText);
		mText2.setTextColor(hintColor);
	}
}
