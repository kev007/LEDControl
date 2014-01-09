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

package com.droidux.components.demo.abs.internal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

public class ComposePathDrawable extends ShapeDrawable {
	private static final float[] RADII = new float[] { 12, 12, 12, 12, 0, 0, 0, 0 };
    public ComposePathDrawable() {
        super(new RoundRectShape(RADII, null, null));
        PathEffect pe = new DiscretePathEffect(10, 4);
        PathEffect pe2 = new CornerPathEffect(4);
        getPaint().setPathEffect(new ComposePathEffect(pe2, pe));
        getPaint().setColor(Color.RED);
    }

    @Override protected void onDraw(Shape s, Canvas c, Paint p) {
        s.draw(c, p);
    }
}