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
import android.os.Handler;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.widget.progress.EnhancedProgressBar;


/**
 * Demonstrates how to use progress bars as widgets and in the title bar.  The progress bar
 * in the title will be shown until the progress is complete, at which point it fades away.
 */
public class EnhancedProgressIndeterminateTest extends BaseTestActivity {
	private static final int DELTA = 5;
	private static int sDelta =DELTA;
	private static int sCounter = 0;
	private EnhancedProgressBar mPLarge;
	private EnhancedProgressBar mPMed;
	private EnhancedProgressBar mPSmall;
	private EnhancedProgressBar mPHoriz;
	private final Handler mHandler = new Handler();
	@Override
	protected int getLayoutId() {
		return R.layout.activity_enhancedprogressindeterminatetest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_prgrs_bar_indeterminate_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPLarge = (EnhancedProgressBar) findViewById(R.id.progressLarge);
		mPMed = (EnhancedProgressBar) findViewById(R.id.progressMedium);
		mPSmall = (EnhancedProgressBar) findViewById(R.id.progressSmall);
		mPHoriz = (EnhancedProgressBar) findViewById(R.id.progressHorizontal);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateColors();
	}
	private void updateColors() {
		sCounter += sDelta;
		if(sCounter<0) {
			sCounter=0;
			sDelta = DELTA;
		} else if(sCounter>100) {
			sCounter=100;
			sDelta = -DELTA;
		}
		double ratio = Utilities.restrict((double) sCounter / 100.0, 0.0, 1.0);
		Helper.setIndeterminateProgressColor(mPLarge, Helper.COLOR1, Helper.COLOR2, ratio);
		Helper.setIndeterminateProgressColor(mPMed, Helper.COLOR3, Helper.COLOR4, ratio);
		Helper.setIndeterminateProgressColor(mPSmall, Helper.COLOR5, Helper.COLOR6, ratio);
		Helper.setIndeterminateProgressColor(mPHoriz, Helper.COLOR6, Helper.COLOR7, ratio);
		mHandler.postDelayed(mUpdateColorTask,150);
	}

	private Runnable mUpdateColorTask = new Runnable() {
		public void run() {
			updateColors();
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateColorTask);
	}
}
