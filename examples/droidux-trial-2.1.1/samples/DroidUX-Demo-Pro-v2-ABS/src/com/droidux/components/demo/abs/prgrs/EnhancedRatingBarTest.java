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
import android.widget.RatingBar;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.progress.EnhancedRatingBar;

/**
 * Demonstrates how to use an enhanced rating bar
 */
public class EnhancedRatingBarTest extends BaseTestActivity implements RatingBar.OnRatingBarChangeListener {
    EnhancedRatingBar mRatingBar1;
    EnhancedRatingBar mRatingBar2;
    EnhancedRatingBar mSmallRatingBar;
    EnhancedRatingBar mIndicatorRatingBar;
    TextView mRatingText;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_enhancedratingbartest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_prgrs_ratingbar_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRatingText = (TextView) findViewById(R.id.rating);

        mRatingBar1 = (EnhancedRatingBar) findViewById(R.id.ratingbar1);
        mRatingBar2 = (EnhancedRatingBar) findViewById(R.id.ratingbar2);
        // We copy the most recently changed rating on to these indicator-only
        // rating bars
        mIndicatorRatingBar = (EnhancedRatingBar) findViewById(R.id.indicator_ratingbar);
        mSmallRatingBar = (EnhancedRatingBar) findViewById(R.id.small_ratingbar);

        // The different rating bars in the layout. Assign the listener to us.
        mRatingBar1.setOnRatingBarChangeListener(this);
        mRatingBar2.setOnRatingBarChangeListener(this);
    }

    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromTouch) {
        final int numStars = ratingBar.getNumStars();
        mRatingText.setText( "Rating: " + rating + "/" + numStars);

        // Since this rating bar is updated to reflect any of the other rating
        // bars, we should update it to the current values.
        if (mIndicatorRatingBar.getNumStars() != numStars) {
            mIndicatorRatingBar.setNumStars(numStars);
            mSmallRatingBar.setNumStars(numStars);
        }
        final float ratingBarStepSize = ratingBar.getStepSize();
        if (mIndicatorRatingBar.getStepSize() != ratingBarStepSize) {
            mIndicatorRatingBar.setStepSize(ratingBarStepSize);
            mSmallRatingBar.setStepSize(ratingBarStepSize);
        }
        if (mIndicatorRatingBar.getRating() != rating) {
            mIndicatorRatingBar.setRating(rating);
            mSmallRatingBar.setRating(rating);
        }
    }
}
