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

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorDialogListener;
import com.droidux.widget.color.ColorBox;
import com.droidux.widget.color.ColorPickerAlertDialog;

public class ColorPickerDialogTests extends BaseTestActivity {
	private ColorPickerAlertDialog mDlg;
	private ColorBox mCbox;
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_colorpickerdialogtests;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_clrpkr_dialog_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ColorBox cbox = mCbox = (ColorBox) findViewById(R.id.colorBox);
		mDlg = new ColorPickerAlertDialog(this, cbox.getColor(), new OnColorDialogListener() {

			@Override
			public void onColorSelected(DialogInterface dialog, int color) {
				cbox.setColor(color);
			}

			@Override
			public void onColorChanged(DialogInterface dialog, int color) {
			}
		});
	}

	public void selectColor(View target) {
		mDlg.setColor(mCbox.getColor());
		mDlg.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mDlg != null) {
			mDlg.dismiss();
			mDlg = null;
		}
	}
}
