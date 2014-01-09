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

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.progress.EnhancedProgressBar;


public class EnhancedProgressBarTest extends BaseTestActivity {


	private EnhancedProgressBar mProgressBar;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_enhancedprogressbartest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_prgrs_bar_color_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressBar = (EnhancedProgressBar) findViewById(R.id.progress_horizontal);

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
    }

	private void updateColor() {
    	Helper.setProgressColor(mProgressBar, Helper.COLOR1, Helper.COLOR2);
	}
	@Override
	protected void onResume() {
		super.onResume();
		updateColor();
	}


}
