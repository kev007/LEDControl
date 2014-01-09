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
package com.droidux.components.demo.abs.clrpkr;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorSelectedListener;
import com.droidux.widget.color.ColorPickerPanel;

public class ColorPickerPanelTests extends BaseTestActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_colorpickertests;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_clrpkr_panel_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ColorPickerPanel picker = (ColorPickerPanel) findViewById(R.id.picker);
		picker.showAlphaPanel(true);
		picker.setColor(0x80A3b3c2);

		picker.setOnColorSelectedListener(new OnColorSelectedListener() {

			@Override
			public void onColorSelected(View sender, int color) {
				picker.setColor(color);
				Toast.makeText(ColorPickerPanelTests.this, "Color accepted: " + Utilities.colorToHex(color), Toast.LENGTH_SHORT).show();
			}
		});

		final TextView text1 = (TextView) findViewById(R.id.text1);
		if(picker.isPortrait()) {
			text1.setText(R.string.instruct_test_clrpkr_panel_portrait);
		} else {
			text1.setText(R.string.instruct_test_clrpkr_panel_landscape);
		}

	}
}
