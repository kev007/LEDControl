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

import com.droidux.components.demo.abs.R;
import com.droidux.widget.gallery.GalleryFlowWall;

public class GalleryFlowWallOutTests extends GalleryFlowWallInTests{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GalleryFlowWall gw = (GalleryFlowWall)mGalleryFlow;	
		gw.setViewPoint(GalleryFlowWall.VIEW_POINT_OUTSIDE);
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_glrflow_wallout_desc;
    }
}
