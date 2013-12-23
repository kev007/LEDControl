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
import com.droidux.widget.RotarySelector;

public class RotarySelectorTest extends BaseTestActivity implements RotarySelector.OnDialTriggerListener{
	private TextView mText1;
	private TextView mText2;
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_rotaryselectortest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_misc_rotaryselector_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mText1 = (TextView) findViewById(R.id.text1);
		mText2 = (TextView) findViewById(R.id.text2);
		RotarySelector rs = (RotarySelector) findViewById(R.id.rotary);
		rs.setLeftHandleResource(R.drawable.ic_jog_dial_answer);
		rs.setRightHandleResource(R.drawable.ic_jog_dial_decline);
		rs.setOnDialTriggerListener(this);
	}

	@Override
	public void onGrabbedStateChange(View v, int grabbedState) {
		String hintText;
		int hintColor;
		switch(grabbedState) {
			case RotarySelector.OnDialTriggerListener.LEFT_HANDLE:
				hintText = "Rotate CW to answer";
				hintColor = Color.GREEN;
				break;
			case RotarySelector.OnDialTriggerListener.RIGHT_HANDLE:
				hintText = "Rotate CCW to decline";
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

	@Override
	public void onDialTrigger(View v, int whichHandle) {
		String hintText;
		int hintColor;
		switch(whichHandle) {
			case RotarySelector.OnDialTriggerListener.LEFT_HANDLE:
				hintText = "Answered.";
				hintColor = Color.GREEN;
				break;
			case RotarySelector.OnDialTriggerListener.RIGHT_HANDLE:
				hintText = "Declined.";
				hintColor = Color.RED;
				break;
			default:
				hintText=null;
				hintColor=0;
				break;
		}
		mText1.setText(hintText);
		mText1.setTextColor(hintColor);
	}
}
