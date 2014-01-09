/*
 * Copyright (C) 2011 Ximpl
 * Copyright 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.actionbarcompat;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.droidux.interfaces.ActionInterfaces.Item.ActionItem;
import com.droidux.interfaces.ActionInterfaces.Item.ActionItem.OnActionListener;
import com.droidux.interfaces.ActionInterfaces.Layout.AppBarLayoutInterface;
import com.droidux.widget.appbar.StandardAppBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that implements the action bar pattern for pre-Honeycomb devices, using DroidUX AppBar widgets.
 */
public class ActionBarHelperDux extends ActionBarHelperBase implements OnActionListener {

    protected ActionBarHelperDux(Activity activity) {
        super(activity);
    }


    /**{@inheritDoc}*/
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        mActivity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.inc_appbar);

        List<MenuActionItem> menuActions = new ArrayList<MenuActionItem>();

        SimpleMenu menu = new SimpleMenu(mActivity);
        // add action items
        // home action
        SimpleMenuItem homeItem = new SimpleMenuItem(
            menu, android.R.id.home, 0, mActivity.getString(R.string.app_name));
        menuActions.add((MenuActionItem) (new MenuActionItem(homeItem).setIcon(R.drawable.ic_home).setOnActionListener(this)));
        // title action
        menuActions.add((MenuActionItem) new MenuActionItem(null, true).setTitle((String) mActivity.getTitle()).setOnActionListener(this));

        mActivity.onCreatePanelMenu(Window.FEATURE_OPTIONS_PANEL, menu);
        mActivity.onPrepareOptionsMenu(menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (mActionItemIds.contains(item.getItemId())) {
                menuActions.add((MenuActionItem) new MenuActionItem(item).setIcon(item.getIcon()).setOnActionListener(this));
            }
        }
        setupActionBar(menuActions);
    }

    /**
     * Here is where we setup the action bar.
     */
    private void setupActionBar(List<MenuActionItem> menuActions) {
        final StandardAppBar appBar = getActionBarCompat();
        if (appBar == null || menuActions==null || menuActions.size()==0) {
            return;
        }
        AppBarLayoutInterface layout = appBar.beginLayout();
        Iterator<MenuActionItem> iter = menuActions.iterator();
        int count = menuActions.size();
        for (int i = 0; i < count; i++) {
        	MenuActionItem  action = iter.next();
        	MenuItem menuItem = action.menuItem;
        	if(action.isTitle) {
        		layout.setTitleAction(action);
        	} else if(menuItem!=null && menuItem.getItemId()==R.id.menu_refresh) {
        		layout.setProgressAction(action);
        	} else {
        		layout.addAction(action, menuItem!=null && menuItem.getItemId()==android.R.id.home);
        	}
		}
        layout.endLayout();
    }
    /**{@inheritDoc}*/
	@Override
	public void onAction(View v, ActionItem action) {
		if(action instanceof MenuActionItem) {
			MenuItem menuItem = ((MenuActionItem)action).menuItem;
			if(menuItem != null) {
                mActivity.onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, menuItem);
			}
		}
	}

    /**{@inheritDoc}*/
    @Override
    public void setRefreshActionItemState(boolean refreshing) {
    	StandardAppBar appBar = getActionBarCompat();
    	appBar.showProgress(refreshing);
    }


    /**{@inheritDoc}*/
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
    	StandardAppBar appBar = getActionBarCompat();
    	if(appBar!=null) {
    		appBar.setTitle(title);
    	}
    }


    /**
     * Returns the {@link com.droidux.widget.appbar.StandardAppbar} for the action bar on phones (compatibility action
     * bar). Can return null, and will return null on Honeycomb.
     */
    private StandardAppBar getActionBarCompat() {
        return (StandardAppBar) mActivity.findViewById(R.id.appbar);
    }

    private static class MenuActionItem extends ActionItem {
    	final MenuItem menuItem;
    	final boolean isTitle;
    	MenuActionItem(MenuItem menuItem) {
    		this(menuItem, false);
    	}
    	MenuActionItem(MenuItem menuItem, boolean isTitle) {
    		this.menuItem = menuItem;
    		this.isTitle=isTitle;
    	}
    }

}
