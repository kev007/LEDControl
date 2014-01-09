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

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.components.demo.abs.R;
import com.droidux.widget.action.QuickTooltip;

public abstract class BaseTestScrollableTabActivity extends __SherlockScrollableTabActivity {
    private QuickTooltip mTooltip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ActivityHelper.getActivityTitle(this));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_help, menu);
        // help
        final int descriptionRes = getDescriptionRes();
        if (descriptionRes != 0) {
            MenuItem helpMenu = menu.findItem(R.id.menu_help);
            ImageView help = (ImageView)helpMenu.getActionView().findViewById(R.id.image);
            help.setImageResource(R.drawable.ic_action_help);
            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTooltip = ActivityHelper.setupDescriptionTooltip(BaseTestScrollableTabActivity.this, mTooltip, descriptionRes);
                    mTooltip.show(v);
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return ActivityHelper.goHome(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTooltip != null) {
            mTooltip.dismiss();
            mTooltip=null;
        }
    }

    protected int getDescriptionRes() {
        return 0;
    }
	protected abstract int getLayoutId();
}
