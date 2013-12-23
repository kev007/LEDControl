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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.droidux.components.demo.abs.R;
import com.droidux.widget.tabs.ScrollableTabHost;
import com.droidux.widget.tabs.ScrollableTabWidget;

/**
 *
 */
class __SherlockScrollableTabActivity extends __SherlockActivityGroup {
    private ScrollableTabHost mTabHost;
    private String mDefaultTab = null;
    private int mDefaultTabIndex = -1;

    public __SherlockScrollableTabActivity() {
    }

    public void setDefaultTab(String tag) {
        mDefaultTab = tag;
        mDefaultTabIndex = -1;
    }

    public void setDefaultTab(int index) {
        mDefaultTab = null;
        mDefaultTabIndex = index;
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        ensureTabHost();
        String cur = state.getString(STRING_CURRENT_TAB);
        if (cur != null) {
            mTabHost.setCurrentTabByTag(cur);
        }
        if (mTabHost.getCurrentTab() < 0) {
            if (mDefaultTab != null) {
                mTabHost.setCurrentTabByTag(mDefaultTab);
            } else if (mDefaultTabIndex >= 0) {
                mTabHost.setCurrentTab(mDefaultTabIndex);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle icicle) {
        super.onPostCreate(icicle);

        ensureTabHost();

        if (mTabHost.getCurrentTab() == -1) {
            mTabHost.setCurrentTab(0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String currentTabTag = mTabHost.getCurrentTabTag();
        if (currentTabTag != null) {
            outState.putString(STRING_CURRENT_TAB, currentTabTag);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mTabHost = (ScrollableTabHost) findViewById(android.R.id.tabhost);

        if (mTabHost == null) {
            throw new RuntimeException(STRING_ID_REQUIRED);
        }
        mTabHost.setup(getLocalActivityManager());
    }

    private void ensureTabHost() {
        if (mTabHost == null) {
            this.setContentView(R.layout.dux_scrltb_content);
        }
    }

    @Override
    protected void
    onChildTitleChanged(Activity childActivity, CharSequence title) {
        // Dorky implementation until we can have multiple activities running.
        if (getLocalActivityManager().getCurrentActivity() == childActivity) {
            View tabView = mTabHost.getCurrentTabView();
            if (tabView != null && tabView instanceof TextView) {
                ((TextView) tabView).setText(title);
            }
        }
    }

    public ScrollableTabHost getTabHost() {
        ensureTabHost();
        return mTabHost;
    }

    public ScrollableTabWidget getTabWidget() {
        return mTabHost.getTabWidget();
    }

    // String resources
    private static final String STRING_CURRENT_TAB = "currentTab";
    private static final String STRING_ID_REQUIRED = "Your content must have a TabHost whose id attribute is 'android.R.id.tabhost'" ;


}
