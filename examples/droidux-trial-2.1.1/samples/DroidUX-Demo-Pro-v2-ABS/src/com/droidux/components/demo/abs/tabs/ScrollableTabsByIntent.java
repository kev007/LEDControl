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

import android.content.Intent;
import android.os.Bundle;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestScrollableTabActivity;
import com.droidux.widget.tabs.ScrollableTabHost;

public class ScrollableTabsByIntent extends BaseTestScrollableTabActivity {
	@Override
	protected int getLayoutId() {
		return R.layout.activity_scrollabletabtests;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_tabs_scroll_byintent_desc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ScrollableTabHost tabHost = getTabHost();

        tabHost.addTab(tabHost.newTabSpec("contact")
    	   	.setIndicator("Contact", getResources().getDrawable(R.drawable.ic_qact_contact))
                .setContent(ScrollableTab_ListContent.getListIntent(this, 0)));

	    tabHost.addTab(tabHost.newTabSpec("calendar")
           	.setIndicator("Calendar", getResources().getDrawable(R.drawable.ic_qact_calendar))
                .setContent(ScrollableTab_TextContent.getTextIntent(this, 0)));

        // This tab sets the intent flag so that it is recreated each time
        // the tab is clicked.
	    tabHost.addTab(tabHost.newTabSpec("chart")
            .setIndicator("Chart", getResources().getDrawable(R.drawable.ic_qact_chart))
                .setContent(ScrollableTab_ListContent.getListIntent(this, 1)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

	    tabHost.addTab(tabHost.newTabSpec("shop")
            .setIndicator("Shopping List", getResources().getDrawable(R.drawable.ic_qact_shop))
                .setContent(ScrollableTab_TextContent.getTextIntent(this, 1)));

	    tabHost.addTab(tabHost.newTabSpec("chat")
            .setIndicator("Chat", getResources().getDrawable(R.drawable.ic_qact_chat))
                .setContent(ScrollableTab_TextContent.getTextIntent(this, 2)));

	    tabHost.addTab(tabHost.newTabSpec("search")
            .setIndicator("Search", getResources().getDrawable(R.drawable.ic_qact_search))
            .setContent(ScrollableTab_ListContent.getListIntent(this, 2)));
    }
}
