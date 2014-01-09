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
package com.droidux.components.demo.abs.edtr;

import android.view.View;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.interfaces.ColorPickerInterfaces.Listeners.OnColorChangedListener;
import com.droidux.widget.color.ColorPickerPanel;
import com.droidux.widget.editor.BubbleTextView;

public class BubbleTextViewTest extends BaseTestActivity {
	private BubbleTextView mBtv1;
	private BubbleTextView mBtv2;
	private ColorPickerPanel mPicker;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_bubbletextviewtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_edtr_bubbletext_desc;
    }

    @Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBtv1 = (BubbleTextView) findViewById(R.id.bubbletext1);
		mBtv2 = (BubbleTextView) findViewById(R.id.bubbletext2);
		mPicker = (ColorPickerPanel) findViewById(R.id.picker);
		mPicker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(View view, int color) {
				mBtv1.setBubbleColor(color);
				mBtv2.setBubbleColor(color);
			}
		});

		mPicker.setColor(0xFFF7825C);
	}

	@Override
	protected void onResume() {
		super.onResume();
		int color = mPicker.getColor();
		mBtv1.setBubbleColor(color);
		mBtv2.setBubbleColor(color);
	}
}
