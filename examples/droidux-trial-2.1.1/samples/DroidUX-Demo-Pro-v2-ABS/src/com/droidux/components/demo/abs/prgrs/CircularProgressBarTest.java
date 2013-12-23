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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.ColorButton;
import com.droidux.widget.progress.CircularProgressBar;


public class CircularProgressBarTest extends BaseTestActivity {
    private CircularProgressBar mProgressBar;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_circularprogressbartest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_prgrs_circular_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressBar = (CircularProgressBar) findViewById(R.id.progress_circular);
        ColorButton button = (ColorButton) findViewById(R.id.decrease);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mProgressBar.incrementProgressBy(-5);
                updateColor();
            }
        });

        button = (ColorButton) findViewById(R.id.increase);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementProgressBy(5);
        		updateColor();
            }
        });

        button = (ColorButton) findViewById(R.id.increase_secondary);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementSecondaryProgressBy(5);
        		updateColor();
            }
        });

        button = (ColorButton) findViewById(R.id.decrease_secondary);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	mProgressBar.incrementSecondaryProgressBy(-5);
        		updateColor();
            }
        });

        CheckBox chk = (CheckBox) findViewById(R.id.chkCustomDrawables);
        chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				applyCustomDrawables();
			}
		});

    }

	private void applyCustomDrawables() {

        CheckBox chk = (CheckBox) findViewById(R.id.chkCustomDrawables);
        if(chk.isChecked()) {
        	mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_circ));
        } else {
        	mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.dux_prgrs_circ));
        }
		updateColor();
	}
	private void updateColor() {
    	Helper.setProgressColor(mProgressBar, Helper.COLOR1, Helper.COLOR2);
	}
	@Override
	protected void onResume() {
		super.onResume();
		applyCustomDrawables();
	}


}
