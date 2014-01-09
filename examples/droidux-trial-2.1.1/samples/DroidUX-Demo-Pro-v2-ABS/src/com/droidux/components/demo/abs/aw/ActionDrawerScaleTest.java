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
package com.droidux.components.demo.abs.aw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import com.droidux.components.demo.abs.R;
import com.droidux.widget.action.ActionDrawer;

/**
 *
 */
public class ActionDrawerScaleTest extends ActionDrawerListMenuTest {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawer.setDrawerFadeEnabled(false);
        mDrawer.setParallaxEnabled(false);
        mDrawer.setOnDrawingListener(new ActionDrawer.OnDrawingListener() {
            @Override
            public boolean onDrawerDrawing(Canvas canvas, float openRatio) {
                canvas.scale(openRatio, 1, 0, 0);
                return true;
            }

            @Override
            public boolean onContentDrawing(Canvas canvas, float openRatio) {
                return false;
            }

            @Override
            public boolean onArrowDrawing(Canvas canvas, float openRatio, Rect arrowRect, Paint paint) {
                return false;
            }
        });

    }


    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_listener_drawerscale_desc;
    }
}