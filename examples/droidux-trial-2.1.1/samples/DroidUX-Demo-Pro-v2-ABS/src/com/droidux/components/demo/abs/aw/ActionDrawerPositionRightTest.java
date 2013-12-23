/*******************************************************************************
 * Copyright (C) 2011 Ximpl
 * All Rights Reserved. This program and the accompanying materials
 * are owned by Ximpl or its suppliers.  The program is protected by international copyright laws and treaty provisions.  Any violation will be prosecuted under applicable laws.
 * NOTICE: The following is Source Code and is subject to all
 * restrictions on such code as contained in the End User License Agreement accompanying this product.
 ******************************************************************************/
package com.droidux.components.demo.abs.aw;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.widget.action.ActionDrawer;

/**
 *
 */
public class ActionDrawerPositionRightTest extends ActionDrawerModeWindowTest implements ActionDrawer.Callback {
    @Override
    void buildDrawer() {
        mDrawer = new ActionDrawer.Builder(this, this)
                .setDrawerPosition(ActionDrawer.DrawerPosition.RIGHT)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.menu_rightdrawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_rightdrawer:
                mDrawer.toggleDrawer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_right_desc;
    }
}