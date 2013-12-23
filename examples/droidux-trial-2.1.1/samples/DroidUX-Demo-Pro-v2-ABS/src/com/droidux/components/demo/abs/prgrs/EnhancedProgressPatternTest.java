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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.progress.EnhancedProgressBar;


public class EnhancedProgressPatternTest extends BaseTestActivity {

	private EnhancedProgressBar mProgressBar;
	private Bitmap mProgressPattern;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_enhancedprogresspatterntest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_prgrs_bar_pattern_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressBar = (EnhancedProgressBar) findViewById(R.id.progress_horizontal);
        mProgressBar.setClipProgress(false);
        mProgressBar.setClipSecondaryProgress(false);

        Button button = (Button) findViewById(R.id.increase);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementProgressBy(5);
        		updateColor();
            }
        });

        button = (Button) findViewById(R.id.decrease);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementProgressBy(-5);
        		updateColor();
            }
        });

        button = (Button) findViewById(R.id.increase_secondary);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementSecondaryProgressBy(5);
        		updateColor();
            }
        });

        button = (Button) findViewById(R.id.decrease_secondary);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementSecondaryProgressBy(-5);
        		updateColor();
            }
        });

        mProgressPattern = BitmapFactory.decodeResource(getResources(), R.drawable.pattern_stripe);

        final CheckBox chkAnimatePattern = (CheckBox) findViewById(R.id.chkAnimatePattern);
        chkAnimatePattern.setChecked(false);
        chkAnimatePattern.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				applyProgressPattern();
				applySecondaryProgressPattern();
			}
		});

        CheckBox chk = (CheckBox) findViewById(R.id.chkPatternProgress);
        chk.setChecked(true);
        chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		       applyProgressPattern();
			}
		});

        chk = (CheckBox) findViewById(R.id.chkPatternSecondProgress);
        chk.setChecked(false);
        chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        applySecondaryProgressPattern();
			}
		});
    }
	private void applyProgressPattern() {
		CheckBox chkPatternProgress = (CheckBox) findViewById(R.id.chkPatternProgress);
		CheckBox chkAnimatePattern = (CheckBox) findViewById(R.id.chkAnimatePattern);
		Bitmap pattern = chkPatternProgress.isChecked()?mProgressPattern:null;
		mProgressBar.setProgressPatternOverlay(pattern, chkAnimatePattern.isChecked());
	}
	private void applySecondaryProgressPattern() {
		CheckBox chkPatternSecondProgress = (CheckBox) findViewById(R.id.chkPatternSecondProgress);
		CheckBox chkAnimatePattern = (CheckBox) findViewById(R.id.chkAnimatePattern);
		Bitmap pattern = chkPatternSecondProgress.isChecked()?mProgressPattern:null;
		mProgressBar.setSecondaryProgressPatternOverlay(pattern, chkAnimatePattern.isChecked(), 10);
	}
	private void updateColor() {
    	Helper.setProgressColor(mProgressBar, Helper.COLOR1, Helper.COLOR2);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateColor();
		applyProgressPattern();
		applySecondaryProgressPattern();
	}

}
