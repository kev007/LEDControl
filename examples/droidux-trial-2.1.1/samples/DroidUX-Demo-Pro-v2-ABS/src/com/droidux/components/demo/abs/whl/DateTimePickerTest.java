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
import com.droidux.interfaces.PickerInterfaces.Listeners.OnDateTimeChangedListener;
import com.droidux.interfaces.PickerInterfaces.Views.DateTimePickerInterface;
import com.droidux.widget.ColorButton;
import com.droidux.widget.color.ColorPickerAlertDialog;
import com.droidux.widget.wheel.DateTimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimePickerTest extends BaseTestActivity {
	private DateTimePicker mDtPicker;
	private ColorPickerAlertDialog mColorPicker;
	private ColorButton mDateColorButton;
	private ColorButton mTimeColorButton;
	private ColorButton mAmPmColorButton;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_datetimepickertest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_whl_datetimepicker_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Calendar cal = Calendar.getInstance();
		final DateTimePicker picker = mDtPicker = (DateTimePicker) findViewById(R.id.datetimePicker);
		mDateColorButton = (ColorButton) findViewById(R.id.dateColorButton);
		mTimeColorButton = (ColorButton) findViewById(R.id.timeColorButton);
		mAmPmColorButton = (ColorButton) findViewById(R.id.ampmColorButton);
		setupPickerAppearance();

		final TextView tv = (TextView)findViewById(R.id.text);
		final DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
		picker.setOnDateTimeChangedListener(new OnDateTimeChangedListener() {
			@Override
			public void onDateTimeChanged(DateTimePickerInterface picker, int year, int month, int day, int hour, int minute) {
				cal.set(year, month, day, hour, minute,00);
				tv.setText(formatter.format(cal.getTime()));
			}
		});


		picker.setSelectedDate(new Date());

	}

	private void setupPickerAppearance() {
		DateTimePicker picker = mDtPicker;
		int dayColor = mDateColorButton.getCurrentTintColor();
		if(dayColor != Color.TRANSPARENT) {
			picker.setDateWheelColor(mDateColorButton.getCurrentTintColor());
		}
		int monthColor =  mTimeColorButton.getCurrentTintColor();
		if(monthColor != Color.TRANSPARENT) {
			picker.setTimeWheelColor(mTimeColorButton.getCurrentTintColor());
		}
		int yearColor = mAmPmColorButton.getCurrentTintColor();
		if(yearColor != Color.TRANSPARENT) {
			picker.setAmPmWheelColor(mAmPmColorButton.getCurrentTintColor());
		}

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
					if(button == mDateColorButton) {
						mDtPicker.setDateWheelColor(color);
					} else if(button==mTimeColorButton) {
						mDtPicker.setTimeWheelColor(color);
					} else if(button==mAmPmColorButton) {
						mDtPicker.setAmPmWheelColor(color);
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
		if(button == mDateColorButton) {
			initialColor=mDtPicker.getDateWheelColor();
		} else if(button == mTimeColorButton) {
			initialColor=mDtPicker.getTimeWheelColor();
		} else if(button == mAmPmColorButton) {
			initialColor= mDtPicker.getAmPmWheelColor();
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
