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

package com.droidux.components.demo.abs.prgrs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.progress.EnhancedSeekBar;


/**
 * Demonstrates how to use an enhanced seek bar
 */
public class EnhancedSeekBarTest extends BaseTestActivity implements SeekBar.OnSeekBarChangeListener {

    EnhancedSeekBar mSeekBar;
    TextView mProgressText;
    TextView mTrackingText;
    Bitmap mProgressPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSeekBar = (EnhancedSeekBar)findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView)findViewById(R.id.progress);
        mTrackingText = (TextView)findViewById(R.id.tracking);

        mProgressPattern = BitmapFactory.decodeResource(getResources(), R.drawable.pattern_stripe);

        final CheckBox chkAnimatePattern = (CheckBox) findViewById(R.id.chkAnimatePattern);
        chkAnimatePattern.setChecked(false);
        chkAnimatePattern.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        applyProgressPattern();
			}
		});
       CheckBox chk = (CheckBox) findViewById(R.id.chkPattern);
        chk.setChecked(true);
        applyProgressPattern();
        chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				applyProgressPattern();
			}
		});
   }
	private void applyProgressPattern() {
		CheckBox chkPatternProgress = (CheckBox) findViewById(R.id.chkPattern);
		CheckBox chkAnimatePattern = (CheckBox) findViewById(R.id.chkAnimatePattern);
		Bitmap pattern = chkPatternProgress.isChecked()?mProgressPattern:null;
		mSeekBar.setProgressPatternOverlay(pattern, chkAnimatePattern.isChecked());
	}

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        mProgressText.setText(progress + " from touch =" + fromTouch);
		updateColor();
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        mTrackingText.setText("Tracking on");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        mTrackingText.setText("Tracking off");
    }

	@Override
	protected int getLayoutId() {
		return R.layout.activity_enhancedseekbartest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_prgrs_seekbar_desc;
    }

    private void updateColor() {
    	Helper.setProgressColor(mSeekBar, Helper.COLOR1, Helper.COLOR2);
	}
	@Override
	protected void onResume() {
		super.onResume();
		updateColor();
		applyProgressPattern();
	}

}
