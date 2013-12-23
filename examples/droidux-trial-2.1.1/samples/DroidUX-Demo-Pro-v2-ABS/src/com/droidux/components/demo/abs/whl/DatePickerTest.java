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

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorDialogListener;
import com.droidux.interfaces.PickerInterfaces.Listeners.OnDateChangedListener;
import com.droidux.interfaces.PickerInterfaces.Views.DatePickerInterface;
import com.droidux.widget.ColorButton;
import com.droidux.widget.color.ColorPickerAlertDialog;
import com.droidux.widget.wheel.DatePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerTest extends BaseTestActivity {

	private DatePicker mPicker;
	private ColorPickerAlertDialog mColorPicker;
	private ColorButton mDayColorButton;
	private ColorButton mMonthColorButton;
	private ColorButton mYearColorButton;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_datepickertest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_whl_datepicker_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Calendar cal = Calendar.getInstance();
		DatePicker picker = mPicker = (DatePicker)findViewById(R.id.datePicker);
		mDayColorButton = (ColorButton) findViewById(R.id.dayColorButton);
		mMonthColorButton = (ColorButton) findViewById(R.id.monthColorButton);
		mYearColorButton = (ColorButton) findViewById(R.id.yearColorButton);

		final TextView tv = (TextView)findViewById(R.id.text);
		final DateFormat formatter = DateFormat.getDateInstance(DateFormat.LONG);
		picker.setOnDateChangedListener(new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePickerInterface picker, int year, int month, int day) {
				cal.set(year, month, day);
				tv.setText(formatter.format(cal.getTime()));
			}
		});


//		picker.setSelectedDate(new Date(103,3,23));
//		cal.setTime(picker.getSelectedDate());
		mPicker.updateDate(2011, 4, 23);
	}

	public void changeColors(View sender) {
		if(!(sender instanceof ColorButton)) {
			return;
		}
		final ColorButton button = (ColorButton) sender;
		ColorPickerAlertDialog dlg = mColorPicker = new ColorPickerAlertDialog(this, getInitialColor(button),
			new OnColorDialogListener() {

				@Override
				public void onColorSelected(DialogInterface dialog, int color) {
					button.setTintColor(color);
					if(button == mDayColorButton) {
						mPicker.setDayWheelColor(color);
					} else if(button==mMonthColorButton) {
						mPicker.setMonthWheelColor(color);
					} else if(button==mYearColorButton) {
						mPicker.setYearWheelColor(color);
					}
				}

				@Override
				public void onColorChanged(DialogInterface dialog, int color) {
					// nothing

				}
			});
		dlg.showAlphaPanel(false);
		dlg.show();
	}
	private int getInitialColor(ColorButton button) {
		int initialColor=Color.WHITE;
		if(button == mDayColorButton) {
			initialColor=mPicker.getDayWheelColor();
		} else if(button == mMonthColorButton) {
			initialColor=mPicker.getMonthWheelColor();
		} else if(button == mYearColorButton) {
			initialColor= mPicker.getYearWheelColor();
		}
		return initialColor;

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mColorPicker != null) {
			mColorPicker.dismiss();
			mColorPicker = null;
		}
	}
}
