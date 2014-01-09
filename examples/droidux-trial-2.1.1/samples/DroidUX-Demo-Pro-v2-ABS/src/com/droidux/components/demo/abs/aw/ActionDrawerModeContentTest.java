/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import com.droidux.components.demo.abs.R;
import com.droidux.widget.action.ActionDrawer;

/**
 *
 */
public class ActionDrawerModeContentTest extends ActionDrawerModeWindowTest implements ActionDrawer.Callback {
    @Override
    void buildDrawer() {
        mDrawer = new ActionDrawer.Builder(this, this)
                .setDrawerMode(ActionDrawer.DrawerMode.CONTENT_OVERLAY)
                .build();
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_content_desc;
    }
}