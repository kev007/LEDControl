/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.utils.Utilities;
import com.droidux.widget.action.ActionDrawer;

/**
 *
 */
public class ActionDrawerArrowHidingTest extends ActionDrawerListMenuTest {
    private final Rect mContentRect = new Rect();
    @Override
    public boolean onPrepareDrawerView(View view) {
        // calculate content's top coord
        View content = mDrawer.getContainer().findViewById(android.R.id.content);
        if (content != null) {
            mContentRect.set(0,0,content.getWidth(), content.getHeight());
            mDrawer.getContainer().offsetDescendantRectToMyCoords(content, mContentRect);
        }
        return super.onPrepareDrawerView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawer.setOnDrawingListener(new ActionDrawer.OnDrawingListener() {
            @Override
            public boolean onDrawerDrawing(Canvas canvas, float openRatio) {
                return false;
            }

            @Override
            public boolean onContentDrawing(Canvas canvas, float openRatio) {
                return false;
            }

            @Override
            public boolean onArrowDrawing(Canvas canvas, float openRatio, Rect arrowRect, Paint paint) {
                boolean transformed = false;
                float arrowTop = arrowRect.top;
                float arrowBottom = arrowRect.bottom;
                float contentTop = mContentRect.top;
                float contentBottom = mContentRect.bottom;
                if (arrowTop < contentTop) {
                    float ratio = Utilities.constrain(1f - ((contentTop - arrowTop) / arrowRect.height()), 0f, 1f);
                    canvas.scale(ratio,ratio,arrowRect.right,arrowRect.bottom);
                    paint.setAlpha((int) (ratio * 255));
                    transformed=true;
                } else if (arrowBottom > contentBottom) {
                    float ratio = Utilities.constrain(1f - ((arrowBottom - contentBottom) / arrowRect.height()), 0f, 1f);
                    canvas.scale(ratio,ratio,arrowRect.right,arrowRect.top);
                    paint.setAlpha((int) (ratio*255));
                    transformed=true;
                }
                return transformed;
            }
        });

    }


    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_listener_arrowhiding_desc;
    }
}