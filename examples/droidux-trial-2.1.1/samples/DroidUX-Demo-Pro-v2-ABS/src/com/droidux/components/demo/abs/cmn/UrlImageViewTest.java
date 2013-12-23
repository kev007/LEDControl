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

package com.droidux.components.demo.abs.cmn;

import android.os.Bundle;
import android.view.View;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.droidux.cache.UrlImageCache;
import com.droidux.components.demo.abs.R;
import com.droidux.components.demo.abs.internal.BaseTestActivity;
import com.droidux.widget.UrlImageView;

public class UrlImageViewTest extends BaseTestActivity {
	private static final int ID_MENU_CLEAR_CACHE = Menu.FIRST+1;

	private UrlImageView mImage;
	@Override
	protected int getLayoutId() {
		return R.layout.activity_urlimageviewtest;
	}

    @Override
    protected int getDescriptionRes() {
        return R.string.activity_cmn_urlimgview_desc;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImage = (UrlImageView)findViewById(R.id.imageView);
		mImage.setImageResource(R.drawable.placeholder_port);
	}

	public void buttonClicked(View sender) {

		mImage.setImageUrl("http://www.droidux.com/images/droidux/apidemos_v2/port/redkite.jpg",
			R.drawable.placeholder_port, R.drawable.placeholder_port_err);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, ID_MENU_CLEAR_CACHE, Menu.NONE, "Clear Cache");
		return true;

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case ID_MENU_CLEAR_CACHE:
				clearImageCache();
				mImage.setImageResource(R.drawable.placeholder_port);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void clearImageCache() {
		// clear downloaded images from the cache
		UrlImageCache cache = mImage.getImageCache();
		if(cache != null) {
			cache.clear();
		}
	}

}
