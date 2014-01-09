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

import android.widget.ProgressBar;

import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.widget.progress.CircularProgressBar;
import com.droidux.widget.progress.EnhancedProgressBar;
import com.droidux.widget.progress.EnhancedRatingBar;
import com.droidux.widget.progress.EnhancedSeekBar;

class Helper {
    static final int COLOR1 = 0xFFB6DB49;
    static final int COLOR2 = 0xFFFF4444;
    static final int COLOR3 = 0xFFF9FF4F;
    static final int COLOR4 = 0xFF4FFFAA;
    static final int COLOR5 = 0XFF4FCDFF;
    static final int COLOR6 = 0XFFFF4FE5;
    static final int COLOR7 = 0xFFFFFAD5;

    static void setProgressColor(ProgressBar progressBar, int startColor, int endColor) {
		int max = progressBar.getMax();

		if(progressBar instanceof EnhancedProgressBar) {
			EnhancedProgressBar epb = (EnhancedProgressBar)progressBar;
			// default progress
			int progress = epb.getProgress();
			epb.setProgressColor(Utilities.calcMixColor(startColor, endColor, (double) progress / (double) max));

			// secondary progress
			progress = progressBar.getSecondaryProgress();
			epb.setSecondaryProgressColor(Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max));
		} else if(progressBar instanceof EnhancedSeekBar) {
			EnhancedSeekBar esb = (EnhancedSeekBar)progressBar;
			// default progress
			int progress = esb.getProgress();
			int progressColor = Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max);
			esb.setProgressColor(progressColor);
			// thumb
			esb.setThumbColor(progressColor);

			// secondary progress
			progress = progressBar.getSecondaryProgress();
			esb.setSecondaryProgressColor(Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max));

		} else if(progressBar instanceof CircularProgressBar) {
			CircularProgressBar cpb = (CircularProgressBar)progressBar;
			// default progress
			int progress = cpb.getProgress();
			cpb.setProgressColor(Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max));

			// secondary progress
			progress = progressBar.getSecondaryProgress();
			cpb.setSecondaryProgressColor(Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max));
		} else if(progressBar instanceof EnhancedRatingBar) {
			EnhancedRatingBar erb = (EnhancedRatingBar)progressBar;
			// default progress
			int progress = erb.getProgress();
			erb.setProgressColor(Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max));

			// secondary progress
			progress = progressBar.getSecondaryProgress();
			erb.setSecondaryProgressColor(Utilities.calcMixColor(startColor, endColor, (double)progress/(double)max));
		}

	}
	static void setIndeterminateProgressColor(EnhancedProgressBar progressBar, int startColor, int endColor, double ratio) {
		progressBar.setIndeterminateProgressColor(Utilities.calcMixColor(startColor, endColor, ratio));
	}
}
