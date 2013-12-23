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
package com.droidux.components.demo.abs.lyt;

import android.os.Bundle;
import com.droidux.components.demo.abs.R;


public class RoundedCornerShortTest extends RoundedCornerLongTest {

	@Override
	protected int getLayoutId() {
		return R.layout.activity_roundedcornershorttest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_lyt_rounded_short_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		mCount = 2;
		super.onCreate(savedInstanceState);
	}
}