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
package com.droidux.components.demo.abs.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestScrollableTabActivity;
import com.droidux.widget.tabs.ScrollableTabHost;

public class ScrollableTabsById extends BaseTestScrollableTabActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_scrollabletabtests;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_tabs_scroll_byid_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollableTabHost tabHost = getTabHost();
        tabHost.setFlingable(true);

        LayoutInflater.from(this).inflate(R.layout.scrollabletabtests_content, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("contact")
                .setIndicator("Contact", getResources().getDrawable(R.drawable.ic_qact_contact))
                .setContent(R.id.view1));
        tabHost.addTab(tabHost.newTabSpec("calendar")
                .setIndicator("Calendar", getResources().getDrawable(R.drawable.ic_qact_calendar))
                .setContent(R.id.view2));
        tabHost.addTab(tabHost.newTabSpec("chart")
                .setIndicator("Chart", getResources().getDrawable(R.drawable.ic_qact_chart))
                .setContent(R.id.view3));
        tabHost.addTab(tabHost.newTabSpec("shop")
                .setIndicator("Shopping List", getResources().getDrawable(R.drawable.ic_qact_shop))
                .setContent(R.id.view4));
        tabHost.addTab(tabHost.newTabSpec("chat")
                .setIndicator("Chat", getResources().getDrawable(R.drawable.ic_qact_chat))
                .setContent(R.id.view5));
        tabHost.addTab(tabHost.newTabSpec("search")
                .setIndicator("Search", getResources().getDrawable(R.drawable.ic_qact_search))
                .setContent(R.id.view6));
 
    }
}
