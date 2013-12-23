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

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.components.demo.abs.internal.BaseTestFragmentActivity;
import com.droidux.widget.action.ActionDrawer;

/**
 *
 */
public class ActionDrawerFragmentStaticTest extends BaseTestFragmentActivity implements ActionDrawer.Callback {
    ActionDrawer mDrawer;
    private static final String STATE_ACTION_DRAWER = "com.droidux.components.demo.state_action_drawer";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildDrawer();
    }

    void buildDrawer() {
        mDrawer = new ActionDrawer.Builder(this, this)
                .build();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_actiondrawertest;
    }

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_actn_drawer_fragment_static_desc;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.toggleDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateDrawerView() {
        return LayoutInflater.from(this).inflate(R.layout.drawer_fragment, null);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerVisible()) {
            mDrawer.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onPrepareDrawerView(View view) {
        view.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_ACTION_DRAWER, mDrawer.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Parcelable state = savedInstanceState.getParcelable(STATE_ACTION_DRAWER);
        mDrawer.onRestoreInstanceState(state);
    }
}