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
package com.droidux.components.demo.abs.glrflw;

import android.os.Bundle;
import android.widget.SpinnerAdapter;
import com.droidux.components.demo.abs.R;
import com.droidux.interfaces.GalleryFlowInterfaces.Adapters.AdapterLooper;
import com.droidux.interfaces.GalleryFlowInterfaces.GalleryFlowViewInterface;

public class GalleryFlowCarouselInTests extends GalleryFlowViewTestsBase{
	private AdapterLooper mAdapterLooper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((GalleryFlowViewInterface)mGalleryFlow).setMaxFlingVelocity(1500);
		mGalleryFlow.setSelection(mAdapterLooper.getCenterPosition());
	}
	@Override
	protected int getLayoutId() {
		return R.layout.activity_gallerycarouselviewtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_glrflow_crslin_desc;
    }

    @Override
	SpinnerAdapter createAdapter() {
		mAdapterLooper = new AdapterLooper(new ImageAdapter(this, mImages));
		return mAdapterLooper;
	}

}
