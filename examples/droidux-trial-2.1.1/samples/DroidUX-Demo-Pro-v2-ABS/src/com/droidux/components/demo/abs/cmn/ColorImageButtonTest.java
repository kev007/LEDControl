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
package com.droidux.components.demo.abs.cmn;

import android.view.View;
import android.widget.Toast;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorChangedListener;
import com.droidux.widget.ColorImageButton;
import com.droidux.widget.color.ColorPickerPanel;

public class ColorImageButtonTest extends BaseTestActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_colorimagebuttontest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_cmn_clrimgbtn_desc;
    }

    protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ColorImageButton cbtn = (ColorImageButton) findViewById(R.id.colorButton);
		final ColorPickerPanel picker = (ColorPickerPanel) findViewById(R.id.picker);
		picker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(View view, int color) {
				cbtn.setTintColor(color);
			}
		});

		picker.setColor(0xFFEFECCA);
	}

	public void buttonClicked(View sender) {
		Toast.makeText(this, "The ColorImageButton is clicked.", Toast.LENGTH_SHORT).show();
	}
}