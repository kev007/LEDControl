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
package com.droidux.components.demo.abs.drwbl;

import android.os.Bundle;
import android.widget.ImageView;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.graphics.drawable.ReflectionDrawable;

/**
 *
 */
public class ReflectionDrawableTest extends BaseTestActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv = (ImageView) findViewById(R.id.image1);
        iv.setImageDrawable(new ReflectionDrawable(getResources().getDrawable(R.drawable.happy_girl)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reflectiondrawabletest;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_drwbl_reflection_desc;
    }
}