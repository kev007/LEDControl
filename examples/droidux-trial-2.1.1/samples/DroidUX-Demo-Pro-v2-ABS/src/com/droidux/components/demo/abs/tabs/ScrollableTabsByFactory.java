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
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestScrollableTabActivity;
import com.droidux.widget.tabs.ScrollableTabHost;

public class ScrollableTabsByFactory extends BaseTestScrollableTabActivity implements ScrollableTabHost.TabContentFactory {

	@Override
	protected int getLayoutId() {
		return R.layout.activity_scrollabletabtests;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_tabs_scroll_byfactory_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ScrollableTabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("contact")
                .setIndicator("Contact", getResources().getDrawable(R.drawable.ic_qact_contact))
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("calendar")
                .setIndicator("Calendar", getResources().getDrawable(R.drawable.ic_qact_calendar))
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("chart")
                .setIndicator("Chart", getResources().getDrawable(R.drawable.ic_qact_chart))
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("shop")
                .setIndicator("Shopping List", getResources().getDrawable(R.drawable.ic_qact_shop))
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("chat")
                .setIndicator("Chat", getResources().getDrawable(R.drawable.ic_qact_chat))
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("search")
                .setIndicator("Search", getResources().getDrawable(R.drawable.ic_qact_search))
                .setContent(this));
    }

    /** {@inheritDoc} */
    public View createTabContent(String tag) {
        final TextView tv = new TextView(this);
        tv.setTextSize(12);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(0xff222526);
        String format = getString(R.string.text_content_scrltb);
        tv.setText(String.format(format, tag));
        return tv;
    }
}
