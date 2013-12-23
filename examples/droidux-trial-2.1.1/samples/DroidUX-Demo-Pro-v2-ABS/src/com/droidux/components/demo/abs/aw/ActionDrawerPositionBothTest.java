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
public class ActionDrawerPositionBothTest extends ActionDrawerModeWindowTest implements ActionDrawer.Callback {
    ActionDrawer mRightDrawer;
    @Override
    void buildDrawer() {
        super.buildDrawer();
        mRightDrawer = new ActionDrawer.Builder(this, this)
                .setDrawerPosition(ActionDrawer.DrawerPosition.RIGHT)
                .build();

        // We don't want the other drawer to be activated when one drawer is open.
        mDrawer.setOnDrawerStateChangeListener(new ActionDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                mRightDrawer.setDrawerEnabled(newState == ActionDrawer.STATE_DRAWER_CLOSED);
            }
        });
        mRightDrawer.setOnDrawerStateChangeListener(new ActionDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                mDrawer.setDrawerEnabled(newState == ActionDrawer.STATE_DRAWER_CLOSED);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.menu_rightdrawer, menu);
        return true;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_leftright_desc;
    }

    @Override
    public void onBackPressed() {
        if (mRightDrawer != null && mRightDrawer.isDrawerVisible()) {
            mRightDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_rightdrawer:
                mRightDrawer.toggleDrawer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}