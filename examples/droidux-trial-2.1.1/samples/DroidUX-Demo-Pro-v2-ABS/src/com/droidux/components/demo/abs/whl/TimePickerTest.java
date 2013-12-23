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
package com.droidux.components.demo.abs.whl;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.PickerInterfaces.Listeners.OnTimeChangedListener;
import com.droidux.interfaces.PickerInterfaces.Views.TimePickerInterface;
import com.droidux.widget.wheel.TimePicker;

import java.util.Random;

public class TimePickerTest extends BaseTestActivity {
	private final Random mRand = new Random();
	private TimePicker mPicker;
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_timepickertest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_whl_timepicker_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mPicker = (TimePicker)findViewById(R.id.datePicker);

		final TextView tv = (TextView)findViewById(R.id.text);
		mPicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePickerInterface picker, int hour, int minute) {
				tv.setText(String.format("Hour: %s, Minute: %s", hour, minute));
			}
		});

		mPicker.updateTime(11, 23);
	}

	public void doRandomTime(View target) {
		int hour = mRand.nextInt(24);
		int minute = mRand.nextInt(60);
		mPicker.updateTime(hour, minute, true);
	}
}
